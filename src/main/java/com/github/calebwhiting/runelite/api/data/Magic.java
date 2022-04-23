package com.github.calebwhiting.runelite.api.data;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.ItemContainer;
import net.runelite.api.ItemID;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

@UtilityClass
public class Magic {

    interface StaveIDs {
        IDs SMOKE = new IDs(ItemID.SMOKE_BATTLESTAFF, ItemID.MYSTIC_SMOKE_STAFF);
        IDs DUST = new IDs(ItemID.DUST_BATTLESTAFF, ItemID.MYSTIC_DUST_STAFF);
        IDs MIST = new IDs(ItemID.MIST_BATTLESTAFF, ItemID.MYSTIC_MIST_STAFF);
        IDs MUD = new IDs(ItemID.MUD_BATTLESTAFF, ItemID.MYSTIC_MUD_STAFF);
        IDs LAVA = new IDs(
                ItemID.LAVA_BATTLESTAFF,
                ItemID.LAVA_BATTLESTAFF_21198,
                ItemID.MYSTIC_LAVA_STAFF,
                ItemID.MYSTIC_LAVA_STAFF_21200
        );
        IDs STEAM = new IDs(
                ItemID.STEAM_BATTLESTAFF,
                ItemID.STEAM_BATTLESTAFF_12795,
                ItemID.MYSTIC_STEAM_STAFF,
                ItemID.MYSTIC_STEAM_STAFF_12796
        );
        IDs AIR = new IDs(
                SMOKE, DUST, STEAM,
                ItemID.STAFF_OF_AIR,
                ItemID.AIR_BATTLESTAFF,
                ItemID.MYSTIC_AIR_STAFF
        );
        IDs WATER = new IDs(
                MUD, MIST, STEAM,
                ItemID.STAFF_OF_WATER,
                ItemID.WATER_BATTLESTAFF,
                ItemID.MYSTIC_WATER_STAFF
        );
        IDs EARTH = new IDs(
                DUST, MUD, LAVA,
                ItemID.STAFF_OF_EARTH,
                ItemID.EARTH_BATTLESTAFF,
                ItemID.MYSTIC_EARTH_STAFF
        );
        IDs FIRE = new IDs(
                SMOKE, STEAM, LAVA,
                ItemID.STAFF_OF_FIRE,
                ItemID.FIRE_BATTLESTAFF,
                ItemID.MYSTIC_FIRE_STAFF
        );
    }

    interface RuneIDs {
        IDs SMOKE = new IDs(ItemID.SMOKE_RUNE);
        IDs DUST = new IDs(ItemID.DUST_RUNE);
        IDs LAVA = new IDs(ItemID.LAVA_RUNE);
        IDs MIST = new IDs(ItemID.MIST_RUNE);
        IDs MUD = new IDs(ItemID.MUD_RUNE);
        IDs STEAM = new IDs(ItemID.STEAM_RUNE);
        IDs AIR = new IDs(
                SMOKE, DUST, MIST,
                ItemID.AIR_RUNE,
                ItemID.AIR_RUNE_NZ,
                ItemID.AIR_RUNE_6422,
                ItemID.AIR_RUNE_7558,
                ItemID.AIR_RUNE_9693,
                ItemID.AIR_RUNE_11688
        );

