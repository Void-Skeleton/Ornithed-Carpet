package carpet.mixins.rule.tnt.yeetTntInitialMotion;

import net.minecraft.entity.PrimedTntEntity;
import carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PrimedTntEntity.class)
public class PrimedTntEntity_yeetTntInitialMotion {
	@ModifyConstant(method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/entity/living/LivingEntity;)V",
		constant = @Constant(floatValue = 0.02f), expect = 2)
	public float yeetTntInitialMotion(float constant) {
		return CarpetSettings.yeetTntInitialMotion ? 0.0f : 0.02f;
	}
}
