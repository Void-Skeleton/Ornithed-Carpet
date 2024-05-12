package carpet.utils.duck;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import carpet.tick.TickPhase;
import carpet.tick.TypedProfiler;

public interface ServerWorld_duck {
	TypedProfiler<TickPhase> getTickPhaseProfiler();

	TypedProfiler<Block> getUpdateProfiler();

	TypedProfiler<Block> getTileTickProfiler();

	TypedProfiler<Block> getBlockEntityProfiler();

	TypedProfiler<Class<? extends Entity>> getEntityProfiler();
}
