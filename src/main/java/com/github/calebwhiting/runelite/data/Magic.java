package com.github.calebwhiting.runelite.data;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.ItemContainer;
import net.runelite.api.ItemID;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@UtilityClass
public class Magic {

    public static final ItemGroup SMOKE_STAVES = new ItemGroup(
            ItemID.SMOKE_BATTLESTAFF, ItemID.MYSTIC_SMOKE_STAFF);

    public static final ItemGroup DUST_STAVES = new ItemGroup(
            ItemID.DUST_BATTLESTAFF, ItemID.MYSTIC_DUST_STAFF);

    public static final ItemGroup MUD_STAVES = new ItemGroup(
            ItemID.MUD_BATTLESTAFF, ItemID.MYSTIC_MUD_STAFF);

    public static final ItemGroup MIST_STAVES = new ItemGroup(
            ItemID.MIST_BATTLESTAFF, ItemID.MYSTIC_MIST_STAFF);

    public static final ItemGroup STEAM_STAVES = new ItemGroup(
            ItemID.STEAM_BATTLESTAFF, ItemID.MYSTIC_STEAM_STAFF,
            ItemID.STEAM_BATTLESTAFF_12795, ItemID.MYSTIC_STEAM_STAFF_12796);

    public static final ItemGroup LAVA_STAVES = new ItemGroup(
            ItemID.LAVA_BATTLESTAFF, ItemID.MYSTIC_LAVA_STAFF,
            ItemID.LAVA_BATTLESTAFF_21198, ItemID.MYSTIC_LAVA_STAFF_21200);

