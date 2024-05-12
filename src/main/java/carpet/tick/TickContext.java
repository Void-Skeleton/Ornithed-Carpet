package carpet.tick;

public class TickContext {
	private static final TickContext INSTANCE = new TickContext();

	public static boolean profilingTickPhases = false;

	public static TickContext getInstance() {
		return INSTANCE;
	}

	private TickContext() {
		nanosPerTick = 50 * 1000 * 1000;
		warping = false;
		frozen = false;
	}

	private long nanosPerTick;
	private boolean warping;
	private boolean frozen;

	public long getNanosPerTick() {
		return nanosPerTick;
	}

	public void setNanosPerTick(long nanosPerTick) {
		this.nanosPerTick = nanosPerTick;
	}

	public boolean isWarping() {
		return warping;
	}

	public void setWarping(boolean warping) {
		this.warping = warping;
	}

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}
}
