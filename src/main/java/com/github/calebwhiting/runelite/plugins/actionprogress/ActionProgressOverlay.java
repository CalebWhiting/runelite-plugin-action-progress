package com.github.calebwhiting.runelite.plugins.actionprogress;

import com.github.calebwhiting.runelite.api.ui.ProgressPanelComponent;
import com.google.inject.Inject;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;

import java.awt.*;

public class ActionProgressOverlay extends Overlay {

    private static final int DEFAULT_WIDTH = 325, DEFAULT_HEIGHT = 50;

    /*private static final int PROGRESS_HEIGHT = 32;
    private static final int MARGIN = 2;
    private static final int PADDING = 2;*/

    @Inject
    private ActionProgressPlugin plugin;

    @Inject
    private Client client;

    private final ProgressPanelComponent progressPanelComponent;

    public ActionProgressOverlay() {
        this.progressPanelComponent = new ProgressPanelComponent();
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        int tick = client.getTickCount();
        if (tick > plugin.getActionEndTick()) {
            return new Dimension(0, 0);
        }
        String time = String.format("%ds", Math.round((float) plugin.getApproximateCompletionTime() / 1000f));
        this.progressPanelComponent.setIcon(plugin.getCurrentProductImage());
        this.progressPanelComponent.setIconText(time);
        this.progressPanelComponent.setHeaderText(plugin.getCurrentProductName());
        this.progressPanelComponent.setProgress(
                plugin.getActionStartTick(),
                plugin.getActionEndTick(),
                tick
        );
        return this.progressPanelComponent.render(graphics);
    }

    /*private final MutableImageComponent itemComponent;
    private final SplitComponent mainComponent;
    private final SplitComponent rightComponent;
    private final LineComponent progressTextComponent;
    private final ProgressBarComponent progressBarComponent;
    private final PanelComponent panelComponent;

    public ActionProgressOverlay() {

        this.itemComponent = new MutableImageComponent();

        this.progressTextComponent = LineComponent.builder()
                .left("<product>").leftColor(Color.WHITE)
                .right("<seconds>").rightColor(Color.ORANGE)
                .build();

        this.progressBarComponent = new ProgressBarComponent();
        this.progressBarComponent.setLabelDisplayMode(ProgressBarComponent.LabelDisplayMode.PERCENTAGE);

        this.rightComponent = SplitComponent.builder()
                .first(this.progressTextComponent)
                .second(this.progressBarComponent)
                .orientation(ComponentOrientation.VERTICAL)
                .gap(new Point(5, 5)).build();

        this.mainComponent = SplitComponent.builder()
                .first(this.itemComponent)
                .second(this.rightComponent)
                .orientation(ComponentOrientation.HORIZONTAL)
                .gap(new Point(5, 5))
                .build();

        this.panelComponent = new PanelComponent();
        this.panelComponent.getChildren().add(this.mainComponent);
        this.panelComponent.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

    }

    @Override
    public Dimension render(Graphics2D graphics) {
        int tick = client.getTickCount();
        if (tick > plugin.getActionEndTick()) {
            return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
        this.itemComponent.setImage(plugin.getCurrentProductImage());
        this.progressTextComponent.setLeft(plugin.getCurrentProductName());
        this.progressTextComponent.setRight(String.format("%.1f seconds",
                (float) plugin.getApproximateCompletionTime() / 1000f));
        this.progressBarComponent.setMinimum(plugin.getActionStartTick());
        this.progressBarComponent.setMaximum(plugin.getActionEndTick());
        this.progressBarComponent.setValue(tick);
        return panelComponent.render(graphics);
    }
*/
    /*@Inject
    private BackgroundComponent background;

    @Inject
    private ActionProgressBar progressBar;

    public ActionProgressOverlay() {
        setPreferredPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
    }

    @Override
    public Dimension render(Graphics2D g) {
        Dimension size = new Dimension(
                DEFAULT_WIDTH + (MARGIN * 2),
                PROGRESS_HEIGHT + (MARGIN * 2));

        int tick = client.getTickCount();
        if (tick > plugin.getActionEndTick()) {
            return size;
        }

        size.setSize(DEFAULT_WIDTH, 24);

        FontMetrics fm = g.getFontMetrics();

        long lastTickTime = plugin.getTickManager().getLastTickTime();
        long avgTickTime = plugin.getTickManager().getAverageTickTime();
        long now = System.currentTimeMillis();
        float duration = plugin.getActionEndTick() - plugin.getActionStartTick();
        float percent = (((float) (tick - plugin.getActionStartTick())) / duration);

        *//*Color foreground = new Color(255, 150, 0, 100);
        if (tick == (float) plugin.getActionEndTick() - 1) {
            foreground = new Color(100, 200, 0, 100);
        } else if (tick == (float) plugin.getActionEndTick()) {
            foreground = new Color(0, 255, 0, 100);
        }*//*

        float timeRemainingTick = ((float) plugin.getActionEndTick() - tick);
        float timeRemainingMs = (long) (timeRemainingTick * avgTickTime);
        float timeRemainingSec = (timeRemainingMs / 1000f);

        int action = plugin.getCurrentActionProcessed();

        String rightText = String.format("%.1f", timeRemainingSec) + " seconds";
        String centerText = String.format("%d/%d", action, plugin.getActionCount());
        String leftText = plugin.getCurrentProductName();

        int cY = RenderingHelper.getCenteredTextY(g.getFontMetrics(), size.height);

        background.setRectangle(new Rectangle(size));
        background.setFill(true);

        this.progressBar.setSize(new Dimension(
                size.width - (MARGIN * 2), size.height - (MARGIN * 2)));

        RenderingHelper.renderEntityRelative(g, background, 0, 0);
        RenderingHelper.renderEntityRelative(g, progressBar, MARGIN, MARGIN);

        *//*progressBounds.setBounds(MARGIN, MARGIN,
                size.width - (MARGIN * 2), size.height - (MARGIN * 2));

        float tickWidth = (((float) progressBounds.width) / duration);
        float tickPercent = ((float) (now - lastTickTime)) / (float) avgTickTime;
        int progressWidth = Math.min(progressBounds.width, (int) (percent * progressBounds.width) + ((int) (tickPercent * tickWidth)));

        g.setColor(foreground);
        g.fillRect(progressBounds.x, progressBounds.y, progressWidth, progressBounds.height);*//*

        int right = size.width - fm.stringWidth(rightText) - MARGIN - PADDING;
        int left = MARGIN + PADDING;

        int center = left + fm.stringWidth(leftText);
        center = center + ((right - center) / 2) - (fm.stringWidth(centerText) / 2);

        g.setColor(Color.BLACK);
        g.drawString(leftText, left + 1, cY + 1);
        g.drawString(centerText, center + 1, cY + 1);
        g.drawString(rightText, right + 1, cY + 1);

        g.setColor(Color.WHITE);
        g.drawString(leftText, left, cY);
        g.drawString(centerText, center, cY);
        g.drawString(rightText, right, cY);

        return size;
    }

    private static class ActionProgressBar extends AbstractProgressBar {

        @Inject
        private ActionProgressPlugin plugin;

        @Inject
        private Client client;

        @Override
        public int getMinValue() {
            return plugin.getActionStartTick();
        }

        @Override
        public int getMaxValue() {
            return plugin.getActionEndTick();
        }

        @Override
        public int getCurrentValue() {
            return client.getTickCount();
        }

    }*/

}
