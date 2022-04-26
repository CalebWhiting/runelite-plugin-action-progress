package com.github.calebwhiting.runelite.plugins.actionprogress;

import com.github.calebwhiting.runelite.api.InventoryManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ActionUtils {

    @Inject
    private InventoryManager inventoryManager;

    public int getActionsUntilFull(int nCreatePerAction, int nDestroyPerAction) {
        int freeSlots = inventoryManager.getFreeSpaces();
        int diffPerAction = (nCreatePerAction - nDestroyPerAction);
        if (diffPerAction <= 0) {
            return Integer.MAX_VALUE;
        }
        return (freeSlots / diffPerAction) + (freeSlots % nCreatePerAction == 0 ? 0 : 1);
    }

}
