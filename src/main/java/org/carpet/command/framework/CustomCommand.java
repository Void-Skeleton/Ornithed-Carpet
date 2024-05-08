package org.carpet.command.framework;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.AbstractCommand;
import net.minecraft.server.command.exception.CommandException;
import net.minecraft.server.command.source.CommandSource;

public abstract class CustomCommand extends AbstractCommand {
	@Override
	public void run(MinecraftServer server, CommandSource source, String[] args) throws CommandException {

	}

	public abstract void execute();
}
