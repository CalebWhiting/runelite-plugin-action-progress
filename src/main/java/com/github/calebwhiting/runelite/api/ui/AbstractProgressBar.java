package com.github.calebwhiting.runelite.api.ui;

import lombok.Setter;
import net.runelite.client.ui.overlay.RenderableEntity;

import java.awt.*;

public abstract class AbstractProgressBar implements RenderableEntity {

    @Setter
    private Dimension size;

    @Override
    public Dimension render(Graphics2D g) {
        int min = getMinValue(), max = getMaxValue(), val = getCurrentValue();
        Color fg;
        int remaining = Math.max(0, (max - min) - (val - min));
        switch (remaining) {
            case 0:
                fg = new Color(6, 137, 52);
                break;
            case 1:
                fg = new Color(98, 137, 6);
                break;
            default:
                fg = new Color(137, 100, 2);
                break;
        }
        float progress = Math.min(1.0f, (float) (val - min) / (float) (max - min));
        g.setColor(new Color(0, 0, 0, 50));
        g.fillRect(0, 0, this.size.width, this.size.height);
        g.setColor(fg);
        g.fillRect(0, 0, (int) (this.size.width * progress), this.size.height);
        return this.size;
    }

    public abstract int getMinValue();

    public abstract int getMaxValue();

    public abstract int getCurrentValue();

}
