package org.carpet.settings.validation;

import org.carpet.mixins.accessor.RedstoneWireBlock_accessor;

public class RPModifier extends Validators.SideEffectValidator<Boolean> {
	@Override
	public Boolean parseValue(Boolean newValue) {
		return true;
	}

	@Override
	public void performEffect(Boolean newValue) {
		RedstoneWireBlock_accessor.setShouldSignal(newValue);
	}
}
