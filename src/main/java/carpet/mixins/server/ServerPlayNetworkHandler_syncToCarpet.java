package carpet.mixins.server;

import carpet.server.CarpetServer;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.server.network.handler.ServerPlayNetworkHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandler_syncToCarpet {
	@Shadow
	public ServerPlayerEntity player;

	@Inject(method = "onDisconnect", at = @At(value = "HEAD"))
	private void onDisconnect(Text text, CallbackInfo ci) {
		CarpetServer.onPlayerLoggedOut(player);
	}
}
