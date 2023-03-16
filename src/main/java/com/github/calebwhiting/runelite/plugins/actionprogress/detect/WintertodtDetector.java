package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.api.InterruptManager;
import com.github.calebwhiting.runelite.api.InventoryManager;
import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.client.eventbus.Subscribe;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Not ready for release
 */
@Slf4j
@Singleton
public class WintertodtDetector extends ActionDetector
{

	public static final int[] WOODCUTTING_ANIMATIONS = {
			AnimationID.WOODCUTTING_DRAGON_OR, AnimationID.WOODCUTTING_RUNE, AnimationID.WOODCUTTING_ADAMANT,
			AnimationID.WOODCUTTING_MITHRIL, AnimationID.WOODCUTTING_BLACK, AnimationID.WOODCUTTING_STEEL,
			AnimationID.WOODCUTTING_IRON, AnimationID.WOODCUTTING_BRONZE, AnimationID.WOODCUTTING_INFERNAL,
			AnimationID.WOODCUTTING_DRAGON, AnimationID.WOODCUTTING_3A_AXE, AnimationID.WOODCUTTING_GILDED,
			AnimationID.WOODCUTTING_CRYSTAL, AnimationID.WOODCUTTING_TRAILBLAZER
	};

	private static final int WINTERTODT_PRISON_REGION_ID = 6462;

	private static final String[] INTERRUPT_MESSAGES = {
			"The cold of the Wintertodt seeps into your bones.", "The freezing cold attack",
			"The brazier is broken and shrapnel damages you.", "The brazier has gone out."
	};

	private static final int[] BRUMA_KINDLING_MATERIALS = {
			ItemID.KNIFE, ItemID.BRUMA_ROOT
	};

	static {
		Arrays.sort(BRUMA_KINDLING_MATERIALS);
	}

	@Inject private Client client;

	@Inject private InventoryManager inventoryManager;

	@Inject private InterruptManager interruptManager;

	private boolean isInWintertodtPrison()
	{
		Player me = this.client.getLocalPlayer();
		if (me == null) {
			return false;
		}
		WorldPoint worldPoint = me.getWorldLocation();
		int region = worldPoint.getRegionID();
		return region == WINTERTODT_PRISON_REGION_ID;
	}

	@Subscribe
	public void onChatMessage(ChatMessage evt)
	{
		ChatMessageType chatMessageType = evt.getType();
		if (chatMessageType != ChatMessageType.GAMEMESSAGE && chatMessageType != ChatMessageType.SPAM) {
			return;
		}
		Action action = this.actionManager.getCurrentAction();
		if (action != Action.WINTERTODT_FIREMAKING && action != Action.WINTERTODT_FLETCHING) {
			return;
		}
		String message = evt.getMessage();
		Stream.of(INTERRUPT_MESSAGES)
			  .filter(message::startsWith)
			  .findFirst()
			  .ifPresent(x -> this.interruptManager.interrupt(evt));
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked evt)
	{
		if (evt.getMenuAction() != MenuAction.ITEM_USE_ON_ITEM) {
			return;
		}
		ItemContainer inventory = this.client.getItemContainer(InventoryID.INVENTORY);
		Widget widget = this.client.getSelectedWidget();
		if (inventory == null || widget == null) {
			return;
		}
		int[] items = {
				Objects.requireNonNull(inventory.getItem(widget.getId())).getId(),
				Objects.requireNonNull(inventory.getItem(evt.getParam0())).getId()
		};
		Arrays.sort(items);
		if (Arrays.equals(items, BRUMA_KINDLING_MATERIALS)) {
			this.actionManager.setAction(Action.WINTERTODT_FLETCHING,
					this.inventoryManager.getItemCountById(ItemID.BRUMA_ROOT),
					ItemID.BRUMA_KINDLING
			);
		}
	}

	@Subscribe
	public void onGameTick(GameTick evt)
	{
		Player local = this.client.getLocalPlayer();
		if (local == null || !this.isInWintertodtPrison()) {
			return;
		}
		Action action = this.actionManager.getCurrentAction();
		if (Arrays.binarySearch(WOODCUTTING_ANIMATIONS, local.getAnimation()) >= 0) {
			int rem = this.inventoryManager.getFreeSpaces();
			if (action != Action.WINTERTODT_WOODCUTTING) {
				this.actionManager.setAction(Action.WINTERTODT_WOODCUTTING, rem, ItemID.BRUMA_ROOT);
			}
		} else if (local.getAnimation() == AnimationID.LOOKING_INTO) {
			if (action != Action.WINTERTODT_FIREMAKING) {
				int rem = this.inventoryManager.getItemCountById(ItemID.BRUMA_ROOT, ItemID.BRUMA_KINDLING);
				this.actionManager.setAction(Action.WINTERTODT_FIREMAKING, rem, ItemID.BRUMA_ROOT);
			}
		}
	}

}
