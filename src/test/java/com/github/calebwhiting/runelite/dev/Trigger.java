package com.github.calebwhiting.runelite.dev;

import org.apache.commons.text.WordUtils;

public enum Trigger {
    EXPERIENCE,
    INVENTORY_ITEM_ADD,
    INVENTORY_ITEM_REMOVE,
    INVENTORY_STACK_ADD,
    INVENTORY_STACK_REMOVE;

    @Override
    public String toString() {
        return WordUtils.capitalizeFully(this.name().replace('_', ' '));
    }

}
