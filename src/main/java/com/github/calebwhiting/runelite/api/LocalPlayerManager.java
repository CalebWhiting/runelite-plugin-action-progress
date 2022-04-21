package com.github.calebwhiting.runelite.api;

import com.github.calebwhiting.runelite.api.event.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.InteractingChanged;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;

import java.util.Objects;

@Slf4j
@Singleton
public class LocalPlayerManager {

    @Inject private Client client;
    @Inject private EventBus eventBus;

    private LocalPoint pDest;
    private LocalPoint pPos;
    private int pRegionId = -1;

    @Subscribe
    public void onClientTick(ClientTick evt) {
        LocalPoint dest = client.getLocalDestinationLocation();
        if (isDifferent(dest, this.pDest)) {
            this.eventBus.post(new DestinationChanged(this.pDest, dest));
            this.pDest = dest;
        }
        Player me = client.getLocalPlayer();
        LocalPoint pos = me == null ? null : me.getLocalLocation();
        if (isDifferent(pos, this.pPos)) {
            this.eventBus.post(new LocalPositionChanged(this.pPos, pos));
            this.pPos = pos;
        }
        int regionId = (pos == null) ? -1 : WorldPoint.fromLocal(client, pos).getRegionID();
        if (regionId != this.pRegionId) {
            eventBus.post(new LocalRegionChanged(this.pRegionId, pRegionId));
            this.pRegionId = regionId;
        }
    }

    @Subscribe
    public void onAnimationChanged(AnimationChanged evt) {
        Player me = client.getLocalPlayer();
        if (evt.getActor() == me) {
            assert Objects.nonNull(me);
            eventBus.post(new LocalAnimationChanged(me));
        }
    }

    @Subscribe
    public void onInteractingChanged(InteractingChanged evt) {
        Player me = client.getLocalPlayer();
        if (evt.getSource() == me) {
            assert Objects.nonNull(me);
            eventBus.post(new LocalInteractingChanged(me, evt.getTarget()));
        }
    }

    private boolean isDifferent(LocalPoint dest, LocalPoint pDest) {
        return dest != pDest && (dest == null || this.pDest == null || dest.distanceTo(this.pDest) > 0);
    }

}
