package carpet.mixins.tick.freeze;

import carpet.tick.TickContext;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import jdk.internal.org.objectweb.asm.Opcodes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServer_freeze {
	@Unique
	private static final TickContext CONTEXT = TickContext.SERVER_CONTEXT;

	@WrapWithCondition(method = "tick", at = @At(value = "FIELD",
		target = "Lnet/minecraft/server/MinecraftServer;ticks:I", opcode = 181 /* PUTFIELD */))
	public boolean wrapServerTickUpdate(MinecraftServer instance, int value) {
		return !CONTEXT.frozen;
	}

	@WrapWithCondition(method = "tick", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/server/MinecraftServer;saveWorlds(Z)V"))
	public boolean wrapAutosave(MinecraftServer instance, boolean silent) {
		return !CONTEXT.frozen;
	}

	@WrapWithCondition(method = "tickWorlds", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;tickEntities()V"))
	public boolean wrapTickEntities(ServerWorld instance) {
		return !CONTEXT.frozen;
	}
}
