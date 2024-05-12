package carpet.mixins.rule.tweak.accurateBlockPlacement;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenserBlock.class)
public abstract class DispenserBlock_accurateBlockPlacement {
	@Inject(method = "onAdded", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/block/DispenserBlock;updateFacing(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/BlockState;)V"),
		cancellable = true)
	public void skipUpdateFacing(World world, BlockPos pos, BlockState state, CallbackInfo ci) {
		if (CarpetSettings.accurateBlockPlacement) ci.cancel();
	}

	@Redirect(method = "onPlaced", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/BlockState;I)Z"))
	public boolean skipFixFacing(World world, BlockPos pos, BlockState state, int flags) {
		if (CarpetSettings.accurateBlockPlacement) return true;
		return world.setBlockState(pos, state, flags);
	}
}
