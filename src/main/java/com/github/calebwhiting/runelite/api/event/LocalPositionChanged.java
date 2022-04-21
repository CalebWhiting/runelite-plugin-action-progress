package com.github.calebwhiting.runelite.api.event;

import lombok.Data;
import net.runelite.api.coords.LocalPoint;

@Data
public class LocalPositionChanged {

    private final LocalPoint from;
    private final LocalPoint to;

}
