package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.data.Magic;
import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionProgressConfig;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.ItemContainer;
import net.runelite.api.MenuAction;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.client.eventbus.Subscribe;

import java.util.Arrays;
@Singleton
public class EnchantSpellDetector extends ActionDetector
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
			for (Magic.EnchantSpell enchantSpell : Magic.EnchantSpell.values()) {
				Magic.Spell spell = enchantSpell.getSpell();
				Widget widget = this.client.getWidget(spell.getWidgetId());
				if (widget != null && widget.getBorderType() == 2) {
					int itemId = evt.getItemId();
					if (Arrays.binarySearch(enchantSpell.getJewellery(), itemId) < 0) {
						continue;
					}
					int amount = Math.min(inventory.count(itemId), spell.getAvailableCasts(this.client));
					this.actionManager.setAction(Action.MAGIC_ENCHANT_JEWELLERY, amount, itemId);
					break;
				}
			}
		}
	}

}
