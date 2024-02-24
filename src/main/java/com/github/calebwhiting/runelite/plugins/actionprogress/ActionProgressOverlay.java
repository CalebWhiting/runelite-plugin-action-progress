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

	private static final int PROGRESS_MIN_WIDTH = 50;

	private static final int PROGRESS_MIN_WIDTH_HORIZONTAL = INSET + ICON_SIZE + PAD + PROGRESS_MIN_WIDTH + INSET;

	@Inject private ActionProgressPlugin plugin;

	@Inject private ActionManager actionManager;

	@Inject private Client client;

	@Inject private RuneLiteConfig runeLiteConfig;

	@Inject private ActionProgressConfig config;

	@Override
	public Dimension render(Graphics2D g)
	{
		try {
			this.setMinimumSize(20);
			Dimension preferredDimension = this.getPreferredSize();
			int tick = this.client.getTickCount();
			Image icon = this.plugin.getCurrentProductImage();
			if (tick > this.actionManager.getActionEndTick()) {
				return EMPTY;
				//return this.renderInfobox(g, "Leather-working", "0/10", "130s", icon, 0, 2, 1, preferredDimension); //For debugging
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

		int preferredWidth = preferredDimension != null ? (int)preferredDimension.getWidth() : 200;
		int preferredHeight = preferredDimension != null ? (int)preferredDimension.getHeight() : 55;

		boolean isVertical = false;
		boolean isCompact = false;
		int height = 0;
		int width = 0;
		
		if (preferredWidth < PROGRESS_MIN_WIDTH_HORIZONTAL){
			isVertical = true;
			width = Math.max(INSET + INSET + PAD + PAD + INSET + INSET, preferredWidth);
			height = Math.max(INSET + ICON_SIZE + PAD + PROGRESS_MIN_HEIGHT + INSET, preferredHeight);
		}
		else{
			if (preferredHeight < 33){
				isCompact = true;
			}
			width = Math.max(INSET + ICON_SIZE + PAD + PROGRESS_MIN_WIDTH + INSET, preferredWidth);
			height = Math.max(PAD + fm.getHeight() + PAD, preferredHeight);
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
			DrawVerticaltMode(g,fm, height, width, timeString, count, actionText, icon, min, max, value, border, progressDoneColor, progressLeftColor);
		}
		else {
			if (isCompact) {
				DrawCompactMode(g,fm, height, width, timeString, count, actionText, icon, min, max, value, border, progressDoneColor, progressLeftColor);
			}
			else {
				DrawStandardMode(g,fm, height, width, timeString, count, actionText, icon, min, max, value, border, progressDoneColor, progressLeftColor);
			}
		}
		return new Dimension(width, height);
	}

	private void DrawStandardMode (Graphics2D g, FontMetrics fm, int height, int width, String timeString, String count, String actionText, Image icon, long min, long max, long value, Color border, Color progressDoneColor, Color progressLeftColor) {
		int heightWithIcon = INSET + ICON_SIZE + fm.getHeight();
		//Icon & time
		if (height <= heightWithIcon){
			Rendering.drawText(g, new Rectangle(INSET, (height / 2) - fm.getHeight() / 2, ICON_SIZE, fm.getHeight() + PAD), Color.ORANGE,
					Alignment.CENTER, timeString, false
			);
		}
		else{
			int iconWidth = icon.getWidth(null);
			int iconHeight = icon.getHeight(null);
			g.drawImage(icon, INSET + (ICON_SIZE / 2) - (iconWidth / 2), INSET + (ICON_SIZE / 2) - (iconHeight / 2),
					iconWidth, iconHeight, null
			);
			Rendering.drawText(g, new Rectangle(INSET, Math.max(INSET + ICON_SIZE + PAD, height / 2), ICON_SIZE, fm.getHeight()), Color.ORANGE,
					Alignment.CENTER, timeString, false
			);
		}


		Rectangle right = new Rectangle(INSET + ICON_SIZE + PAD, INSET, Math.max(PROGRESS_MIN_WIDTH, width - INSET - ICON_SIZE - PAD - INSET), height - INSET - INSET);
		//Action
		int stringActionWidth = g.getFontMetrics().stringWidth(actionText);
		if (right.width - INSET - INSET > stringActionWidth) {
			Rendering.drawText(g,
					new Rectangle(right.x, right.y, right.width, PAD + fm.getHeight()),
					Color.WHITE,
					Alignment.CENTER,
					actionText,
					false
			);
		}
		else{
			Rendering.drawText(g,
					new Rectangle(right.x, right.y, right.width, PAD + fm.getHeight()),
					Color.WHITE,
					Alignment.CENTER,
					count,
					false
			);
		}
		//Progress bar
		Rendering.drawProgressBar(g, new Rectangle(right.x, INSET + fm.getHeight() + INSET, right.width, height - (INSET + fm.getHeight() + INSET + INSET)),
				border, progressLeftColor, progressDoneColor, min, max, value, false
		);
	}
	private void DrawCompactMode (Graphics2D g, FontMetrics fm, int height, int width, String timeString, String count, String actionText, Image icon, long min, long max, long value, Color border, Color progressDoneColor, Color progressLeftColor){
		//Progress bar
		Rendering.drawProgressBar(g,
				new Rectangle(0, 0, width, height),
				border,
				progressLeftColor,
				progressDoneColor,
				min, max, value,
				false
		);
		//Time
		int stringTimeWidth = g.getFontMetrics().stringWidth(timeString);
		Rectangle rectTime = new Rectangle(width - PAD - stringTimeWidth, (height / 2) - fm.getHeight() / 2, stringTimeWidth, fm.getHeight());
		Rendering.drawText(g,
				rectTime,
				Color.ORANGE,
				Alignment.RIGHT,
				timeString,
				false
		);
		//Icon
		int iconWidth = icon.getWidth(null);
		int iconHeight = icon.getHeight(null);
		double iconRatio = height / icon.getHeight(null);
		Rectangle rectIcon = new Rectangle(0, 0, height, height);
		g.drawImage(icon,
				0,
				0,
				height,
				height,
				null
		);
		//Action
		int stringActionWidth = g.getFontMetrics().stringWidth(actionText);
		int actionTextRemainingSpace = width - rectIcon.width - rectTime.width - INSET - INSET;
		if (actionTextRemainingSpace > stringActionWidth){
			int textCenterPosition = actionTextRemainingSpace / 2;
			Rectangle rectAction = new Rectangle((width / 2) - (stringActionWidth / 2), (height / 2) - fm.getHeight() / 2, stringActionWidth, fm.getHeight());
			Rendering.drawText(g,
					rectAction,
					Color.WHITE,
					Alignment.CENTER,
					actionText,
					false
			);
		}
		else{
			int stringCountWidth = g.getFontMetrics().stringWidth(count);
			int textCenterPosition = stringCountWidth / 2;
			Rectangle rectAction = new Rectangle((width / 2) - (stringCountWidth / 2), (height / 2) - fm.getHeight() / 2, stringCountWidth, fm.getHeight());
			Rendering.drawText(g,
					rectAction,
					Color.WHITE,
					Alignment.CENTER,
					count,
					false
			);
		}
	}

	private void DrawVerticaltMode (Graphics2D g, FontMetrics fm, int height, int width, String timeString, String count, String actionText, Image icon, long min, long max, long value, Color border, Color progressDoneColor, Color progressLeftColor){
		int widthWithIcon = INSET + ICON_SIZE + INSET + INSET + PAD + INSET;

		Rectangle left = new Rectangle(INSET, INSET, fm.getHeight(), height - (INSET * 2));
		Rectangle right = new Rectangle((int)left.getMaxX() + left.x, left.y, Math.max(INSET,width - left.width - INSET - left.x - PAD), height - (INSET * 2));

		if(height < actionText.length() * 10 + ICON_SIZE + INSET + INSET + INSET){
			actionText = count;
		}

		if (width <= widthWithIcon){
			Rendering.drawText(g, new Rectangle(PAD, left.y, width, INSET + fm.getHeight() + PAD), Color.ORANGE,
					Alignment.CENTER, timeString, false
			);
			Rendering.drawText(g, new Rectangle(left.x, PAD + INSET + fm.getHeight() + INSET + PAD, left.width, Math.max(INSET,right.height - PAD - INSET - fm.getHeight() - PAD)), Color.WHITE,
					Alignment.CENTER, actionText, true
			);
		}
		else
		{
			left = new Rectangle(INSET, INSET + ICON_SIZE, ICON_SIZE, height - ICON_SIZE - (INSET * 2));
			right = new Rectangle(INSET + ICON_SIZE + PAD, INSET, Math.max(INSET,width - INSET - ICON_SIZE - PAD - INSET), height - (INSET * 2));
			int iconWidth = icon.getWidth(null);
			int iconHeight = icon.getHeight(null);
			g.drawImage(icon, INSET + (ICON_SIZE / 2) - (iconWidth / 2), INSET + (ICON_SIZE / 2) - (iconHeight / 2),
					iconWidth, iconHeight, null
			);
			Rendering.drawText(g, new Rectangle(right.x, right.y, right.width, INSET + fm.getHeight() + PAD), Color.ORANGE,
					Alignment.CENTER, timeString, false
			);
			Rendering.drawText(g, left, Color.WHITE,
					Alignment.CENTER, actionText, true
			);
		}

		Rendering.drawProgressBar(g, new Rectangle(right.x, PAD + INSET + fm.getHeight() + INSET + PAD, right.width, Math.max(INSET,right.height - PAD - INSET - fm.getHeight() - PAD)),
				border, progressLeftColor, progressDoneColor, min, max, value, true
		);
	}

}