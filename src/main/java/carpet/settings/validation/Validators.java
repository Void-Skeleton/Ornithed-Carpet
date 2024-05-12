package carpet.settings.validation;

//#if MC>=11300
//$$ import net.minecraft.server.command.source.CommandSourceStack;
//#else
import net.minecraft.server.command.source.CommandSource;
//#endif
import carpet.settings.CarpetSettings;
import carpet.settings.rule.CarpetRule;
import carpet.utils.Translations;

import java.util.Arrays;
import java.util.List;

public final class Validators {
	private Validators() {}

	public static class CommandLevel extends Validator<String> {
		public static final List<String> OPTIONS = Arrays.asList("true", "false", "ops", "0", "1", "2", "3", "4");
		@Override
		//#if MC>=11300
//$$ 		public String validate(CommandSourceStack source, CarpetRule<String> currentRule, String newValue, String userString) {
		//#else
		public String validate(CommandSource source, CarpetRule<String> currentRule, String newValue, String userString) {
		//#endif
			if (!OPTIONS.contains(newValue)) {
				return null;
			}
			return newValue;
		}

		@Override public String description() {
			return "Can be limited to 'ops' only, true/false for everyone/no one, or a custom permission level";
		}
	}

	public static class NonNegativeNumber<T extends Number> extends Validator<T> {
		@Override
		//#if MC>=11300
//$$ 		public T validate(CommandSourceStack source, CarpetRule<T> currentRule, T newValue, String string) {
		//#else
		public T validate(CommandSource source, CarpetRule<T> currentRule, T newValue, String string) {
		//#endif
			return newValue.doubleValue() >= 0 ? newValue : null;
		}
		@Override
		public String description() {
			return "Must be a positive number or 0";
		}
	}

	public static class CarpetPermissionLevel extends Validator<String> {
		@Override
		//#if MC>=11300
//$$ 		public String validate(CommandSourceStack source, CarpetRule<String> currentRule, String newValue, String string) {
		//#else
		public String validate(CommandSource source, CarpetRule<String> currentRule, String newValue, String string) {
		//#endif
			//#if MC>=11300
//$$ 			if (source == null || source.hasPermissions(4)) {
			//#else
			if (source == null || source.canUseCommand(4, source.getName())) {
			//#endif
				return newValue;
			}
			return null;
		}

		@Override
		public String description() {
			return "This setting can only be set by admins with op level 4";
		}
	}

	public static class OneHourMaxDelayLimit extends Validator<Integer> {
		@Override
		//#if MC>=11300
//$$ 		public Integer validate(CommandSourceStack source, CarpetRule<Integer> currentRule, Integer newValue, String string) {
		//#else
		public Integer validate(CommandSource source, CarpetRule<Integer> currentRule, Integer newValue, String string) {
		//#endif
			return (newValue > 0 && newValue <= 72000) ? newValue : null;
		}

		@Override
		public String description() {
			return "You must choose a value from 1 to 72000";
		}
	}

	public static class Percentage extends Validator<Integer> {
		@Override
		public Integer validate(CommandSource source, CarpetRule<Integer> changingRule, Integer newValue, String userInput) {
			return (newValue >= 0 && newValue <= 100) ? newValue : null;
		}

		public String description() {
			return "You must choose a value from 0 to 100";
		}
	}

	public static class PositiveIn10Bits extends Validator<Integer> {
		@Override
		//#if MC>=11300
//$$ 		public Integer validate(CommandSourceStack source, CarpetRule<Integer> currentRule, Integer newValue, String string) {
		//#else
		public Integer validate(CommandSource source, CarpetRule<Integer> currentRule, Integer newValue, String string) {
		//#endif
			return (newValue > 0 && newValue <= 1024) ? newValue : null;
		}

		@Override
		public String description() { return "You must choose a value from 1 to 1024";}
	}

	public static class LanguageValidator extends Validator<String> {
		@Override
		//#if MC>=11300
//$$ 		public String validate(CommandSourceStack source, CarpetRule<String> currentRule, String newValue, String string) {
		//#else
		public String validate(CommandSource source, CarpetRule<String> currentRule, String newValue, String string) {
		//#endif
			CarpetSettings.language = newValue;
			Translations.updateLanguage();
			return newValue;
		}
	}

	public static class Probability<T extends Number> extends Validator<T> {
		@Override
		//#if MC>=11300
	//$$ 		public T validate(CommandSourceStack source, CarpetRule<T> currentRule, T newValue, String string) {
		//#else
		public T validate(CommandSource source, CarpetRule<T> currentRule, T newValue, String string) {
			//#endif
			return (newValue.doubleValue() >= 0 && newValue.doubleValue() <= 1) ? newValue : null;
		}

		@Override
		public String description() {
			return "Must be between 0 and 1";
		}
	}

	public static class OptionalProbability<T extends Number> extends Validator<T> {
		@Override
		//#if MC>=11300
		//$$ 		public T validate(CommandSourceStack source, CarpetRule<T> currentRule, T newValue, String string) {
		//#else
		public T validate(CommandSource source, CarpetRule<T> currentRule, T newValue, String string) {
			//#endif
			// <0 = default
			return newValue.doubleValue() <= 1 ? newValue : null;
		}

		@Override
		public String description() {
			return "Must be between 0 and 1";
		}
	}

	public static abstract class SideEffectValidator<T> extends Validator<T> {
		public abstract T parseValue(T newValue);

		public abstract void performEffect(T newValue);

		@Override
		public T validate(CommandSource source, CarpetRule<T> changingRule, T newValue, String userInput) {
			performEffect(newValue);
			return parseValue(newValue);
		}
	}
}
