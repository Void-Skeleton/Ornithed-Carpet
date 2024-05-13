package carpet.mixins.tick.profile;

import carpet.tick.TypedProfiler;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.server.world.ServerWorld;
import carpet.tick.TickContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(ServerWorld.class)
public abstract class ServerWorld_profile {
	@Redirect(method = "doScheduledTicks", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/block/Block;tick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/BlockState;Ljava/util/Random;)V"))
	public void profileTileTicks(Block instance, World world, BlockPos pos, BlockState state, Random random) {
		if (TickContext.profilingTileTicks) {
			TypedProfiler<Block> tileTickProfiler = TickContext.SERVER_CONTEXT.tileTickProfiler;
			tileTickProfiler.swap(instance);
			instance.tick(world, pos, state, random);
			tileTickProfiler.swap(null);
		} else instance.tick(world, pos, state, random);
	}
}
