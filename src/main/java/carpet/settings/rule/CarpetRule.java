package carpet.settings.rule;

//#if MC>=11300
//$$ import net.minecraft.server.command.source.CommandSourceStack;
//#else
import net.minecraft.server.command.source.CommandSource;
//#endif
import net.minecraft.text.Text;
import carpet.settings.SettingsManager;

import java.util.Collection;
import java.util.List;

public interface CarpetRule<T> {
	String name();
	String desc();
	List<Text> extraInfo();
	Collection<String> categories();
	Collection<String> suggestions();
	SettingsManager settingsManager();
	T value();
	boolean canBeToggledClientSide();
	Class<T> type();
	T defaultValue();
	default boolean strict() {
		return false;
	}
	//#if MC>=11300
//$$ 	void set(CommandSourceStack source, String value) throws InvalidRuleValueException;
//$$ 	void set(CommandSourceStack source, T value) throws InvalidRuleValueException;
	//#else
	void set(CommandSource source, String value) throws InvalidRuleValueException;
	void set(CommandSource source, T value) throws InvalidRuleValueException;
	//#endif
}
