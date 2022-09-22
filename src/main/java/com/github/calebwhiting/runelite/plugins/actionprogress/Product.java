package com.github.calebwhiting.runelite.plugins.actionprogress;

import com.github.calebwhiting.runelite.data.Ingredient;
import com.github.calebwhiting.runelite.data.Recipe;
import lombok.Getter;
import net.runelite.api.Item;

import java.util.Arrays;
import java.util.stream.Stream;

@Getter
public class Product extends Recipe
{

	private final Action action;

	public Product(Action action, int productId, Ingredient... requirements)
	{
		super(productId, requirements);
		this.action = action;
	}

	public Product(Action action, int productId, Ingredient[] requirements, Ingredient tool)
	{
		super(productId, requirements, tool);
		this.action = action;
	}

	public boolean isMadeWith(Item... items)
	{
		return Stream.of(items)
					 .mapToInt(Item::getId)
					 .allMatch(id -> Arrays.stream(this.getRequirements())
										   .mapToInt(Ingredient::getItemId)
										   .anyMatch(i -> i == id));
	}

}
