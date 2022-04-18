package com.github.calebwhiting.runelite.plugins.actionprogress.event;

import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import lombok.Getter;

@Getter
public class ActionStoppedEvent extends GameActionEvent {

    private final boolean interrupted;

    public ActionStoppedEvent(Action action, int productId, int actionCount, int startTick, int endTick, boolean interrupted) {
        super(action, productId, actionCount, startTick, endTick);
        this.interrupted = interrupted;
    }

}