        IDs WATER = new IDs(
                MIST, MUD, STEAM,
                ItemID.WATER_RUNE,
                ItemID.WATER_RUNE_NZ,
                ItemID.WATER_RUNE_6424,
                ItemID.WATER_RUNE_7556,
                ItemID.WATER_RUNE_9691,
                ItemID.WATER_RUNE_11687
        );
        IDs EARTH = new IDs(
                DUST, LAVA, MUD,
                ItemID.EARTH_RUNE,
                ItemID.EARTH_RUNE_NZ,
                ItemID.EARTH_RUNE_6426,
                ItemID.EARTH_RUNE_9695,
                ItemID.EARTH_RUNE_11689
        );
        IDs FIRE = new IDs(
                SMOKE, LAVA, STEAM,
                ItemID.FIRE_RUNE,
                ItemID.FIRE_RUNE_NZ,
                ItemID.FIRE_RUNE_6428,
                ItemID.FIRE_RUNE_7554,
                ItemID.FIRE_RUNE_9699,
                ItemID.FIRE_RUNE_11686
        );
        IDs MIND = new IDs(
                ItemID.MIND_RUNE,
                ItemID.MIND_RUNE_6436,
                ItemID.MIND_RUNE_9697,
                ItemID.MIND_RUNE_11690
        );
        IDs BODY = new IDs(
                ItemID.BODY_RUNE,
                ItemID.BODY_RUNE_6438,
                ItemID.BODY_RUNE_11691
        );
        IDs COSMIC = new IDs(
                ItemID.COSMIC_RUNE,
                ItemID.COSMIC_RUNE_11696
        );
        IDs CHAOS = new IDs(
                ItemID.CHAOS_RUNE,
                ItemID.CHAOS_RUNE_NZ,
                ItemID.CHAOS_RUNE_6430,
                ItemID.CHAOS_RUNE_7560,
                ItemID.CHAOS_RUNE_11694
        );
        IDs ASTRAL = new IDs(
                ItemID.ASTRAL_RUNE,
                ItemID.ASTRAL_RUNE_11699
        );
        IDs NATURE = new IDs(
                ItemID.NATURE_RUNE,
                ItemID.NATURE_RUNE_11693
        );
        IDs LAW = new IDs(
                ItemID.LAW_RUNE,
                ItemID.LAW_RUNE_6434,
                ItemID.LAW_RUNE_11695
        );
        IDs DEATH = new IDs(
                ItemID.DEATH_RUNE,
                ItemID.DEATH_RUNE_NZ,
                ItemID.DEATH_RUNE_6432,
                ItemID.DEATH_RUNE_11692
        );
        IDs BLOOD = new IDs(
                ItemID.BLOOD_RUNE,
                ItemID.BLOOD_RUNE_NZ,
                ItemID.BLOOD_RUNE_11697
        );
        IDs SOUL = new IDs(
                ItemID.SOUL_RUNE,
                ItemID.SOUL_RUNE_11698
        );
        IDs WRATH = new IDs(
                ItemID.WRATH_RUNE,
                ItemID.WRATH_RUNE_22208
        );
    }

