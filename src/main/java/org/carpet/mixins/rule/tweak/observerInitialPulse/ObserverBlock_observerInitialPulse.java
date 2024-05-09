package org.carpet.mixins.rule.tweak.observerInitialPulse;

import net.minecraft.block.ObserverBlock;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.property.Property;
import net.minecraft.util.math.Direction;
import org.carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ObserverBlock.class)
public abstract class ObserverBlock_observerInitialPulse {
	@Redirect(method = "getPlacementState", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/BlockState;set(Lnet/minecraft/block/state/property/Property;Ljava/lang/Comparable;)Lnet/minecraft/block/state/BlockState;"))
	public <T extends Comparable<T>, V extends T> BlockState redirectPlacementState(
		BlockState state, Property<T> property, V val) {
		return state.set(property, val).set(ObserverBlock.POWERED, !CarpetSettings.observerInitialPulse);
	}
}
