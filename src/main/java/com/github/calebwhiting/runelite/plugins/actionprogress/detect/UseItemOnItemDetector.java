package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.api.InventoryHelper;
import com.github.calebwhiting.runelite.api.data.Ingredient;
import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import com.github.calebwhiting.runelite.plugins.actionprogress.Product;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.eventbus.Subscribe;

import java.util.stream.IntStream;

import static net.runelite.api.ItemID.*;

@Slf4j
public class UseItemOnItemDetector extends ActionDetector {

    @Inject
    private InventoryHelper inventoryHelper;

    @Inject
    private Client client;

    private static final Product[] PRODUCTS = {
            simpleProduct(Action.GRIND, GROUND_ASHES, PESTLE_AND_MORTAR, ASHES),
            simpleProduct(Action.GRIND, CRUSHED_NEST, PESTLE_AND_MORTAR, BIRD_NEST_5075),
            simpleProduct(Action.GRIND, DRAGON_SCALE_DUST, PESTLE_AND_MORTAR, BLUE_DRAGON_SCALE),
            simpleProduct(Action.GRIND, GROUND_CHARCOAL, PESTLE_AND_MORTAR, CHARCOAL),
            simpleProduct(Action.GRIND, CHOCOLATE_DUST, PESTLE_AND_MORTAR, CHOCOLATE_BAR),
            simpleProduct(Action.GRIND, GROUND_CRAB_MEAT, PESTLE_AND_MORTAR, CRAB_MEAT),
            simpleProduct(Action.GRIND, GOAT_HORN_DUST, PESTLE_AND_MORTAR, DESERT_GOAT_HORN),
            simpleProduct(Action.GRIND, GROUND_THISTLE, PESTLE_AND_MORTAR, DRIED_THISTLE),
            simpleProduct(Action.GRIND, GORAK_CLAW_POWDER, PESTLE_AND_MORTAR, GORAK_CLAWS),
            simpleProduct(Action.GRIND, GROUND_GUAM, PESTLE_AND_MORTAR, GUAM_LEAF),
            simpleProduct(Action.GRIND, KEBBIT_TEETH_DUST, PESTLE_AND_MORTAR, KEBBIT_TEETH),
            simpleProduct(Action.GRIND, GROUND_KELP, PESTLE_AND_MORTAR, KELP),
            simpleProduct(Action.GRIND, LAVA_SCALE_SHARD, PESTLE_AND_MORTAR, LAVA_SCALE),
            simpleProduct(Action.GRIND, GROUND_MUD_RUNES, PESTLE_AND_MORTAR, MUD_RUNE),
            simpleProduct(Action.GRIND, MYSTERIOUS_CRUSHED_MEAT, PESTLE_AND_MORTAR, MYSTERIOUS_MEAT),
            simpleProduct(Action.GRIND, NIHIL_DUST, PESTLE_AND_MORTAR, NIHIL_SHARD),
            simpleProduct(Action.GRIND, KARAMBWAN_PASTE, PESTLE_AND_MORTAR, POISON_KARAMBWAN),
            simpleProduct(Action.GRIND, GROUND_COD, PESTLE_AND_MORTAR, RAW_COD),
            simpleProduct(Action.GRIND, RUNE_DUST, PESTLE_AND_MORTAR, RUNE_SHARDS),
            simpleProduct(Action.GRIND, GROUND_SEAWEED, PESTLE_AND_MORTAR, SEAWEED),
            simpleProduct(Action.GRIND, GROUND_TOOTH, PESTLE_AND_MORTAR, SUQAH_TOOTH),
            simpleProduct(Action.GRIND, UNICORN_HORN_DUST, PESTLE_AND_MORTAR, UNICORN_HORN),
            simpleProduct(Action.GRIND, CRUSHED_SUPERIOR_DRAGON_BONES, PESTLE_AND_MORTAR, SUPERIOR_DRAGON_BONES)
    };

    private static Product simpleProduct(Action action, int productId, int toolId, int materialId) {
        return new Product(action, productId,
                new Ingredient(toolId, 1, false),
                new Ingredient(materialId, 1, true)
        );
    }

    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked evt) {
        if (evt.getMenuAction() != MenuAction.ITEM_USE_ON_ITEM) {
            return;
        }

        ItemContainer inventory = this.client.getItemContainer(InventoryID.INVENTORY);
        if (inventory == null) {
            return;
        }

        Item[] items = IntStream.of(client.getSelectedItemIndex(), evt.getParam0())
                .mapToObj(inventory::getItem)
                .toArray(Item[]::new);

        for (Product product : PRODUCTS) {
            if (product.isMadeWith(items)) {
                int amount = product.getMakeProductCount(inventoryHelper);
                actionManager.setAction(product.getAction(), amount, product.getProductId());
            }
        }

    }

}
