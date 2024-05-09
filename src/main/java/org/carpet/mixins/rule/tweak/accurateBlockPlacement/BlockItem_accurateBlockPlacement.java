package org.carpet.mixins.rule.tweak.accurateBlockPlacement;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.carpet.settings.CarpetSettings;
import org.carpet.utils.feature.AccuratePlacementProtocol;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockItem.class)
public abstract class BlockItem_accurateBlockPlacement {
	@Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getPlacementState(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;FFFILnet/minecraft/entity/living/LivingEntity;)Lnet/minecraft/block/state/BlockState;"))
	public BlockState getPlacementState(Block block, World world, BlockPos pos, Direction dir, float dx, float dy, float dz, int metadata, LivingEntity entity) {
		// Vanilla behavior - dx doesn't matter anyways
		BlockState state = block.getPlacementState(world, pos, dir, dx, dy, dz, metadata, entity);
		if (CarpetSettings.accurateBlockPlacement) {
			BlockState accurateState = AccuratePlacementProtocol.decodeAccuratePlacementProtocol(state, dx);
			return accurateState == null ? state : accurateState;
		} else return state;
	}
}
