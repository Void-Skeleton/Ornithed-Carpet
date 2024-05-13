package carpet.command;

import carpet.command.framework.StructuredCommand;
import carpet.tick.TickCommandData;

public class TickCommand extends StructuredCommand<TickCommandData> {
	public TickCommand() {
		super(TickCommandData.class, "tick");
	}
}
