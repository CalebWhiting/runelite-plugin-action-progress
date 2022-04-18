package com.github.calebwhiting.runelite.api.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public
enum Anchor {
    TOP_LEFT(Anchor.MIN, Anchor.MIN),
    TOP(Anchor.MID, Anchor.MIN),
    TOP_RIGHT(Anchor.MAX, Anchor.MIN),
    LEFT(Anchor.MIN, Anchor.MID),
    CENTER(Anchor.MID, Anchor.MID),
    RIGHT(Anchor.MAX, Anchor.MID),
    BOTTOM_LEFT(Anchor.MIN, Anchor.MAX),
    BOTTOM(Anchor.MID, Anchor.MAX),
    BOTTOM_RIGHT(Anchor.MAX, Anchor.MAX);

    public static final int MIN = 0;
    public static final int MID = 1;
    public static final int MAX = 2;

    private final int alignmentX;
    private final int alignmentY;
}
