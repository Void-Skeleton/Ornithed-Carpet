package org.carpet.mixins.rule.feature.movableBEs;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.carpet.settings.CarpetSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldChunk.class)
public abstract class WorldChunk_movableBEs {
	@Shadow
	@Final
	private World world;

	@Redirect(method = "setBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/WorldChunk;getBlockEntity(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/chunk/WorldChunk$BlockEntityCreationType;)Lnet/minecraft/block/entity/BlockEntity;"))
	public BlockEntity getBlockEntityMovableBEsFix(WorldChunk chunk, BlockPos pos, WorldChunk.BlockEntityCreationType creationType) {
		if (CarpetSettings.movableBlockEntities || CarpetSettings.flattenedNoteBlocks) {
			// Old carpet, movable block entity fix, 2no2name
			return world.getBlockEntity(pos);
		} else {
			return chunk.getBlockEntity(pos, creationType);
		}
	}
}
