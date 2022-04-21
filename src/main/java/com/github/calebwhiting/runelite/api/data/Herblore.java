package com.github.calebwhiting.runelite.api.data;

import com.github.calebwhiting.runelite.api.data.Ingredient;
import com.github.calebwhiting.runelite.api.data.Recipe;
import lombok.experimental.UtilityClass;
import net.runelite.api.ItemID;

/**
 * NOTE:
 * The difference between a finished and unfinished potion is that
 * unfinished potions take 1 tick to produce, whereas finished potions take 2 ticks.
 * This means you need to "wrongly" categorize certain recipes for correct functionality.
 */
@UtilityClass
public class Herblore {

    public static final Recipe[] UNFINISHED_POTIONS = {
            // @formatter:off
            new Recipe(ItemID.GUAM_POTION_UNF, Ingredients.VIAL_OF_WATER, Ingredients.GUAM_LEAF),
            new Recipe(ItemID.UNFINISHED_POTION_4840, Ingredients.VIAL_OF_WATER, Ingredients.ROGUES_PURSE),
            new Recipe(ItemID.MARRENTILL_POTION_UNF, Ingredients.VIAL_OF_WATER, Ingredients.MARRENTILL),
            new Recipe(ItemID.TARROMIN_POTION_UNF, Ingredients.VIAL_OF_WATER, Ingredients.TARROMIN),
            new Recipe(ItemID.UNFINISHED_POTION, Ingredients.VIAL_OF_WATER, Ingredients.ASHES),
            new Recipe(ItemID.HARRALANDER_POTION_UNF, Ingredients.VIAL_OF_WATER, Ingredients.HARRALANDER),
            new Recipe(ItemID.MAGIC_ESSENCE_UNF, Ingredients.VIAL_OF_WATER, Ingredients.STAR_FLOWER),
            new Recipe(ItemID.RANARR_POTION_UNF, Ingredients.VIAL_OF_WATER, Ingredients.RANARR_WEED),
            new Recipe(ItemID.TOADFLAX_POTION_UNF, Ingredients.VIAL_OF_WATER, Ingredients.TOADFLAX),
            new Recipe(ItemID.IRIT_POTION_UNF, Ingredients.VIAL_OF_WATER, Ingredients.IRIT_LEAF),
            new Recipe(ItemID.AVANTOE_POTION_UNF, Ingredients.VIAL_OF_WATER, Ingredients.AVANTOE),
            new Recipe(ItemID.KWUARM_POTION_UNF, Ingredients.VIAL_OF_WATER, Ingredients.KWUARM),
            new Recipe(ItemID.SNAPDRAGON_POTION_UNF, Ingredients.VIAL_OF_WATER, Ingredients.SNAPDRAGON),
            new Recipe(ItemID.CADANTINE_POTION_UNF, Ingredients.VIAL_OF_WATER, Ingredients.CADANTINE),
            new Recipe(ItemID.LANTADYME_POTION_UNF, Ingredients.VIAL_OF_WATER, Ingredients.LANTADYME),
            new Recipe(ItemID.DWARF_WEED_POTION_UNF, Ingredients.VIAL_OF_WATER, Ingredients.DWARF_WEED),
            new Recipe(ItemID.TORSTOL_POTION_UNF, Ingredients.VIAL_OF_WATER, Ingredients.TORSTOL),
            new Recipe(ItemID.CADANTINE_BLOOD_POTION_UNF, Ingredients.VIAL_OF_BLOOD, Ingredients.CADANTINE),
            new Recipe(ItemID.GUTHIX_BALANCE_UNF_7658, Ingredients.RESTORE_1, Ingredients.GARLIC),
            new Recipe(ItemID.GUTHIX_BALANCE_UNF_7656, Ingredients.RESTORE_2, Ingredients.GARLIC),
            new Recipe(ItemID.GUTHIX_BALANCE_UNF_7654, Ingredients.RESTORE_3, Ingredients.GARLIC),
            new Recipe(ItemID.GUTHIX_BALANCE_UNF, Ingredients.RESTORE_4, Ingredients.GARLIC),
            // Seriously, fuck this recipe with a barbed harpoon :(
            new Recipe(HerbTeaMix.HERB_TEA_MIX_G, Ingredients.CUP_OF_HOT_WATER, Ingredients.GUAM_LEAF),
            new Recipe(HerbTeaMix.HERB_TEA_MIX_M, Ingredients.CUP_OF_HOT_WATER, Ingredients.MARRENTILL),
            new Recipe(HerbTeaMix.HERB_TEA_MIX_H, Ingredients.CUP_OF_HOT_WATER, Ingredients.HARRALANDER),
            new Recipe(HerbTeaMix.HERB_TEA_MIX_GG, Ingredients.HERB_TEA_MIX_G, Ingredients.GUAM_LEAF),
            new Recipe(HerbTeaMix.HERB_TEA_MIX_GM, Ingredients.HERB_TEA_MIX_G, Ingredients.MARRENTILL),
            new Recipe(HerbTeaMix.HERB_TEA_MIX_GM, Ingredients.HERB_TEA_MIX_M, Ingredients.GUAM_LEAF),
            new Recipe(HerbTeaMix.HERB_TEA_MIX_HG, Ingredients.HERB_TEA_MIX_G, Ingredients.HARRALANDER),
            new Recipe(HerbTeaMix.HERB_TEA_MIX_HG, Ingredients.HERB_TEA_MIX_H, Ingredients.GUAM_LEAF),
            new Recipe(HerbTeaMix.HERB_TEA_MIX_HM, Ingredients.HERB_TEA_MIX_H, Ingredients.MARRENTILL),
            new Recipe(HerbTeaMix.HERB_TEA_MIX_HM, Ingredients.HERB_TEA_MIX_M, Ingredients.HARRALANDER),
            new Recipe(HerbTeaMix.HERB_TEA_MIX_GGH, Ingredients.HERB_TEA_MIX_GG, Ingredients.HARRALANDER),
            new Recipe(HerbTeaMix.HERB_TEA_MIX_GGH, Ingredients.HERB_TEA_MIX_HG, Ingredients.GUAM_LEAF),
            new Recipe(HerbTeaMix.HERB_TEA_MIX_GGM, Ingredients.HERB_TEA_MIX_GG, Ingredients.MARRENTILL),
            new Recipe(HerbTeaMix.HERB_TEA_MIX_GGM, Ingredients.HERB_TEA_MIX_GM, Ingredients.GUAM_LEAF),
            new Recipe(HerbTeaMix.HERB_TEA_MIX_HMG, Ingredients.HERB_TEA_MIX_GM, Ingredients.HARRALANDER),
            new Recipe(HerbTeaMix.HERB_TEA_MIX_HMG, Ingredients.HERB_TEA_MIX_HM, Ingredients.GUAM_LEAF),
            new Recipe(HerbTeaMix.HERB_TEA_MIX_HMG, Ingredients.HERB_TEA_MIX_HG, Ingredients.MARRENTILL),
            // Technically not unfinished, but it only takes 1 tick to make
            new Recipe(ItemID.GUTHIX_REST3, Ingredients.HERB_TEA_MIX_GGH, Ingredients.MARRENTILL),
            new Recipe(ItemID.GUTHIX_REST3, Ingredients.HERB_TEA_MIX_GGM, Ingredients.HARRALANDER),
            new Recipe(ItemID.GUTHIX_REST3, Ingredients.HERB_TEA_MIX_HMG, Ingredients.GUAM_LEAF)
            // @formatter:on
    };