    @Getter
    public enum StandardSpell implements Spell {
        LUMBRIDGE_HOME_TELEPORT(
                "Lumbridge Home Teleport", 0, 0
        ),
        WIND_STRIKE(
                "Wind Strike", 1, 1,
                new RuneRequirement(Rune.AIR, 1),
                new RuneRequirement(Rune.MIND, 1)
        ),
        CONFUSE(
                "Confuse", 2, 3,
                new RuneRequirement(Rune.BODY, 1),
                new RuneRequirement(Rune.EARTH, 2),
                new RuneRequirement(Rune.WATER, 3)
        ),
        ENCHANT_CROSSBOW_BOLT(
                "Enchant Crossbow Bolt", 3, 4
        ),
        WATER_STRIKE(
                "Water Strike", 4, 5,
                new RuneRequirement(Rune.AIR, 1),
                new RuneRequirement(Rune.MIND, 1),
                new RuneRequirement(Rune.WATER, 1)
        ),
        LVL_1_ENCHANT(
                "Lvl-1 Enchant", 5, 7,
                new RuneRequirement(Rune.COSMIC, 1),
                new RuneRequirement(Rune.WATER, 1)
        ),
        EARTH_STRIKE(
                "Earth Strike", 6, 9,
                new RuneRequirement(Rune.AIR, 1),
                new RuneRequirement(Rune.EARTH, 2),
                new RuneRequirement(Rune.MIND, 1)
        ),
        WEAKEN(
                "Weaken", 7, 11,
                new RuneRequirement(Rune.BODY, 1),
                new RuneRequirement(Rune.EARTH, 2),
                new RuneRequirement(Rune.WATER, 3)
        ),
        FIRE_STRIKE(
                "Fire Strike", 8, 13,
                new RuneRequirement(Rune.AIR, 2),
                new RuneRequirement(Rune.FIRE, 3),
                new RuneRequirement(Rune.MIND, 1)
        ),
        BONES_TO_BANANAS(
                "Bones to Bananas", 9, 15,
                new RuneRequirement(Rune.EARTH, 2),
                new RuneRequirement(Rune.NATURE, 1),
                new RuneRequirement(Rune.WATER, 2)
        ),
        WIND_BOLT(
                "Wind Bolt", 10, 17,
                new RuneRequirement(Rune.AIR, 2),
                new RuneRequirement(Rune.CHAOS, 1)
        ),
        CURSE(
                "Curse", 11, 19,
                new RuneRequirement(Rune.BODY, 1),
                new RuneRequirement(Rune.EARTH, 3),
                new RuneRequirement(Rune.WATER, 2)
        ),
        BIND(
                "Bind", 12, 20,
                new RuneRequirement(Rune.EARTH, 3),
                new RuneRequirement(Rune.NATURE, 2),
                new RuneRequirement(Rune.WATER, 3)
        ),
        LOW_LEVEL_ALCHEMY(
                "Low Level Alchemy", 13, 21,
                new RuneRequirement(Rune.FIRE, 3),
                new RuneRequirement(Rune.NATURE, 1)
        ),
        WATER_BOLT(
                "Water Bolt", 14, 23,
                new RuneRequirement(Rune.AIR, 2),
                new RuneRequirement(Rune.CHAOS, 1),
                new RuneRequirement(Rune.WATER, 2)
        ),
        VARROCK_TELEPORT(
                "Varrock Teleport", 15, 25,
                new RuneRequirement(Rune.AIR, 3),
                new RuneRequirement(Rune.FIRE, 1),
                new RuneRequirement(Rune.LAW, 1)
        ),
        LVL_2_ENCHANT(
                "Lvl-2 Enchant", 16, 27,
                new RuneRequirement(Rune.AIR, 3),
                new RuneRequirement(Rune.COSMIC, 1)
        ),
        EARTH_BOLT(
                "Earth Bolt", 17, 29,
                new RuneRequirement(Rune.AIR, 3),
                new RuneRequirement(Rune.CHAOS, 1),
                new RuneRequirement(Rune.EARTH, 1)
        ),
        LUMBRIDGE_TELEPORT(
                "Lumbridge Teleport", 18, 31,
                new RuneRequirement(Rune.AIR, 3),
                new RuneRequirement(Rune.EARTH, 1),
                new RuneRequirement(Rune.LAW, 1)
        ),
        TELEKINETIC_GRAB(
                "Telekinetic Grab", 19, 33,
                new RuneRequirement(Rune.AIR, 1),
                new RuneRequirement(Rune.LAW, 1)
        ),
        FIRE_BOLT(
                "Fire Bolt", 20, 35,
                new RuneRequirement(Rune.AIR, 3),
                new RuneRequirement(Rune.CHAOS, 1),
                new RuneRequirement(Rune.FIRE, 4)
        ),
        FALADOR_TELEPORT(
                "Falador Teleport", 21, 37,
                new RuneRequirement(Rune.AIR, 3),
                new RuneRequirement(Rune.LAW, 1),
                new RuneRequirement(Rune.WATER, 1)
        ),
        CRUMBLE_UNDEAD(
                "Crumble Undead", 22, 39,
                new RuneRequirement(Rune.AIR, 2),
                new RuneRequirement(Rune.CHAOS, 1),
                new RuneRequirement(Rune.EARTH, 2)
        ),
        TELEPORT_TO_HOUSE(
                "Teleport to House", 23, 40,
                new RuneRequirement(Rune.AIR, 1),
                new RuneRequirement(Rune.EARTH, 1),
                new RuneRequirement(Rune.LAW, 1)
        ),
        WIND_BLAST(
                "Wind Blast", 24, 41,
                new RuneRequirement(Rune.AIR, 3),
                new RuneRequirement(Rune.DEATH, 1)
        ),
        SUPERHEAT_ITEM(
                "Superheat Item", 25, 43,
                new RuneRequirement(Rune.FIRE, 4),
                new RuneRequirement(Rune.NATURE, 1)
        ),
        CAMELOT_TELEPORT(
                "Camelot Teleport", 26, 45,
                new RuneRequirement(Rune.AIR, 5),
                new RuneRequirement(Rune.LAW, 1)
        ),
        WATER_BLAST(
                "Water Blast", 27, 47,
                new RuneRequirement(Rune.AIR, 3),
                new RuneRequirement(Rune.DEATH, 1),
                new RuneRequirement(Rune.WATER, 3)
        ),
        LVL_3_ENCHANT(
                "Lvl-3 Enchant", 28, 49,
                new RuneRequirement(Rune.COSMIC, 1),
                new RuneRequirement(Rune.FIRE, 5)
        ),
        IBAN_BLAST(
                "Iban Blast", 29, 50,
                new RuneRequirement(Rune.DEATH, 1),
                new RuneRequirement(Rune.FIRE, 5)
        ),
        SNARE(
                "Snare", 30, 50,
                new RuneRequirement(Rune.EARTH, 4),
                new RuneRequirement(Rune.NATURE, 3),
                new RuneRequirement(Rune.WATER, 4)
        ),
        MAGIC_DART(
                "Magic Dart", 31, 50,
                new RuneRequirement(Rune.DEATH, 1),
                new RuneRequirement(Rune.MIND, 4)
        ),
        ARDOUGNE_TELEPORT(
                "Ardougne Teleport", 32, 51,
                new RuneRequirement(Rune.LAW, 2),
                new RuneRequirement(Rune.WATER, 2)
        ),
        EARTH_BLAST(
                "Earth Blast", 33, 53,
                new RuneRequirement(Rune.AIR, 3),
                new RuneRequirement(Rune.DEATH, 1),
                new RuneRequirement(Rune.EARTH, 4)
        ),
        HIGH_LEVEL_ALCHEMY(
                "High Level Alchemy", 34, 55,
                new RuneRequirement(Rune.FIRE, 5),
                new RuneRequirement(Rune.NATURE, 1)
        ),
        CHARGE_WATER_ORB(
                "Charge Water Orb", 35, 56,
                new RuneRequirement(Rune.COSMIC, 3),
                new RuneRequirement(Rune.WATER, 30)
        ),
        LVL_4_ENCHANT(
                "Lvl-4 Enchant", 36, 57,
                new RuneRequirement(Rune.COSMIC, 1),
                new RuneRequirement(Rune.EARTH, 10)
        ),
        WATCHTOWER_TELEPORT(
                "Watchtower Teleport", 37, 58,
                new RuneRequirement(Rune.EARTH, 2),
                new RuneRequirement(Rune.LAW, 2)
        ),
        FIRE_BLAST(
                "Fire Blast", 38, 59,
                new RuneRequirement(Rune.AIR, 4),
                new RuneRequirement(Rune.DEATH, 1),
                new RuneRequirement(Rune.FIRE, 5)
        ),
        CHARGE_EARTH_ORB(
                "Charge Earth Orb", 39, 60,
                new RuneRequirement(Rune.COSMIC, 3),
                new RuneRequirement(Rune.EARTH, 30)
        ),
        BONES_TO_PEACHES(
                "Bones to Peaches", 40, 60,
                new RuneRequirement(Rune.EARTH, 2),
                new RuneRequirement(Rune.NATURE, 2),
                new RuneRequirement(Rune.WATER, 4)
        ),
        SARADOMIN_STRIKE(
                "Saradomin Strike", 41, 60,
                new RuneRequirement(Rune.AIR, 4),
                new RuneRequirement(Rune.BLOOD, 2),
                new RuneRequirement(Rune.FIRE, 2)
        ),
        CLAWS_OF_GUTHIX(
                "Claws of Guthix", 42, 60,
                new RuneRequirement(Rune.AIR, 4),
                new RuneRequirement(Rune.BLOOD, 2),
                new RuneRequirement(Rune.FIRE, 1)
        ),
        FLAMES_OF_ZAMORAK(
                "Flames of Zamorak", 43, 60,
                new RuneRequirement(Rune.AIR, 1),
                new RuneRequirement(Rune.BLOOD, 2),
                new RuneRequirement(Rune.FIRE, 4)
        ),
        TROLLHEIM_TELEPORT(
                "Trollheim Teleport", 44, 61,
                new RuneRequirement(Rune.FIRE, 2),
                new RuneRequirement(Rune.LAW, 2)
        ),
        WIND_WAVE(
                "Wind Wave", 45, 62,
                new RuneRequirement(Rune.AIR, 5),
                new RuneRequirement(Rune.BLOOD, 1)
        ),
        CHARGE_FIRE_ORB(
                "Charge Fire Orb", 46, 63,
                new RuneRequirement(Rune.COSMIC, 3),
                new RuneRequirement(Rune.FIRE, 30)
        ),
        APE_ATOLL_TELEPORT(
                "Ape Atoll Teleport", 47, 64,
                new RuneRequirement(Rune.FIRE, 2),
                new RuneRequirement(Rune.LAW, 2),
                new RuneRequirement(Rune.WATER, 2)
        ),
        WATER_WAVE(
                "Water Wave", 48, 65,
                new RuneRequirement(Rune.AIR, 5),
                new RuneRequirement(Rune.BLOOD, 1),
                new RuneRequirement(Rune.WATER, 7)
        ),
        CHARGE_AIR_ORB(
                "Charge Air Orb", 49, 66,
                new RuneRequirement(Rune.AIR, 30),
                new RuneRequirement(Rune.COSMIC, 3)
        ),
        VULNERABILITY(
                "Vulnerability", 50, 66,
                new RuneRequirement(Rune.EARTH, 5),
                new RuneRequirement(Rune.SOUL, 1),
                new RuneRequirement(Rune.WATER, 5)
        ),
        LVL_5_ENCHANT(
                "Lvl-5 Enchant", 51, 68,
                new RuneRequirement(Rune.COSMIC, 1),
                new RuneRequirement(Rune.EARTH, 15),
                new RuneRequirement(Rune.WATER, 15)
        ),
        KOUREND_CASTLE_TELEPORT(
                "Kourend Castle Teleport", 52, 69,
                new RuneRequirement(Rune.FIRE, 5),
                new RuneRequirement(Rune.LAW, 2),
                new RuneRequirement(Rune.SOUL, 2),
                new RuneRequirement(Rune.WATER, 4)
        ),
        EARTH_WAVE(
                "Earth Wave", 53, 70,
                new RuneRequirement(Rune.AIR, 5),
                new RuneRequirement(Rune.BLOOD, 1),
                new RuneRequirement(Rune.EARTH, 7)
        ),
        ENFEEBLE(
                "Enfeeble", 54, 73,
                new RuneRequirement(Rune.EARTH, 8),
                new RuneRequirement(Rune.SOUL, 1),
                new RuneRequirement(Rune.WATER, 8)
        ),
        TELEOTHER_LUMBRIDGE(
                "Teleother Lumbridge", 55, 74,
                new RuneRequirement(Rune.EARTH, 1),
                new RuneRequirement(Rune.LAW, 1),
                new RuneRequirement(Rune.SOUL, 1)
        ),
        FIRE_WAVE(
                "Fire Wave", 56, 75,
                new RuneRequirement(Rune.AIR, 5),
                new RuneRequirement(Rune.BLOOD, 1),
                new RuneRequirement(Rune.FIRE, 7)
        ),
        ENTANGLE(
                "Entangle", 57, 79,
                new RuneRequirement(Rune.EARTH, 5),
                new RuneRequirement(Rune.NATURE, 4),
                new RuneRequirement(Rune.WATER, 5)
        ),
        STUN(
                "Stun", 58, 80,
                new RuneRequirement(Rune.EARTH, 12),
                new RuneRequirement(Rune.SOUL, 1),
                new RuneRequirement(Rune.WATER, 12)
        ),
        CHARGE(
                "Charge", 59, 80,
                new RuneRequirement(Rune.AIR, 3),
                new RuneRequirement(Rune.BLOOD, 3),
                new RuneRequirement(Rune.FIRE, 3)
        ),
        WIND_SURGE(
                "Wind Surge", 60, 81,
                new RuneRequirement(Rune.AIR, 7),
                new RuneRequirement(Rune.WRATH, 1)
        ),
        TELEOTHER_FALADOR(
                "Teleother Falador", 61, 82,
                new RuneRequirement(Rune.LAW, 1),
                new RuneRequirement(Rune.SOUL, 1),
                new RuneRequirement(Rune.WATER, 1)
        ),
        WATER_SURGE(
                "Water Surge", 62, 85,
                new RuneRequirement(Rune.AIR, 7),
                new RuneRequirement(Rune.WATER, 10),
                new RuneRequirement(Rune.WRATH, 1)
        ),
        TELE_BLOCK(
                "Tele Block", 63, 85,
                new RuneRequirement(Rune.CHAOS, 1),
                new RuneRequirement(Rune.DEATH, 1),
                new RuneRequirement(Rune.LAW, 1)
        ),
        TELEPORT_TO_BOUNTY_TARGET(
                "Teleport to Bounty Target", 64, 85,
                new RuneRequirement(Rune.CHAOS, 1),
                new RuneRequirement(Rune.DEATH, 1),
                new RuneRequirement(Rune.LAW, 1)
        ),
        LVL_6_ENCHANT(
                "Lvl-6 Enchant", 65, 87,
                new RuneRequirement(Rune.COSMIC, 1),
                new RuneRequirement(Rune.EARTH, 20),
                new RuneRequirement(Rune.FIRE, 20)
        ),
        TELEOTHER_CAMELOT(
                "Teleother Camelot", 66, 90,
                new RuneRequirement(Rune.LAW, 1),
                new RuneRequirement(Rune.SOUL, 2)
        ),
        EARTH_SURGE(
                "Earth Surge", 67, 90,
                new RuneRequirement(Rune.AIR, 7),
                new RuneRequirement(Rune.EARTH, 10),
                new RuneRequirement(Rune.WRATH, 1)
        ),
        LVL_7_ENCHANT(
                "Lvl-7 Enchant", 68, 93,
                new RuneRequirement(Rune.BLOOD, 20),
                new RuneRequirement(Rune.COSMIC, 1),
                new RuneRequirement(Rune.SOUL, 20)
        ),
        FIRE_SURGE(
                "Fire Surge", 69, 95,
                new RuneRequirement(Rune.AIR, 7),
                new RuneRequirement(Rune.FIRE, 10),
                new RuneRequirement(Rune.WRATH, 1)
        );

