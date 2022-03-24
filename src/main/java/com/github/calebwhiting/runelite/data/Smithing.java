package com.github.calebwhiting.runelite.data;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.ItemContainer;
import net.runelite.api.ItemID;

public interface Smithing {

    @Getter
    enum Bar {
        BRONZE(ItemID.BRONZE_BAR,
                new OreRequirement(Ore.COPPER, 1),
                new OreRequirement(Ore.TIN, 1)),
        BLURITE(ItemID.BLURITE_BAR,
                new OreRequirement(Ore.BLURITE, 1)),
        IRON(ItemID.IRON_BAR,
                new OreRequirement(Ore.IRON, 1)),
        SILVER(ItemID.SILVER_BAR,
                new OreRequirement(Ore.SILVER, 1)),
        STEEL(ItemID.STEEL_BAR,
                new OreRequirement(Ore.IRON, 1),
                new OreRequirement(Ore.COAL, 2)),
        GOLD(ItemID.GOLD_BAR,
                new OreRequirement(Ore.GOLD, 1)),
        MITHRIL(ItemID.MITHRIL_BAR,
                new OreRequirement(Ore.MITHRIL, 1),
                new OreRequirement(Ore.COAL, 4)),
        ADAMANTITE(ItemID.ADAMANTITE_BAR,
                new OreRequirement(Ore.ADAMANTITE, 1),
                new OreRequirement(Ore.COAL, 6)),
        RUNITE(ItemID.RUNITE_BAR,
                new OreRequirement(Ore.RUNITE, 1),
                new OreRequirement(Ore.COAL, 8));

        private final int itemId;
        private final OreRequirement[] requirements;


        Bar(int itemId, OreRequirement... requirements) {
            this.itemId = itemId;
            this.requirements = requirements;
        }

        public int countAvailableOres(Client client) {
            ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
            if (inventory == null) {
                return 0;
            }
            int count = Integer.MAX_VALUE;
            for (OreRequirement requirement : this.getRequirements()) {
                int oreCount = inventory.count(requirement.getOre().getItemId());
                if (oreCount == 0) {
                    return 0;
                }
                count = Math.min(count, oreCount / requirement.amount);
            }
            return count;
        }
    }

    @Getter
    @RequiredArgsConstructor
    enum Ore {
        TIN(ItemID.TIN_ORE),
        COPPER(ItemID.COPPER_ORE),
        IRON(ItemID.IRON_ORE),
        BLURITE(ItemID.BLURITE_ORE),
        SILVER(ItemID.SILVER_ORE),
        COAL(ItemID.COAL),
        GOLD(ItemID.GOLD_ORE),
        MITHRIL(ItemID.MITHRIL_ORE),
        ADAMANTITE(ItemID.ADAMANTITE_ORE),
        RUNITE(ItemID.RUNITE_ORE);

        private final int itemId;
    }

    @Data
    class OreRequirement {

        private final Ore ore;
        private final int amount;

    }
}
