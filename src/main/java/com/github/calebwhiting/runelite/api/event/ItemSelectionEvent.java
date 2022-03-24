package com.github.calebwhiting.runelite.api.event;

import lombok.Data;
import net.runelite.api.Item;

@Data
public class ItemSelectionEvent {

    private final Item item;

}
