package com.github.calebwhiting.runelite.api.event;

import lombok.Data;
import lombok.NonNull;
import net.runelite.api.Player;

@Data
public class LocalAnimationChanged {

    @NonNull private final Player localPlayer;

}
