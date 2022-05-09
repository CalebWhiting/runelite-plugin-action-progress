package com.github.calebwhiting.runelite.api.event;

import lombok.Data;
import lombok.NonNull;
import net.runelite.api.Actor;
import net.runelite.api.Player;

@Data
public class LocalInteractingChanged
{

	@NonNull private final Player localPlayer;

	private final Actor target;

}
