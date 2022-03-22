package com.github.calebwhiting.runelite.api.ui;

import java.awt.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;

@RequiredArgsConstructor
@Setter
public class MutableImageComponent implements LayoutableRenderableEntity
{
	private Image image;

	@Getter
	private final Rectangle bounds = new Rectangle();

	private Point preferredLocation = new Point();

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (image == null)
		{
			return null;
		}

		graphics.drawImage(image, preferredLocation.x, preferredLocation.y, null);
		final Dimension dimension = new Dimension(image.getWidth(null), image.getHeight(null));
		bounds.setLocation(preferredLocation);
		bounds.setSize(dimension);
		return dimension;
	}

	@Override
	public void setPreferredSize(Dimension dimension)
	{
		// Just use image dimensions for now
	}
}