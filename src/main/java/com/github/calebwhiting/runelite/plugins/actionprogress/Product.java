package com.github.calebwhiting.runelite.plugins.actionprogress;

import com.github.calebwhiting.runelite.api.data.Ingredient;
import com.github.calebwhiting.runelite.api.data.Recipe;
import lombok.Getter;
import net.runelite.api.Item;

import java.util.stream.Stream;

@Getter
public class Product extends Recipe {

    private final Action action;

    public Product(Action action, int productId, Ingredient... requirements) {
        super(productId, requirements);
        this.action = action;
    }

    public boolean isMadeWith(Item... items) {
        return Stream.of(items).mapToInt(Item::getId).allMatch(
                // (requirements[].ids).contains(item.id)
                id -> Stream.of(getRequirements())
                        .mapToInt(Ingredient::getItemId)
                        .anyMatch(i -> i == id));
    }

}
