package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.api.InventoryHelper;
import com.github.calebwhiting.runelite.api.event.LocalAnimationChanged;
import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionProgressPlugin;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.eventbus.Subscribe;

import java.util.Arrays;

@Slf4j
@Singleton
public class TemporossDetector extends ActionDetector {

    private static final int TEMPOROSS_REGION = 12078;

    private static final int[] TEMPOROSS_AMMUNITION_CRATE = {NpcID.AMMUNITION_CRATE, NpcID.AMMUNITION_CRATE_10577, NpcID.AMMUNITION_CRATE_10578, NpcID.AMMUNITION_CRATE_10579};
    private static final int[] TEMPOROSS_AMMUNITION = {ItemID.RAW_HARPOONFISH, ItemID.HARPOONFISH, ItemID.CRYSTALLISED_HARPOONFISH};

    @Inject private Client client;
    @Inject private ActionProgressPlugin plugin;
    @Inject private InventoryHelper inventoryHelper;

    static {
        Arrays.sort(TEMPOROSS_AMMUNITION_CRATE);
        Arrays.sort(TEMPOROSS_AMMUNITION);
    }

    @Subscribe
    public void onLocalAnimationChanged(LocalAnimationChanged evt) {
        Action action = this.actionManager.getCurrentAction();
        Player me = evt.getLocalPlayer();
        if (action == Action.TEMPOROSS_FILL_CRATE || action == Action.TEMPOROSS_COOKING) {
            return;
        }
        if (me.getAnimation() != AnimationID.COOKING_RANGE) {
            return;
        }
        WorldPoint worldPoint = me.getWorldLocation();
        int region = worldPoint.getRegionID();
        if (region != TEMPOROSS_REGION) {
            return;
        }

        Actor interacting = me.getInteracting();
        int id = interacting instanceof NPC ? ((NPC) interacting).getId() : -1;
        if (id != -1 && Arrays.binarySearch(TEMPOROSS_AMMUNITION_CRATE, id) >= 0) {
            int amount = this.inventoryHelper.getItemCountById(TEMPOROSS_AMMUNITION);
            this.actionManager.setAction(Action.TEMPOROSS_FILL_CRATE, amount, -1);
        } else {
            int amount = this.inventoryHelper.getItemCountById(ItemID.RAW_HARPOONFISH);
            this.actionManager.setAction(Action.TEMPOROSS_COOKING, amount, ItemID.HARPOONFISH);
        }
    }

}
