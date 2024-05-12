package carpet.mixins.rule.yeet.yeetUpdates;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldChunk.class)
public abstract class WorldChunk_yeetUpdates {
	@Redirect(method = "setBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onAdded(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/BlockState;)V"))
	public void yeetInitialUpdates(Block block, World world, BlockPos pos, BlockState state) {
		if (!CarpetSettings.yeetInitialUpdates) block.onAdded(world, pos, state);
	}

	@Redirect(method = "setBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onRemoved(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/BlockState;)V"))
	public void yeetRemovalUpdates(Block block, World world, BlockPos pos, BlockState state) {
		if (!CarpetSettings.yeetRemovalUpdates) block.onRemoved(world, pos, state);
	}
}
