package carpet.mixins.rule.tnt.tntFixedRandomAngle;

import net.minecraft.entity.PrimedTntEntity;
import carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = PrimedTntEntity.class, priority = 300)
public abstract class PrimedTntEntity_tntFixedRandomAngle {
	@Redirect(method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/entity/living/LivingEntity;)V",
		at = @At(value = "INVOKE", target = "Ljava/lang/Math;random()D"))
	public double tryFixRandomAngle() {
		double d = CarpetSettings.tntFixedRandomAngle;
		return d < 0 ? Math.random() : (d / (2 * Math.PI));
	}
}
