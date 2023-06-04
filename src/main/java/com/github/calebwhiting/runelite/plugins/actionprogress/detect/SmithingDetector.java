package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.runelite.api.Client;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.eventbus.Subscribe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Singleton
public class SmithingDetector extends ActionDetector
{

	private static final Pattern X_BARS_PATTERN = Pattern.compile("^(?<x>\\d*) (bars?)$");

	private static final int VAR_AVAILABLE_MATERIALS = 2224;

	@Inject private Client client;

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked evt)
	{
		int param1 = evt.getParam1();
		if (param1 == -1) {
			return;
		}
		Widget widget = this.client.getWidget(param1);
		if (widget == null) {
			return;
		}
		if (widget.getParentId() == WidgetInfo.SMITHING_INVENTORY_ITEMS_CONTAINER.getId()) {
			Widget xBarsWidget = widget.getChild(1);
			Widget itemContainer = widget.getChild(2);
			String text = xBarsWidget.getText();
			Matcher matcher = X_BARS_PATTERN.matcher(text);
			if (matcher.matches()) {
				String x = matcher.group("x");
				int barsPerItem = Integer.parseInt(x);
				int availableBars = this.client.getVarpValue(VAR_AVAILABLE_MATERIALS);
				int productId = itemContainer.getItemId();
				this.actionManager.setAction(Action.SMITHING, (availableBars / barsPerItem), productId);
			}
		}
	}

}
