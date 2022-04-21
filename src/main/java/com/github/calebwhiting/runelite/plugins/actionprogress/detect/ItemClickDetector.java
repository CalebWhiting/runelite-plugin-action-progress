package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.api.InventoryHelper;
import com.github.calebwhiting.runelite.api.data.Herblore;
import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.MenuAction;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.eventbus.Subscribe;

@Slf4j
@Singleton
public class ItemClickDetector extends ActionDetector {

    @Inject private InventoryHelper inventoryHelper;
    @Inject private ActionManager actionManager;

    @Override
    public void setup() {
        super.setup();
        registerAction(Action.HERB_CLEAN, Herblore.GRIMY_HERBS);
    }

    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked evt) {
        if (evt.getMenuAction() == MenuAction.ITEM_FIRST_OPTION) {
            Action action = (Action) this.itemActions.get(evt.getId());
            if (action != null) {
                int amount = inventoryHelper.getItemCountById(evt.getId());
                actionManager.setAction(action, amount, evt.getId());
            }
        }
    }

}
