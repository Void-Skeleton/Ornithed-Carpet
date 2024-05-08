package org.carpet.settings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.carpet.settings.rule.Rule;
import org.carpet.settings.rule.RuleCategory;
import org.carpet.settings.validation.Validators;

public class CarpetSettings {
	public static final String carpetVersion = "0.1.0";
	public static final Logger LOG = LogManager.getLogger("carpet");

	@Rule(
			desc = "Sets the language for Carpet",
			categories = RuleCategory.FEATURE,
			options = {"en_us"},
			validators = Validators.LanguageValidator.class
	)
	public static String language = "en_us";

	@Rule(
			desc = "Carpet command permission level",
			categories = RuleCategory.CREATIVE,
			validators = Validators.CarpetPermissionLevel.class,
			options = {"ops", "2", "4"}
	)
	public static String carpetCommandPermissionLevel = "ops";

	// -----------------------------------------
	//                 Features
	// -----------------------------------------

	//#if MC>=11200
	@Rule(desc = "Parrots don't get of your shoulders until you receive proper damage", categories = {RuleCategory.SURVIVAL, RuleCategory.FEATURE})
	public static boolean persistentParrots = false;
	//#endif

	@Rule(desc = "Players absorb XP instantly, without delay", categories = RuleCategory.CREATIVE)
	public static boolean xpNoCooldown = false;

	@Rule(
		desc = "Creative No Clip",
		extra = {
			"On servers it needs to be set on both ",
			"client and server to function properly.",
			"Has no effect when set on the server only",
			"Can allow to phase through walls",
			"if only set on the carpet client side",
			"but requires some trapdoor magic to",
			"allow the player to enter blocks"
		},
		categories = {RuleCategory.CREATIVE, RuleCategory.CLIENT}
	)
	public static boolean creativeNoClip = false;

	/*
          _____         _
         |_   _|       | |
           | |   _ __  | |_  ___
           | |  | '_ \ | __|/ __|
          _| |_ | | | || |_ \__ \
         |_____||_| |_| \__||___/
     */
	// INTS - This sections contains all the integer fields
	@Rule(
		desc = "Range for block events to be synced to client",
		options = {"4", "64", "256"},
		categories = RuleCategory.OPTIMIZATION,
		strict = false,
		validators = Validators.PositiveIn10Bits.class
	)
	public static int blockEventRange = 64;

	@Rule(
		desc = "Custom limit of changed blocks for /fill and /clone",
		options = {"32768", "114514", "1919810", "2147483647"},
		categories = RuleCategory.CREATIVE,
		strict = false,
		validators = Validators.NonNegativeNumber.class
	)
	public static int fillLimit = 32768;

	@Rule(
		desc = "Amount of delay ticks to use a nether portal in creative",
		options = {"1", "40", "80", "72000"},
		categories = RuleCategory.CREATIVE,
		strict = false,
		validators = Validators.OneHourMaxDelayLimit.class
	)
	public static int portalCreativeDelay = 1;

	@Rule(
		desc = "Amount of delay ticks to use a nether portal in survival",
		options = {"1", "40", "80", "72000"},
		categories = RuleCategory.SURVIVAL,
		strict = false,
		validators = Validators.OneHourMaxDelayLimit.class
	)
	public static int portalSurvivalDelay = 80;

	@Rule(
		desc = "Customizable piston push limit",
		options = {"10", "12", "14", "100"},
		categories = RuleCategory.CREATIVE,
		strict = false,
		validators = Validators.PositiveIn10Bits.class
	)
	public static int pushLimit = 12;

	@Rule(
		desc = "Customizable powered rail power range",
		options = {"9", "15", "30"},
		categories = RuleCategory.CREATIVE,
		strict = false,
		validators = Validators.PositiveIn10Bits.class
	)
	public static int railPowerLimit = 9;

	@Rule(
		desc = "Customizable maximum # of tile ticks ran per tick",
		options = {"1000", "65536", "2147483647"},
		categories = RuleCategory.CREATIVE,
		strict = false,
		validators = Validators.NonNegativeNumber.class
	)
	public static int tileTickLimit = 65536;

	/*
		 _____  _   _  _____
		|_   _|| \ | ||_   _|
		  | |  |  \| |  | |
		  | |  | |\  |  | |
		  |_|  |_| \_|  |_|
	 */
	// TNT - This section contains all the TNT related settings

	@Rule(desc = "Explosions won't destroy blocks",
		categories = {RuleCategory.CREATIVE, RuleCategory.TNT})
	public static boolean explosionNoBlockDamage = false;

	@Rule(desc = "TNT doesn't update when placed against a power source",
		categories = {RuleCategory.CREATIVE, RuleCategory.TNT})
	public static boolean tntDoNotUpdate = false;

	@Rule(desc = "Fix random angle of TNT for debugging; unit in radians, <0 = vanilla behavior",
		categories = {RuleCategory.CREATIVE, RuleCategory.TNT},
		options = {"-1.0"}, strict = false)
	public static double tntFixedRandomAngle = -1.0;

	@Rule(desc = "Fix random range of TNT to 0.7+0.6*setting, <0 = vanilla behavior",
		categories = {RuleCategory.CREATIVE, RuleCategory.TNT},
		options = {"-1.0"}, strict = false,
		validators = Validators.OptionalProbability.class)
	public static float tntFixedRandomRange = -1.0f;

	@Rule(desc = "Changes default tnt fuse.",
		categories = {RuleCategory.CREATIVE, RuleCategory.TNT},
		validators = Validators.NonNegativeNumber.class,
		options = {"70", "80", "100"},
		strict = false)
	public static int tntFuseLength = 80;
}
