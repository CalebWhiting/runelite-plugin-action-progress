package com.github.calebwhiting.runelite.api.ui.icon;

import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SpriteManager;

import java.awt.image.BufferedImage;

public interface IconSource {

    BufferedImage toBufferedImage(ItemManager itemManager, SpriteManager spriteManager);

}
