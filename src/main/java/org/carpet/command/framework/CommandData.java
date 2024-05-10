package org.carpet.command.framework;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.Command;
import net.minecraft.server.command.source.CommandSource;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface CommandData<T extends Command> {
	int run(T command, MinecraftServer server, CommandSource source);

	List<String> getSuggestions(T command, int slot, MinecraftServer server,
								CommandSource source, @Nullable BlockPos pos);
}
