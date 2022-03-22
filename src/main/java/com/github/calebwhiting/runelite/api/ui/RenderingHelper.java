package com.github.calebwhiting.runelite.api.ui;

import lombok.experimental.UtilityClass;
import net.runelite.client.ui.overlay.RenderableEntity;
import net.runelite.client.ui.overlay.components.ComponentConstants;

import java.awt.*;

@UtilityClass
public class RenderingHelper {

    public static final Color BACKGROUND_COLOR = ComponentConstants.STANDARD_BACKGROUND_COLOR;
    public static final Color BORDER_COLOR = new Color(64, 55, 34, 255);

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
        g.drawString(text, x+1, y+1);
        g.setColor(color);
        g.drawString(text, x, y);
    }

    public static void drawProgressBar(Graphics2D g, Rectangle bounds, long min, long max, long value) {
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

        g.setColor(BORDER_COLOR);
        g.draw(bounds);
    }
}
