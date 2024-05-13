package carpet.mixins.tick.freeze.client;

import carpet.tick.TickContext;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Minecraft.class)
public abstract class Minecraft_freeze {
	@Unique
	private static final TickContext CONTEXT = TickContext.CLIENT_CONTEXT;

	@WrapWithCondition(method = "tick", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/client/world/ClientWorld;tickEntities()V"))
	public boolean wrapTickEntities(ClientWorld instance) {
		return !CONTEXT.frozen;
	}
}
