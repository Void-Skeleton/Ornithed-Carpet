package carpet.mixins.rule.tweak.relaxedBlockPlacement;

import net.minecraft.block.PumpkinBlock;
import net.minecraft.block.state.BlockState;
import carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PumpkinBlock.class)
public abstract class PumpkinBlock_relaxedBlockPlacement {
	@Redirect(method = "canSurvive", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/BlockState;isFullBlock()Z"))
	public boolean relaxBlockSurvival(BlockState state) {
		return CarpetSettings.relaxedBlockPlacement || state.isFullBlock();
	}
}
