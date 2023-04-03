package com.github.calebwhiting.runelite.plugins.actionprogress;

import com.github.calebwhiting.runelite.api.ui.Alignment;
import com.github.calebwhiting.runelite.api.ui.Rendering;
import com.google.inject.Inject;
import com.google.inject.Singleton;
//import net.runelite.client.game.ItemManager;
import net.runelite.api.Client;
import net.runelite.client.config.RuneLiteConfig;
import net.runelite.client.ui.overlay.Overlay;

import java.awt.*;

@Singleton
public class ActionProgressOverlay extends Overlay
{

	private static final Dimension EMPTY = new Dimension(0, 0);

	private static final int ICON_SIZE = 32;

	private static final int PROGRESS_MIN_WIDTH = 150;

	private static final int INSET = 5;

	private static final int PAD = 2;

	@Inject private ActionProgressPlugin plugin;

	@Inject private ActionManager actionManager;

	@Inject private Client client;

	@Inject private RuneLiteConfig runeLiteConfig;

	@Inject private ActionProgressConfig config;

	//@Inject private ItemManager itemManager;

	@Override
	public Dimension render(Graphics2D g)
	{
		try {
			Dimension preferredDimension = this.getPreferredSize();
			int tick = this.client.getTickCount();
			if (tick > this.actionManager.getActionEndTick()) {
				return EMPTY;
			}
			//else if setting to show when empty
			//Image i = this.itemManager.getImage(331);
			//return this.renderInfobox(g, "Cooking (1/2)", "0s", i, 0, 2, 1, d);
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
			return this.renderInfobox(g, header, timeString, icon, min, max, value, preferredDimension);
		} catch (Throwable t) {
			t.printStackTrace();
			return EMPTY;
		}
	}

	private Dimension renderInfobox(
			Graphics2D g, String header, String timeString, Image icon, long min, long max, long value, Dimension preferredDimension)
	{
		FontMetrics fm = g.getFontMetrics();
		int width = Math.max(INSET + ICON_SIZE + PAD + PROGRESS_MIN_WIDTH + INSET, (int)preferredDimension.getWidth());
		int heightWithIcon = INSET + ICON_SIZE + fm.getHeight();
		int height = Math.max(INSET + INSET + fm.getHeight() + INSET + INSET + PAD + PAD , (int)preferredDimension.getHeight()) ;
		Color border = Rendering.outsideStrokeColor(this.runeLiteConfig.overlayBackgroundColor());
		Color progressDoneColor = this.config.progressDoneColor();
		Color progressLeftColor = this.config.progressLeftColor();
		g.setColor(this.runeLiteConfig.overlayBackgroundColor());
		g.fillRect(0, 0, width, height);
		g.setColor(border);
		g.drawRect(0, 0, width, height);
		
		if (height <= heightWithIcon){
			Rendering.drawText(g, new Rectangle(INSET, (height / 2) - fm.getHeight() / 2, ICON_SIZE, fm.getHeight() + PAD), Color.ORANGE,
				Alignment.CENTER, timeString
			);
		}
		else{
			int iconWidth = icon.getWidth(null);
			int iconHeight = icon.getHeight(null);
			g.drawImage(icon, INSET + (ICON_SIZE / 2) - (iconWidth / 2), INSET + (ICON_SIZE / 2) - (iconHeight / 2),
					iconWidth, iconHeight, null
			);
			Rendering.drawText(g, new Rectangle(INSET, Math.max(INSET + ICON_SIZE + PAD, height / 2), ICON_SIZE, fm.getHeight()), Color.ORANGE,
				Alignment.CENTER, timeString
			);	
		}

		Rectangle right = new Rectangle(INSET + ICON_SIZE + PAD, INSET, Math.max(PROGRESS_MIN_WIDTH,width - INSET - ICON_SIZE - PAD - INSET), height - (INSET * 2));
		Rendering.drawText(g, new Rectangle(right.x, right.y, right.width, INSET + fm.getHeight() + PAD), Color.WHITE,
			Alignment.CENTER, header
		);
		Rendering.drawProgressBar(g, new Rectangle(right.x, PAD + INSET + fm.getHeight() + INSET + PAD, right.width, height - (INSET + INSET + fm.getHeight() + INSET + INSET)),
			border, progressLeftColor, progressDoneColor, min, max, value
		);	
		return new Dimension(width, height);
	}

}