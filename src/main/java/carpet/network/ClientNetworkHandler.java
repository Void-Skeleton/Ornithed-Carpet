package carpet.network;

import carpet.server.CarpetExtension;
import carpet.server.CarpetServer;
import carpet.settings.CarpetSettings;
import carpet.settings.rule.CarpetRule;
import carpet.settings.rule.InvalidRuleValueException;
import carpet.settings.SettingsManager;
import carpet.tick.TickContext;
import io.netty.buffer.Unpooled;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;

import net.minecraft.nbt.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;

public class ClientNetworkHandler {
    private static final Map<String, BiConsumer<LocalClientPlayerEntity, NbtElement>> dataHandlers = new HashMap<>();
	public static Lock lockedClientPlayer = new ReentrantLock();
	public static Condition clientPlayerLoaded = lockedClientPlayer.newCondition();

	// NbtNumber is package-private, but I had to do an instanceof call with it
	// At least I can do that reflectively
	private static final Class<?> NBT_NUMBER = NbtInt.class.getSuperclass();
	private static final Method GET_FLOAT;

	static {
		Method getFloat = null;
		for (Method method : NBT_NUMBER.getDeclaredMethods()) {
			if (method.getReturnType() == float.class && method.getParameterTypes().length == 0) {
				getFloat = method; break;
			}
		}
		if (getFloat != null) {
			GET_FLOAT = getFloat;
		} else {
			throw new AssertionError("Unable to locate getFloat() in NbtNumber");
		}
	}

    static {
        dataHandlers.put(CarpetClient.HI, (p, t) -> onHi((NbtString) t));
        dataHandlers.put("Rules", (p, t) -> {
            NbtCompound ruleset = (NbtCompound) t;
            for (String ruleKey : ruleset.getKeys()) {
				NbtCompound ruleNBT = (NbtCompound) ruleset.get(ruleKey);
                SettingsManager manager = null;
                String ruleName;
                if (ruleNBT.contains("Manager")) {
                    ruleName = ruleNBT.getString("Rule");
                    String managerName = ruleNBT.getString("Manager");
                    if (managerName.equals("carpet")) {
                        manager = CarpetServer.settingsManager;
                    } else {
                        for (CarpetExtension extension : CarpetServer.extensions) {
                            SettingsManager eManager = extension.extensionSettingsManager();
                            if (eManager != null && managerName.equals(eManager.identifier())) {
                                manager = eManager;
                                break;
                            }
                        }
                    }
                } else {
                    manager = CarpetServer.settingsManager;
                    ruleName = ruleKey;
                }
                CarpetRule<?> rule = (manager != null) ? manager.getCarpetRule(ruleName) : null;
                if (rule != null) {
                    String value = ruleNBT.getString("Value");
                    try {
                        rule.set(null, value);
                    } catch (InvalidRuleValueException ignored) { }
                }
            }
        });
		dataHandlers.put("TickRate", (p, t) -> {
			if (NBT_NUMBER.isAssignableFrom(t.getClass())) {
				float tickRate;
				try {
					tickRate = (Float) GET_FLOAT.invoke(t, new Object[0]);
				} catch (IllegalAccessException | InvocationTargetException e) {
					throw new AssertionError(e);
				}
				TickContext.CLIENT_CONTEXT.nanosPerTick = (long) (1.0e9f / tickRate);
			}
		});
		dataHandlers.put("TickingState", (p, t) -> {
			if (!(t instanceof NbtCompound)) return;
			// Who thought it would be a good idea to name one of the protocol keys
			//  using lower camel and another using underscores
			// Old fabric carpet use deepFreeze to freeze chunk ticket expiry
			// This is 1.12 with no chunk tickets, so no
			if (((NbtCompound) t).getBoolean("deepFreeze"));
			TickContext.CLIENT_CONTEXT.frozen = (boolean) ((NbtCompound) t).getBoolean("is_frozen");
		});
		dataHandlers.put("SuperHotState", (p, t) -> {
			TickContext.CLIENT_CONTEXT.superHot = ((t instanceof NbtByte) && ((NbtByte) t).getByte() > 0);
		});
        dataHandlers.put("clientCommand", (p, t) -> CarpetClient.onClientCommand(t));
    }

    // Ran on the Main Minecraft Thread

    private static void onHi(NbtString versionElement) {
        CarpetClient.setCarpetServer();
        CarpetClient.serverCarpetVersion = versionElement.asString();
        if (CarpetSettings.carpetVersion.equals(CarpetClient.serverCarpetVersion)) {
            CarpetSettings.LOG.info("Joined carpet server with matching carpet version");
        } else {
            CarpetSettings.LOG.warn("Joined carpet server with another carpet version: " + CarpetClient.serverCarpetVersion);
        }
        // We can ensure that this packet is
        // processed AFTER the player has joined
        respondHello();
    }

    public static void respondHello() {
        NbtCompound data = new NbtCompound();
        data.putString(CarpetClient.HELLO, CarpetSettings.carpetVersion);
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeNbtCompound(data);
		// TODO wait for clientPlayer != null
		if (CarpetClient.getClientPlayer() == null) {
			lockedClientPlayer.lock();
		}
		try {
			while (CarpetClient.getClientPlayer() == null) {
				clientPlayerLoaded.await();
			}
		} catch (InterruptedException ignored) {
		} finally {
			lockedClientPlayer.lock();  // TODO unsure...
            lockedClientPlayer.unlock();
        }
        CarpetClient.getClientPlayer().networkHandler.sendPacket(new CustomPayloadC2SPacket(
			//#if MC>11202
//$$ 			CarpetClient.CARPET_CHANNEL,
			//#else
			CarpetClient.CARPET_CHANNEL.toString(),
			//#endif
			buf
		));
    }

    public static void onServerData(NbtCompound compound, LocalClientPlayerEntity player) {
        for (Object k : compound.getKeys()) {
			String key = (String) k;
            if (dataHandlers.containsKey(key)) {
                try {
                    dataHandlers.get(key).accept(player, compound.get(key));
                } catch (Exception exc) {
                    CarpetSettings.LOG.info("Corrupt carpet data for " + key);
                }
            } else {
                CarpetSettings.LOG.error("Unknown carpet data: " + key);
            }
        }
    }

    public static void clientCommand(String command) {
		NbtCompound tag = new NbtCompound();
        tag.putString("id", command);
        tag.putString("command", command);
		NbtCompound outer = new NbtCompound();
        outer.put("clientCommand", tag);
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeNbtCompound(outer);
		CarpetClient.getClientPlayer().networkHandler.sendPacket(new CustomPayloadC2SPacket(
			//#if MC>11202
//$$ 			CarpetClient.CARPET_CHANNEL,
			//#else
			CarpetClient.CARPET_CHANNEL.toString(),
			//#endif
			buf
		));
    }
}
