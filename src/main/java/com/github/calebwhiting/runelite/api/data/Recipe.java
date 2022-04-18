package com.github.calebwhiting.runelite.api.data;

import com.github.calebwhiting.runelite.api.InventoryHelper;
import lombok.Data;

@Data
public class Recipe {

    private final int productId;
    private final Ingredient[] requirements;

    public Recipe(int productId, Ingredient... requirements) {
        this.productId = productId;
        this.requirements = requirements;
    }

    public int getMakeProductCount(InventoryHelper inventoryHelper) {
        int amount = Integer.MAX_VALUE;
        for (Ingredient requirement : this.getRequirements()) {
            if (requirement.isConsumed()) {
                amount = Math.min(
                        amount,
                        inventoryHelper.getItemCountById(requirement.getItemId()) / requirement.getAmount()
                );
            }
        }
        return amount;
    }

    public static <T extends Recipe> T forProduct(T[] all, int productId) {
        for (T v : all) {
            if (v.getProductId() == productId) {
                return v;
            }
        }
        return null;
    }

}