    @Getter
    @RequiredArgsConstructor
    enum StandardSpell implements Spell {
        LUMBRIDGE_HOME_TELEPORT("Lumbridge Home Teleport", 0, new RunesBuilder().build()),
        WIND_STRIKE("Wind Strike", 1, new RunesBuilder().rune(Rune.AIR, 1).rune(Rune.MIND, 1).build()),
        CONFUSE("Confuse", 3, new RunesBuilder().rune(Rune.BODY, 1).rune(Rune.EARTH, 2).rune(Rune.WATER, 3).build()),
        ENCHANT_CROSSBOW_BOLT("Enchant Crossbow Bolt", 4, new RunesBuilder().build()),
        WATER_STRIKE("Water Strike", 5, new RunesBuilder().rune(Rune.AIR, 1).rune(Rune.MIND, 1).rune(Rune.WATER, 1).build()),
        LVL_1_ENCHANT("Lvl-1 Enchant", 7, new RunesBuilder().rune(Rune.COSMIC, 1).rune(Rune.WATER, 1).build()),
        EARTH_STRIKE("Earth Strike", 9, new RunesBuilder().rune(Rune.AIR, 1).rune(Rune.EARTH, 2).rune(Rune.MIND, 1).build()),
        WEAKEN("Weaken", 11, new RunesBuilder().rune(Rune.BODY, 1).rune(Rune.EARTH, 2).rune(Rune.WATER, 3).build()),
        FIRE_STRIKE("Fire Strike", 13, new RunesBuilder().rune(Rune.AIR, 2).rune(Rune.FIRE, 3).rune(Rune.MIND, 1).build()),
        BONES_TO_BANANAS("Bones to Bananas", 15, new RunesBuilder().rune(Rune.EARTH, 2).rune(Rune.NATURE, 1).rune(Rune.WATER, 2).build()),
        WIND_BOLT("Wind Bolt", 17, new RunesBuilder().rune(Rune.AIR, 2).rune(Rune.CHAOS, 1).build()),
        CURSE("Curse", 19, new RunesBuilder().rune(Rune.BODY, 1).rune(Rune.EARTH, 3).rune(Rune.WATER, 2).build()),
        BIND("Bind", 20, new RunesBuilder().rune(Rune.EARTH, 3).rune(Rune.NATURE, 2).rune(Rune.WATER, 3).build()),
        LOW_LEVEL_ALCHEMY("Low Level Alchemy", 21, new RunesBuilder().rune(Rune.FIRE, 3).rune(Rune.NATURE, 1).build()),
        WATER_BOLT("Water Bolt", 23, new RunesBuilder().rune(Rune.AIR, 2).rune(Rune.CHAOS, 1).rune(Rune.WATER, 2).build()),
        VARROCK_TELEPORT("Varrock Teleport", 25, new RunesBuilder().rune(Rune.AIR, 3).rune(Rune.FIRE, 1).rune(Rune.LAW, 1).build()),
        LVL_2_ENCHANT("Lvl-2 Enchant", 27, new RunesBuilder().rune(Rune.AIR, 3).rune(Rune.COSMIC, 1).build()),
        EARTH_BOLT("Earth Bolt", 29, new RunesBuilder().rune(Rune.AIR, 3).rune(Rune.CHAOS, 1).rune(Rune.EARTH, 1).build()),
        LUMBRIDGE_TELEPORT("Lumbridge Teleport", 31, new RunesBuilder().rune(Rune.AIR, 3).rune(Rune.EARTH, 1).rune(Rune.LAW, 1).build()),
        TELEKINETIC_GRAB("Telekinetic Grab", 33, new RunesBuilder().rune(Rune.AIR, 1).rune(Rune.LAW, 1).build()),
        FIRE_BOLT("Fire Bolt", 35, new RunesBuilder().rune(Rune.AIR, 3).rune(Rune.CHAOS, 1).rune(Rune.FIRE, 4).build()),
        FALADOR_TELEPORT("Falador Teleport", 37, new RunesBuilder().rune(Rune.AIR, 3).rune(Rune.LAW, 1).rune(Rune.WATER, 1).build()),
        CRUMBLE_UNDEAD("Crumble Undead", 39, new RunesBuilder().rune(Rune.AIR, 2).rune(Rune.CHAOS, 1).rune(Rune.EARTH, 2).build()),
        TELEPORT_TO_HOUSE("Teleport to House", 40, new RunesBuilder().rune(Rune.AIR, 1).rune(Rune.EARTH, 1).rune(Rune.LAW, 1).build()),
        WIND_BLAST("Wind Blast", 41, new RunesBuilder().rune(Rune.AIR, 3).rune(Rune.DEATH, 1).build()),
        SUPERHEAT_ITEM("Superheat Item", 43, new RunesBuilder().rune(Rune.FIRE, 4).rune(Rune.NATURE, 1).build()),
        CAMELOT_TELEPORT("Camelot Teleport", 45, new RunesBuilder().rune(Rune.AIR, 5).rune(Rune.LAW, 1).build()),
        WATER_BLAST("Water Blast", 47, new RunesBuilder().rune(Rune.AIR, 3).rune(Rune.DEATH, 1).rune(Rune.WATER, 3).build()),
        LVL_3_ENCHANT("Lvl-3 Enchant", 49, new RunesBuilder().rune(Rune.COSMIC, 1).rune(Rune.FIRE, 5).build()),
        IBAN_BLAST("Iban Blast", 50, new RunesBuilder().rune(Rune.DEATH, 1).rune(Rune.FIRE, 5).build()),
        SNARE("Snare", 50, new RunesBuilder().rune(Rune.EARTH, 4).rune(Rune.NATURE, 3).rune(Rune.WATER, 4).build()),
        MAGIC_DART("Magic Dart", 50, new RunesBuilder().rune(Rune.DEATH, 1).rune(Rune.MIND, 4).build()),
        ARDOUGNE_TELEPORT("Ardougne Teleport", 51, new RunesBuilder().rune(Rune.LAW, 2).rune(Rune.WATER, 2).build()),
        EARTH_BLAST("Earth Blast", 53, new RunesBuilder().rune(Rune.AIR, 3).rune(Rune.DEATH, 1).rune(Rune.EARTH, 4).build()),
        HIGH_LEVEL_ALCHEMY("High Level Alchemy", 55, new RunesBuilder().rune(Rune.FIRE, 5).rune(Rune.NATURE, 1).build()),
        CHARGE_WATER_ORB("Charge Water Orb", 56, new RunesBuilder().rune(Rune.COSMIC, 3).rune(Rune.WATER, 30).build()),
        LVL_4_ENCHANT("Lvl-4 Enchant", 57, new RunesBuilder().rune(Rune.COSMIC, 1).rune(Rune.EARTH, 10).build()),
        WATCHTOWER_TELEPORT("Watchtower Teleport", 58, new RunesBuilder().rune(Rune.EARTH, 2).rune(Rune.LAW, 2).build()),
        FIRE_BLAST("Fire Blast", 59, new RunesBuilder().rune(Rune.AIR, 4).rune(Rune.DEATH, 1).rune(Rune.FIRE, 5).build()),
        CHARGE_EARTH_ORB("Charge Earth Orb", 60, new RunesBuilder().rune(Rune.COSMIC, 3).rune(Rune.EARTH, 30).build()),
        BONES_TO_PEACHES("Bones to Peaches", 60, new RunesBuilder().rune(Rune.EARTH, 2).rune(Rune.NATURE, 2).rune(Rune.WATER, 4).build()),
        SARADOMIN_STRIKE("Saradomin Strike", 60, new RunesBuilder().rune(Rune.AIR, 4).rune(Rune.BLOOD, 2).rune(Rune.FIRE, 2).build()),
        CLAWS_OF_GUTHIX("Claws of Guthix", 60, new RunesBuilder().rune(Rune.AIR, 4).rune(Rune.BLOOD, 2).rune(Rune.FIRE, 1).build()),
        FLAMES_OF_ZAMORAK("Flames of Zamorak", 60, new RunesBuilder().rune(Rune.AIR, 1).rune(Rune.BLOOD, 2).rune(Rune.FIRE, 4).build()),
        TROLLHEIM_TELEPORT("Trollheim Teleport", 61, new RunesBuilder().rune(Rune.FIRE, 2).rune(Rune.LAW, 2).build()),
        WIND_WAVE("Wind Wave", 62, new RunesBuilder().rune(Rune.AIR, 5).rune(Rune.BLOOD, 1).build()),
        CHARGE_FIRE_ORB("Charge Fire Orb", 63, new RunesBuilder().rune(Rune.COSMIC, 3).rune(Rune.FIRE, 30).build()),
        APE_ATOLL_TELEPORT("Ape Atoll Teleport", 64, new RunesBuilder().rune(Rune.FIRE, 2).rune(Rune.LAW, 2).rune(Rune.WATER, 2).build()),
        WATER_WAVE("Water Wave", 65, new RunesBuilder().rune(Rune.AIR, 5).rune(Rune.BLOOD, 1).rune(Rune.WATER, 7).build()),
        CHARGE_AIR_ORB("Charge Air Orb", 66, new RunesBuilder().rune(Rune.AIR, 30).rune(Rune.COSMIC, 3).build()),
        VULNERABILITY("Vulnerability", 66, new RunesBuilder().rune(Rune.EARTH, 5).rune(Rune.SOUL, 1).rune(Rune.WATER, 5).build()),
        LVL_5_ENCHANT("Lvl-5 Enchant", 68, new RunesBuilder().rune(Rune.COSMIC, 1).rune(Rune.EARTH, 15).rune(Rune.WATER, 15).build()),
        KOUREND_CASTLE_TELEPORT("Kourend Castle Teleport", 69, new RunesBuilder().rune(Rune.FIRE, 5).rune(Rune.LAW, 2).rune(Rune.SOUL, 2).rune(Rune.WATER, 4).build()),
        EARTH_WAVE("Earth Wave", 70, new RunesBuilder().rune(Rune.AIR, 5).rune(Rune.BLOOD, 1).rune(Rune.EARTH, 7).build()),
        ENFEEBLE("Enfeeble", 73, new RunesBuilder().rune(Rune.EARTH, 8).rune(Rune.SOUL, 1).rune(Rune.WATER, 8).build()),
        TELEOTHER_LUMBRIDGE("Teleother Lumbridge", 74, new RunesBuilder().rune(Rune.EARTH, 1).rune(Rune.LAW, 1).rune(Rune.SOUL, 1).build()),
        FIRE_WAVE("Fire Wave", 75, new RunesBuilder().rune(Rune.AIR, 5).rune(Rune.BLOOD, 1).rune(Rune.FIRE, 7).build()),
        ENTANGLE("Entangle", 79, new RunesBuilder().rune(Rune.EARTH, 5).rune(Rune.NATURE, 4).rune(Rune.WATER, 5).build()),
        STUN("Stun", 80, new RunesBuilder().rune(Rune.EARTH, 12).rune(Rune.SOUL, 1).rune(Rune.WATER, 12).build()),
        CHARGE("Charge", 80, new RunesBuilder().rune(Rune.AIR, 3).rune(Rune.BLOOD, 3).rune(Rune.FIRE, 3).build()),
        WIND_SURGE("Wind Surge", 81, new RunesBuilder().rune(Rune.AIR, 7).rune(Rune.WRATH, 1).build()),
        TELEOTHER_FALADOR("Teleother Falador", 82, new RunesBuilder().rune(Rune.LAW, 1).rune(Rune.SOUL, 1).rune(Rune.WATER, 1).build()),
        WATER_SURGE("Water Surge", 85, new RunesBuilder().rune(Rune.AIR, 7).rune(Rune.WATER, 10).rune(Rune.WRATH, 1).build()),
        TELE_BLOCK("Tele Block", 85, new RunesBuilder().rune(Rune.CHAOS, 1).rune(Rune.DEATH, 1).rune(Rune.LAW, 1).build()),
        TELEPORT_TO_BOUNTY_TARGET("Teleport to Bounty Target", 85, new RunesBuilder().rune(Rune.CHAOS, 1).rune(Rune.DEATH, 1).rune(Rune.LAW, 1).build()),
        LVL_6_ENCHANT("Lvl-6 Enchant", 87, new RunesBuilder().rune(Rune.COSMIC, 1).rune(Rune.EARTH, 20).rune(Rune.FIRE, 20).build()),
        TELEOTHER_CAMELOT("Teleother Camelot", 90, new RunesBuilder().rune(Rune.LAW, 1).rune(Rune.SOUL, 2).build()),
        EARTH_SURGE("Earth Surge", 90, new RunesBuilder().rune(Rune.AIR, 7).rune(Rune.EARTH, 10).rune(Rune.WRATH, 1).build()),
        LVL_7_ENCHANT("Lvl-7 Enchant", 93, new RunesBuilder().rune(Rune.BLOOD, 20).rune(Rune.COSMIC, 1).rune(Rune.SOUL, 20).build()),
        FIRE_SURGE("Fire Surge", 95, new RunesBuilder().rune(Rune.AIR, 7).rune(Rune.FIRE, 10).rune(Rune.WRATH, 1).build());

