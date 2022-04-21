package com.github.calebwhiting.runelite.api.event;

import lombok.Data;

@Data
public class LocalRegionChanged {

    private final int from;
    private final int to;

}
