package com.github.calebwhiting.runelite.data;


import lombok.Getter;

@Getter
public class Ingredient {

    private final int itemId;
    private final int amount;
    private final boolean consumed;

    public Ingredient(int itemId, int amount, boolean consumed) {
        this.itemId = itemId;
        this.amount = amount;
        this.consumed = consumed;
    }

    public Ingredient(int itemId, int amount) {
        this(itemId, amount, true);
    }

    public Ingredient(int itemId) {
        this(itemId, 1, true);
    }

    public Ingredient clone(int amount) {
        return new Ingredient(this.itemId, amount, this.consumed);
    }

}
