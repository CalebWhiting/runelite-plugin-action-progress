package com.github.calebwhiting.runelite.api;

import com.github.calebwhiting.runelite.api.event.ItemSelectionChanged;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.Stream;

@Singleton
public class InventoryManager
{

	@Inject private Client client;

	@Inject private EventBus eventBus;

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked evt)
	{
		if (evt.getMenuAction() != MenuAction.WIDGET_TARGET) {
			return;
		}
		ItemContainer inventory = this.client.getItemContainer(InventoryID.INVENTORY);
		if (inventory == null) {
			return;
		}
		Item item = inventory.getItem(evt.getParam0());
		this.eventBus.post(new ItemSelectionChanged(item));
	}

	public Stream<Item> getItems()
	{
		ItemContainer inventory = this.client.getItemContainer(InventoryID.INVENTORY);
		if (inventory == null) {
			return Stream.empty();
		}
		return Stream.of(inventory.getItems());
	}

	public int getFreeSpaces()
	{
		ItemContainer container = this.client.getItemContainer(InventoryID.INVENTORY);
		if (container == null) {
			return 0;
		}
		int free = 28;
		for (Item item : container.getItems()) {
			if (item.getId() >= 0) {
				free--;
			}
		}
		return free;
	}

	public int getItemCount(IntPredicate idPredicate)
	{
		return this.getItems().filter(it -> idPredicate.test(it.getId())).mapToInt(Item::getQuantity).sum();
	}

	public int getItemCountById(int... ids)
	{
		if (ids.length == 0) {
			throw new IllegalArgumentException("Must specify at least one item ID");
		}
		int[] copy = ids.clone();
		Arrays.sort(copy);
		return this.getItemCount(id -> Arrays.binarySearch(copy, id) >= 0);
	}

}
