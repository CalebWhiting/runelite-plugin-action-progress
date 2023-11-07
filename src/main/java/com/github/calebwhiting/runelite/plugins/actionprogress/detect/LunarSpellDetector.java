package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.data.Magic;
import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionProgressConfig;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.ItemID;
import net.runelite.api.MenuAction;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.client.eventbus.Subscribe;

@Singleton
public class LunarSpellDetector extends ActionDetector
{

	@Inject private ActionProgressConfig config;

	@Inject private Client client;

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked evt)
	{
		if (!this.config.magicEnchantJewellery()) {
			return;
		}
		if (evt.getMenuAction() == MenuAction.WIDGET_TARGET_ON_WIDGET) {
			ItemContainer inventory = this.client.getItemContainer(InventoryID.INVENTORY);
			if (inventory == null) {
				return;
			}
			for (Magic.PlankMakeSpell plankMakeSpell : Magic.PlankMakeSpell.values()) {
				Magic.Spell spell = plankMakeSpell.getSpell();
				Widget widget = this.client.getWidget(spell.getWidgetId());
				if (widget != null && widget.getBorderType() == 0) {
					int itemId = evt.getItemId();
					if (plankMakeSpell.getPlank() != itemId || !inventory.contains(ItemID.COINS_995)) {
						continue;
					}

					int coinsQuantity = 0;
					for (Item item : inventory.getItems()) {
						if(item.getId() == ItemID.COINS_995){
							coinsQuantity = item.getQuantity();
						}
					}

					int amount = Math.min(inventory.count(itemId), Math.min(spell.getAvailableCasts(this.client), coinsQuantity / plankMakeSpell.getCost()));
					this.actionManager.setAction(Action.MAGIC_PLANK_MAKE, amount, itemId);
					break;
				}
			}
		}
	}

}
