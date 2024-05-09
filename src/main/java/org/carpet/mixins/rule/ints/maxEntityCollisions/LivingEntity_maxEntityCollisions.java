package org.carpet.mixins.rule.ints.maxEntityCollisions;

import net.minecraft.entity.living.LivingEntity;
import org.carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntity_maxEntityCollisions {
	@Redirect(method = "pushAwayCollidingEntities", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I"),
		slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z")))
	public int limitEntityCollisions(List list) {
		int maxEntityCollisions = CarpetSettings.maxEntityCollisions;
		return maxEntityCollisions > 0 ? Math.max(list.size(), maxEntityCollisions) : list.size();
	}
}
