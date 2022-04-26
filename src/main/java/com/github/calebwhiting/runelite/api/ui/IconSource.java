package com.github.calebwhiting.runelite.api.ui;

import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SpriteManager;

import java.awt.image.BufferedImage;

public interface IconSource {

    BufferedImage toBufferedImage(ItemManager itemManager, SpriteManager spriteManager);

}
