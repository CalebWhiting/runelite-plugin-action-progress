package com.github.calebwhiting.runelite.api.ui;

import lombok.Setter;
import net.runelite.client.ui.overlay.RenderableEntity;
import net.runelite.client.ui.overlay.components.BackgroundComponent;
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

    private final BackgroundComponent backgroundComponent = new BackgroundComponent();
    private Color backgroundColor = ComponentConstants.STANDARD_BACKGROUND_COLOR;

    public void setProgress(long min, long max, long value) {
        this.min = min;
        this.max = max;
        this.value = value;
    }

    @Override
    public Dimension render(Graphics2D g) {
        this.backgroundComponent.setBackgroundColor(this.backgroundColor);
        this.backgroundComponent.setRectangle(this.fullBounds);
        this.backgroundComponent.render(g);

        g.drawImage(this.icon, iconBounds.x, iconBounds.y, null);

        RenderingHelper.drawText(g, this.iconTextBounds, Color.ORANGE, Anchor.CENTER, this.iconText);
        RenderingHelper.drawText(g, this.headerTextBounds, Color.WHITE, Anchor.CENTER, this.headerText);

        Color borderColor = RenderingHelper.outsideStrokeColor(this.backgroundColor);
        RenderingHelper.drawProgressBar(
                g, this.progressBounds, borderColor, this.min, this.max, this.value);

        return fullBounds.getSize();
    }

}
