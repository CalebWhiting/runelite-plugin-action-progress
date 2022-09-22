package com.github.calebwhiting.runelite.data;

import com.github.calebwhiting.runelite.api.InventoryManager;
import lombok.Data;
import static net.runelite.api.ItemID.*;

@Data
public class Recipe
{

	private final int productId;

	private final Ingredient[] requirements;

	private final Ingredient tool;

	public Recipe(int productId, Ingredient[] requirements, Ingredient tool)
	{
		this.productId = productId;
		this.requirements = requirements;
		this.tool = tool;
	}

	public Recipe(int productId, Ingredient... requirements)
	{
		this.productId = productId;
		this.requirements = requirements;
		this.tool = null;
	}

	public static <T extends Recipe> T forProduct(T[] all, int productId)
	{
		for (T v : all) {
			if (v.getProductId() == productId) {
				return v;
			}
		}
		return null;
	}

	public int getMakeProductCount(InventoryManager inventoryManager)
	{
		int amount = Integer.MAX_VALUE;
		for (Ingredient requirement : this.getRequirements()) {
			if (requirement.isConsumed()) {
				if (this.getTool() != null && inventoryManager.getItems().anyMatch(item -> item.getId() == tool.getItemId())){
					amount = Math.min(
							amount,
							getMakeProductCountWithTool(inventoryManager.getItemCountById(requirement.getItemId()) / requirement.getAmount())
					);
				}
				else{
					amount = Math.min(
							amount,
							inventoryManager.getItemCountById(requirement.getItemId()) / requirement.getAmount()
					);
				}
			}
		}
		return amount;
	}

	private int getMakeProductCountWithTool(int amount){
		int toolAmount = Integer.MAX_VALUE;
		switch (productId) {
			case CANNONBALL:
				//Round up since the double ammo mould is able to smelt with only one silver bar
				toolAmount = (int)Math.ceil((double)amount / 2);
				break;
			default:
				toolAmount = amount;
				break;
		}
		return toolAmount;
	}

}
