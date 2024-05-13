package carpet.mixins.protocol.client;

import carpet.network.CarpetClient;
import carpet.network.ClientNetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.handler.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.network.packet.s2c.play.LoginS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC<=10809
//$$ import java.io.IOException;
//#endif

//#if MC<=10710
//$$ import net.minecraft.network.PacketByteBuf;
//$$ import io.netty.buffer.Unpooled;
//#endif

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandler_syncLoggingAndPayload {
	@Shadow
	private Minecraft minecraft;

	@Inject(method = "handleLogin", at = @At("RETURN"))
	private void onJoin(LoginS2CPacket loginS2CPacket, CallbackInfo ci) {
		CarpetClient.gameJoined(minecraft.player);
		ClientNetworkHandler.lockedClientPlayer.lock();
		try {
			ClientNetworkHandler.clientPlayerLoaded.signal();
		} finally {
			ClientNetworkHandler.lockedClientPlayer.unlock();
		}
	}

	@Inject(method = "onDisconnect", at = @At(value = "HEAD"))
	private void onDisconnect(Text text, CallbackInfo ci) {
		CarpetClient.disconnect();
	}

	@Inject(method = "handleCustomPayload", at = @At("HEAD"), cancellable = true)
	private void onCustomPayload(CustomPayloadS2CPacket customPayloadS2CPacket, CallbackInfo ci) {
		//#if MC>11202
//$$ 		if (CarpetClient.CARPET_CHANNEL.equals(customPayloadS2CPacket.getChannel())) {
		//#else
		if (CarpetClient.CARPET_CHANNEL.toString().equals(customPayloadS2CPacket.getChannel())) {
		//#endif
			//#if MC<=10710
			//$$
			//#elseif MC<=10809
			//$$ try {
			//#endif
				//#if MC>10710
				NbtCompound nbt = customPayloadS2CPacket.getData().readNbtCompound();
				//#else
				//$$ NbtCompound nbt = new PacketByteBuf(Unpooled.wrappedBuffer(customPayloadS2CPacket.getData())).readNbtCompound();
				//#endif
				if (nbt != null) {
					ClientNetworkHandler.onServerData(nbt, minecraft.player);
				}
				ci.cancel();
			//#if MC<=10710
			//$$
			//#elseif MC<=10809
			//$$ } catch (IOException ignored) {}
			//#endif
		}
	}
}
