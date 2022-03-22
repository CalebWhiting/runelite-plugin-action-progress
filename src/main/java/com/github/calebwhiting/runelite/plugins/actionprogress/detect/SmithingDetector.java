package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.plugins.actionprogress.ActionProgressPlugin;
import com.github.calebwhiting.runelite.plugins.actionprogress.TimedAction;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.eventbus.Subscribe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SmithingDetector {

    private static final Pattern X_BARS_PATTERN = Pattern.compile("^(?<x>[0-9]*) (bar[s]?)$");
    private static final int VAR_AVAILABLE_MATERIALS = 2224;

    @Inject
    private ActionProgressPlugin plugin;

    @Inject
    private Client client;

    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked evt) {
        if (evt.getParam1() == -1) {
            return;
        }
        Widget widget = client.getWidget(evt.getParam1());
        if (widget == null) {
            return;
        }
        if (widget.getParentId() != WidgetInfo.SMITHING_INVENTORY_ITEMS_CONTAINER.getId()) {
            return;
        }
        Widget xBarsWidget = widget.getChild(1);
        Widget itemContainer = widget.getChild(2);
        Matcher matcher = X_BARS_PATTERN.matcher(xBarsWidget.getText());
        if (!matcher.matches()) {
            return;
        }
        int barsPerItem = Integer.parseInt(matcher.group("x"));
        int availableBars = client.getVarpValue(VAR_AVAILABLE_MATERIALS);
        int productId = itemContainer.getItemId();
        plugin.setActionUnchecked(TimedAction.SMITHING, productId, (availableBars / barsPerItem));

    }

}
