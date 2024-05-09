package org.carpet.settings.rule;

public class RuleCategory {
	// Modifications to unsatisfactory vanilla features that is lightly survival oriented
	public static final String TWEAK = "tweak";

	// Slightly not-vanilla features primarily designed for enhancing survival experience
	public static final String SURVIVAL = "survival";

	// Heavy modifications to vanilla logic designed for creative testing only and is never suitable for survival
	// For example, the rule hopperNoItemCost should be off completely for survival
	public static final String CREATIVE = "creative";

	// Modifications for conducting experiments for a new piece of technology
	public static final String EXPERIMENTAL = "experimental";

	// Modifications to vanilla logic to decrease lag
	public static final String OPTIMIZATION = "optimization";

	// Brand new additional features to the vanilla game
	public static final String FEATURE = "feature";

	// Toggle for a command
	public static final String COMMAND = "command";

	// Rules related to TNT entities or explosions
	public static final String TNT = "tnt";

	// Rules related to population, population suppression or async exploits
	public static final String POPULATION = "population";

	// Rules related to additional dispenser behavior
	public static final String DISPENSER = "dispenser";

	// Radical changes often disabling entire parts of the game, designed to be enabled for only a short period of time for testing
	public static final String YEET = "yeet";

	// Carpet script
	public static final String SCARPET = "scarpet";

	// Rules that require clientside cooperation to function to the maximal extent
	public static final String CLIENT = "client";
}
