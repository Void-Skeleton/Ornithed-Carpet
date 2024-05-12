package carpet.mixins.rule.tweak.cakeAlwaysEat;

import net.minecraft.block.CakeBlock;
import net.minecraft.entity.living.player.PlayerEntity;
import carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CakeBlock.class)
public abstract class CakeBlock_cakeAlwaysEat {
	@Redirect(method = "tryEatCake", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;canEat(Z)Z"))
	public boolean useAlwaysEat(PlayerEntity player, boolean ignoreHunger) {
		return CarpetSettings.cakeAlwaysEat || player.canEat(ignoreHunger);
	}
}
