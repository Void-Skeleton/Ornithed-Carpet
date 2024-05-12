package carpet.settings.validation;

import net.minecraft.block.Blocks;
import carpet.mixins.accessor.RedstoneWireBlock_accessor;

public class RPModifier extends Validators.SideEffectValidator<Boolean> {
	@Override
	public Boolean parseValue(Boolean newValue) {
		return true;
	}

	@Override
	public void performEffect(Boolean newValue) {
		((RedstoneWireBlock_accessor) Blocks.REDSTONE_WIRE).setShouldSignal(newValue);
	}
}
