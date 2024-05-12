package carpet.mixins.command;

import carpet.server.CarpetServer;
//#if MC>=11300
//$$ import com.mojang.brigadier.CommandDispatcher;
//$$ import net.minecraft.server.command.source.CommandSourceStack;
//$$ import org.spongepowered.asm.mixin.Final;
//$$ import org.spongepowered.asm.mixin.Shadow;
//#else
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.handler.CommandRegistry;
//#endif
import net.minecraft.server.command.handler.CommandManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
//#if MC>=11300
//$$ public class CommandManagerMixin {
//$$ 	@Shadow
//$$ 	@Final
//$$ 	private CommandDispatcher<CommandSourceStack> dispatcher;
//#else
public abstract class CommandManager_registerCarpetCommands extends CommandRegistry {
//#endif

	@Inject(method = "<init>", at = @At("RETURN"))
	//#if MC>=11300
//$$ 	private void onRegister(boolean isDedicatedServer, CallbackInfo ci) {
//$$ 		CarpetServer.registerCarpetCommands(this.dispatcher);
	//#elseif MC>10809
	private void onRegister(MinecraftServer server, CallbackInfo ci) {
		CarpetServer.registerCarpetCommands((CommandRegistry) (Object) this);
	//#else
	//$$ private void onRegister(CallbackInfo ci) {
	//$$	CarpetServer.registerCarpetCommands((CommandRegistry) (Object) this);
	//#endif
	}
}
