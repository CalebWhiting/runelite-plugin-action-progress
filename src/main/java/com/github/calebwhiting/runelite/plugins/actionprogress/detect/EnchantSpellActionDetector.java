package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.plugins.actionprogress.ActionProgressPlugin;
import com.github.calebwhiting.runelite.plugins.actionprogress.TimedAction;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.client.eventbus.Subscribe;
import com.github.calebwhiting.runelite.plugins.actionprogress.data.Magic;

import java.util.Arrays;

@Slf4j
public class EnchantSpellActionDetector {

    @Inject
    private ActionProgressPlugin plugin;

    @Inject
    private Client client;

    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked evt) {
        if (evt.getMenuAction() == MenuAction.ITEM_USE_ON_WIDGET) {
            final ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
            if (inventory == null) {
                return;
            }
            for (Magic.EnchantSpell enchantSpell : Magic.EnchantSpell.values()) {
                Magic.Spell spell = enchantSpell.getSpell();
                Widget widget = client.getWidget(spell.getWidgetId());
                if (widget != null && widget.getBorderType() == 2) {
                    int itemId = evt.getId();
                    if (Arrays.binarySearch(enchantSpell.getJewellery(), itemId) < 0) {
                        continue;
                    }
                    int amount = Math.min(inventory.count(itemId), spell.getAvailableCasts(client));
                    plugin.setActionUnchecked(TimedAction.MAGIC_ENCHANT_JEWELLERY, itemId, amount);
                    break;
                }
            }
        }
    }

}
