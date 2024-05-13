package carpet.tick;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;

public class TickContext {
	// Cheap check fields for tick phase profiling and verbose profiling
	public static boolean profilingTickPhases = false;
	public static boolean profilingNeighborUpdates = false;
	public static boolean profilingTileTicks = false;
	public static boolean profilingEntities = false;
	public static boolean profilingBlockEntities = false;

	// Singleton
	private TickContext() {
		nanosPerTick = 50 * 1000 * 1000;
		warping = false;
		frozen = Freeze.NONE;
		superHot = false;
	}
	public static final TickContext INSTANCE = new TickContext();

	// Tick rate context
	public enum Freeze {
		NONE, LIGHT, DEEP
	}
	public long nanosPerTick;
	public boolean warping;
	public Freeze frozen;
	public boolean superHot;

	// Tick phase context
	// DIM_MAP[dimId + 1] = (0 = overworld, 1 = nether, 2 = end, 3 = canonical)
	private final int[] DIM_MAP = new int[] {1, 0, 2, 3};
	public static final int DIMENSION_INDEPENDENT_ID = 2;
	public int tickingDimension;
	public TickPhase currentPhase;

	public void swapTickingDimension(int dimensionId) {
		int newTickingDimension = DIM_MAP[dimensionId + 1];
		if (tickingDimension != newTickingDimension) {
			if (profilingTickPhases) tickPhaseProfilers[tickingDimension].swap(null);
			tickingDimension = newTickingDimension;
		}
	}
	public void swapTickPhase(TickPhase currentPhase) {
		this.currentPhase = currentPhase;
		if (profilingTickPhases) {
			if (currentPhase == null)
				tickPhaseProfilers[tickingDimension].swap(null);
			else if (currentPhase.profiled)
				tickPhaseProfilers[tickingDimension].swap(currentPhase);
			else
				tickPhaseProfilers[tickingDimension].swap(null);
		}
	}

	// Tick profile context;
	public final TypedProfiler<TickPhase>[] tickPhaseProfilers = new TickPhaseProfiler[]{
		new TickPhaseProfiler(), new TickPhaseProfiler(),
		new TickPhaseProfiler(), new TickPhaseProfiler()};
	public final TypedProfiler<Block> updateProfiler = new TypedProfiler<>();
	public final TypedProfiler<Block> tileTickProfiler = new TypedProfiler<>();
	public final TypedProfiler<Class<? extends BlockEntity>> blockEntityProfiler = new TypedProfiler<>();
	public final TypedProfiler<Class<? extends Entity>> entityProfiler = new TypedProfiler<>();
}
