package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.api.InventoryManager;
import com.github.calebwhiting.runelite.data.Ingredient;
import com.github.calebwhiting.runelite.plugins.actionprogress.Product;
import com.google.inject.Inject;
import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.client.eventbus.Subscribe;

import java.util.stream.IntStream;

import static com.github.calebwhiting.runelite.plugins.actionprogress.Action.GRIND;
import static net.runelite.api.ItemID.*;

public class UseItemOnItemDetector extends ActionDetector
{

	private static final Ingredient PESTLE_AND_MORTAR = new Ingredient(ItemID.PESTLE_AND_MORTAR, 1, false);

	private static final Product[] PRODUCTS = {
			new Product(GRIND, GROUND_ASHES, PESTLE_AND_MORTAR, new Ingredient(ASHES)),
			new Product(GRIND, CRUSHED_NEST, PESTLE_AND_MORTAR, new Ingredient(BIRD_NEST_5075)),
			new Product(GRIND, DRAGON_SCALE_DUST, PESTLE_AND_MORTAR, new Ingredient(BLUE_DRAGON_SCALE)),
			new Product(GRIND, GROUND_CHARCOAL, PESTLE_AND_MORTAR, new Ingredient(CHARCOAL)),
			new Product(GRIND, CHOCOLATE_DUST, PESTLE_AND_MORTAR, new Ingredient(CHOCOLATE_BAR)),
			new Product(GRIND, GROUND_CRAB_MEAT, PESTLE_AND_MORTAR, new Ingredient(CRAB_MEAT)),
			new Product(GRIND, GOAT_HORN_DUST, PESTLE_AND_MORTAR, new Ingredient(DESERT_GOAT_HORN)),
			new Product(GRIND, GROUND_THISTLE, PESTLE_AND_MORTAR, new Ingredient(DRIED_THISTLE)),
			new Product(GRIND, GORAK_CLAW_POWDER, PESTLE_AND_MORTAR, new Ingredient(GORAK_CLAWS)),
			new Product(GRIND, GROUND_GUAM, PESTLE_AND_MORTAR, new Ingredient(GUAM_LEAF)),
			new Product(GRIND, KEBBIT_TEETH_DUST, PESTLE_AND_MORTAR, new Ingredient(KEBBIT_TEETH)),
			new Product(GRIND, GROUND_KELP, PESTLE_AND_MORTAR, new Ingredient(KELP)),
			new Product(GRIND, LAVA_SCALE_SHARD, PESTLE_AND_MORTAR, new Ingredient(LAVA_SCALE)),
			new Product(GRIND, GROUND_MUD_RUNES, PESTLE_AND_MORTAR, new Ingredient(MUD_RUNE)),
			new Product(GRIND, MYSTERIOUS_CRUSHED_MEAT, PESTLE_AND_MORTAR, new Ingredient(MYSTERIOUS_MEAT)),
			new Product(GRIND, NIHIL_DUST, PESTLE_AND_MORTAR, new Ingredient(NIHIL_SHARD)),
			new Product(GRIND, KARAMBWAN_PASTE, PESTLE_AND_MORTAR, new Ingredient(POISON_KARAMBWAN)),
			new Product(GRIND, GROUND_COD, PESTLE_AND_MORTAR, new Ingredient(RAW_COD)),
			new Product(GRIND, RUNE_DUST, PESTLE_AND_MORTAR, new Ingredient(RUNE_SHARDS)),
			new Product(GRIND, GROUND_SEAWEED, PESTLE_AND_MORTAR, new Ingredient(SEAWEED)),
			new Product(GRIND, GROUND_TOOTH, PESTLE_AND_MORTAR, new Ingredient(SUQAH_TOOTH)),
			new Product(GRIND, UNICORN_HORN_DUST, PESTLE_AND_MORTAR, new Ingredient(UNICORN_HORN)),
			new Product(GRIND, CRUSHED_SUPERIOR_DRAGON_BONES, PESTLE_AND_MORTAR, new Ingredient(SUPERIOR_DRAGON_BONES))
	};

	@Inject private InventoryManager inventoryManager;

	@Inject private Client client;

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked evt)
	{
		if (evt.getMenuAction() != MenuAction.WIDGET_TARGET_ON_WIDGET) {
			return;
		}
		ItemContainer inventory = this.client.getItemContainer(InventoryID.INVENTORY);
		Widget widget = this.client.getSelectedWidget();
		if (inventory == null|| widget == null) {
			return;
		}
		Item[] items = IntStream.of(widget.getId(), evt.getParam0())
								.mapToObj(inventory::getItem)
								.toArray(Item[]::new);
		for (Product product : PRODUCTS) {
			if (product.isMadeWith(items)) {
				int amount = product.getMakeProductCount(this.inventoryManager);
				this.actionManager.setAction(product.getAction(), amount, product.getProductId());
			}
		}
	}

}
