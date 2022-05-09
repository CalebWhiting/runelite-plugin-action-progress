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

@Slf4j
@Singleton
public class LocalPlayerManager
{

	@Inject private Client client;

	@Inject private EventBus eventBus;

	private LocalPoint pDest;

	private LocalPoint pPos;

	private int pRegionId = -1;

	@Subscribe
	public void onClientTick(ClientTick evt)
	{
		LocalPoint dest = this.client.getLocalDestinationLocation();
		if (this.isDifferent(dest, this.pDest)) {
			this.eventBus.post(new DestinationChanged(this.pDest, dest));
			this.pDest = dest;
		}
		Player me = this.client.getLocalPlayer();
		LocalPoint pos = me == null ? null : me.getLocalLocation();
		if (this.isDifferent(pos, this.pPos)) {
			this.eventBus.post(new LocalPositionChanged(this.pPos, pos));
			this.pPos = pos;
		}
		int regionId = (pos == null) ? -1 : WorldPoint.fromLocal(this.client, pos).getRegionID();
		if (regionId != this.pRegionId) {
			this.eventBus.post(new LocalRegionChanged(this.pRegionId, this.pRegionId));
			this.pRegionId = regionId;
		}
	}

	@Subscribe
	public void onAnimationChanged(AnimationChanged evt)
	{
		Player me = this.client.getLocalPlayer();
		if (me != null && evt.getActor() == me) {
			this.eventBus.post(new LocalAnimationChanged(me));
		}
	}

	@Subscribe
	public void onInteractingChanged(InteractingChanged evt)
	{
		Player me = this.client.getLocalPlayer();
		if (me != null && evt.getSource() == me) {
			this.eventBus.post(new LocalInteractingChanged(me, evt.getTarget()));
		}
	}

	private boolean isDifferent(LocalPoint first, LocalPoint second)
	{
		if (first == second) {
			return false;
		}
		return first == null || second == null || first.distanceTo(second) > 0;
	}

}