        private static final int STANDARD_SPELLBOOK_GROUP_ID = 218;
        private static final int STANDARD_SPELLBOOK_FIRST_SPELL_INDEX = 6;

        private final String name;
        private final int index;
        private final int levelRequirement;
        private final RuneRequirement[] runeRequirements;

        StandardSpell(String name, int index, int levelRequirement, RuneRequirement... runeRequirements) {
            this.name = name;
            this.index = index;
            this.levelRequirement = levelRequirement;
            this.runeRequirements = runeRequirements;
        }

        @Override
        public int getGroupId() {
            return STANDARD_SPELLBOOK_GROUP_ID;
        }

        public int getChildId() {
            return STANDARD_SPELLBOOK_FIRST_SPELL_INDEX + this.getIndex();
        }
    }

    @Getter
    public enum EnchantSpell {
        ENCHANT_SAPPHIRE(StandardSpell.LVL_1_ENCHANT, Crafting.SAPPHIRE_AND_OPAL_JEWELLERY),
        ENCHANT_EMERALD(StandardSpell.LVL_2_ENCHANT, Crafting.EMERALD_JEWELLERY),
        ENCHANT_RUBY(StandardSpell.LVL_3_ENCHANT, Crafting.TOPAZ_AND_RUBY_JEWELLERY),
        ENCHANT_DIAMOND(StandardSpell.LVL_4_ENCHANT, Crafting.DIAMOND_JEWELLERY),
        ENCHANT_DRAGONSTONE(StandardSpell.LVL_5_ENCHANT, Crafting.DRAGONSTONE_JEWELLERY),
        ENCHANT_ONYX(StandardSpell.LVL_6_ENCHANT, Crafting.ONYX_JEWELLERY),
        ENCHANT_ZENYTE(StandardSpell.LVL_7_ENCHANT, Crafting.ZENYTE_JEWELLERY);

