package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.calebwhiting.runelite.api.InterruptManager;
import com.github.calebwhiting.runelite.api.InventoryManager;
import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.ItemContainer;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.client.eventbus.Subscribe;


@Slf4j
@Singleton
public class GuardianOfTheRift extends ActionDetector
{
	private static final int REWARD_GUARDIAN_ID = 43695;
	private static final int WORKBENCH_ID = 43754;
	private static final int GUARDIAN_FRAGMENTS_ITEM_ID = 26878;
	private static final int CRAFTING_ANIMATION_ID = 9365;
	private static final int REWARD_SEARCHING_ANIMATION_ID = 9353;
	private static final int MINIGAME_MAIN_REGION = 14484;

	//Apparently varbits 13686 & 13685 have the values of the points, but I couldn't get them to work so the workaround is to find them manually.
	private static final String REWARD_POINT_REGEX = "Total elemental energy:[^>]+>([\\d,]+).*Total catalytic energy:[^>]+>([\\d,]+).";
	private static final Pattern REWARD_POINT_PATTERN = Pattern.compile(REWARD_POINT_REGEX);
	private static final String CHECK_POINT_REGEX = "You have (\\d+) catalytic energy and (\\d+) elemental energy";
	private static final Pattern CHECK_POINT_PATTERN = Pattern.compile(CHECK_POINT_REGEX);

	private static final int DIALOG_WIDGET_GROUP = 229;
	private static final int DIALOG_WIDGET_MESSAGE = 1;

	private int currentElementalRewardPoints = -1;
	private int currentCatalyticRewardPoints = -1;

	private boolean triggeringEssenceCrafting;
	private boolean triggeringRewardSearch;

	@Inject private Client client;

	@Inject private InventoryManager inventoryManager;

	@Inject protected InterruptManager intterupManager;

	private boolean checkInMainRegion(){
		int[] currentMapRegions = client.getMapRegions();
		return Arrays.stream(currentMapRegions).anyMatch(x -> x == MINIGAME_MAIN_REGION);
	}

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage)
	{
		if(!checkInMainRegion()) return;
		if(chatMessage.getType() != ChatMessageType.SPAM && chatMessage.getType() != ChatMessageType.GAMEMESSAGE) return;

		String msg = chatMessage.getMessage();
		Matcher rewardPointMatcher = REWARD_POINT_PATTERN.matcher(msg);
		
		if(msg.contains("You found some loot:")){
			currentElementalRewardPoints--;
			currentCatalyticRewardPoints--;
		}
		else if(msg.contains("The Great Guardian was defeated!")){
			intterupManager.interrupt("Guardian of the Rift game concluded");
		}
		else if(rewardPointMatcher.find()) {
			intterupManager.interrupt("Guardian of the Rift game concluded");
			currentElementalRewardPoints = Integer.parseInt(rewardPointMatcher.group(1).replaceAll(",", ""));
			currentCatalyticRewardPoints = Integer.parseInt(rewardPointMatcher.group(2).replaceAll(",", ""));
		}
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked evt)
	{
		if(!checkInMainRegion()) return;

		if (evt.getId() == REWARD_GUARDIAN_ID && evt.getMenuOption().toLowerCase().equals("search")){
			triggeringRewardSearch = true;
		}

		if(evt.getId() == WORKBENCH_ID && evt.getMenuOption().toLowerCase().equals("work-at")){			
			triggeringEssenceCrafting = true;
		}
	}

	@Subscribe
	public void onGameTick(GameTick evt)
	{
		if(!checkInMainRegion()) return;

		Widget dialog = client.getWidget(DIALOG_WIDGET_GROUP, DIALOG_WIDGET_MESSAGE);
		if (dialog != null){			
		String dialogText = dialog.getText();
		final Matcher checkMatcher = CHECK_POINT_PATTERN.matcher(dialogText);
		if (checkMatcher.find(0))
		{
			currentCatalyticRewardPoints = Integer.parseInt(checkMatcher.group(1));
			currentElementalRewardPoints = Integer.parseInt(checkMatcher.group(2));
			}
		}
		
		Player local = this.client.getLocalPlayer();
		if (local == null || local.getAnimation() == -1) return;

		log.debug (local.getAnimation() + " local animation");
		
		if (triggeringEssenceCrafting && local.getAnimation() == CRAFTING_ANIMATION_ID){
			triggeringEssenceCrafting = false;

			ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
			
			int numberOfGuardianEssencesToMake = inventory == null ? 0 : Math.min(inventory.count(GUARDIAN_FRAGMENTS_ITEM_ID), this.inventoryManager.getFreeSpaces());

			if (numberOfGuardianEssencesToMake == 0) return;
			
			this.actionManager.setAction(Action.GUARDIAN_OF_THE_RIFT_CRAFTING, numberOfGuardianEssencesToMake, ItemID.GUARDIAN_ESSENCE);	
		}
		else if (triggeringRewardSearch && local.getAnimation() == REWARD_SEARCHING_ANIMATION_ID){
			triggeringRewardSearch = false;

			int numberOfRewards = Math.min(currentElementalRewardPoints, currentCatalyticRewardPoints);

			if (numberOfRewards == 0) return;

			this.actionManager.setAction(Action.GUARDIAN_OF_THE_RIFT_REWARD_POOL, numberOfRewards, -1);
		}

	}
}
