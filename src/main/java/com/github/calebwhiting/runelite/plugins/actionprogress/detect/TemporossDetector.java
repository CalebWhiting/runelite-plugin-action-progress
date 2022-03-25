package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.api.InventoryHelper;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionProgressPlugin;
import com.github.calebwhiting.runelite.plugins.actionprogress.TimedAction;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.AnimationChanged;
import net.runelite.client.eventbus.Subscribe;

import java.util.Arrays;

@Slf4j
public class TemporossDetector {

    private static final int TEMPOROSS_REGION = 12078;

    private static final int[] ID_AMMUNITION_CRATE;
    private static final int[] AMMUNITION;

    static {
        ID_AMMUNITION_CRATE = new int[]{
                NpcID.AMMUNITION_CRATE, NpcID.AMMUNITION_CRATE_10577,
                NpcID.AMMUNITION_CRATE_10578, NpcID.AMMUNITION_CRATE_10579
        };

        Arrays.sort(ID_AMMUNITION_CRATE);
        AMMUNITION = new int[] {
                ItemID.HARPOONFISH, ItemID.RAW_HARPOONFISH, ItemID.CRYSTALLISED_HARPOONFISH
        };
        Arrays.sort(AMMUNITION);
    }

    @Inject
    private Client client;

    @Inject
    private ActionProgressPlugin plugin;

    @Inject
    private InventoryHelper inventoryHelper;

    @Subscribe
    public void onAnimationChanged(AnimationChanged evt) {
        if (plugin.getCurrentAction() == TimedAction.TEMPOROSS_FILL_CRATE) {
            return;
        }

        if (plugin.getCurrentAction() == TimedAction.TEMPOROSS_COOKING) {
            return;
        }

        Player localPlayer = client.getLocalPlayer();
        if (localPlayer == null || evt.getActor() != localPlayer) {
            return;
        }

        int region = WorldPoint.fromLocalInstance(client, localPlayer.getLocalLocation()).getRegionID();
        if (region != TEMPOROSS_REGION)
        {
            return;
        }

        int animationId = localPlayer.getAnimation();
        if (animationId != AnimationID.COOKING_RANGE) {
            return;
        }

        Actor interacting = localPlayer.getInteracting();
        if (interacting instanceof NPC &&
                Arrays.binarySearch(ID_AMMUNITION_CRATE, ((NPC) interacting).getId()) >= 0) {
            int ammunition = inventoryHelper.getItemCount(AMMUNITION);
            plugin.setAction(TimedAction.TEMPOROSS_FILL_CRATE, ItemID.HARPOONFISH, ammunition);
        } else {
            int rawFood = inventoryHelper.getItemCount(ItemID.RAW_HARPOONFISH);
            plugin.setAction(TimedAction.TEMPOROSS_COOKING, ItemID.RAW_HARPOONFISH, rawFood);
        }

    }

}
