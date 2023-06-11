package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.data.Crafting;
import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.widgets.Widget;
import net.runelite.client.eventbus.Subscribe;

import java.util.Arrays;

/**
 * Detects actions initiated from the furnace casting interface (Gold/Silver products)
 */
public class FurnaceCastingDetector extends ActionDetector
{

	/**
	 * Widgets that contain item buttons in the silver casting interface.
	 */
	private static final int[] FURNACE_SILVER_PARENTS;

	/**
	 * Widgets that contain item buttons in the gold casting interface.
	 */
	private static final int[] FURNACE_GOLD_PARENTS;

	/**
	 * Indicates how many items are to be created in the crafting dialogue.
	 */
	private static final int VAR_FURNACE_MAKE_AMOUNT = 2224;

	static {
		FURNACE_SILVER_PARENTS = new int[]{393222, 393226, 393230, 393234, 393238};
		Arrays.sort(FURNACE_SILVER_PARENTS);
		FURNACE_GOLD_PARENTS = new int[]{29229059, 29229073, 29229076, 29229086, 29229103};
		Arrays.sort(FURNACE_GOLD_PARENTS);
	}

	@Inject private Client client;

	@Override
	public void setup()
	{
		this.registerAction(Action.CRAFT_CAST_GOLD_AND_SILVER, Crafting.SILVER_AND_GOLD_ITEMS);
	}

	@Subscribe
	@Singleton
	public void onMenuOptionClicked(net.runelite.api.events.MenuOptionClicked evt)
	{
		if (evt.getParam1() <= 0 || evt.getMenuAction() != MenuAction.CC_OP) {
			return;
		}
		Widget widget = this.client.getWidget(evt.getParam1());
		if (widget == null) {
			return;
		}
		int actionCount = this.client.getVarpValue(VAR_FURNACE_MAKE_AMOUNT);
		if (Arrays.binarySearch(FURNACE_SILVER_PARENTS, widget.getParentId()) >= 0) {
			Widget itemContainer = widget.getChild(0);
			if (itemContainer != null) {
				int product = itemContainer.getItemId();
				this.setActionByItemId(product, actionCount);
			}
		} else if (Arrays.binarySearch(FURNACE_GOLD_PARENTS, widget.getParentId()) >= 0) {
			int product = widget.getItemId();
			this.setActionByItemId(product, actionCount);
		}
	}

}
