package org.carpet.mixins.rule.tweak.relaxedBlockPlacement;

import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.material.Material;
import org.carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FenceGateBlock.class)
public abstract class FenceGateBlock_relaxedBlockPlacement {
	@Redirect(method = "canSurvive", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/material/Material;isSolid()Z"))
	public boolean relaxBlockSurvival(Material material) {
		return CarpetSettings.relaxedBlockPlacement || material.isSolid();
	}
}
