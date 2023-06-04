package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.data.Magic;
import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.client.eventbus.Subscribe;
@Singleton
public class LecternDetector extends ActionDetector
{

	private static final int VAR_MAKE_AMOUNT = 2224;

	private static final int WIDGET_LECTERN = 5177344;

	@Inject private Client client;

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked evt)
	{
		if (evt.getParam1() == -1 || evt.getMenuAction() != MenuAction.CC_OP) {
			return;
		}
		Widget widget = this.client.getWidget(evt.getParam1());
		if (widget == null || widget.getParentId() != WIDGET_LECTERN) {
			return;
		}
		int amount = this.client.getVarpValue(VAR_MAKE_AMOUNT);
		for (Magic.LecternSpell item : Magic.LecternSpell.values()) {
			if (evt.getParam1() == item.getWidgetId()) {
				this.actionManager.setAction(Action.MAGIC_CREATE_TABLET, amount, item.getProduct());
				break;
			}
		}
	}

}
