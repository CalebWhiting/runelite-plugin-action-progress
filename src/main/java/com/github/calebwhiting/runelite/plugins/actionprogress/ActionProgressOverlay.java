package com.github.calebwhiting.runelite.plugins.actionprogress;

import com.github.calebwhiting.runelite.api.ui.Alignment;
import com.github.calebwhiting.runelite.api.ui.Rendering;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.runelite.api.Client;
import net.runelite.client.config.RuneLiteConfig;
import net.runelite.client.ui.overlay.Overlay;

import java.awt.*;

@Singleton
public class ActionProgressOverlay extends Overlay
{

	private static final Dimension EMPTY = new Dimension(0, 0);

	private static final int ICON_SIZE = 32;

	private static final int PROGRESS_WIDTH = 175;

	private static final int INSET = 5;

	private static final int PAD = 2;

	@Inject private ActionProgressPlugin plugin;

	@Inject private ActionManager actionManager;

	@Inject private Client client;

	@Inject private RuneLiteConfig runeLiteConfig;

	@Inject private ActionProgressConfig config;

	@Override
	public Dimension render(Graphics2D g)
	{
		try {
			int tick = this.client.getTickCount();
			if (tick > this.actionManager.getActionEndTick()) {
				return EMPTY;
			}
			String timeString = String.format("%ds", Math.round(
					(float) this.actionManager.getApproximateCompletionTime() / 1000));
			String header = String.format("%s (%d/%d)", this.plugin.getCurrentActionName(),
					this.actionManager.getCurrentActionProcessed(),
					this.actionManager.getActionCount()
			);
			Image icon = this.plugin.getCurrentProductImage();
			long min, max, value;
			if (this.config.smoothProgressBar()) {
				min = this.actionManager.getActionStartMs();
				max = this.actionManager.getActionEndMs();
				value = System.currentTimeMillis();
			} else {
				min = this.actionManager.getActionStartTick();
				max = this.actionManager.getActionEndTick();
				value = tick;
			}
			return this.renderInfobox(g, header, timeString, icon, min, max, value);
		} catch (Throwable t) {
			t.printStackTrace();
			return EMPTY;
		}
	}

	private Dimension renderInfobox(
			Graphics2D g, String header, String timeString, Image icon, long min, long max, long value)
	{
		FontMetrics fm = g.getFontMetrics();
		int width = INSET + ICON_SIZE + PAD + PROGRESS_WIDTH + INSET;
		int height = INSET + ICON_SIZE + PAD + fm.getHeight() + INSET;
		Color border = Rendering.outsideStrokeColor(this.runeLiteConfig.overlayBackgroundColor());
		g.setColor(this.runeLiteConfig.overlayBackgroundColor());
		g.fillRect(0, 0, width, height);
		g.setColor(border);
		g.drawRect(0, 0, width, height);
		int iconWidth = icon.getWidth(null);
		int iconHeight = icon.getHeight(null);
		g.drawImage(icon, INSET + (ICON_SIZE / 2) - (iconWidth / 2), INSET + (ICON_SIZE / 2) - (iconHeight / 2),
				iconWidth, iconHeight, null
		);
		Rendering.drawText(g, new Rectangle(INSET, INSET + ICON_SIZE + PAD, ICON_SIZE, fm.getHeight()), Color.ORANGE,
				Alignment.CENTER, timeString
		);
		Rectangle right = new Rectangle(INSET + ICON_SIZE + PAD, INSET, PROGRESS_WIDTH, height - (INSET * 2));
		Rendering.drawText(g, new Rectangle(right.x, right.y, right.width, (right.height / 2)), Color.WHITE,
				Alignment.CENTER, header
		);
		Rendering.drawProgressBar(g, new Rectangle(right.x, (int) right.getCenterY(), right.width, (right.height / 2)),
				border, min, max, value
		);
		return new Dimension(width, height);
	}

}