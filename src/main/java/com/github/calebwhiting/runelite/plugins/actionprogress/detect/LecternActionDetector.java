package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.client.eventbus.Subscribe;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionProgressPlugin;
import com.github.calebwhiting.runelite.plugins.actionprogress.TimedAction;
import com.github.calebwhiting.runelite.plugins.actionprogress.data.Magic;

@Slf4j
public class LecternActionDetector {

    private static final int VAR_MAKE_AMOUNT = 2224;
    private static final int WIDGET_LECTERN = 5177344;

    @Inject
    private Client client;

    @Inject
    private ActionProgressPlugin plugin;

    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked evt) {
        if (evt.getParam1() == -1) return;
        if (evt.getMenuAction() != MenuAction.CC_OP) return;
        Widget widget = client.getWidget(evt.getParam1());
        if (widget == null) return;
        if (widget.getParentId() != WIDGET_LECTERN) return;
        int amount = client.getVarpValue(VAR_MAKE_AMOUNT);
        for (Magic.LecternItem item : Magic.LecternItem.values()) {
            if (evt.getParam1() == item.getWidgetId()) {
                plugin.setActionUnchecked(
                        TimedAction.MAGIC_CREATE_TABLET, item.getProduct(), amount);
                break;
            }
        }
    }

}
