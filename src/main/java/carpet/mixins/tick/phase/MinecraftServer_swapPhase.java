package carpet.mixins.tick.phase;

import carpet.tick.TickContext;
import jdk.internal.org.objectweb.asm.Opcodes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServer_swapPhase {
	@Redirect(method = "tickWorlds", at = @At(value = "FIELD",
		target = "Lnet/minecraft/server/MinecraftServer;worlds:[Lnet/minecraft/server/world/ServerWorld;",
		opcode = Opcodes.GETFIELD, args = "array=get"))
	public ServerWorld swapToDimension(ServerWorld[] worlds, int index) {
		TickContext.INSTANCE.swapTickingDimension(index);
		return worlds[index];
	}
}
