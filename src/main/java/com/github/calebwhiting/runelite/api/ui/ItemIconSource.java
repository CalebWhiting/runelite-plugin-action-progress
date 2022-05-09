package com.github.calebwhiting.runelite.api.ui;

import lombok.RequiredArgsConstructor;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SpriteManager;

import java.awt.image.BufferedImage;

@RequiredArgsConstructor
public class ItemIconSource implements IconSource
{

	private final int itemId;

	@Override
	public BufferedImage toBufferedImage(ItemManager itemManager, SpriteManager spriteManager)
	{
		return itemManager.getImage(this.itemId);
	}

}
