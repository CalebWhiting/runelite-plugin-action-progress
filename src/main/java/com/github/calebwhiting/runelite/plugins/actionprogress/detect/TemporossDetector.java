package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.api.InventoryManager;
import com.github.calebwhiting.runelite.api.event.LocalAnimationChanged;
import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionProgressPlugin;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ChatMessage;
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
    @Inject private InventoryManager inventoryManager;

    static {
        Arrays.sort(TEMPOROSS_AMMUNITION_CRATE);
        Arrays.sort(TEMPOROSS_AMMUNITION);
    }

    @Subscribe
    public void onLocalAnimationChanged(LocalAnimationChanged evt) {
        Action action = this.actionManager.getCurrentAction();
        Player me = evt.getLocalPlayer();
        int region = WorldPoint.fromLocalInstance(client, me.getLocalLocation()).getRegionID();
        if (region != TEMPOROSS_REGION) {
            log.debug("not in tempoross region");
            return;
        }
        if (action == Action.TEMPOROSS_FILL_CRATE || action == Action.TEMPOROSS_COOKING) {
            log.debug("action is already {}", action);
            return;
        }
        if (me.getAnimation() != AnimationID.COOKING_RANGE) {
            log.debug("incorrect animation");
            return;
        }

        Actor interacting = me.getInteracting();
        int id = interacting instanceof NPC ? ((NPC) interacting).getId() : -1;
        if (id != -1 && Arrays.binarySearch(TEMPOROSS_AMMUNITION_CRATE, id) >= 0) {
            log.debug("filling crate");
            int amount = this.inventoryManager.getItemCountById(TEMPOROSS_AMMUNITION);
            this.actionManager.setAction(Action.TEMPOROSS_FILL_CRATE, amount, -1);
        } else {
            log.info("cooking fish");
            int amount = this.inventoryManager.getItemCountById(ItemID.RAW_HARPOONFISH);
            this.actionManager.setAction(Action.TEMPOROSS_COOKING, amount, ItemID.HARPOONFISH);
        }
    }

}
