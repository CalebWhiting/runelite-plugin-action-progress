package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.api.InventoryManager;
import com.github.calebwhiting.runelite.data.Herblore;
import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.InterfaceID;
import net.runelite.client.eventbus.Subscribe;

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
		if (evt.getMenuAction() != MenuAction.CC_OP || !evt.getMenuOption().toLowerCase().equals("clean")) {
			return;
		}
		int itemID = evt.getItemId();
		ItemContainer inventory = this.client.getItemContainer(InventoryID.INVENTORY);
		if (inventory == null || !inventory.contains(itemID)) {
			return;
		}
		Action action = this.itemActions.get(itemID);
		if (action == null) {
			return;
		}
		int amount = this.inventoryManager.getItemCountById(itemID);
		this.actionManager.setAction(action, amount, itemID);
	}

}
