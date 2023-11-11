package com.github.calebwhiting.runelite.plugins.actionprogress;

import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import java.awt.Color;

@ConfigGroup("actionprogress")
public interface ActionProgressConfig extends Config
{

	@ConfigSection(
			name = "Cooking actions",
			description = "Enable/Disable cooking specific actions.",
			position = 7
	) String COOKING = "Cooking actions";
	@ConfigSection(
			name = "Smithing actions",
			description = "Enable/Disable smithing specific actions.",
			position = 8
	) String SMITHING = "Smithing actions";
	@ConfigSection(
			name = "Crafting actions",
			description = "Enable/Disable crafting specific actions.",
			position = 9
	) String CRAFTING = "Crafting actions";
	@ConfigSection(
			name = "Fletching actions",
			description = "Enable/Disable fletching specific actions.",
			position = 10
	) String FLETCHING = "Fletching actions";
	@ConfigSection(
			name = "Herblore actions",
			description = "Enable/Disable herblore specific actions.",
			position = 11
	) String HERBLORE = "Herblore actions";
	@ConfigSection(
			name = "Magic actions",
			description = "Enable/Disable magic specific actions.",
			position = 12
	) String MAGIC = "Magic actions";
	@ConfigSection(
			name = "Farming actions",
			description = "Enable/Disable Farming actions.",
			position = 13
	) String FARMING = "Farming actions";
	@ConfigSection(
			name = "Tempoross actions",
			description = "Enable/Disable tempoross specific actions.",
			position = 14
	) String TEMPOROSS = "Tempoross actions";
	@ConfigSection(
			name = "Wintertodt actions (not yet supported)",
			description = "Enable/Disable wintertodt specific actions.",
			position = 15,
			closedByDefault = true
	) String WINTERTODT = "Wintertodt actions";
	@ConfigSection(
			name = "Miscellaneous actions",
			description = "Enable/Disable miscellaneous actions.",
			position = 16
	) String MISCELLANEOUS = "Miscellaneous actions";

	@ConfigItem(
			name = "Show ticks instead of seconds",
			keyName = "use-ticks",
			description = "Enable/Disable the usage of ticks when displaying the progress bar.",
			position = 0
	)
	default boolean useTicks()
	{
		return false;
	}

	@ConfigItem(
			name = "Notify when finished",
			keyName = "notify-when-finished",
			description = "Enable/Disable notifications when actions are completed or interrupted.",
			position = 1
	)
	default boolean notifyWhenFinished()
	{
		return true;
	}

	@ConfigItem(
			name = "Smooth progress bar",
			keyName = "smooth-progress-bar",
			description = "Enable/Disable smooth progress bar.",
			position = 2
	)
	default boolean smoothProgressBar()
	{
		return true;
	}

	@ConfigItem(
			name = "Product icons",
			keyName = "show-product-icons",
			description = "When enabled, show the product icon in the infobox where available. " +
						  "Otherwise fallback to skill icons.",
			position = 3
	)
	default boolean showProductIcons()
	{
		return true;
	}

