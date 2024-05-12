package carpet.tick;

import net.minecraft.server.command.AbstractCommand;
import carpet.command.framework.StructuredCommandData;

public interface TickCommandData extends StructuredCommandData<AbstractCommand> {
	enum NullaryTickOption {
		ENTITIES, FREEZE, HEALTH, QUERY, STEP, WARP
	}

	enum UnaryTickOption {
		RATE, STEP, WARP
	}
}
