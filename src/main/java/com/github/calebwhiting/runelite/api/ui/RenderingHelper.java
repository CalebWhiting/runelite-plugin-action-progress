package com.github.calebwhiting.runelite.api.ui;

import lombok.experimental.UtilityClass;
import net.runelite.client.ui.overlay.RenderableEntity;

import java.awt.*;

@UtilityClass
public class RenderingHelper {

    private static final float INFOBOX_COLOR_OFFSET = 0.2f;
    private static final float INFOBOX_OUTER_COLOR_OFFSET = 1 - INFOBOX_COLOR_OFFSET;
    private static final float INFOBOX_INNER_COLOR_OFFSET = 1 + INFOBOX_COLOR_OFFSET;
    private static final float INFOBOX_ALPHA_COLOR_OFFSET = 1 + 2 * INFOBOX_COLOR_OFFSET;

    public static Color outsideStrokeColor(Color backgroundColor) {
        return new Color(
                (int) (backgroundColor.getRed() * INFOBOX_OUTER_COLOR_OFFSET),
                (int) (backgroundColor.getGreen() * INFOBOX_OUTER_COLOR_OFFSET),
                (int) (backgroundColor.getBlue() * INFOBOX_OUTER_COLOR_OFFSET),
                Math.min(255, (int) (backgroundColor.getAlpha() * INFOBOX_ALPHA_COLOR_OFFSET))
        );
    }

    public static Color insideStrokeColor(Color backgroundColor) {
        return new Color(
                Math.min(255, (int) (backgroundColor.getRed() * INFOBOX_INNER_COLOR_OFFSET)),
                Math.min(255, (int) (backgroundColor.getGreen() * INFOBOX_INNER_COLOR_OFFSET)),
                Math.min(255, (int) (backgroundColor.getBlue() * INFOBOX_INNER_COLOR_OFFSET)),
                Math.min(255, (int) (backgroundColor.getAlpha() * INFOBOX_ALPHA_COLOR_OFFSET))
        );
    }

    public static void renderEntityRelative(Graphics2D gfx, RenderableEntity entity, int x, int y) {
        Graphics2D g = (Graphics2D) gfx.create();
        try {
            g.translate(x, y);
            entity.render(g);
        } finally {
            g.dispose();
        }
    }

    public static int getCenteredTextY(FontMetrics fm, int boundaryHeight) {
        return ((boundaryHeight - fm.getHeight()) / 2) + (fm.getAscent());
    }

    public static void drawText(Graphics2D g, Rectangle bounds, Color color, Anchor anchor, String text) {
        FontMetrics fm = g.getFontMetrics();
        int x, y;
        switch (anchor) {
            case TOP_LEFT:
                x = bounds.x;
                y = bounds.y;
                break;
            case TOP:
                x = (int) ((bounds.getCenterX()) - (fm.stringWidth(text) / 2));
                y = bounds.y;
                break;
            case TOP_RIGHT:
                x = (int) ((bounds.getMaxX()) - fm.stringWidth(text));
                y = bounds.y;
                break;
            case LEFT:
                x = bounds.x;
                y = bounds.y + getCenteredTextY(fm, bounds.height);
                break;
            case CENTER:
                x = (int) (bounds.getCenterX() - (fm.stringWidth(text) / 2));
                y = bounds.y + getCenteredTextY(fm, bounds.height);
                break;
            case RIGHT:
                x = (int) ((bounds.getMaxX()) - fm.stringWidth(text));
                y = bounds.y + getCenteredTextY(fm, bounds.height);
                break;
            case BOTTOM_LEFT:
                x = 0;
                y = (int) (bounds.getMaxY() - fm.getHeight());
                break;
            case BOTTOM:
                x = (int) (bounds.getCenterX() - (fm.stringWidth(text) / 2));
                y = (int) (bounds.getMaxY() - fm.getHeight());
                break;
            case BOTTOM_RIGHT:
                x = (int) ((bounds.getMaxX()) - fm.stringWidth(text));
                y = (int) (bounds.getMaxY() - fm.getHeight());
                break;
            default:
                throw new IllegalArgumentException();
        }
        g.setColor(Color.BLACK);
        g.drawString(text, x + 1, y + 1);
        g.setColor(color);
        g.drawString(text, x, y);
    }

    public static void drawProgressBar(Graphics2D g,
                                       Rectangle bounds,
                                       Color borderColor,
                                       long min, long max, long value) {
        float progress = ((float) value - (float) min) / ((float) max - (float) min);

        Rectangle progressDone = new Rectangle(bounds);
        progressDone.width = (int) (progress * progressDone.width);

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
