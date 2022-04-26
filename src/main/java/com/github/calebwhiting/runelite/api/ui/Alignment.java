package com.github.calebwhiting.runelite.api.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public
enum Alignment {
    TOP_LEFT(Alignment.MIN, Alignment.MIN),
    TOP(Alignment.MID, Alignment.MIN),
    TOP_RIGHT(Alignment.MAX, Alignment.MIN),
    LEFT(Alignment.MIN, Alignment.MID),
    CENTER(Alignment.MID, Alignment.MID),
    RIGHT(Alignment.MAX, Alignment.MID),
    BOTTOM_LEFT(Alignment.MIN, Alignment.MAX),
    BOTTOM(Alignment.MID, Alignment.MAX),
    BOTTOM_RIGHT(Alignment.MAX, Alignment.MAX);

    public static final int MIN = 0;
    public static final int MID = 1;
    public static final int MAX = 2;

    private final int alignmentX;
    private final int alignmentY;
}
