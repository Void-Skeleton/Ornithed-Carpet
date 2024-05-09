package org.carpet.mixins.rule.tweak.accurateBlockPlacement;

import net.minecraft.block.PistonBaseBlock;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PistonBaseBlock.class)
public abstract class PistonBaseBlock_accurateBlockPlacement {
	@Redirect(method = "onPlaced", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/BlockState;I)Z"))
	public boolean skipFixFacing(World world, BlockPos pos, BlockState state, int flags) {
		if (CarpetSettings.accurateBlockPlacement) return true;
		return world.setBlockState(pos, state, flags);
	}
}
