package carpet.tick;

import carpet.utils.Messenger;
import carpet.utils.algebraic.Algebraic;
import carpet.utils.algebraic.MatchWith;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.AbstractCommand;
import carpet.command.framework.StructuredCommandData;
import net.minecraft.server.command.exception.CommandException;
import net.minecraft.server.command.source.CommandSource;

@Algebraic(TickCommandData.Unary.class)
public interface TickCommandData extends StructuredCommandData<AbstractCommand> {
	enum NullaryOption {
		ENTITIES, FREEZE, HEALTH, QUERY, STEP, WARP
	}

	enum UnaryOption {
		RATE {
			@Override
			public boolean run(MinecraftServer server, CommandSource source, double value) {
				Messenger.m(source, "w Tick rate is {}", value);
				TickContext.INSTANCE.setTps(value);
				return true;
			}
		};
		private final String name = name().toLowerCase();
		public boolean run(MinecraftServer server, CommandSource source, double value) {
			return true;
		}
		@Override
		public String toString() {
			return name;
		}
	}

	class Unary implements TickCommandData {
		@MatchWith private UnaryOption option;
		@MatchWith private double value;

		@Override
		public void run(AbstractCommand command, MinecraftServer server, CommandSource source) throws CommandException {
			option.run(server, source, value);
		}
	}
}
