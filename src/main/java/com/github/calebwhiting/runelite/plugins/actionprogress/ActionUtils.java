package com.github.calebwhiting.runelite.plugins.actionprogress;

import com.github.calebwhiting.runelite.api.InventoryHelper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ActionUtils {

    @Inject
    private InventoryHelper inventoryHelper;

    public int getActionsUntilFull(int nCreatePerAction, int nDestroyPerAction) {
        int freeSlots = inventoryHelper.getFreeSpaces();
        int diffPerAction = (nCreatePerAction - nDestroyPerAction);
        if (diffPerAction <= 0) {
            return Integer.MAX_VALUE;
        }
        return (freeSlots / diffPerAction) + (freeSlots % nCreatePerAction == 0 ? 0 : 1);
    }

}
