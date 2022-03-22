package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.plugins.actionprogress.ActionProgressPlugin;
import com.github.calebwhiting.runelite.plugins.actionprogress.TimedAction;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.AnimationChanged;
import net.runelite.client.eventbus.Subscribe;

@Slf4j
public class SandpitDetector {

    @Inject
    private Client client;

    @Inject
    private ActionProgressPlugin plugin;

    @Subscribe
    public void onAnimationChanged(AnimationChanged evt) {
        if (evt.getActor() != client.getLocalPlayer())
            return;
        if (evt.getActor().getAnimation() != AnimationID.SAND_COLLECTION) {
            return;
        }
        if (plugin.getCurrentAction() == TimedAction.COLLECT_SAND) {
            return;
        }
        ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
        if (inventory == null) {
            return;
        }
        int buckets = inventory.count(ItemID.BUCKET);
        plugin.setActionUnchecked(TimedAction.COLLECT_SAND, ItemID.BUCKET_OF_SAND, buckets);
    }

}
