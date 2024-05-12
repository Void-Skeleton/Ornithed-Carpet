package carpet.mixins.rule.tweak.observerInitialPulse;

import net.minecraft.block.FacingBlock;
import net.minecraft.block.ObserverBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.property.BooleanProperty;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ObserverBlock.class)
public abstract class ObserverBlock_observerInitialPulse extends FacingBlock {
	@Shadow
	@Final
	public static BooleanProperty POWERED;

	protected ObserverBlock_observerInitialPulse(Material material) {
		super(material);
	}

	@Inject(method = "getPlacementState", at = @At("HEAD"), cancellable = true)
	public void controlInitialPulse(World world, BlockPos pos, Direction dir, float dx, float dy, float dz, int metadata, LivingEntity entity, CallbackInfoReturnable<BlockState> cir) {
		if (!world.isClient && !CarpetSettings.observerInitialPulse) {
			cir.setReturnValue(defaultState()
				.set(FACING, Direction.nearest(pos, entity).getOpposite())
				.set(POWERED, true));
		}
	}
}
