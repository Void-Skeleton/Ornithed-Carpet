package org.carpet.mixins.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.handler.CommandManager;
import org.carpet.command.framework.CustomCommands;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
public class CommandManager_customCommand {
	@Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/AbstractCommand;setListener(Lnet/minecraft/server/command/handler/CommandListener;)V"))
	public void injectCustomCommands(MinecraftServer server, CallbackInfo ci) {
		CustomCommands.registerCommands();
	}
}
