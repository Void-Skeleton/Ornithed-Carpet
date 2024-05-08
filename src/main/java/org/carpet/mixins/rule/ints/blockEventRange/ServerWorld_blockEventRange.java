package org.carpet.mixins.rule.ints.blockEventRange;

import net.minecraft.server.world.ServerWorld;
import org.carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerWorld.class)
public abstract class ServerWorld_blockEventRange {
	@ModifyConstant(method = "doBlockEvents", constant = @Constant(doubleValue = 64.0))
	public double modifyBlockEventRange(double constant) {
		return CarpetSettings.blockEventRange;
	}
}