        private static final int STANDARD_SPELLBOOK_GROUP_ID = 218;
        private static final int STANDARD_SPELLBOOK_FIRST_SPELL_INDEX = 5;

        private final String name;
        private final int levelRequirement;
        private final RuneRequirement[] runeRequirements;

        @Override
        public int getGroupId() {
            return STANDARD_SPELLBOOK_GROUP_ID;
        }

        public int getChildId() {
            return STANDARD_SPELLBOOK_FIRST_SPELL_INDEX + this.ordinal();
        }

    }

    @Getter
    public enum EnchantSpell {
        ENCHANT_SAPPHIRE(StandardSpell.LVL_1_ENCHANT, new int[]{
                ItemID.SAPPHIRE_RING,
                ItemID.SAPPHIRE_NECKLACE,
                ItemID.SAPPHIRE_BRACELET,
                ItemID.SAPPHIRE_AMULET,
                ItemID.OPAL_RING,
                ItemID.OPAL_NECKLACE,
                ItemID.OPAL_BRACELET,
                ItemID.OPAL_AMULET
        }),
        ENCHANT_EMERALD(StandardSpell.LVL_2_ENCHANT, new int[]{
                ItemID.EMERALD_RING,
                ItemID.EMERALD_NECKLACE,
                ItemID.EMERALD_BRACELET,
                ItemID.EMERALD_AMULET,
                ItemID.PRENATURE_AMULET,
                ItemID.JADE_RING,
                ItemID.JADE_NECKLACE,
                ItemID.JADE_BRACELET,
                ItemID.JADE_AMULET
        }),
        ENCHANT_RUBY(StandardSpell.LVL_3_ENCHANT, new int[]{
                ItemID.RUBY_RING,
                ItemID.RUBY_NECKLACE,
                ItemID.RUBY_BRACELET,
                ItemID.RUBY_AMULET,
                ItemID.TOPAZ_RING,
                ItemID.TOPAZ_NECKLACE,
                ItemID.TOPAZ_BRACELET,
                ItemID.TOPAZ_AMULET
        }),
        ENCHANT_DIAMOND(StandardSpell.LVL_4_ENCHANT, new int[]{
                ItemID.DIAMOND_RING,
                ItemID.DIAMOND_NECKLACE,
                ItemID.DIAMOND_BRACELET,
                ItemID.DIAMOND_AMULET
        }),
        ENCHANT_DRAGONSTONE(StandardSpell.LVL_5_ENCHANT, new int[]{
                ItemID.DRAGONSTONE_RING,
                ItemID.DRAGON_NECKLACE,
                ItemID.DRAGONSTONE_BRACELET,
                ItemID.DRAGONSTONE_AMULET
        }),
        ENCHANT_ONYX(StandardSpell.LVL_6_ENCHANT, new int[]{
                ItemID.ONYX_RING,
                ItemID.ONYX_NECKLACE,
                ItemID.ONYX_BRACELET,
                ItemID.ONYX_AMULET
        }),
        ENCHANT_ZENYTE(StandardSpell.LVL_7_ENCHANT, new int[]{
                ItemID.ZENYTE_RING,
                ItemID.ZENYTE_NECKLACE,
                ItemID.ZENYTE_BRACELET,
                ItemID.ZENYTE_AMULET
        });

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
        AIR(new int[]{ItemID.AIR_RUNE, ItemID.SMOKE_RUNE, ItemID.DUST_RUNE, ItemID.MIST_RUNE},
                new ItemGroup()
                        .and(ItemID.STAFF_OF_AIR, ItemID.AIR_BATTLESTAFF, ItemID.MYSTIC_AIR_STAFF)
                        .and(SMOKE_STAVES).and(DUST_STAVES).and(STEAM_STAVES)
                        .build()
        ),
        WATER(new int[]{ItemID.AIR_RUNE, ItemID.MUD_RUNE, ItemID.MIST_RUNE, ItemID.STEAM_RUNE},
                new ItemGroup()
                        .and(ItemID.STAFF_OF_WATER, ItemID.WATER_BATTLESTAFF, ItemID.MYSTIC_WATER_STAFF)
                        .and(MUD_STAVES, MIST_STAVES, STEAM_STAVES)
                        .build()
        ),
        EARTH(new int[]{ItemID.EARTH_RUNE, ItemID.DUST_RUNE, ItemID.MUD_RUNE, ItemID.LAVA_RUNE},
                new ItemGroup()
                        .and(ItemID.STAFF_OF_EARTH, ItemID.EARTH_BATTLESTAFF, ItemID.MYSTIC_EARTH_STAFF)
                        .and(DUST_STAVES, MUD_STAVES, LAVA_STAVES)
                        .build()
        ),
        FIRE(new int[]{ItemID.FIRE_RUNE, ItemID.SMOKE_RUNE, ItemID.STEAM_RUNE, ItemID.LAVA_RUNE},
                new ItemGroup()
                        .and(ItemID.STAFF_OF_FIRE, ItemID.FIRE_BATTLESTAFF, ItemID.MYSTIC_FIRE_STAFF, ItemID.TOME_OF_FIRE)
                        .and(SMOKE_STAVES).and(STEAM_STAVES).and(LAVA_STAVES)
                        .build()
        ),
        MIND(ItemID.MIND_RUNE),
        BODY(ItemID.BODY_RUNE),
        COSMIC(ItemID.COSMIC_RUNE),
        CHAOS(ItemID.CHAOS_RUNE),
        ASTRAL(ItemID.ASTRAL_RUNE),
        NATURE(ItemID.NATURE_RUNE),
        LAW(ItemID.LAW_RUNE),
        DEATH(ItemID.DEATH_RUNE),
        BLOOD(ItemID.BLOOD_RUNE),
        SOUL(ItemID.SOUL_RUNE),
        WRATH(ItemID.WRATH_RUNE);

