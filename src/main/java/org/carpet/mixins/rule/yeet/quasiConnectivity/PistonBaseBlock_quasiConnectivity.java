package org.carpet.mixins.rule.yeet.quasiConnectivity;

import net.minecraft.block.PistonBaseBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonBaseBlock.class)
public abstract class PistonBaseBlock_quasiConnectivity {
	@Inject(method = "shouldExtend", at = @At(value = "INVOKE_ASSIGN",
		target = "Lnet/minecraft/util/math/BlockPos;up()Lnet/minecraft/util/math/BlockPos;"), cancellable = true)
	public void modifyQuasiConnectivity(World world, BlockPos pos, Direction facing, CallbackInfoReturnable<Boolean> cir) {
		if (!CarpetSettings.quasiConnectivity) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}
}
