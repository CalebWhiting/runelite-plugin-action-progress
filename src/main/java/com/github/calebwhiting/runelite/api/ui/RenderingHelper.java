package com.github.calebwhiting.runelite.api.ui;

import lombok.experimental.UtilityClass;
import net.runelite.client.ui.overlay.RenderableEntity;

import java.awt.*;

@UtilityClass
public
class RenderingHelper {

    private final float INFOBOX_COLOR_OFFSET = 0.2f;
    private final float INFOBOX_OUTER_COLOR_OFFSET = 1 - INFOBOX_COLOR_OFFSET;
    private final float INFOBOX_INNER_COLOR_OFFSET = 1 + INFOBOX_COLOR_OFFSET;
    private final float INFOBOX_ALPHA_COLOR_OFFSET = 1 + 2 * INFOBOX_COLOR_OFFSET;

    private final int MAX_BYTE = 255;

    public Color outsideStrokeColor(Color backgroundColor) {
        return new Color(
                Math.round(backgroundColor.getRed() * INFOBOX_OUTER_COLOR_OFFSET),
                Math.round(backgroundColor.getGreen() * INFOBOX_OUTER_COLOR_OFFSET),
                Math.round(backgroundColor.getBlue() * INFOBOX_OUTER_COLOR_OFFSET),
                Math.min(MAX_BYTE, Math.round(backgroundColor.getAlpha() * INFOBOX_ALPHA_COLOR_OFFSET))
        );
    }

    public Color insideStrokeColor(Color backgroundColor) {
        return new Color(
                Math.min(MAX_BYTE, Math.round(backgroundColor.getRed() * INFOBOX_INNER_COLOR_OFFSET)),
                Math.min(MAX_BYTE, Math.round(backgroundColor.getGreen() * INFOBOX_INNER_COLOR_OFFSET)),
                Math.min(MAX_BYTE, Math.round(backgroundColor.getBlue() * INFOBOX_INNER_COLOR_OFFSET)),
                Math.min(MAX_BYTE, Math.round(backgroundColor.getAlpha() * INFOBOX_ALPHA_COLOR_OFFSET))
        );
    }

    public void renderEntityRelative(Graphics2D gfx, RenderableEntity entity, int x, int y) {
        Graphics2D g = (Graphics2D) gfx.create();
        try {
            g.translate(x, y);
            entity.render(g);
        } finally {
            g.dispose();
        }
    }

    private int getCenteredTextY(FontMetrics fm, int boundaryHeight) {
        return ((boundaryHeight - fm.getHeight()) / 2) + (fm.getAscent());
    }

    public void drawText(Graphics2D g, Rectangle bounds, Color color, Anchor anchor, String text) {
        FontMetrics fm = g.getFontMetrics();
        double x = bounds.x;
        switch (anchor.getAlignmentX()) {
            case Anchor.MIN:
                x = bounds.x;
                break;
            case Anchor.MID:
                x = (bounds.getCenterX() - ((double) fm.stringWidth(text) / 2));
                break;
            case Anchor.MAX:
                x = ((bounds.getMaxX()) - fm.stringWidth(text));
                break;
        }
        double y = bounds.y;
        switch (anchor.getAlignmentY()) {
            case Anchor.MIN:
                y = bounds.y;
                break;
            case Anchor.MID:
                y = bounds.y + getCenteredTextY(fm, bounds.height);
                break;
            case Anchor.MAX:
                y = (bounds.getMaxY() - fm.getHeight());
                break;
        }

        float fX = Math.round(x);
        float fY = Math.round(y);

        g.setColor(Color.BLACK);
        g.drawString(text, fX + 1, fY + 1);

        g.setColor(color);
        g.drawString(text, fX, fY);
    }

    public void drawProgressBar(Graphics2D g, Rectangle bounds, Color borderColor, long min, long max, long value) {
        double progress = ((double) value - min) / ((double) max - min);
        progress = Math.min(1.0, progress);

        Rectangle progressDone = new Rectangle(bounds);
        progressDone.width = (int) Math.round(progress * progressDone.width);

        Rectangle progressLeft = new Rectangle(bounds);
        progressLeft.x += progressDone.width;
        progressLeft.width -= progressDone.width;

        g.setColor(new Color(255, 52, 52, 100));
        g.fill(progressLeft);

        g.setColor(new Color(0, 255, 52, 100));
        g.fill(progressDone);

        g.setColor(borderColor);
        g.draw(bounds);
    }
}
