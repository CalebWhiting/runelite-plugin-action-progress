package com.github.calebwhiting.runelite.plugins.actionprogress.event;

import com.github.calebwhiting.runelite.plugins.actionprogress.Action;

public class ActionStartedEvent extends GameActionEvent {

    public ActionStartedEvent(Action action, int productId, int actionCount, int startTick, int endTick) {
        super(action, productId, actionCount, startTick, endTick);
    }

}
