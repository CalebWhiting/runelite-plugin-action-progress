package com.github.calebwhiting.runelite.api;

import com.github.calebwhiting.runelite.api.event.ItemSelectionEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.Stream;

@Slf4j
@Singleton
public class InventoryHelper {

    @Inject
    private Client client;

    @Inject
    private EventBus eventBus;

    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked evt) {
        if (evt.getMenuAction() != MenuAction.ITEM_USE) {
            return;
        }
        ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
        if (inventory == null) {
            return;
        }
        Item item = inventory.getItem(evt.getParam0());
        this.eventBus.post(new ItemSelectionEvent(item));
    }

    public Stream<Item> getItems() {
        ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
        if (inventory == null) {
            return Stream.empty();
        }
        return Stream.of(inventory.getItems());
    }

    public int getItemCount(IntPredicate idPredicate) {
        return getItems().filter(it -> idPredicate.test(it.getId())).mapToInt(Item::getQuantity).sum();
    }

    public final int getItemCount(int... ids) {
        int[] copy = ids.clone();
        Arrays.sort(copy);
        return getItemCount(id -> Arrays.binarySearch(copy, id) >= 0);
    }

}
