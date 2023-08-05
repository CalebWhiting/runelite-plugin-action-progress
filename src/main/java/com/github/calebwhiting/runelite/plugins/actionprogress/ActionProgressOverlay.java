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
	
	private static final int INSET = 5;

	private static final int PAD = 2;

	private static final int PROGRESS_MIN_HEIGHT = 40;

	private static final int PROGRESS_MIN_WIDTH = 150;

	private static final int PROGRESS_MIN_WIDTH_VERTICAL = INSET;

	@Inject private ActionProgressPlugin plugin;

	@Inject private ActionManager actionManager;

	@Inject private Client client;

	@Inject private RuneLiteConfig runeLiteConfig;

	@Inject private ActionProgressConfig config;

	@Override
	public Dimension render(Graphics2D g)
	{
		try {
			Dimension preferredDimension = this.getPreferredSize();
			int tick = this.client.getTickCount();
			Image icon = this.plugin.getCurrentProductImage();
			if (tick > this.actionManager.getActionEndTick()) {
				return EMPTY;
				//return this.renderInfobox(g, "Spawning items", "0/10", "130s", icon, 0, 2, 1, preferredDimension); //For debugging
			}
			String timeString = 
				this.config.useTicks() 
					? String.format("%dt", Math.round((float) this.actionManager.getTicksLeft()))
					: String.format("%ds", Math.round((float) this.actionManager.getApproximateCompletionTime() / 1000));
			String header = String.format("%s", this.plugin.getCurrentActionName());
			String count = String.format("%d/%d", this.actionManager.getCurrentActionProcessed(),this.actionManager.getActionCount());
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
			return this.renderInfobox(g, header, count, timeString, icon, min, max, value, preferredDimension);
		} catch (Throwable t) {
			t.printStackTrace();
			return EMPTY;
		}
	}

	private Dimension renderInfobox(
			Graphics2D g, String header, String count, String timeString, Image icon, long min, long max, long value, Dimension preferredDimension)
	{
		FontMetrics fm = g.getFontMetrics();

		int minWidthHorizontal = INSET + ICON_SIZE + PAD + PROGRESS_MIN_WIDTH + INSET;
		int preferredWidth = preferredDimension != null ? (int)preferredDimension.getWidth() : 200;
		int widthWithIcon = INSET + ICON_SIZE + INSET + INSET + PAD + PROGRESS_MIN_WIDTH_VERTICAL;
		
		int preferredHeight = preferredDimension != null ? (int)preferredDimension.getHeight() : 55;
		int heightWithIcon = INSET + ICON_SIZE + fm.getHeight();

		boolean isVertical = false;
		int height, width = 0;
		
		if (preferredWidth < minWidthHorizontal){
			isVertical = true;
			width = Math.max(INSET + INSET + INSET + PAD + PAD + PROGRESS_MIN_WIDTH_VERTICAL, preferredWidth);
			height = Math.max(INSET + ICON_SIZE + PAD + PROGRESS_MIN_HEIGHT + INSET, preferredHeight);
		}
		else{
			width = Math.max(INSET + ICON_SIZE + PAD + PROGRESS_MIN_WIDTH + INSET, preferredWidth);
			height = Math.max(INSET + INSET + fm.getHeight() + INSET + INSET + PAD + PAD , preferredHeight);
		}
		
		Color border = Rendering.outsideStrokeColor(this.runeLiteConfig.overlayBackgroundColor());
		Color progressDoneColor = this.config.progressDoneColor();
		Color progressLeftColor = this.config.progressLeftColor();
		g.setColor(this.runeLiteConfig.overlayBackgroundColor());
		g.fillRect(0, 0, width, height);
		g.setColor(border);
		g.drawRect(0, 0, width, height);
		
		String actionText = header + " (" + count + ")";

		if (isVertical){
			Rectangle left = new Rectangle(INSET, INSET, fm.getHeight(), height - (INSET * 2));
			Rectangle right = new Rectangle((int)left.getMaxX() + left.x, left.y, Math.max(PROGRESS_MIN_WIDTH_VERTICAL,width - left.width - INSET - left.x - PAD), height - (INSET * 2));
			
			if(height < actionText.length() * 10 + ICON_SIZE + INSET + INSET + INSET){
				actionText = count;
			}
			
			if (width <= widthWithIcon){
				Rendering.drawText(g, new Rectangle(PAD, left.y, width, INSET + fm.getHeight() + PAD), Color.ORANGE,
					Alignment.CENTER, timeString, false
				);
				Rendering.drawText(g, new Rectangle(left.x, PAD + INSET + fm.getHeight() + INSET + PAD, left.width, Math.max(PROGRESS_MIN_WIDTH_VERTICAL,right.height - PAD - INSET - fm.getHeight() - PAD)), Color.WHITE,
					Alignment.CENTER, actionText, isVertical
				);
			}
			else
			{
				left = new Rectangle(INSET, INSET + ICON_SIZE, ICON_SIZE, height - ICON_SIZE - (INSET * 2));
				right = new Rectangle(INSET + ICON_SIZE + PAD, INSET, Math.max(PROGRESS_MIN_WIDTH_VERTICAL,width - INSET - ICON_SIZE - PAD - INSET), height - (INSET * 2));
				int iconWidth = icon.getWidth(null);
				int iconHeight = icon.getHeight(null);
				g.drawImage(icon, INSET + (ICON_SIZE / 2) - (iconWidth / 2), INSET + (ICON_SIZE / 2) - (iconHeight / 2),
						iconWidth, iconHeight, null
				);
				Rendering.drawText(g, new Rectangle(right.x, right.y, right.width, INSET + fm.getHeight() + PAD), Color.ORANGE,
					Alignment.CENTER, timeString, false
				);
				Rendering.drawText(g, left, Color.WHITE,
					Alignment.CENTER, actionText, isVertical
				);
			}

			Rendering.drawProgressBar(g, new Rectangle(right.x, PAD + INSET + fm.getHeight() + INSET + PAD, right.width, Math.max(PROGRESS_MIN_WIDTH_VERTICAL,right.height - PAD - INSET - fm.getHeight() - PAD)),
				border, progressLeftColor, progressDoneColor, min, max, value, isVertical
			);	
		}
		else{
			if (height <= heightWithIcon){
				Rendering.drawText(g, new Rectangle(INSET, (height / 2) - fm.getHeight() / 2, ICON_SIZE, fm.getHeight() + PAD), Color.ORANGE,
					Alignment.CENTER, timeString, isVertical
				);
			}
			else{
				int iconWidth = icon.getWidth(null);
				int iconHeight = icon.getHeight(null);
				g.drawImage(icon, INSET + (ICON_SIZE / 2) - (iconWidth / 2), INSET + (ICON_SIZE / 2) - (iconHeight / 2),
						iconWidth, iconHeight, null
				);
				Rendering.drawText(g, new Rectangle(INSET, Math.max(INSET + ICON_SIZE + PAD, height / 2), ICON_SIZE, fm.getHeight()), Color.ORANGE,
					Alignment.CENTER, timeString, isVertical
				);	
			}
	
			Rectangle right = new Rectangle(INSET + ICON_SIZE + PAD, INSET, Math.max(PROGRESS_MIN_WIDTH,width - INSET - ICON_SIZE - PAD - INSET), height - (INSET * 2));
			Rendering.drawText(g, new Rectangle(right.x, right.y, right.width, INSET + fm.getHeight() + PAD), Color.WHITE,
				Alignment.CENTER, actionText, isVertical
			);
			Rendering.drawProgressBar(g, new Rectangle(right.x, PAD + INSET + fm.getHeight() + INSET + PAD, right.width, height - (INSET + INSET + fm.getHeight() + INSET + INSET)),
				border, progressLeftColor, progressDoneColor, min, max, value, isVertical
			);	
		}
		return new Dimension(width, height);
	}

}