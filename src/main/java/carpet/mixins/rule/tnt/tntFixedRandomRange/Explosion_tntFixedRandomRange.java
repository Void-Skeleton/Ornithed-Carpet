package carpet.mixins.rule.tnt.tntFixedRandomRange;

import net.minecraft.entity.Entity;
import net.minecraft.entity.PrimedTntEntity;
import net.minecraft.world.explosion.Explosion;
import carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(Explosion.class)
public abstract class Explosion_tntFixedRandomRange {
	@Final @Shadow
	private Entity source;

	@Redirect(method = "damageEntities", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F"))
	public float fixRandomRange(Random random) {
		float v;
		if (source instanceof PrimedTntEntity
			&& (v = CarpetSettings.tntFixedRandomRange) >= 0) return v;
		return random.nextFloat();
	}
}
