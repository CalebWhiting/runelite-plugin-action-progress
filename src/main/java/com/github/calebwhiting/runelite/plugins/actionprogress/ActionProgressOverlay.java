package com.github.calebwhiting.runelite.plugins.actionprogress;

import com.github.calebwhiting.runelite.api.ui.ProgressPanelComponent;
import com.google.inject.Inject;

import net.runelite.api.Client;
import net.runelite.client.config.RuneLiteConfig;
import net.runelite.client.ui.overlay.Overlay;

import java.awt.*;

public class ActionProgressOverlay extends Overlay {

    @Inject
    private ActionProgressPlugin plugin;

    @Inject
    private Client client;

    @Inject
    private RuneLiteConfig runeLiteConfig;

    private final ProgressPanelComponent progressPanelComponent = new ProgressPanelComponent();

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
        this.progressPanelComponent.setBackgroundColor(runeLiteConfig.overlayBackgroundColor());
        this.progressPanelComponent.setProgress(
                plugin.getActionStartTick(),
                plugin.getActionEndTick(),
                tick
        );
        return this.progressPanelComponent.render(graphics);
    }

}
