package com.github.calebwhiting.runelite.plugins.actionprogress.event;

import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import lombok.Data;

@Data
public abstract class GameActionEvent {

    private final Action action;
    private final int productId;
    private final int actionCount;
    private final int startTick;
    private final int endTick;

}
