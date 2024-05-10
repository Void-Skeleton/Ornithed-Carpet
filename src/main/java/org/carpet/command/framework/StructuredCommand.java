package org.carpet.command.framework;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.AbstractCommand;
import net.minecraft.server.command.exception.CommandException;
import net.minecraft.server.command.exception.IncorrectUsageException;
import net.minecraft.server.command.source.CommandSource;
import net.minecraft.util.math.BlockPos;
import org.carpet.utils.algebraic.AdtMatcher;
import org.carpet.utils.algebraic.StructuredAdtMatcher;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class StructuredCommand<D extends StructuredCommandData> extends AbstractCommand {
	private final ThreadLocal<StructuredAdtMatcher<D>> matcherLocal;

	protected StructuredCommand(Class<D> dataClass) {
		this.matcherLocal = new ThreadLocal<>();
		matcherLocal.set(new StructuredAdtMatcher<>(dataClass));
	}

	protected D parseData(String[] args) {
		AdtMatcher<D> matcher = matcherLocal.get();
		D data = matcher.clear().append(Arrays.asList(args)).matchedValue();
		return data;
	}

	protected D parsePartialData(String[] args) {
		StructuredAdtMatcher<D> matcher = matcherLocal.get();
		D data = matcher.clear().append(Arrays.asList(args)).nonFailureValue();
		return data;
	}

	@Override
	public abstract String getName();

	@Override
	public List<String> getSuggestions(MinecraftServer server, CommandSource source, String[] args, @Nullable BlockPos pos) {
		D data = parsePartialData(args);
		if (data == null) return Collections.emptyList();
		return data.getSuggestions(this, args.length, server, source, pos);
	}

	@Override
	public boolean hasTargetSelectorAt(String[] args, int index) {
		D data = parseData(args);
		if (data == null) return false;
		return index == data.getTargetSelector();
	}

	@Override
	public void run(MinecraftServer server, CommandSource source, String[] args) throws CommandException {
		D data = parseData(args);
		if (data == null) throw new IncorrectUsageException("Command data parse failed");
		data.run(this, server, source);
	}
}
