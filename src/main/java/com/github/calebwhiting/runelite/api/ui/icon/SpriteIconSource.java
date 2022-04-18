package com.github.calebwhiting.runelite.api.ui.icon;

import lombok.RequiredArgsConstructor;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SpriteManager;

import java.awt.image.BufferedImage;

@RequiredArgsConstructor
public class SpriteIconSource implements IconSource {

    private final int spriteId;
    private final int fileId;

    @Override
    public BufferedImage toBufferedImage(ItemManager itemManager, SpriteManager spriteManager) {
        return spriteManager.getSprite(this.spriteId, this.fileId);
    }

}
