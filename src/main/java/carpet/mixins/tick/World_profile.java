package carpet.mixins.tick;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public abstract class World_profile {
	@Inject(method = "tickEntities", at = @At(value = "INVOKE_STRING",
		target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V",
		args = "ldc=entities"))
	public void swapToGlobalEntities(CallbackInfo ci) {

	}
}
