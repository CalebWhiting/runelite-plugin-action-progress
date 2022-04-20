package com.github.calebwhiting.runelite.api.event;

import net.runelite.api.Player;

public class LocalInteractingChanged extends AbstractLocalPlayerEvent {

    public LocalInteractingChanged(Player localPlayer) {
        super(localPlayer);
    }

}
