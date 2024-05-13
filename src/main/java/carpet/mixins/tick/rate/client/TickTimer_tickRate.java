package carpet.mixins.tick.rate.client;

import carpet.settings.CarpetSettings;
import carpet.tick.TickContext;
import net.minecraft.client.TickTimer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TickTimer.class)
public abstract class TickTimer_tickRate {
	@Shadow
	private float mspt;

	@Inject(method = "advance", at = @At("HEAD"))
	public void advance(CallbackInfo ci) {
		if (CarpetSettings.smoothClientAnimations)
			this.mspt = TickContext.CLIENT_CONTEXT.nanosPerTick / 1e6f;
		else
			this.mspt = 50.0f;
	}
}
