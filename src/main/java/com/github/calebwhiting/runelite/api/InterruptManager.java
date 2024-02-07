package com.github.calebwhiting.runelite.api;

import com.github.calebwhiting.runelite.api.event.DestinationChanged;
import com.github.calebwhiting.runelite.api.event.Interrupt;
import com.github.calebwhiting.runelite.api.event.LocalInteractingChanged;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.MenuAction;
import net.runelite.api.events.*;
import net.runelite.api.widgets.InterfaceID;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;

import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionManager;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionProgressConfig;

import java.util.Arrays;

@Slf4j
@Singleton
public class InterruptManager
{
	private static final int[] WIDGET_CLICK_INTERRUPTS = {
			// Accept Aid
			PACK(InterfaceID.SETTINGS_SIDE, 72),
			// Toggle Run
			PACK(InterfaceID.SETTINGS_SIDE, 73),
			// House settings
			PACK(InterfaceID.SETTINGS_SIDE, 74),
			// Bond pouch
			PACK(InterfaceID.SETTINGS_SIDE, 75),
			// Settings buttons
			7602203, 7602204, 7602206,
			// Equipment buttons
			25362433, 25362435, 25362437, 25362439,
			// Combat buttons
			38862852, 38862856, 38862860, 38862864, 38862878,
			// Equipment slots
			25362447, 25362448, 25362449, 25362457, 25362450, 25362451, 25362452, 25362453, 25362455, 25362454,
			25362456,
			// Prayers
			35454981, 35454982, 35454983, 35454984, 35454985, 35454986, 35454987, 35454988, 35454989, 35454990,
			35454991, 35454992, 35454993, 35454994, 35454995, 35454996, 35454997, 35454998, 35454999, 35455000,
			35455001, 35455002, 35455003, 35455004, 35455005, 35455006, 35455007, 35455008
	};

	private static final int KOUREND_FAVOUR_OVERVIEW_GROUP_ID = 626;

	private static final int CHATBOX_MAKE_GROUP_ID = 270;

	private static final int SKILL_GUIDE_GROUP_ID = 214;

	private static final int COMBAT_ACHIEVEMENTS_GROUP_ID = 717;

	private static final int VAR_PLAYER_RUNNING = 173;

	private static final int[] WIDGET_GROUPS_INTERRUPT = {
			InterfaceID.COLLECTION_LOG, InterfaceID.LEVEL_UP, InterfaceID.BANK, InterfaceID.BANK_PIN,
			InterfaceID.DEPOSIT_BOX, InterfaceID.ACHIEVEMENT_DIARY, InterfaceID.ADVENTURE_LOG,
			InterfaceID.BANK_INVENTORY, InterfaceID.DIALOG_NPC, InterfaceID.DIALOG_PLAYER,
			InterfaceID.DIALOG_OPTION, InterfaceID.DIALOG_SPRITE, InterfaceID.DESTROY_ITEM,
			InterfaceID.DUEL_INVENTORY, InterfaceID.DUEL_INVENTORY_OTHER,
			InterfaceID.EXPLORERS_RING, InterfaceID.FAIRY_RING,
			InterfaceID.TRAWLER_REWARD, InterfaceID.BARROWS_REWARD,
			InterfaceID.GRAND_EXCHANGE, InterfaceID.KILL_LOG, InterfaceID.GUIDE_PRICES,
			InterfaceID.KEPT_ON_DEATH, InterfaceID.RUNE_POUCH, InterfaceID.SHOP,
			InterfaceID.SEED_VAULT, InterfaceID.SLAYER_REWARDS, InterfaceID.SMITHING,
			InterfaceID.DIARY, KOUREND_FAVOUR_OVERVIEW_GROUP_ID, CHATBOX_MAKE_GROUP_ID,
			SKILL_GUIDE_GROUP_ID, COMBAT_ACHIEVEMENTS_GROUP_ID
	};

	private static final MenuAction[] MENU_ACTIONS_INTERRUPT = {
			MenuAction.WALK, MenuAction.WIDGET_FIRST_OPTION, MenuAction.WIDGET_SECOND_OPTION,
			MenuAction.WIDGET_TARGET_ON_GAME_OBJECT, MenuAction.WIDGET_TARGET_ON_GROUND_ITEM, MenuAction.WIDGET_TARGET_ON_PLAYER,
			MenuAction.WIDGET_TARGET_ON_NPC, MenuAction.WIDGET_TARGET_ON_GROUND_ITEM, MenuAction.WIDGET_TARGET_ON_PLAYER,
			MenuAction.WIDGET_TARGET_ON_NPC, MenuAction.WIDGET_TARGET_ON_GAME_OBJECT,
			MenuAction.WIDGET_TARGET_ON_WIDGET
	};