	@ConfigItem(
			name = "Ignore single actions",
			keyName = "ignore-single-actions",
			description = "Ignore single actions",
			position = 4
	)
	default boolean ignoreSingleActions()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
			name = "Progress left color",
			keyName = "progress-left-color",
			description = "Color to be used to display the remaining actions on the progress bar",
			position = 5
	)
	default Color progressLeftColor()
	{
		return new Color(255, 52, 52, 100);
	}

	@Alpha
	@ConfigItem(
			name = "Progress done color",
			keyName = "progress-done-color",
			description = "Color to be used to display the completed actions on the progress bar",
			position = 6
	)
	default Color progressDoneColor()
	{
		return new Color(0, 255, 52, 100);
	}

	@ConfigItem(
			name = "Cooking",
			keyName = "cooking.cooking",
			description = "Enable/Disable monitoring cooking.",
			section = COOKING
	)
	default boolean cookingCooking()
	{
		return true;
	}

	@ConfigItem(
			name = "Mixing wine",
			keyName = "cooking.mix-wine",
			description = "Enable/Disable monitoring mixing wine.",
			section = COOKING
	)
	default boolean cookingMixWines()
	{
		return true;
	}

	@ConfigItem(
			name = "Mixing pastry",
			keyName = "cooking.mix-pastry",
			description = "Enable/Disable monitoring mixing pastry.",
			section = COOKING
	)
	default boolean cookingMixPastry()
	{
		return true;
	}

	@ConfigItem(
			name = "Topping pizza",
			keyName = "cooking.top-pizza",
			description = "Enable/Disable monitoring topping pizza.",
			section = COOKING
	)
	default boolean cookingTopPizza()
	{
		return true;
	}

	@ConfigItem(
			name = "Cutting fruit",
			keyName = "cooking.slicing",
			description = "Enable/Disable monitoring cutting fruit.",
			section = COOKING
	)
	default boolean cookingCutFruit()
	{
		return true;
	}

	@ConfigItem(
			name = "Smelting Items",
			keyName = "smithing.smelting",
			description = "Enable/Disable monitoring smelting.",
			section = SMITHING
	)
	default boolean smithSmelting()
	{
		return true;
	}

	@ConfigItem(
			name = "Smithing Items",
			keyName = "smithing.smithing",
			description = "Enable/Disable monitoring smithing items.",
			section = SMITHING
	)
	default boolean smithSmithing()
	{
		return true;
	}

	@ConfigItem(
			name = "Cannonballs",
			keyName = "smithing.cannonballs",
			description = "Enable/Disable monitoring casting cannonballs",
			section = SMITHING
	)
	default boolean smithCannonballs()
	{
		return true;
	}

	@ConfigItem(
			name = "Casting gold & silver",
			keyName = "crafting.cast-gold-and-silver",
			description = "Enable/Disable monitoring casting gold & silver items.",
			section = CRAFTING
	)
	default boolean craftCastGoldAndSilver()
	{
		return true;
	}

	@ConfigItem(
			name = "Creating molten glass",
			keyName = "crafting.molten-glass",
			description = "Enable/Disable monitoring creating molten glass.",
			section = CRAFTING
	)
	default boolean craftMakeMoltenGlass()
	{
		return true;
	}

	@ConfigItem(
			name = "Glassblowing",
			keyName = "crafting.glassblowing",
			description = "Enable/Disable monitoring glassblowing.",
			section = CRAFTING
	)
	default boolean craftGlassblowing()
	{
		return true;
	}

	@ConfigItem(
			name = "Stringing jewellery",
			keyName = "crafting.string",
			description = "Enable/Disable monitoring stringing jewellery.",
			section = CRAFTING
	)
	default boolean craftStringJewellery()
	{
		return true;
	}

	@ConfigItem(
			name = "Cutting gems",
			keyName = "crafting.gems",
			description = "Enable/Disable monitoring cutting gems.",
			section = CRAFTING
	)
	default boolean craftCutGems()
	{
		return true;
	}

	@ConfigItem(
			name = "Leather-working",
			keyName = "crafting.leather",
			description = "Enable/Disable monitoring leather-working.",
			section = CRAFTING
	)
	default boolean craftLeatherWorking()
	{
		return true;
	}

	@ConfigItem(
			name = "Battlestaves",
			keyName = "crafting.battlestaves",
			description = "Enable/Disable monitoring attaching orbs to battlestaves.",
			section = CRAFTING
	)
	default boolean craftBattlestaves()
	{
		return true;
	}

	
	@ConfigItem(
			name = "Weaving",
			keyName = "crafting.waeving",
			description = "Enable/Disable monitoring for items crafted at a loom.",
			section = CRAFTING
	)
	default boolean craftWeaving()
	{
		return true;
	}

	@ConfigItem(
			name = "Making shields",
			keyName = "crafting.shields",
			description = "Enable/Disable monitoring for crafting wooden shields into leather shields.",
			section = CRAFTING
	)
	default boolean craftShields()
	{
		return true;
	}

	@ConfigItem(
			name = "Making amethyst heads and tips",
			keyName = "crafting.headsAndTips",
			description = "Enable/Disable monitoring for crafting amethyst heads and tips.",
			section = CRAFTING
	)
	default boolean craftHeadsAndTips()
	{
		return true;
	}

	@ConfigItem(
			name = "Arrows & bolts",
			keyName = "fletching.ammunition",
			description = "Enable/Disable monitoring fletching arrows & bolts.",
			section = FLETCHING
	)
	default boolean fletchArrowsAndBolts()
	{
		return true;
	}

	@ConfigItem(
			name = "Making bows",
			keyName = "fletching.bows",
			description = "Enable/Disable monitoring cutting & stringing bows.",
			section = FLETCHING
	)
	default boolean fletchBows()
	{
		return true;
	}

	@ConfigItem(
			name = "Making crossbows",
			keyName = "fletching.crossbows",
			description = "Enable/Disable monitoring cutting & stringing crossbows.",
			section = FLETCHING
	)
	default boolean fletchCrossbows()
	{
		return true;
	}

	@ConfigItem(
			name = "Making shields",
			keyName = "fletching.shields",
			description = "Enable/Disable monitoring cutting logs into shields.",
			section = FLETCHING
	)
	default boolean fletchShields()
	{
		return true;
	}

	@ConfigItem(
			name = "Making javelins",
			keyName = "fletching.javelins",
			description = "Enable/Disable monitoring attaching heads on javelin shafts.",
			section = FLETCHING
	)
	default boolean fletchJavelin()
	{
		return true;
	}

	@ConfigItem(
			name = "Herb cleaning",
			keyName = "herblore.cleaning",
			description = "Enable/Disable monitoring herb cleaning.",
			section = HERBLORE
	)
	default boolean herbCleaning()
	{
		return true;
	}

	@ConfigItem(
			name = "Mixing potions",
			keyName = "herblore.potions",
			description = "Enable/Disable monitoring mixing potions.",
			section = HERBLORE
	)
	default boolean herbPotions()
	{
		return true;
	}

	@ConfigItem(
			name = "Chemistry amulet",
			keyName = "herblore.chemistry",
			description = "Stop when chemistry amulet breaks.",
			section = HERBLORE
	)
	default boolean herbChemistry()
	{
		return true;
	}

	@ConfigItem(
			name = "Mixing tar",
			keyName = "herblore.tar",
			description = "Enable/Disable monitoring mixing tar.",
			section = HERBLORE
	)
	default boolean herbTar()
	{
		return true;
	}

	@ConfigItem(
			name = "Enchant jewellery spells",
			keyName = "magic.enchant-jewellery",
			description = "Enable/Disable monitoring jewellery enchantment spells.",
			section = MAGIC
	)
	default boolean magicEnchantJewellery()
	{
		return true;
	}

	@ConfigItem(
			name = "Charge orb spells",
			keyName = "magic.charge-orbs",
			description = "Enable/Disable monitoring charge obs spells.",
			section = MAGIC
	)
	default boolean magicChargeOrbs()
	{
		return true;
	}

	@ConfigItem(
			name = "Enchant bolt spells",
			keyName = "magic.enchant-bolts",
			description = "Enable/Disable monitoring bolt enchantment spells.",
			section = MAGIC
	)
	default boolean magicEnchantBolts()
	{
		return true;
	}

	@ConfigItem(
			name = "Creating tablets",
			keyName = "magic.tablets",
			description = "Enable/Disable monitoring creating magic tablets at a lectern.",
			section = MAGIC
	)
	default boolean magicTablets()
	{
		return true;
	}

	@ConfigItem(
			name = "Creating ultracompost",
			keyName = "farming.ultracompost",
			description = "Enable/Disable monitoring for creating ultracompost.",
			section = FARMING
	)
	default boolean farmUltraCompost()
	{
		return true;
	}

	@ConfigItem(
			name = "Cooking",
			keyName = "tempoross.cooking",
			description = "Enable/Disable monitoring tempoross cooking.",
			section = TEMPOROSS
	)
	default boolean temporossCooking()
	{
		return true;
	}

	@ConfigItem(
			name = "Firing",
			keyName = "tempoross.firing",
			description = "Enable/Disable monitoring tempoross firing.",
			section = TEMPOROSS
	)
	default boolean temporossFiring()
	{
		return true;
	}

	@ConfigItem(
			name = "Reward pool",
			keyName = "tempoross.reward-pool",
			description = "Enable/Disable monitoring tempoross reward pool.",
			section = TEMPOROSS
	)
	default boolean temporossRewardPool()
	{
		return true;
	}

	@ConfigItem(
			name = "Woodcutting",
			keyName = "wintertodt.woodcutting",
			description = "Enable/Disable monitoring wintertodt woodcutting.",
			section = WINTERTODT
	)
	default boolean wintertodtWoodcutting()
	{
		return true;
	}

	@ConfigItem(
			name = "Burning",
			keyName = "wintertodt.burning",
			description = "Enable/Disable monitoring wintertodt burning.",
			section = WINTERTODT
	)
	default boolean wintertodtLighting()
	{
		return true;
	}

	@ConfigItem(
			name = "Fletching",
			keyName = "wintertodt.fletching",
			description = "Enable/Disable monitoring wintertodt fletching.",
			section = WINTERTODT
	)
	default boolean wintertodtFletching()
	{
		return true;
	}

	@ConfigItem(
			name = "Collecting sand",
			keyName = "misc.collect-sand",
			description = "Enable/Disable monitoring collecting sand.",
			section = MISCELLANEOUS
	)
	default boolean collectSand()
	{
		return true;
	}

	@ConfigItem(
			name = "Grinding items",
			keyName = "misc.grinding",
			description = "Enable/Disable monitoring grinding items.",
			section = MISCELLANEOUS
	)
	default boolean grinding()
	{
		return true;
	}
	
}