        private final Spell spell;
        private final int[] jewellery;

        EnchantSpell(Spell spell, int[] jewellery) {
            this.spell = spell;
            this.jewellery = jewellery;
            Arrays.sort(jewellery);
        }
    }

    @RequiredArgsConstructor
    @Getter
    public enum Rune {
        AIR("Air", RuneIDs.AIR.build(), StaveIDs.AIR.build()),
        WATER("Water", RuneIDs.WATER.build(), new IDs(StaveIDs.WATER, ItemID.TOME_OF_WATER).build()),
        EARTH("Earth", RuneIDs.EARTH.build(), StaveIDs.EARTH.build()),
        FIRE("Fire", RuneIDs.FIRE.build(), new IDs(StaveIDs.FIRE, ItemID.TOME_OF_FIRE).build()),
        MIND("Mind", RuneIDs.MIND.build()),
        BODY("Body", RuneIDs.BODY.build()),
        COSMIC("Cosmic", RuneIDs.COSMIC.build()),
        CHAOS("Chaos", RuneIDs.CHAOS.build()),
        ASTRAL("Astral", RuneIDs.ASTRAL.build()),
        NATURE("Nature", RuneIDs.NATURE.build()),
        LAW("Law", RuneIDs.LAW.build()),
        DEATH("Death", RuneIDs.DEATH.build()),
        BLOOD("Blood", RuneIDs.BLOOD.build()),
        SOUL("Soul", RuneIDs.SOUL.build()),
        WRATH("Wrath", RuneIDs.WRATH.build());