	static {
		Arrays.sort(WIDGET_CLICK_INTERRUPTS);
		Arrays.sort(WIDGET_GROUPS_INTERRUPT);
		Arrays.sort(MENU_ACTIONS_INTERRUPT);
	}

	@Getter private boolean waiting;

	@Inject private ActionProgressConfig config;

	@Inject private Client client;

	@Inject private EventBus eventBus;
	@Inject protected ActionManager actionManager;

	private static int PACK(int groupId, int childId)
	{
		return groupId << 16 | childId;
	}

	public void interrupt(Object source)
	{
		if (this.waiting) {
			Interrupt interrupt = new Interrupt(source);
			this.eventBus.post(interrupt);
			if (!interrupt.isConsumed()) {
				this.waiting = false;
				log.debug("Interrupted by {}", source);
			}
		}
	}

	@Subscribe
	public void onVarbitChanged(VarbitChanged evt)
	{
		if (evt.getValue() == VAR_PLAYER_RUNNING) {
			if (client.getLocalDestinationLocation() == null) {
				this.interrupt("run toggled");
			}
		}
	}

	public void setWaiting(boolean waiting)
	{
		this.waiting = waiting;
	}

	@Subscribe
	public void onDestinationChanged(DestinationChanged evt)
	{
		if (evt.getTo() != null) {
			Action action = this.actionManager.getCurrentAction();

			if (action != null && ( action == Action.FLETCH_ATTACH
				|| action == Action.FLETCH_CUT_ARROW_SHAFT
				|| action == Action.FLETCH_CUT_BOW
				|| action == Action.FLETCH_CUT_TIPS
				|| action == Action.FLETCH_ATTACH_TIPS
				|| action == Action.FLETCH_CUT_TIPS_AMETHYST
				|| action == Action.FLETCH_STRING_BOW
				|| action == Action.FLETCH_ATTACH_CROSSBOW
				|| action == Action.FLETCH_STRING_CROSSBOW
				|| action == Action.FLETCH_JAVELIN
				|| action == Action.FLETCH_DART
        || action == Action.GUARDIAN_OF_THE_RIFT_REWARD_POOL 
        || action == Action.GUARDIAN_OF_THE_RIFT_CRAFTING)) {
				return;
			}
      
			this.interrupt(evt);
		}
	}

	@Subscribe
	public void onLocalInteractingChanged(LocalInteractingChanged evt)
	{
		this.interrupt(evt);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged evt)
	{
		if (evt.getGameState() == GameState.LOGIN_SCREEN) {
			this.interrupt(evt);
		}
	}

	@Subscribe
	public void onHitsplatApplied(HitsplatApplied evt)
	{
		if (evt.getActor() == client.getLocalPlayer() && evt.getHitsplat().isMine()) {
			this.interrupt(evt);
		}
	}

	@Subscribe
	public void onResizeableChanged(ResizeableChanged evt)
	{
		this.interrupt(evt);
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked evt)
	{
		if (Arrays.binarySearch(MENU_ACTIONS_INTERRUPT, evt.getMenuAction()) >= 0) {
			this.interrupt(evt);
		} else if (evt.getMenuAction() == MenuAction.CC_OP &&
				   Arrays.binarySearch(WIDGET_CLICK_INTERRUPTS, evt.getParam1()) >= 0) {
			this.interrupt(evt);
		}
	}

	@Subscribe
	public void onWidgetLoaded(WidgetLoaded evt)
	{
		int groupId = evt.getGroupId();
		if (Arrays.binarySearch(WIDGET_GROUPS_INTERRUPT, groupId) >= 0) {
			this.interrupt(evt);
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage evt)
	{
		if (evt.getType() == ChatMessageType.GAMEMESSAGE) {
			if (evt.getMessage().matches("You need level (\\d*) ([A-Za-z]*) to (.*)$")) {
				// level requirement not met
				this.interrupt(evt);
			} else if (evt.getMessage().matches("The [a-z-]*, [a-z-]* portal shield has dropped!")) {
				// pest control portal dropped
				this.interrupt(evt);
			} else if (evt.getMessage().matches("Your amulet of chemistry helps you create a [0-9]{1}-dose potion. <col=ff0000>It then crumbles to dust.</col>") && this.config.herbChemistry()) {
				// Creating potions interrupted by chemistry amulet breaking
				this.interrupt(evt);
			}
		}
	}

}