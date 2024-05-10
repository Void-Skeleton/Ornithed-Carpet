package org.carpet.command;

import com.google.common.collect.Iterables;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.exception.CommandException;
import net.minecraft.server.command.source.CommandSource;
import org.carpet.command.framework.StructuredCommand;
import org.carpet.command.framework.StructuredCommandData;
import org.carpet.settings.SettingsManager;
import org.carpet.settings.rule.CarpetRule;
import org.carpet.settings.rule.RuleHelper;
import org.carpet.utils.Messenger;
import org.carpet.utils.TranslationKeys;
import org.carpet.utils.algebraic.Algebraic;
import org.carpet.utils.algebraic.MatchWith;

import static org.carpet.utils.Translations.tr;

public class CarpetCommand extends StructuredCommand<CarpetCommand.CarpetCommandData> {

	public final SettingsManager manager;
	public CarpetCommand(SettingsManager manager) {
		super(CarpetCommandData.class);
		this.manager = manager;
	}

	@Override
	public String getName() {
		return manager.identifier();
	}

	@Override
	public String getUsage(CommandSource source) {
		return this.manager.identifier() + " <rule> <value>";
	}

	@Algebraic({SetDefault.class, ListMatching.class, RemoveDefault.class, SetRule.class,
		ListRules.class, QueryRule.class, ListAll.class})
	public interface CarpetCommandData extends StructuredCommandData<CarpetCommand> {
	}

	public static class SetDefault implements CarpetCommandData {
		@MatchWith(prefix = "setDefault") private MatchWith.Empty setDefault;
		@MatchWith private String name;
		@MatchWith private String value;

		@Override
		public void run(CarpetCommand command, MinecraftServer server, CommandSource source) throws CommandException {
			CarpetRule<?> rule = command.manager.contextRule(name);
			if (rule != null) {
				command.manager.setDefault(source, rule, value);
			} else {
				Messenger.c("rb " + tr(TranslationKeys.UNKNOWN_RULE) + ": " + value);
			}
		}
	}

	public static class ListMatching implements CarpetCommandData {
		@MatchWith(prefix = "list") private MatchWith.Empty list;
		@MatchWith private String category;

		@Override
		public void run(CarpetCommand command, MinecraftServer server, CommandSource source) throws CommandException {
			SettingsManager manager = command.manager;
			if (!Iterables.contains(manager.getCategories(), category)) return;
			manager.listSettings(source, String.format(tr(TranslationKeys.MOD_SETTINGS_MATCHING),
				manager.getFancyName(), RuleHelper.translatedCategory(manager.identifier(), category)),
				manager.getRulesMatching(category));
		}
	}

	public static class RemoveDefault implements CarpetCommandData {
		@MatchWith(prefix = "removeDefault") private MatchWith.Empty removeDefault;
		@MatchWith private String name;

		@Override
		public void run(CarpetCommand command, MinecraftServer server, CommandSource source) throws CommandException {
			CarpetRule<?> rule = command.manager.contextRule(name);
			if (rule != null) {
				command.manager.removeDefault(source, rule);
			} else {
				Messenger.c("rb " + tr(TranslationKeys.UNKNOWN_RULE) + ": " + name);
			}
		}
	}

	public static class SetRule implements CarpetCommandData {
		@MatchWith private String name;
		@MatchWith private String value;

		@Override
		public void run(CarpetCommand command, MinecraftServer server, CommandSource source) throws CommandException {
			CarpetRule<?> rule = command.manager.contextRule(name);
			if (rule != null) {
				command.manager.setRule(source, rule, value);
			} else {
				Messenger.c("rb " + tr(TranslationKeys.UNKNOWN_RULE) + ": " + name);
			}
		}
	}

	public static class ListRules implements CarpetCommandData {
		@MatchWith(prefix = "list") private MatchWith.Empty list;

		@Override
		public void run(CarpetCommand command, MinecraftServer server, CommandSource source) throws CommandException {
			SettingsManager manager = command.manager;
			manager.listSettings(source, String.format(tr(TranslationKeys.ALL_MOD_SETTINGS),
				manager.getFancyName()), manager.getRulesSorted());
		}
	}

	public static class QueryRule implements CarpetCommandData {
		@MatchWith private String name;

		@Override
		public void run(CarpetCommand command, MinecraftServer server, CommandSource source) throws CommandException {
			CarpetRule<?> rule = command.manager.contextRule(name);
			if (rule != null) {
				command.manager.displayRuleMenu(source, rule);
			} else {
				Messenger.c("rb " + tr(TranslationKeys.UNKNOWN_RULE) + ": " + name);
			}
		}
	}

	public static class ListAll implements CarpetCommandData {
		@Override
		public void run(CarpetCommand command, MinecraftServer server, CommandSource source) throws CommandException {
			command.manager.listAllSettings(source);
		}
	}
}