        private final int[] runeIds;
        private final int[] unlimitedSources;

        Rune(int... runeId) {
            this(runeId, new int[]{});
        }

        public int countAvailable(Client client) {
            for (int itemId : this.getUnlimitedSources()) {
                ItemContainer equipment = client.getItemContainer(InventoryID.EQUIPMENT);
                if (equipment != null && equipment.contains(itemId)) {
                    return Integer.MAX_VALUE;
                }
            }
            int count = 0;
            for (int itemId : this.getRuneIds()) {
                ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
                count += inventory == null ? 0 : inventory.count(itemId);
            }
            return count;
        }

    }

    @RequiredArgsConstructor
    @Getter
    public enum LecternItem {
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

    public interface Spell {

        String getName();

        int getGroupId();

        int getChildId();

        int getLevelRequirement();

        RuneRequirement[] getRuneRequirements();

        default int getWidgetId() {
            return getGroupId() << 16 | getChildId();
        }

        default int getAvailableCasts(Client client) {
            int min = Integer.MAX_VALUE;
            for (RuneRequirement requirement : getRuneRequirements()) {
                int amount = requirement.getRune().countAvailable(client);
                min = Math.min(min, amount / requirement.getAmount());
            }
            return min;
        }


    }

    private static class RunesBuilder {

        private final List<RuneRequirement> requirements = new LinkedList<>();

        public RunesBuilder rune(Rune rune, int amount) {
            this.requirements.add(new RuneRequirement(rune, amount));
            return this;
        }

        public RuneRequirement[] build() {
            return this.requirements.toArray(new RuneRequirement[0]);
        }

    }

    @Data
    public static class RuneRequirement {
        private final Rune rune;
        private final int amount;
    }

}