        private final String name;
        private final int[] runeIds;
        private final int[] unlimitedSources;

        Rune(String name, int... runeId) {
            this(name, runeId, ArrayUtils.EMPTY_INT_ARRAY);
        }

        public int countAvailable(Client client) {
            for (int itemId : this.unlimitedSources) {
                ItemContainer equipment = client.getItemContainer(InventoryID.EQUIPMENT);
                if (equipment != null && equipment.contains(itemId)) {
                    return Integer.MAX_VALUE;
                }
            }
            int count = 0;
            for (int itemId : this.runeIds) {
                ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
                count += inventory == null ? 0 : inventory.count(itemId);
            }
            return count;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum LecternSpell {
        ENCHANT_ONYX(ItemID.ENCHANT_ONYX, 5177355, StandardSpell.LVL_6_ENCHANT),
        LUMBRIDGE_TELEPORT(ItemID.LUMBRIDGE_TELEPORT, 5177356, StandardSpell.LUMBRIDGE_TELEPORT),
        ENCHANT_DIAMOND(ItemID.ENCHANT_DIAMOND, 5177357, StandardSpell.LVL_4_ENCHANT),
        WATCHTOWER_TELEPORT(ItemID.WATCHTOWER_TELEPORT, 5177358, StandardSpell.WATCHTOWER_TELEPORT),
        HOUSE_TELEPORT(ItemID.TELEPORT_TO_HOUSE, 5177359, StandardSpell.TELEPORT_TO_HOUSE),
        ENCHANT_EMERALD(ItemID.ENCHANT_EMERALD_OR_JADE, 5177360, StandardSpell.LVL_2_ENCHANT),
        ENCHANT_SAPPHIRE(ItemID.ENCHANT_SAPPHIRE_OR_OPAL, 5177361, StandardSpell.LVL_1_ENCHANT),
        FALADOR_TELEPORT(ItemID.FALADOR_TELEPORT, 5177362, StandardSpell.FALADOR_TELEPORT),
        ARDOUGNE_TELEPORT(ItemID.ARDOUGNE_TELEPORT, 5177363, StandardSpell.ARDOUGNE_TELEPORT),
        BONES_TO_BANANAS(ItemID.BONES_TO_BANANAS, 5177364, StandardSpell.BONES_TO_BANANAS),
        ENCHANT_DRAGONSTONE(ItemID.ENCHANT_DRAGONSTONE, 5177365, StandardSpell.LVL_5_ENCHANT),
        ENCHANT_RUBY(ItemID.ENCHANT_RUBY_OR_TOPAZ, 5177366, StandardSpell.LVL_3_ENCHANT),
        VARROCK_TELEPORT(ItemID.VARROCK_TELEPORT, 5177367, StandardSpell.VARROCK_TELEPORT),
        CAMELOT_TELEPORT(ItemID.CAMELOT_TELEPORT, 5177368, StandardSpell.CAMELOT_TELEPORT),
        BONES_TO_PEACHES(ItemID.BONES_TO_PEACHES, 5177369, StandardSpell.BONES_TO_PEACHES);

        private final int product;
        private final int widgetId;
        private final Spell spell;
    }

    @Getter
    @RequiredArgsConstructor
    public enum ChargeOrbSpell {
        CHARGE_AIR_ORB(ItemID.AIR_ORB, StandardSpell.CHARGE_AIR_ORB),
        CHARGE_EARTH_ORB(ItemID.EARTH_ORB, StandardSpell.CHARGE_EARTH_ORB),
        CHARGE_FIRE_ORB(ItemID.FIRE_ORB, StandardSpell.CHARGE_FIRE_ORB),
        CHARGE_WATER_ORB(ItemID.WATER_ORB, StandardSpell.CHARGE_WATER_ORB);

        private final int product;
        private final Spell spell;

        public static ChargeOrbSpell byProduct(int productId) {
            return Arrays.stream(values())
                    .filter(x -> x.product == productId)
                    .findFirst()
                    .orElse(null);
        }

    }

    public interface Spell {
        String getName();

        int getGroupId();

        int getChildId();

        int getLevelRequirement();

        RuneRequirement[] getRuneRequirements();

        default int getWidgetId() {
            return this.getGroupId() << 16 | this.getChildId();
        }

        default int getAvailableCasts(Client client) {
            int min = Integer.MAX_VALUE;
            for (RuneRequirement requirement : this.getRuneRequirements()) {
                int amount = requirement.getRune().countAvailable(client);
                min = Math.min(min, amount / requirement.getAmount());
            }
            return min;
        }
    }

    @Data
    public static
    class RuneRequirement {
        private final Rune rune;
        private final int amount;
    }
}