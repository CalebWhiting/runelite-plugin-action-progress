package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.api.InventoryHelper;
import com.github.calebwhiting.runelite.api.data.IDQuery;
import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionProgressPlugin;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.AnimationChanged;
import net.runelite.client.eventbus.Subscribe;

import java.util.Arrays;

@Slf4j
@Singleton
public class TemporossDetector extends ActionDetector {

    private static final int TEMPOROSS_REGION = 12078;

    private static final int[] ID_AMMUNITION_CRATE;
    private static final int[] AMMUNITION;

    @Inject private Client client;
    @Inject private ActionProgressPlugin plugin;
    @Inject private InventoryHelper inventoryHelper;

    static {
        ID_AMMUNITION_CRATE = IDQuery.ofNPCs().query("AMMUNITION_CRATE").results().build();
        Arrays.sort(ID_AMMUNITION_CRATE);

        AMMUNITION = IDQuery.ofItems().query("((RAW|CRYSTALLISED)_)?HARPOONFISH").results().build();
        Arrays.sort(AMMUNITION);
    }

    @Subscribe
    public void onAnimationChanged(AnimationChanged evt) {
        Action action = this.actionManager.getCurrentAction();
        Player me = this.client.getLocalPlayer();
        if (me == null || action == Action.TEMPOROSS_FILL_CRATE || action == Action.TEMPOROSS_COOKING) {
            return;
        }
        if (evt.getActor() != me || me.getAnimation() != AnimationID.COOKING_RANGE) {
            return;
        }
        LocalPoint localPoint = me.getLocalLocation();
        int region = WorldPoint.fromLocalInstance(this.client, localPoint).getRegionID();
        if (region != TEMPOROSS_REGION) {
            return;
        }

        Actor interacting = me.getInteracting();
        int id = interacting instanceof NPC ? ((NPC) interacting).getId() : -1;
        if (id != -1 && Arrays.binarySearch(ID_AMMUNITION_CRATE, id) >= 0) {
            int amount = this.inventoryHelper.getItemCountById(AMMUNITION);
            this.actionManager.setAction(Action.TEMPOROSS_FILL_CRATE, amount, -1);
        } else {
            int amount = this.inventoryHelper.getItemCountById(ItemID.RAW_HARPOONFISH);
            this.actionManager.setAction(Action.TEMPOROSS_COOKING, amount, ItemID.HARPOONFISH);
        }
    }

}
