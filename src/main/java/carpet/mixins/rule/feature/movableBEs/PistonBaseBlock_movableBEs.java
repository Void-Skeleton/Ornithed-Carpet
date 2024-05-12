package carpet.mixins.rule.feature.movableBEs;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MovingBlock;
import net.minecraft.block.PistonBaseBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MovingBlockEntity;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import carpet.utils.duck.MovingBlockEntity_duck;
import carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PistonBaseBlock.class)
public abstract class PistonBaseBlock_movableBEs {
	@Unique
	private BlockEntity blockEntityToMove;

	@Unique
	private static boolean isPushableEntityBlock(Block block) {
		if (CarpetSettings.movableBlockEntities) {
			return block != Blocks.ENDER_CHEST && block != Blocks.ENCHANTING_TABLE && block != Blocks.END_GATEWAY
				&& block != Blocks.END_PORTAL && block != Blocks.MOB_SPAWNER && block != Blocks.MOVING_BLOCK;
		} else if (CarpetSettings.flattenedNoteBlocks) {
			return block == Blocks.NOTEBLOCK;
		} else return false;
	}

	@Redirect(method = "canMoveBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;hasBlockEntity()Z"))
	private static boolean redirectHasBlockEntity(Block block) {
		if (CarpetSettings.movableBlockEntities || CarpetSettings.flattenedNoteBlocks) {
			return block.hasBlockEntity() && !isPushableEntityBlock(block);
		} else {
			return block.hasBlockEntity();
		}
	}

	@Redirect(method = "move", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/BlockState;",
		ordinal = 2))
	public BlockState recordBlockEntity(World world, BlockPos pos) {
		if (CarpetSettings.movableBlockEntities || CarpetSettings.flattenedNoteBlocks) {
			blockEntityToMove = world.getBlockEntity(pos);
			world.removeBlockEntity(pos);
		}
		else blockEntityToMove = null;
		return world.getBlockState(pos);
	}

	@Redirect(method = "move", at = @At(value = "INVOKE",
		target = "Lnet/minecraft/block/MovingBlock;createMovingBlockEntity(Lnet/minecraft/block/state/BlockState;Lnet/minecraft/util/math/Direction;ZZ)Lnet/minecraft/block/entity/BlockEntity;",
		ordinal = 0))
	public BlockEntity createMovingBlockEntity(BlockState movedState, Direction facing, boolean extending, boolean source) {
		MovingBlockEntity blockEntity = (MovingBlockEntity) MovingBlock.createMovingBlockEntity(movedState, facing, extending, source);
		if (blockEntityToMove != null)
			// No need to check the carpet rules here
			// If the rules are off, blockEntityToMove would be null here
			((MovingBlockEntity_duck) blockEntity).setCarriedBlockEntity(blockEntityToMove);
		return blockEntity;
	}
}
