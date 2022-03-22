package com.github.calebwhiting.runelite.api.ui;

import lombok.Setter;
import net.runelite.client.ui.overlay.RenderableEntity;
import net.runelite.client.ui.overlay.components.ComponentConstants;

import java.awt.*;

@Setter
public class ProgressPanelComponent implements RenderableEntity {

    private final Rectangle fullBounds = new Rectangle(0, 0, 213, 49);
    private final Rectangle iconBounds = new Rectangle(4, 2, 31, 31);
    private final Rectangle iconTextBounds = new Rectangle(4, 29, 31, 18);
    private final Rectangle headerTextBounds = new Rectangle(38, 2, 168, 20);
    private final Rectangle progressBounds = new Rectangle(38, 22, 168, 20);

    private Image icon;
    private String iconText;
    private String headerText;

    private long min, max, value;

    public void setProgress(long min, long max, long value) {
        this.min = min;
        this.max = max;
        this.value = value;
    }

    public void setProgressFloat(float value) {
        this.min = 0;
        this.max = Long.MAX_VALUE;
        this.value = (long) (value * Long.MAX_VALUE);
    }

    @Override
    public Dimension render(Graphics2D g) {
        g.setColor(ComponentConstants.STANDARD_BACKGROUND_COLOR);
        g.fill(fullBounds);

        g.drawImage(this.icon, iconBounds.x, iconBounds.y, null);

        RenderingHelper.drawText(g, this.iconTextBounds, Color.ORANGE, Anchor.CENTER, this.iconText);
        RenderingHelper.drawText(g, this.headerTextBounds, Color.WHITE, Anchor.CENTER, this.headerText);

        RenderingHelper.drawProgressBar(g, this.progressBounds, this.min, this.max, this.value);

        g.setColor(RenderingHelper.BORDER_COLOR);
        g.draw(fullBounds);

        return fullBounds.getSize();
    }

}