    public static final Recipe[] POTIONS = {
            // @formatter:off
            new Recipe(ItemID.ATTACK_POTION3, Ingredients.GUAM_UNF, Ingredients.EYE_OF_NEWT),
            new Recipe(ItemID.ANTIPOISON3, Ingredients.MARRENTILL_UNF, Ingredients.UNICORN_HORN_DUST),
            new Recipe(ItemID.RELICYMS_BALM3, Ingredients.ROGUES_PURSE_UNF, Ingredients.SNAKE_WEED),
            new Recipe(ItemID.STRENGTH_POTION3, Ingredients.TARROMIN_UNF, Ingredients.LIMPWURT_ROOT),
            new Recipe(ItemID.SERUM_207_3, Ingredients.TARROMIN_UNF, Ingredients.ASHES),
            new Recipe(ItemID.SERUM_207_3, Ingredients.ASH_POTION, Ingredients.TARROMIN),
            new Recipe(ItemID.COMPOST_POTION3, Ingredients.HARRALANDER_UNF, Ingredients.VOLCANIC_ASH),
            new Recipe(ItemID.RESTORE_POTION3, Ingredients.HARRALANDER_UNF, Ingredients.RED_SPIDERS_EGGS),
            new Recipe(ItemID.GUTHIX_BALANCE1, Ingredients.GUTHIX_BALANCE_UNF_1, Ingredients.SILVER_DUST),
            new Recipe(ItemID.GUTHIX_BALANCE2, Ingredients.GUTHIX_BALANCE_UNF_2, Ingredients.SILVER_DUST),
            new Recipe(ItemID.GUTHIX_BALANCE3, Ingredients.GUTHIX_BALANCE_UNF_3, Ingredients.SILVER_DUST),
            new Recipe(ItemID.GUTHIX_BALANCE4, Ingredients.GUTHIX_BALANCE_UNF_4, Ingredients.SILVER_DUST),
            new Recipe(ItemID.BLAMISH_OIL, Ingredients.HARRALANDER_UNF, Ingredients.BLAMISH_SNAIL_SLIME),
            new Recipe(ItemID.ENERGY_POTION3, Ingredients.HARRALANDER_UNF, Ingredients.CHOCOLATE_DUST),
            new Recipe(ItemID.DEFENCE_POTION3, Ingredients.RANARR_UNF, Ingredients.WHITE_BERRIES),
            new Recipe(ItemID.AGILITY_POTION3, Ingredients.TOADFLAX_UNF, Ingredients.TOADS_LEGS),
            new Recipe(ItemID.COMBAT_POTION3, Ingredients.HARRALANDER_UNF, Ingredients.GOAT_HORN_DUST),
            new Recipe(ItemID.PRAYER_POTION3, Ingredients.RANARR_UNF, Ingredients.SNAPE_GRASS),
            new Recipe(ItemID.SUPER_ATTACK3, Ingredients.IRIT_UNF, Ingredients.EYE_OF_NEWT),
            new Recipe(ItemID.GOBLIN_POTION3, Ingredients.TOADFLAX_UNF, Ingredients.PHARMAKOS_BERRIES),
            new Recipe(ItemID.SUPERANTIPOISON3, Ingredients.IRIT_UNF, Ingredients.UNICORN_HORN_DUST),
            new Recipe(ItemID.FISHING_POTION3, Ingredients.AVANTOE_UNF, Ingredients.SNAPE_GRASS),
            new Recipe(ItemID.SUPER_ENERGY3, Ingredients.AVANTOE_UNF, Ingredients.MORT_MYRE_FUNGUS),
            new Recipe(ItemID.SHRINKMEQUICK, Ingredients.TARROMIN_UNF, Ingredients.SHRUNK_OGLEROOT),
            new Recipe(ItemID.HUNTER_POTION3, Ingredients.AVANTOE_UNF, Ingredients.KEBBIT_TEETH_DUST),
            new Recipe(ItemID.SUPER_STRENGTH3, Ingredients.KWUARM_UNF, Ingredients.LIMPWURT_ROOT),
            new Recipe(ItemID.MAGIC_ESSENCE3, Ingredients.MAGIC_ESSENCE_UNF, Ingredients.GORAK_CLAW_POWDER),
            new Recipe(ItemID.WEAPON_POISON, Ingredients.KWUARM_UNF, Ingredients.DRAGON_SCALE_DUST),
            new Recipe(ItemID.SUPER_RESTORE3, Ingredients.SNAPDRAGON_UNF, Ingredients.RED_SPIDERS_EGGS),
            new Recipe(SanfewSerum.MIX_1_1, Ingredients.SUPER_RESTORE_1, Ingredients.UNICORN_HORN_DUST),
            new Recipe(SanfewSerum.MIX_1_2, Ingredients.SUPER_RESTORE_2, Ingredients.UNICORN_HORN_DUST),
            new Recipe(SanfewSerum.MIX_1_3, Ingredients.SUPER_RESTORE_3, Ingredients.UNICORN_HORN_DUST),
            new Recipe(SanfewSerum.MIX_1_4, Ingredients.SUPER_RESTORE_4, Ingredients.UNICORN_HORN_DUST),
            new Recipe(SanfewSerum.MIX_2_1, Ingredients.MIXTURE_STEP_1_1, Ingredients.SNAKE_WEED),
            new Recipe(SanfewSerum.MIX_2_2, Ingredients.MIXTURE_STEP_1_2, Ingredients.SNAKE_WEED),
            new Recipe(SanfewSerum.MIX_2_3, Ingredients.MIXTURE_STEP_1_3, Ingredients.SNAKE_WEED),
            new Recipe(SanfewSerum.MIX_2_4, Ingredients.MIXTURE_STEP_1_4, Ingredients.SNAKE_WEED),
            new Recipe(ItemID.SANFEW_SERUM1, Ingredients.MIXTURE_STEP_2_1, Ingredients.NAIL_BEAST_NAILS),
            new Recipe(ItemID.SANFEW_SERUM2, Ingredients.MIXTURE_STEP_2_2, Ingredients.NAIL_BEAST_NAILS),
            new Recipe(ItemID.SANFEW_SERUM3, Ingredients.MIXTURE_STEP_2_3, Ingredients.NAIL_BEAST_NAILS),
            new Recipe(ItemID.SANFEW_SERUM4, Ingredients.MIXTURE_STEP_2_4, Ingredients.NAIL_BEAST_NAILS),
            new Recipe(ItemID.SUPER_DEFENCE3, Ingredients.CADANTINE_UNF, Ingredients.WHITE_BERRIES),
            new Recipe(ItemID.ANTIDOTE3, Ingredients.ANTIDOTE_P_UNF, Ingredients.YEW_ROOTS),
            new Recipe(ItemID.ANTIFIRE_POTION3, Ingredients.LANTADYME_UNF, Ingredients.DRAGON_SCALE_DUST),
            new Recipe(ItemID.RANGING_POTION3, Ingredients.DWARF_WEED_UNF, Ingredients.WINE_OF_ZAMORAK),
            new Recipe(ItemID.WEAPON_POISON_5937, Ingredients.WEAPON_POISON_P_UNF, Ingredients.RED_SPIDERS_EGGS),
            new Recipe(ItemID.MAGIC_POTION3, Ingredients.LANTADYME_UNF, Ingredients.POTATO_CACTUS),
            new Recipe(ItemID.STAMINA_POTION1, Ingredients.SUPER_ENERGY_1, Ingredients.AMYLASE_CRYSTAL),
            new Recipe(ItemID.STAMINA_POTION2, Ingredients.SUPER_ENERGY_2, Ingredients.AMYLASE_CRYSTAL.clone(2)),
            new Recipe(ItemID.STAMINA_POTION3, Ingredients.SUPER_ENERGY_3, Ingredients.AMYLASE_CRYSTAL.clone(3)),
            new Recipe(ItemID.STAMINA_POTION4, Ingredients.SUPER_ENERGY_4, Ingredients.AMYLASE_CRYSTAL.clone(4)),
            new Recipe(ItemID.ZAMORAK_BREW3, Ingredients.TORSTOL_UNF, Ingredients.JANGERBERRIES),
            new Recipe(ItemID.ANTIDOTE3_5954, Ingredients.ANTIDOTE_PP_UNF, Ingredients.MAGIC_ROOTS),
            new Recipe(ItemID.BASTION_POTION3, Ingredients.CADANTINE_BLOOD_UNF, Ingredients.WINE_OF_ZAMORAK),
            new Recipe(ItemID.BATTLEMAGE_POTION3, Ingredients.CADANTINE_BLOOD_UNF, Ingredients.POTATO_CACTUS),
            new Recipe(ItemID.SARADOMIN_BREW3, Ingredients.TOADFLAX_UNF, Ingredients.CRUSHED_NEST),
            new Recipe(ItemID.WEAPON_POISON_5940, Ingredients.WEAPON_POISON_PP_UNF, Ingredients.POISON_IVY_BERRIES),
            new Recipe(ItemID.EXTENDED_ANTIFIRE1, Ingredients.ANTIFIRE_1, Ingredients.LAVA_SCALE_SHARD),
            new Recipe(ItemID.EXTENDED_ANTIFIRE2, Ingredients.ANTIFIRE_2, Ingredients.LAVA_SCALE_SHARD.clone(2)),
            new Recipe(ItemID.EXTENDED_ANTIFIRE3, Ingredients.ANTIFIRE_3, Ingredients.LAVA_SCALE_SHARD.clone(3)),
            new Recipe(ItemID.EXTENDED_ANTIFIRE4, Ingredients.ANTIFIRE_4, Ingredients.LAVA_SCALE_SHARD.clone(4)),
            new Recipe(ItemID.ANCIENT_BREW3, Ingredients.DWARF_WEED_UNF, Ingredients.NIHIL_DUST),
            new Recipe(ItemID.ANTIVENOM1, Ingredients.ANTIDOTE_PP_1, Ingredients.ZULRAHS_SCALES.clone(5)),
            new Recipe(ItemID.ANTIVENOM2, Ingredients.ANTIDOTE_PP_2, Ingredients.ZULRAHS_SCALES.clone(10)),
            new Recipe(ItemID.ANTIVENOM3, Ingredients.ANTIDOTE_PP_3, Ingredients.ZULRAHS_SCALES.clone(15)),
            new Recipe(ItemID.ANTIVENOM4, Ingredients.ANTIDOTE_PP_4, Ingredients.ZULRAHS_SCALES.clone(20)),
            new Recipe(ItemID.SUPER_COMBAT_POTION4, Ingredients.SUPER_ATTACK_4, Ingredients.SUPER_STRENGTH_4, Ingredients.SUPER_DEFENCE_4, Ingredients.TORSTOL),
            new Recipe(ItemID.SUPER_COMBAT_POTION4, Ingredients.SUPER_ATTACK_4, Ingredients.SUPER_STRENGTH_4, Ingredients.SUPER_DEFENCE_4, Ingredients.TORSTOL_UNF),
            new Recipe(ItemID.SUPER_ANTIFIRE_POTION4, Ingredients.ANTIFIRE_4, Ingredients.CRUSHED_SUPERIOR_DRAGON_BONES),
            new Recipe(ItemID.ANTIVENOM4_12913, Ingredients.ANTIVENOM_4, Ingredients.TORSTOL),
            new Recipe(ItemID.EXTENDED_SUPER_ANTIFIRE1, Ingredients.SUPER_ANTIFIRE_1, Ingredients.LAVA_SCALE_SHARD),
            new Recipe(ItemID.EXTENDED_SUPER_ANTIFIRE2, Ingredients.SUPER_ANTIFIRE_2, Ingredients.LAVA_SCALE_SHARD.clone(2)),
            new Recipe(ItemID.EXTENDED_SUPER_ANTIFIRE3, Ingredients.SUPER_ANTIFIRE_3, Ingredients.LAVA_SCALE_SHARD.clone(3)),
            new Recipe(ItemID.EXTENDED_SUPER_ANTIFIRE4, Ingredients.SUPER_ANTIFIRE_4, Ingredients.LAVA_SCALE_SHARD.clone(4)),
            // Divine potions
            new Recipe(ItemID.DIVINE_SUPER_ATTACK_POTION1, Ingredients.SUPER_ATTACK_1, Ingredients.CRYSTAL_DUST),
            new Recipe(ItemID.DIVINE_SUPER_ATTACK_POTION2, Ingredients.SUPER_ATTACK_2, Ingredients.CRYSTAL_DUST.clone(2)),
            new Recipe(ItemID.DIVINE_SUPER_ATTACK_POTION3, Ingredients.SUPER_ATTACK_3, Ingredients.CRYSTAL_DUST.clone(3)),
            new Recipe(ItemID.DIVINE_SUPER_ATTACK_POTION4, Ingredients.SUPER_ATTACK_4, Ingredients.CRYSTAL_DUST.clone(4)),
            new Recipe(ItemID.DIVINE_SUPER_STRENGTH_POTION1, Ingredients.SUPER_STRENGTH_1, Ingredients.CRYSTAL_DUST),
            new Recipe(ItemID.DIVINE_SUPER_STRENGTH_POTION2, Ingredients.SUPER_STRENGTH_2, Ingredients.CRYSTAL_DUST.clone(2)),
            new Recipe(ItemID.DIVINE_SUPER_STRENGTH_POTION3, Ingredients.SUPER_STRENGTH_3, Ingredients.CRYSTAL_DUST.clone(3)),
            new Recipe(ItemID.DIVINE_SUPER_STRENGTH_POTION4, Ingredients.SUPER_STRENGTH_4, Ingredients.CRYSTAL_DUST.clone(4)),
            new Recipe(ItemID.DIVINE_SUPER_DEFENCE_POTION1, Ingredients.SUPER_DEFENCE_1, Ingredients.CRYSTAL_DUST),
            new Recipe(ItemID.DIVINE_SUPER_DEFENCE_POTION2, Ingredients.SUPER_DEFENCE_2, Ingredients.CRYSTAL_DUST.clone(2)),
            new Recipe(ItemID.DIVINE_SUPER_DEFENCE_POTION3, Ingredients.SUPER_DEFENCE_3, Ingredients.CRYSTAL_DUST.clone(3)),
            new Recipe(ItemID.DIVINE_SUPER_DEFENCE_POTION4, Ingredients.SUPER_DEFENCE_4, Ingredients.CRYSTAL_DUST.clone(4)),
            new Recipe(ItemID.DIVINE_RANGING_POTION1, Ingredients.RANGING_1, Ingredients.CRYSTAL_DUST),
            new Recipe(ItemID.DIVINE_RANGING_POTION2, Ingredients.RANGING_2, Ingredients.CRYSTAL_DUST.clone(2)),
            new Recipe(ItemID.DIVINE_RANGING_POTION3, Ingredients.RANGING_3, Ingredients.CRYSTAL_DUST.clone(3)),
            new Recipe(ItemID.DIVINE_RANGING_POTION4, Ingredients.RANGING_4, Ingredients.CRYSTAL_DUST.clone(4)),
            new Recipe(ItemID.DIVINE_MAGIC_POTION1, Ingredients.MAGIC_1, Ingredients.CRYSTAL_DUST),
            new Recipe(ItemID.DIVINE_MAGIC_POTION2, Ingredients.MAGIC_2, Ingredients.CRYSTAL_DUST.clone(2)),
            new Recipe(ItemID.DIVINE_MAGIC_POTION3, Ingredients.MAGIC_3, Ingredients.CRYSTAL_DUST.clone(3)),
            new Recipe(ItemID.DIVINE_MAGIC_POTION4, Ingredients.MAGIC_4, Ingredients.CRYSTAL_DUST.clone(4)),
            new Recipe(ItemID.DIVINE_BASTION_POTION1, Ingredients.BASTION_1, Ingredients.CRYSTAL_DUST),
            new Recipe(ItemID.DIVINE_BASTION_POTION2, Ingredients.BASTION_2, Ingredients.CRYSTAL_DUST.clone(2)),
            new Recipe(ItemID.DIVINE_BASTION_POTION3, Ingredients.BASTION_3, Ingredients.CRYSTAL_DUST.clone(3)),
            new Recipe(ItemID.DIVINE_BASTION_POTION4, Ingredients.BASTION_4, Ingredients.CRYSTAL_DUST.clone(4)),
            new Recipe(ItemID.DIVINE_BATTLEMAGE_POTION1, Ingredients.BATTLEMAGE_1, Ingredients.CRYSTAL_DUST),
            new Recipe(ItemID.DIVINE_BATTLEMAGE_POTION2, Ingredients.BATTLEMAGE_2, Ingredients.CRYSTAL_DUST.clone(2)),
            new Recipe(ItemID.DIVINE_BATTLEMAGE_POTION3, Ingredients.BATTLEMAGE_3, Ingredients.CRYSTAL_DUST.clone(3)),
            new Recipe(ItemID.DIVINE_BATTLEMAGE_POTION4, Ingredients.BATTLEMAGE_4, Ingredients.CRYSTAL_DUST.clone(4)),
            new Recipe(ItemID.DIVINE_SUPER_COMBAT_POTION1, Ingredients.SUPER_COMBAT_1, Ingredients.CRYSTAL_DUST),
            new Recipe(ItemID.DIVINE_SUPER_COMBAT_POTION2, Ingredients.SUPER_COMBAT_2, Ingredients.CRYSTAL_DUST.clone(2)),
            new Recipe(ItemID.DIVINE_SUPER_COMBAT_POTION3, Ingredients.SUPER_COMBAT_3, Ingredients.CRYSTAL_DUST.clone(3)),
            new Recipe(ItemID.DIVINE_SUPER_COMBAT_POTION4, Ingredients.SUPER_COMBAT_4, Ingredients.CRYSTAL_DUST.clone(4)),
            // These potions are more correctly classified as unfinished, however they take 2 ticks to make
            new Recipe(ItemID.ANTIDOTE_UNF, Ingredients.COCONUT_MILK, Ingredients.TOADFLAX),
            new Recipe(ItemID.ANTIDOTE_UNF_5951, Ingredients.COCONUT_MILK, Ingredients.IRIT_LEAF),
            new Recipe(ItemID.WEAPON_POISON_UNF, Ingredients.COCONUT_MILK, Ingredients.CACTUS_SPINE),
            new Recipe(ItemID.WEAPON_POISON_UNF_5939, Ingredients.COCONUT_MILK, Ingredients.CAVE_NIGHTSHADE)
            // @formatter:on
    };

