package carpet.tick;

import carpet.utils.TranslationKeys;

public enum TickPhase {
	SPAWNING, CHUNK_SOURCE, TILE_TICK, CHUNK_TICK,
	VILLAGES, CHUNK_MAP, BLOCK_EVENT, ENTITY, ENTITY_REMOVAL,
	BLOCK_ENTITY, BLOCK_ENTITY_ADDITION;
	private final String name;

	TickPhase(String name) {
		this.name = name;
	}

	TickPhase() {
		this.name = TranslationKeys.TICK_STAGE + this.name().toLowerCase();
	}

	@Override
	public String toString() {
		return this.name;
	}
}
