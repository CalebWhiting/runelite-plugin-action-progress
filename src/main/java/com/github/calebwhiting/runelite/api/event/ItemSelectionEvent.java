package com.github.calebwhiting.runelite.api.event;

import lombok.Data;
import net.runelite.api.Item;

/**
 * An event fired when an item is selected in the players inventory
 * <p>
 * Note: There is not an event fired when an item is deselected
 */
@Data
public class ItemSelectionEvent {

    private final Item item;

}