    public static final int[] GRIMY_HERBS = {
            ItemID.GRIMY_GUAM_LEAF, ItemID.GRIMY_MARRENTILL, ItemID.GRIMY_TARROMIN, ItemID.GRIMY_HARRALANDER,
            ItemID.GRIMY_RANARR_WEED, ItemID.GRIMY_TOADFLAX, ItemID.GRIMY_IRIT_LEAF, ItemID.GRIMY_AVANTOE,
            ItemID.GRIMY_KWUARM, ItemID.GRIMY_SNAPDRAGON, ItemID.GRIMY_CADANTINE, ItemID.GRIMY_LANTADYME,
            ItemID.GRIMY_DWARF_WEED, ItemID.GRIMY_TORSTOL, ItemID.GRIMY_ARDRIGAL, ItemID.GRIMY_SITO_FOIL,
            ItemID.GRIMY_ROGUES_PURSE, ItemID.GRIMY_SNAKE_WEED, ItemID.GRIMY_VOLENCIA_MOSS, ItemID.GRIMY_BUCHU_LEAF,
            ItemID.GRIMY_NOXIFER, ItemID.GRIMY_GOLPAR
    };

    interface HerbTeaMix {
        int HERB_TEA_MIX_H = ItemID.HERB_TEA_MIX;
        int HERB_TEA_MIX_G = ItemID.HERB_TEA_MIX_4466;
        int HERB_TEA_MIX_M = ItemID.HERB_TEA_MIX_4468;
        int HERB_TEA_MIX_HM = ItemID.HERB_TEA_MIX_4470;
        int HERB_TEA_MIX_HG = ItemID.HERB_TEA_MIX_4472;
        int HERB_TEA_MIX_GG = ItemID.HERB_TEA_MIX_4474;
        int HERB_TEA_MIX_GM = ItemID.HERB_TEA_MIX_4476;
        int HERB_TEA_MIX_HMG = ItemID.HERB_TEA_MIX_4478;
        int HERB_TEA_MIX_GGM = ItemID.HERB_TEA_MIX_4480;
        int HERB_TEA_MIX_GGH = ItemID.HERB_TEA_MIX_4482;
    }

