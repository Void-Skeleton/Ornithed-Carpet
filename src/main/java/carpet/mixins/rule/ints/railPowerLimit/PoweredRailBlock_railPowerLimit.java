package carpet.mixins.rule.ints.railPowerLimit;

import carpet.settings.CarpetSettings;
import net.minecraft.block.PoweredRailBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PoweredRailBlock.class)
public class PoweredRailBlock_railPowerLimit {
	@ModifyConstant(
		method = "isPoweredByConnectedRails",
		constant = @Constant(
			intValue = 8
		)
	)
	private int powerLimit(int original) {
		return CarpetSettings.railPowerLimit - 1;
	}
}
