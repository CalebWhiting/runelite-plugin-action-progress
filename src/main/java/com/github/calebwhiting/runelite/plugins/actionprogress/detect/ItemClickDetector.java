package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.api.InventoryManager;
import com.github.calebwhiting.runelite.data.Herblore;
import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.eventbus.Subscribe;

@Slf4j
@Singleton
public class ItemClickDetector extends ActionDetector
{

	@Inject private Client client;

	@Inject private InventoryManager inventoryManager;

	@Inject private ActionManager actionManager;

	@Override
	public void setup()
	{
		super.setup();
		this.registerAction(Action.HERB_CLEAN, Herblore.GRIMY_HERBS);
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked evt)
	{
		if (evt.getMenuAction() != MenuAction.CC_OP) {
			return;
		}
		if (evt.getParam1() != WidgetInfo.INVENTORY.getPackedId()) {
			return;
		}
		ItemContainer inventory = this.client.getItemContainer(InventoryID.INVENTORY);
		if (inventory == null) {
			return;
		}
		Item item = inventory.getItem(evt.getParam0());
		if (item == null) {
			return;
		}
		Action action = (Action) this.itemActions.get(item.getId());
		if (action == null) {
			return;
		}
		int amount = this.inventoryManager.getItemCountById(item.getId());
		this.actionManager.setAction(action, amount, item.getId());
	}

}
