package com.github.calebwhiting.runelite.api.ui;

import java.awt.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;

@RequiredArgsConstructor
@Setter
public class MutableImageComponent implements LayoutableRenderableEntity {
    @Getter
    private final Rectangle bounds = new Rectangle();
    private Image image;
    private Point preferredLocation = new Point();

    @Override
    public Dimension render(Graphics2D g) {
        if (image == null) return null;

        g.drawImage(image, preferredLocation.x, preferredLocation.y, null);

        int w = image.getWidth(null);
        int h = image.getHeight(null);

        final Dimension dimension = new Dimension(w, h);
        bounds.setLocation(preferredLocation);
        bounds.setSize(dimension);
        return dimension;
    }

    @Override
    public void setPreferredSize(Dimension dimension) {
        // Just use image dimensions for now
    }

}