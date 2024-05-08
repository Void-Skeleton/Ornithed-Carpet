package org.carpet.mixins.rule.ints.fillLimit;

import net.minecraft.server.command.CloneCommand;
import org.carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CloneCommand.class)
public abstract class CloneCommand_fillLimit {
	@ModifyConstant(method = "run", constant = @Constant(intValue = 32768), expect = 2)
	public int modifyFillLimit(int constant) {
		return CarpetSettings.fillLimit;
	}
}
