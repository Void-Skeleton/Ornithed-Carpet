package org.carpet.mixins.duck;

import net.minecraft.block.entity.BlockEntity;

public interface MovingBlockEntity_duck {
	BlockEntity getCarriedBlockEntity();

	void setCarriedBlockEntity(BlockEntity blockEntity);
}