    interface SanfewSerum {
        int MIX_1_1 = ItemID.MIXTURE__STEP_11;
        int MIX_1_2 = ItemID.MIXTURE__STEP_12;
        int MIX_1_3 = ItemID.MIXTURE__STEP_13;
        int MIX_1_4 = ItemID.MIXTURE__STEP_14;
        int MIX_2_1 = ItemID.MIXTURE__STEP_21;
        int MIX_2_2 = ItemID.MIXTURE__STEP_22;
        int MIX_2_3 = ItemID.MIXTURE__STEP_23;
        int MIX_2_4 = ItemID.MIXTURE__STEP_24;
    }

    public interface Ingredients {
        // Base
        Ingredient VIAL_OF_WATER = new Ingredient(ItemID.VIAL_OF_WATER);
        Ingredient VIAL_OF_BLOOD = new Ingredient(ItemID.VIAL_OF_BLOOD);
        Ingredient COCONUT_MILK = new Ingredient(ItemID.COCONUT_MILK);
        Ingredient CUP_OF_HOT_WATER = new Ingredient(ItemID.CUP_OF_HOT_WATER);
        Ingredient GUAM_UNF = new Ingredient(ItemID.GUAM_POTION_UNF);
        Ingredient ROGUES_PURSE_UNF = new Ingredient(ItemID.UNFINISHED_POTION_4840);
        Ingredient MARRENTILL_UNF = new Ingredient(ItemID.MARRENTILL_POTION_UNF);
        Ingredient TARROMIN_UNF = new Ingredient(ItemID.TARROMIN_POTION_UNF);
        Ingredient ASH_POTION = new Ingredient(ItemID.UNFINISHED_POTION);
        Ingredient HARRALANDER_UNF = new Ingredient(ItemID.HARRALANDER_POTION_UNF);
        Ingredient MAGIC_ESSENCE_UNF = new Ingredient(ItemID.MAGIC_ESSENCE_UNF);
        Ingredient RANARR_UNF = new Ingredient(ItemID.RANARR_POTION_UNF);
        Ingredient TOADFLAX_UNF = new Ingredient(ItemID.TOADFLAX_POTION_UNF);
        Ingredient IRIT_UNF = new Ingredient(ItemID.IRIT_POTION_UNF);
        Ingredient AVANTOE_UNF = new Ingredient(ItemID.AVANTOE_POTION_UNF);
        Ingredient KWUARM_UNF = new Ingredient(ItemID.KWUARM_POTION_UNF);
        Ingredient SNAPDRAGON_UNF = new Ingredient(ItemID.SNAPDRAGON_POTION_UNF);
        Ingredient CADANTINE_UNF = new Ingredient(ItemID.CADANTINE_POTION_UNF);
        Ingredient LANTADYME_UNF = new Ingredient(ItemID.LANTADYME_POTION_UNF);
        Ingredient DWARF_WEED_UNF = new Ingredient(ItemID.DWARF_WEED_POTION_UNF);
        Ingredient TORSTOL_UNF = new Ingredient(ItemID.TORSTOL_POTION_UNF);
        Ingredient CADANTINE_BLOOD_UNF = new Ingredient(ItemID.CADANTINE_BLOOD_POTION_UNF);
        // Combination base
        Ingredient RESTORE_1 = new Ingredient(ItemID.RESTORE_POTION1);
        Ingredient RESTORE_2 = new Ingredient(ItemID.RESTORE_POTION2);
        Ingredient RESTORE_3 = new Ingredient(ItemID.RESTORE_POTION3);
        Ingredient RESTORE_4 = new Ingredient(ItemID.RESTORE_POTION4);
        Ingredient SUPER_RESTORE_1 = new Ingredient(ItemID.SUPER_RESTORE1);
        Ingredient SUPER_RESTORE_2 = new Ingredient(ItemID.SUPER_RESTORE2);
        Ingredient SUPER_RESTORE_3 = new Ingredient(ItemID.SUPER_RESTORE3);
        Ingredient SUPER_RESTORE_4 = new Ingredient(ItemID.SUPER_RESTORE4);
        Ingredient GUTHIX_BALANCE_UNF_1 = new Ingredient(ItemID.GUTHIX_BALANCE_UNF_7658);
        Ingredient GUTHIX_BALANCE_UNF_2 = new Ingredient(ItemID.GUTHIX_BALANCE_UNF_7656);
        Ingredient GUTHIX_BALANCE_UNF_3 = new Ingredient(ItemID.GUTHIX_BALANCE_UNF_7654);
        Ingredient GUTHIX_BALANCE_UNF_4 = new Ingredient(ItemID.GUTHIX_BALANCE_UNF);
        Ingredient SUPER_ENERGY_1 = new Ingredient(ItemID.SUPER_ENERGY1);
        Ingredient SUPER_ENERGY_2 = new Ingredient(ItemID.SUPER_ENERGY2);
        Ingredient SUPER_ENERGY_3 = new Ingredient(ItemID.SUPER_ENERGY3);
        Ingredient SUPER_ENERGY_4 = new Ingredient(ItemID.SUPER_ENERGY4);
        Ingredient WEAPON_POISON_P_UNF = new Ingredient(ItemID.WEAPON_POISON_UNF);
        Ingredient WEAPON_POISON_PP_UNF = new Ingredient(ItemID.WEAPON_POISON_UNF_5939);
        Ingredient ANTIDOTE_P_UNF = new Ingredient(ItemID.ANTIDOTE_UNF);
        Ingredient ANTIDOTE_PP_UNF = new Ingredient(ItemID.ANTIDOTE_UNF_5951);
        Ingredient ANTIFIRE_1 = new Ingredient(ItemID.ANTIFIRE_POTION1);
        Ingredient ANTIFIRE_2 = new Ingredient(ItemID.ANTIFIRE_POTION2);
        Ingredient ANTIFIRE_3 = new Ingredient(ItemID.ANTIFIRE_POTION3);
        Ingredient ANTIFIRE_4 = new Ingredient(ItemID.ANTIFIRE_POTION4);
        Ingredient SUPER_ANTIFIRE_1 = new Ingredient(ItemID.SUPER_ANTIFIRE_POTION1);
        Ingredient SUPER_ANTIFIRE_2 = new Ingredient(ItemID.SUPER_ANTIFIRE_POTION2);
        Ingredient SUPER_ANTIFIRE_3 = new Ingredient(ItemID.SUPER_ANTIFIRE_POTION3);
        Ingredient SUPER_ANTIFIRE_4 = new Ingredient(ItemID.SUPER_ANTIFIRE_POTION4);
        Ingredient ANTIVENOM_4 = new Ingredient(ItemID.ANTIVENOM4);
        Ingredient ANTIDOTE_PP_1 = new Ingredient(ItemID.ANTIDOTE1_5958);
        Ingredient ANTIDOTE_PP_2 = new Ingredient(ItemID.ANTIDOTE2_5956);
        Ingredient ANTIDOTE_PP_3 = new Ingredient(ItemID.ANTIDOTE3_5954);
        Ingredient ANTIDOTE_PP_4 = new Ingredient(ItemID.ANTIDOTE4_5952);
        Ingredient SUPER_ATTACK_1 = new Ingredient(ItemID.SUPER_ATTACK1);
        Ingredient SUPER_ATTACK_2 = new Ingredient(ItemID.SUPER_ATTACK2);
        Ingredient SUPER_ATTACK_3 = new Ingredient(ItemID.SUPER_ATTACK3);
        Ingredient SUPER_ATTACK_4 = new Ingredient(ItemID.SUPER_ATTACK4);
        Ingredient SUPER_STRENGTH_1 = new Ingredient(ItemID.SUPER_STRENGTH1);
        Ingredient SUPER_STRENGTH_2 = new Ingredient(ItemID.SUPER_STRENGTH2);
        Ingredient SUPER_STRENGTH_3 = new Ingredient(ItemID.SUPER_STRENGTH3);
        Ingredient SUPER_STRENGTH_4 = new Ingredient(ItemID.SUPER_STRENGTH4);
        Ingredient SUPER_DEFENCE_1 = new Ingredient(ItemID.SUPER_DEFENCE1);
        Ingredient SUPER_DEFENCE_2 = new Ingredient(ItemID.SUPER_DEFENCE2);
        Ingredient SUPER_DEFENCE_3 = new Ingredient(ItemID.SUPER_DEFENCE3);
        Ingredient SUPER_DEFENCE_4 = new Ingredient(ItemID.SUPER_DEFENCE4);
        Ingredient RANGING_1 = new Ingredient(ItemID.RANGING_POTION1);
        Ingredient RANGING_2 = new Ingredient(ItemID.RANGING_POTION2);
        Ingredient RANGING_3 = new Ingredient(ItemID.RANGING_POTION3);
        Ingredient RANGING_4 = new Ingredient(ItemID.RANGING_POTION4);
        Ingredient MAGIC_1 = new Ingredient(ItemID.MAGIC_POTION1);
        Ingredient MAGIC_2 = new Ingredient(ItemID.MAGIC_POTION2);
        Ingredient MAGIC_3 = new Ingredient(ItemID.MAGIC_POTION3);
        Ingredient MAGIC_4 = new Ingredient(ItemID.MAGIC_POTION4);
        Ingredient BASTION_1 = new Ingredient(ItemID.BASTION_POTION1);
        Ingredient BASTION_2 = new Ingredient(ItemID.BASTION_POTION2);
        Ingredient BASTION_3 = new Ingredient(ItemID.BASTION_POTION3);
        Ingredient BASTION_4 = new Ingredient(ItemID.BASTION_POTION4);
        Ingredient BATTLEMAGE_1 = new Ingredient(ItemID.BATTLEMAGE_POTION1);
        Ingredient BATTLEMAGE_2 = new Ingredient(ItemID.BATTLEMAGE_POTION2);
        Ingredient BATTLEMAGE_3 = new Ingredient(ItemID.BATTLEMAGE_POTION3);
        Ingredient BATTLEMAGE_4 = new Ingredient(ItemID.BATTLEMAGE_POTION4);
        Ingredient SUPER_COMBAT_1 = new Ingredient(ItemID.SUPER_COMBAT_POTION1);
        Ingredient SUPER_COMBAT_2 = new Ingredient(ItemID.SUPER_COMBAT_POTION2);
        Ingredient SUPER_COMBAT_3 = new Ingredient(ItemID.SUPER_COMBAT_POTION3);
        Ingredient SUPER_COMBAT_4 = new Ingredient(ItemID.SUPER_COMBAT_POTION4);
        // Sanfew serum mixtures
        Ingredient MIXTURE_STEP_1_1 = new Ingredient(ItemID.MIXTURE__STEP_11);
        Ingredient MIXTURE_STEP_1_2 = new Ingredient(ItemID.MIXTURE__STEP_12);
        Ingredient MIXTURE_STEP_1_3 = new Ingredient(ItemID.MIXTURE__STEP_13);
        Ingredient MIXTURE_STEP_1_4 = new Ingredient(ItemID.MIXTURE__STEP_14);
        Ingredient MIXTURE_STEP_2_1 = new Ingredient(ItemID.MIXTURE__STEP_21);
        Ingredient MIXTURE_STEP_2_2 = new Ingredient(ItemID.MIXTURE__STEP_22);
        Ingredient MIXTURE_STEP_2_3 = new Ingredient(ItemID.MIXTURE__STEP_23);
        Ingredient MIXTURE_STEP_2_4 = new Ingredient(ItemID.MIXTURE__STEP_24);
        // Guthix rest tea mixtures
        Ingredient HERB_TEA_MIX_H = new Ingredient(ItemID.HERB_TEA_MIX);
        Ingredient HERB_TEA_MIX_G = new Ingredient(ItemID.HERB_TEA_MIX_4466);
        Ingredient HERB_TEA_MIX_M = new Ingredient(ItemID.HERB_TEA_MIX_4468);
        Ingredient HERB_TEA_MIX_HM = new Ingredient(ItemID.HERB_TEA_MIX_4470);
        Ingredient HERB_TEA_MIX_HG = new Ingredient(ItemID.HERB_TEA_MIX_4472);
        Ingredient HERB_TEA_MIX_GG = new Ingredient(ItemID.HERB_TEA_MIX_4474);
        Ingredient HERB_TEA_MIX_GM = new Ingredient(ItemID.HERB_TEA_MIX_4476);
        Ingredient HERB_TEA_MIX_HMG = new Ingredient(ItemID.HERB_TEA_MIX_4478);
        Ingredient HERB_TEA_MIX_GGM = new Ingredient(ItemID.HERB_TEA_MIX_4480);
        Ingredient HERB_TEA_MIX_GGH = new Ingredient(ItemID.HERB_TEA_MIX_4482);
        // Herbs
        Ingredient GUAM_LEAF = new Ingredient(ItemID.GUAM_LEAF);
        Ingredient ROGUES_PURSE = new Ingredient(ItemID.ROGUES_PURSE);
        Ingredient MARRENTILL = new Ingredient(ItemID.MARRENTILL);
        Ingredient TARROMIN = new Ingredient(ItemID.TARROMIN);
        Ingredient HARRALANDER = new Ingredient(ItemID.HARRALANDER);
        Ingredient STAR_FLOWER = new Ingredient(ItemID.STAR_FLOWER);
        Ingredient RANARR_WEED = new Ingredient(ItemID.RANARR_WEED);
        Ingredient TOADFLAX = new Ingredient(ItemID.TOADFLAX);
        Ingredient IRIT_LEAF = new Ingredient(ItemID.IRIT_LEAF);
        Ingredient AVANTOE = new Ingredient(ItemID.AVANTOE);
        Ingredient KWUARM = new Ingredient(ItemID.KWUARM);
        Ingredient SNAPDRAGON = new Ingredient(ItemID.SNAPDRAGON);
        Ingredient CADANTINE = new Ingredient(ItemID.CADANTINE);
        Ingredient LANTADYME = new Ingredient(ItemID.LANTADYME);
        Ingredient DWARF_WEED = new Ingredient(ItemID.DWARF_WEED);
        Ingredient TORSTOL = new Ingredient(ItemID.TORSTOL);
        // Secondaries
        Ingredient GARLIC = new Ingredient(ItemID.GARLIC);
        Ingredient EYE_OF_NEWT = new Ingredient(ItemID.EYE_OF_NEWT);
        Ingredient UNICORN_HORN_DUST = new Ingredient(ItemID.UNICORN_HORN_DUST);
        Ingredient SNAKE_WEED = new Ingredient(ItemID.SNAKE_WEED);
        Ingredient LIMPWURT_ROOT = new Ingredient(ItemID.LIMPWURT_ROOT);
        Ingredient ASHES = new Ingredient(ItemID.ASHES);
        Ingredient VOLCANIC_ASH = new Ingredient(ItemID.VOLCANIC_ASH);
        Ingredient RED_SPIDERS_EGGS = new Ingredient(ItemID.RED_SPIDERS_EGGS);
        Ingredient SILVER_DUST = new Ingredient(ItemID.SILVER_DUST);
        Ingredient BLAMISH_SNAIL_SLIME = new Ingredient(ItemID.BLAMISH_SNAIL_SLIME);
        Ingredient CHOCOLATE_DUST = new Ingredient(ItemID.CHOCOLATE_DUST);
        Ingredient WHITE_BERRIES = new Ingredient(ItemID.WHITE_BERRIES);
        Ingredient TOADS_LEGS = new Ingredient(ItemID.TOADS_LEGS);
        Ingredient GOAT_HORN_DUST = new Ingredient(ItemID.GOAT_HORN_DUST);
        Ingredient SNAPE_GRASS = new Ingredient(ItemID.SNAPE_GRASS);
        Ingredient PHARMAKOS_BERRIES = new Ingredient(ItemID.PHARMAKOS_BERRIES);
        Ingredient MORT_MYRE_FUNGUS = new Ingredient(ItemID.MORT_MYRE_FUNGUS);
        Ingredient SHRUNK_OGLEROOT = new Ingredient(ItemID.SHRUNK_OGLEROOT);
        Ingredient KEBBIT_TEETH_DUST = new Ingredient(ItemID.KEBBIT_TEETH_DUST);
        Ingredient GORAK_CLAW_POWDER = new Ingredient(ItemID.GORAK_CLAW_POWDER);
        Ingredient DRAGON_SCALE_DUST = new Ingredient(ItemID.DRAGON_SCALE_DUST);
        Ingredient NAIL_BEAST_NAILS = new Ingredient(ItemID.NAIL_BEAST_NAILS);
        Ingredient YEW_ROOTS = new Ingredient(ItemID.YEW_ROOTS);
        Ingredient WINE_OF_ZAMORAK = new Ingredient(ItemID.WINE_OF_ZAMORAK);
        Ingredient POTATO_CACTUS = new Ingredient(ItemID.POTATO_CACTUS);
        Ingredient JANGERBERRIES = new Ingredient(ItemID.JANGERBERRIES);
        Ingredient MAGIC_ROOTS = new Ingredient(ItemID.MAGIC_ROOTS);
        Ingredient CRUSHED_NEST = new Ingredient(ItemID.CRUSHED_NEST);
        Ingredient POISON_IVY_BERRIES = new Ingredient(ItemID.POISON_IVY_BERRIES);
        Ingredient NIHIL_DUST = new Ingredient(ItemID.NIHIL_DUST);
        Ingredient LAVA_SCALE_SHARD = new Ingredient(ItemID.LAVA_SCALE_SHARD);
        Ingredient CRYSTAL_DUST = new Ingredient(ItemID.CRYSTAL_DUST);
        Ingredient CRUSHED_SUPERIOR_DRAGON_BONES = new Ingredient(ItemID.CRUSHED_SUPERIOR_DRAGON_BONES);
        Ingredient ZULRAHS_SCALES = new Ingredient(ItemID.ZULRAHS_SCALES);
        Ingredient CAVE_NIGHTSHADE = new Ingredient(ItemID.CAVE_NIGHTSHADE);
        Ingredient AMYLASE_CRYSTAL = new Ingredient(ItemID.AMYLASE_CRYSTAL);
        Ingredient CACTUS_SPINE = new Ingredient(ItemID.CACTUS_SPINE);
    }

}
