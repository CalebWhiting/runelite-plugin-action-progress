package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import com.google.inject.Inject;
import net.runelite.api.AnimationID;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.annotations.Varbit;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.eventbus.Subscribe;

public class TemporossRewardPoolDetector extends ActionDetector {

    @Varbit
    private static final int VAR_TEMPOROSS_PERMITS = 11936;

    @Inject
    private Client client;

    private int previousPermits = -1;

    @Subscribe
    public void onGameTick(GameTick evt) {
        int permits = client.getVarbitValue(VAR_TEMPOROSS_PERMITS);
        if (permits < previousPermits) {
            if (actionManager.getCurrentAction() != Action.TEMPOROSS_REWARD_POOL) {
                actionManager.setAction(Action.TEMPOROSS_REWARD_POOL, permits + 1, -1);
            }
        }
        this.previousPermits = permits;

    }

    // @Subscribe
    // public void onLocalAnimationChanged(LocalAnimationChanged evt) {
    //     int permits = client.getVarbitValue(VAR_TEMPOROSS_PERMITS);
    //     if (permits == 0) {
    //         return;
    //     }
    //     Player localPlayer = evt.getLocalPlayer();
    //     if (actionManager.getCurrentAction() == Action.TEMPOROSS_REWARD_POOL) {
    //         return;
    //     }
    //     if (localPlayer.getAnimation() != AnimationID.FISHING_NET) {
    //         return;
    //     }
    //     WorldPoint pos = localPlayer.getWorldLocation();
    //     if (pos.getX() == 3153 && (pos.getY() >= 2832 && pos.getY() <= 2833)) {
    //         actionManager.setAction(Action.TEMPOROSS_REWARD_POOL, permits, -1);
    //     }
    // }

}
