package org.carpet.mixins.rule.ints.fillLimit;

import net.minecraft.server.command.FillCommand;
import org.carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(FillCommand.class)
public abstract class FillCommand_fillLimit {
	@ModifyConstant(method = "run", constant = @Constant(intValue = 32768), expect = 2)
	public int modifyFillLimit(int constant) {
		return CarpetSettings.blockEventRange;
	}
}
