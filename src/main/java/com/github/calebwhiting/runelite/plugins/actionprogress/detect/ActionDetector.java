package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionManager;
import com.google.inject.Inject;
import com.jogamp.common.util.IntObjectHashMap;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.game.ItemManager;

@Slf4j
public class ActionDetector {
    @Inject protected ItemManager itemManager;
    @Inject protected ActionManager actionManager;
    protected final IntObjectHashMap itemActions = new IntObjectHashMap();

    protected void registerAction(Action action, int... itemIds) {
        for (int id : itemIds) {
            this.itemActions.put(id, action);
            log.debug("Registered action {} for item: {}", action, id);
        }
    }

    protected void setActionByItemId(int itemId, int amount) {
        Action action = (Action) this.itemActions.get(itemId);
        if (action == null) {
            unhandled(itemId);
        } else {
            this.actionManager.setAction(action, amount, itemId);
        }
    }

    protected void unhandled(int itemId) {
        log.error("Unhandled product: {}", itemId);
    }

    public void setup() {
    }

}