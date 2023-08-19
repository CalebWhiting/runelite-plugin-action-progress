package com.github.calebwhiting.runelite.plugins.actionprogress;

import com.github.calebwhiting.runelite.api.ui.IconSource;
import lombok.Getter;

import java.util.function.Function;

@Getter
public enum Action
{
	COLLECT_SAND("Collecting", ActionProgressConfig::collectSand, ActionIcon.SPRITE_BUCKET, 1, 2),
	COOKING("Cooking", ActionProgressConfig::cookingCooking, ActionIcon.SPRITE_COOKING, 2, 3, 4),
	COOKING_CUT_FRUIT("Cutting", ActionProgressConfig::cookingCutFruit, ActionIcon.SPRITE_COOKING, 2),
	COOKING_MIX_DOUGH("Combining", ActionProgressConfig::cookingMixPastry, ActionIcon.SPRITE_COOKING, 1, 2),
	COOKING_MIX_GRAPES("Combining", ActionProgressConfig::cookingMixWines, ActionIcon.SPRITE_COOKING, 3, 2),
	COOKING_TOP_PIZZA("Combining", ActionProgressConfig::cookingTopPizza, ActionIcon.SPRITE_COOKING, 2),
	CRAFT_BATTLESTAVES("Combining", ActionProgressConfig::craftBattlestaves, ActionIcon.SPRITE_CRAFTING, 3, 2),
	CRAFT_BLOW_GLASS("Glassblowing", ActionProgressConfig::craftGlassblowing, ActionIcon.SPRITE_CRAFTING, 3),
	CRAFT_CAST_GOLD_AND_SILVER("Casting", ActionProgressConfig::craftCastGoldAndSilver, ActionIcon.SPRITE_CRAFTING, 3),
	CRAFT_CUT_GEMS("Cutting", ActionProgressConfig::craftCutGems, ActionIcon.SPRITE_CRAFTING, 2),
	CRAFT_HARD_LEATHER("Leather-working", ActionProgressConfig::craftLeatherWorking, ActionIcon.SPRITE_CRAFTING, 2),
	CRAFT_LEATHER("Leather-working", ActionProgressConfig::craftLeatherWorking, ActionIcon.SPRITE_CRAFTING, 3),
	CRAFT_MOLTEN_GLASS("Creating", ActionProgressConfig::craftMakeMoltenGlass, ActionIcon.SPRITE_CRAFTING, 2),
	CRAFT_STRING_JEWELLERY("Stringing", ActionProgressConfig::craftStringJewellery, ActionIcon.SPRITE_CRAFTING, 2),
	CRAFT_LOOM("Weaving,", ActionProgressConfig::craftWeaving,ActionIcon.SPRITE_CRAFTING, 4, 3),
	CRAFT_LOOM_DRIFT_NET("Weaving,", ActionProgressConfig::craftWeaving,ActionIcon.SPRITE_CRAFTING, 3),
	CRAFT_SHIELD("Crafting", ActionProgressConfig::craftShields, ActionIcon.SPRITE_CRAFTING, 5),
	CRAFT_AMETHYST_HEADS_AND_TIPS("Crafting", ActionProgressConfig::craftHeadsAndTips,ActionIcon.SPRITE_CRAFTING,2),
	FLETCH_ATTACH("Attaching", ActionProgressConfig::fletchArrowsAndBolts, ActionIcon.SPRITE_FLETCHING, 2),
	FLETCH_CUT_ARROW_SHAFT("Cutting", ActionProgressConfig::fletchArrowsAndBolts, ActionIcon.SPRITE_FLETCHING, 3),
	FLETCH_CUT_BOW("Cutting", ActionProgressConfig::fletchBows, ActionIcon.SPRITE_FLETCHING, 3),
	FLETCH_CUT_TIPS("Cutting", ActionProgressConfig::fletchArrowsAndBolts, ActionIcon.SPRITE_FLETCHING, 5),
	FLETCH_ATTACH_TIPS("Attaching", ActionProgressConfig::fletchArrowsAndBolts, ActionIcon.SPRITE_FLETCHING, 2), 
	FLETCH_CUT_TIPS_AMETHYST("Cutting", ActionProgressConfig::fletchArrowsAndBolts, ActionIcon.SPRITE_FLETCHING, 2),
	FLETCH_STRING_BOW("Stringing", ActionProgressConfig::fletchBows, ActionIcon.SPRITE_FLETCHING, 2),
	FLETCH_SHIELD("Cutting", ActionProgressConfig::fletchShields, ActionIcon.SPRITE_FLETCHING, 7),	
	FLETCH_CUT_CROSSBOW("Cutting", ActionProgressConfig::fletchCrossbows, ActionIcon.SPRITE_FLETCHING, 2, 3),
	FLETCH_ATTACH_CROSSBOW("Attaching", ActionProgressConfig::fletchCrossbows, ActionIcon.SPRITE_FLETCHING, 2),
	FLETCH_STRING_CROSSBOW("Stringing", ActionProgressConfig::fletchCrossbows, ActionIcon.SPRITE_FLETCHING, 2),
	FLETCH_JAVELIN_1Tick("Attaching", ActionProgressConfig::fletchJavelin, ActionIcon.SPRITE_FLETCHING, 1),
	FLETCH_JAVELIN_2Tick("Attaching", ActionProgressConfig::fletchJavelin, ActionIcon.SPRITE_FLETCHING, 2),
	FLETCH_DART("Attaching", ActionProgressConfig::fletchArrowsAndBolts, ActionIcon.SPRITE_FLETCHING, 2),
	GRIND("Grinding", ActionProgressConfig::grinding, ActionIcon.SPRITE_TOTAL, 0, 2, 3),
	HERB_CLEAN("Cleaning", ActionProgressConfig::herbCleaning, ActionIcon.SPRITE_HERBLORE, 0, 2),
	HERB_MIX_POTIONS("Mixing", ActionProgressConfig::herbPotions, ActionIcon.SPRITE_HERBLORE, 2),
	HERB_MIX_TAR("Mixing", ActionProgressConfig::herbTar, ActionIcon.SPRITE_HERBLORE, 2, 3),
	HERB_MIX_UNFINISHED("Mixing", ActionProgressConfig::herbPotions, ActionIcon.SPRITE_HERBLORE, 2, 1),
	MAGIC_CREATE_TABLET("Enchanting", ActionProgressConfig::magicTablets, ActionIcon.SPRITE_MAGIC, 2, 4),
	MAGIC_ENCHANT_BOLTS("Enchanting", ActionProgressConfig::magicEnchantBolts, ActionIcon.SPRITE_MAGIC, 1, 3),
	MAGIC_ENCHANT_JEWELLERY("Enchanting", ActionProgressConfig::magicEnchantJewellery, ActionIcon.SPRITE_MAGIC, 0, 7),
	MAGIC_CHARGE_ORB("Charging", ActionProgressConfig::magicChargeOrbs, ActionIcon.SPRITE_MAGIC, 3, 6),
	MAGIC_PLANK_MAKE("Making plank", ActionProgressConfig::magicChargeOrbs, ActionIcon.SPRITE_MAGIC, 3, 6),
	SMELTING("Smelting", ActionProgressConfig::smithSmelting, ActionIcon.SPRITE_SMITHING, 5),
	SMELTING_CANNONBALLS("Casting", ActionProgressConfig::smithCannonballs, ActionIcon.SPRITE_SMITHING, 7, 10),
	SMITHING("Forging", ActionProgressConfig::smithSmithing, ActionIcon.SPRITE_SMITHING, 5),
	TEMPOROSS_COOKING("Cooking", ActionProgressConfig::temporossCooking, ActionIcon.SPRITE_COOKING, 4, 3),
	TEMPOROSS_FILL_CRATE("Filling", ActionProgressConfig::temporossFiring, ActionIcon.SPRITE_FISHING, 2),
	TEMPOROSS_REWARD_POOL("Fishing", ActionProgressConfig::temporossRewardPool, ActionIcon.SPRITE_FISHING, 1, 3),
	WINTERTODT_WOODCUTTING("Chopping", ActionProgressConfig::wintertodtWoodcutting, ActionIcon.SPRITE_WOODCUTTING, 2, 3),
	WINTERTODT_FIREMAKING("Lighting", ActionProgressConfig::wintertodtLighting, ActionIcon.SPRITE_FIREMAKING, 4, 3),
	WINTERTODT_FLETCHING("Cutting", ActionProgressConfig::wintertodtFletching, ActionIcon.SPRITE_FLETCHING, 4, 4);

	private final String description;

	private final IconSource iconSource;

	private final int[] tickTimes;

	private final Function<ActionProgressConfig, Boolean> enabledFunction;

	Action(
			String description,
			Function<ActionProgressConfig, Boolean> enabledFunction,
			IconSource iconSource,
			int tickTime0,
			int... otherTickTimes)
	{
		this.description = description;
		this.enabledFunction = enabledFunction;
		this.iconSource = iconSource;
		// I did it this way so that we never accidentally create an action with zero tick times,
		// while preserving the convenience of var-args
		int[] tickTimes = new int[otherTickTimes.length + 1];
		tickTimes[0] = tickTime0;
		System.arraycopy(otherTickTimes, 0, tickTimes, 1, otherTickTimes.length);
		this.tickTimes = tickTimes;
	}
}