package com.github.calebwhiting.runelite.api.ui;

import lombok.experimental.UtilityClass;
import net.runelite.client.ui.overlay.RenderableEntity;

import java.awt.*;

@UtilityClass
public class Rendering
{

	private final float INFOBOX_COLOR_OFFSET = 0.2f;
	private final float INFOBOX_OUTER_COLOR_OFFSET = 1 - INFOBOX_COLOR_OFFSET;
	private final float INFOBOX_INNER_COLOR_OFFSET = 1 + INFOBOX_COLOR_OFFSET;
	private final float INFOBOX_ALPHA_COLOR_OFFSET = 1 + 2 * INFOBOX_COLOR_OFFSET;

	private final int MAX_BYTE = 255;

	public Color outsideStrokeColor(Color backgroundColor)
	{
		return new Color(
				Math.round(backgroundColor.getRed() * INFOBOX_OUTER_COLOR_OFFSET),
				Math.round(backgroundColor.getGreen() * INFOBOX_OUTER_COLOR_OFFSET),
				Math.round(backgroundColor.getBlue() * INFOBOX_OUTER_COLOR_OFFSET),
				Math.min(MAX_BYTE, Math.round(backgroundColor.getAlpha() * INFOBOX_ALPHA_COLOR_OFFSET))
		);
	}

	public Color insideStrokeColor(Color backgroundColor)
	{
		return new Color(
				Math.min(MAX_BYTE, Math.round(backgroundColor.getRed() * INFOBOX_INNER_COLOR_OFFSET)),
				Math.min(MAX_BYTE, Math.round(backgroundColor.getGreen() * INFOBOX_INNER_COLOR_OFFSET)),
				Math.min(MAX_BYTE, Math.round(backgroundColor.getBlue() * INFOBOX_INNER_COLOR_OFFSET)),
				Math.min(MAX_BYTE, Math.round(backgroundColor.getAlpha() * INFOBOX_ALPHA_COLOR_OFFSET))
		);
	}

	public void renderEntityRelative(Graphics2D gfx, RenderableEntity entity, int x, int y)
	{
		Graphics2D g = (Graphics2D) gfx.create();
		try {
			g.translate(x, y);
			entity.render(g);
		} finally {
			g.dispose();
		}
	}

	private int getCenteredTextY(FontMetrics fm, int boundaryHeight)
	{
		return ((boundaryHeight - fm.getHeight()) / 2) + (fm.getAscent());
	}

	public void drawText(Graphics2D g, Rectangle bounds, Color color, Alignment alignment, String text, boolean isVertical)
	{
		Font currentFont = g.getFont();
		Font newFont = currentFont.deriveFont(currentFont.getStyle(), isVertical ? 13 : 16);
		g.setFont(newFont);
		FontMetrics fm = g.getFontMetrics();
		double x = bounds.x;

		switch (alignment.getAlignmentX()) {
			case Alignment.MIN:
				x = bounds.x;
				break;
			case Alignment.MID:
				x = isVertical ? (bounds.getCenterX() + bounds.x - (fm.getHeight() / 2)) : (bounds.getCenterX() - ((double) fm.stringWidth(text) / 2));
				break;
			case Alignment.MAX:
				x = isVertical ? (bounds.getMaxX() + bounds.x - fm.getHeight()): (bounds.getMaxX() - fm.stringWidth(text));
				break;
		}
		double y = bounds.y;
		switch (alignment.getAlignmentY()) {
			case Alignment.MIN:
				y = isVertical ? bounds.y + fm.getHeight() : bounds.y;
				break;
			case Alignment.MID:
				y = isVertical ? bounds.getCenterY() - (text.length() * 10 / 2) + fm.getHeight() : bounds.y + getCenteredTextY(fm, bounds.height);
				break;
			case Alignment.MAX:
				y = isVertical ? bounds.getMaxY() - ((text.length() - 1) * 10) : (bounds.getMaxY() - fm.getHeight());
				break;
		}
		float fX = Math.round(x);
		float fY = Math.round(y);
		if(isVertical){
			char [] textChar = text.toCharArray();
			for(int i = 0; i < textChar.length; i++){
				g.setColor(Color.BLACK);
				g.drawChars(textChar, i, 1, (int)fX + 1, (int)fY + 1 + i * 10);
				g.setColor(color);
				g.drawChars(textChar, i, 1, (int)fX, (int)fY + i * 10);
			}
		}
		else{
			g.setColor(Color.BLACK);
			g.drawString(text, fX + 1, fY + 1);
			g.setColor(color);
			g.drawString(text, fX, fY);
		}
	}

	public void drawProgressBar(Graphics2D g, Rectangle bounds, Color borderColor, Color progressLeftColor, Color progressDoneColor, long min, long max, long value, boolean isVertical)
	{
		double progress = ((double) value - min) / ((double) max - min);
		progress = Math.min(1.0, progress);
		Rectangle progressDone = new Rectangle(bounds);
		Rectangle progressLeft = new Rectangle(bounds);
		if (isVertical){
			progressDone.height = (int) Math.round(progress * progressDone.height);
			progressLeft.y += progressDone.height;
			progressLeft.height -= progressDone.height;
		}
		else{
			progressDone.width = (int) Math.round(progress * progressDone.width);
			progressLeft.x += progressDone.width;
			progressLeft.width -= progressDone.width;
		}
		g.setColor(progressLeftColor);
		g.fill(progressLeft);
		g.setColor(progressDoneColor);
		g.fill(progressDone);
		g.setColor(borderColor);
		g.draw(bounds);
	}

}
