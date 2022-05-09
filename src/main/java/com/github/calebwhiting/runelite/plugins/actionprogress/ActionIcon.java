package com.github.calebwhiting.runelite.plugins.actionprogress;

import com.github.calebwhiting.runelite.api.ui.IconSource;
import com.github.calebwhiting.runelite.api.ui.ItemIconSource;
import com.github.calebwhiting.runelite.api.ui.SpriteIconSource;
import net.runelite.api.ItemID;
import net.runelite.api.SpriteID;

public interface ActionIcon
{

	IconSource SPRITE_TOTAL = new SpriteIconSource(SpriteID.SKILL_TOTAL, 0);
	IconSource SPRITE_CRAFTING = new SpriteIconSource(SpriteID.SKILL_CRAFTING, 0);
	IconSource SPRITE_FISHING = new SpriteIconSource(SpriteID.SKILL_FISHING, 0);
	IconSource SPRITE_WOODCUTTING = new SpriteIconSource(SpriteID.SKILL_WOODCUTTING, 0);
	IconSource SPRITE_COOKING = new SpriteIconSource(SpriteID.SKILL_COOKING, 0);
	IconSource SPRITE_FLETCHING = new SpriteIconSource(SpriteID.SKILL_FLETCHING, 0);
	IconSource SPRITE_FIREMAKING = new SpriteIconSource(SpriteID.SKILL_FIREMAKING, 0);
	IconSource SPRITE_SMITHING = new SpriteIconSource(SpriteID.SKILL_SMITHING, 0);
	IconSource SPRITE_MAGIC = new SpriteIconSource(SpriteID.SKILL_MAGIC, 0);
	IconSource SPRITE_HERBLORE = new SpriteIconSource(SpriteID.SKILL_HERBLORE, 0);
	IconSource SPRITE_BUCKET = new ItemIconSource(ItemID.BUCKET);

}
