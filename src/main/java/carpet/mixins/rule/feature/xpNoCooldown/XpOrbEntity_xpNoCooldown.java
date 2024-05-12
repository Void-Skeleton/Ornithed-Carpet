package carpet.mixins.rule.feature.xpNoCooldown;

import carpet.settings.CarpetSettings;
import net.minecraft.entity.XpOrbEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(XpOrbEntity.class)
public class XpOrbEntity_xpNoCooldown {
	@Inject(method = "onPlayerCollision", at = @At(value = "HEAD"))
	private void removeDelay(PlayerEntity playerEntity, CallbackInfo ci) {
		if (CarpetSettings.xpNoCooldown) {
			playerEntity.xpCooldown = 0;
		}
	}
}
