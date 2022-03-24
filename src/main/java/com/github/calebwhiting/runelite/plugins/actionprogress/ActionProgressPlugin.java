package com.github.calebwhiting.runelite.plugins.actionprogress;

import com.github.calebwhiting.runelite.api.InterruptionListener;
import com.github.calebwhiting.runelite.api.InventoryHelper;
import com.github.calebwhiting.runelite.api.event.InterruptEvent;
import com.github.calebwhiting.runelite.plugins.actionprogress.detect.*;
import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import com.github.calebwhiting.runelite.api.TickManager;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@PluginDescriptor(name = "Action Progress")
public class ActionProgressPlugin extends Plugin {

    @Inject
    private ActionProgressConfig config;

    @Inject
    private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private ActionProgressOverlay overlay;

    @Inject
    private ItemManager itemManager;

    @Inject
    @Getter
    private TickManager tickManager;

    @Inject
    private InterruptionListener interruptionListener;

    @Inject
    private InventoryHelper inventoryHelper;

    @Inject
    private Notifier notifier;

    @Inject
    private EventBus eventBus;

    /**
     * The tick which started the series of actions
     */
    @Getter
    private int actionStartTick;

    /**
     * The tick when the current series of actions will be complete
     */
    @Getter
    private int actionEndTick;

    /**
     * The amount of actions in the sequence of actions
     */
    @Getter
    private int actionCount;

    @Getter
    private TimedAction currentAction;

    @Getter
    private String currentProductName;

    @Getter
    private Image currentProductImage;

    private final List<Object> eventHandlers = new LinkedList<>();

    @Override
    public void configure(Binder binder) {
        super.configure(binder);
    }

    @Override
    protected void startUp() throws Exception {
        super.startUp();
        this.overlayManager.add(overlay);
        Collections.addAll(
                this.eventHandlers,
                this.tickManager,
                this.interruptionListener,
                this.inventoryHelper,
                this.injector.getInstance(ChatboxActionDetector.class),
                this.injector.getInstance(FurnaceCastingActionDetector.class),
                this.injector.getInstance(EnchantSpellActionDetector.class),
                this.injector.getInstance(LecternActionDetector.class),
                this.injector.getInstance(SmithingDetector.class),
                this.injector.getInstance(SandpitDetector.class),
                this.injector.getInstance(TemporossDetector.class));
        for (Object o : this.eventHandlers) this.eventBus.register(o);
    }

    @Override
    protected void shutDown() throws Exception {
        super.shutDown();
        this.overlayManager.remove(overlay);
        for (Object o : this.eventHandlers) this.eventBus.unregister(o);
        this.eventHandlers.clear();
    }

    private void resetAction() {
        this.currentAction = null;
        this.currentProductName = null;
        this.currentProductImage = null;
        this.actionStartTick = 0;
        this.actionEndTick = 0;
        this.actionCount = 0;
    }

    @Subscribe
    public void onInterruptEvent(InterruptEvent evt) {
        resetAction();
    }

    @Subscribe
    public void onGameTick(GameTick tick) {
        if (client.getTickCount() >= actionEndTick) {
            if (this.interruptionListener.isWaiting()) {
                this.interruptionListener.setWaiting(false);
                if (this.actionCount > 1 && config.notifyWhenFinished())
                    this.notifier.notify("All of your items have been processed.", TrayIcon.MessageType.INFO);
            }
            resetAction();
        }
    }

    public void setAction(int currentProduct, int actionCount) {
        for (TimedAction action : TimedAction.values()) {
            if (Arrays.binarySearch(action.getProduct(), currentProduct) < 0)
                continue;
            setActionImpl(action, currentProduct, actionCount);
            break;
        }
    }

    public boolean setAction(TimedAction action, int currentProduct, int actionCount) {
        if (Arrays.binarySearch(action.getProduct(), currentProduct) < 0) return false;
        setActionImpl(action, currentProduct, actionCount);
        return true;
    }

    public void setActionUnchecked(TimedAction action, int currentProduct, int actionCount) {
        setActionImpl(action, currentProduct, actionCount);
    }

    private void setActionImpl(TimedAction action, int currentProduct, int actionCount) {
        if (this.config.ignoreSingleActions() && actionCount == 1) {
            return;
        }
        if (!this.config.monitorEnchantBoltSpells() && action == TimedAction.MAGIC_ENCHANT_BOLTS) {
            return;
        }
        if (!this.config.monitorEnchantJewellerySpells() && action == TimedAction.MAGIC_ENCHANT_JEWELLERY) {
            return;
        }
        ItemComposition composition = this.itemManager.getItemComposition(currentProduct);
        this.currentAction = action;
        this.currentProductName = composition.getName();
        this.currentProductImage = this.itemManager.getImage(currentProduct);
        this.actionStartTick = client.getTickCount();
        this.actionEndTick = this.actionStartTick + getActionDuration(action, actionCount);
        this.actionCount = actionCount;
        this.interruptionListener.setWaiting(true);
        log.info("Making item [id={}, amount={}, name={}]", currentProduct, actionCount, currentProductName);
    }

    private int getActionDuration(TimedAction action, int actionCount) {
        int duration = 0;
        if (actionCount > 0) {
            duration += action.getFirstActionTicks();
        }
        int rem = actionCount - 1;
        if (rem > 0) {
            duration += (rem * action.getSubsequentActionTicks());
        }
        return duration;
    }

    public int getCurrentActionProcessed() {
        if (currentAction == null) {
            return 0;
        }
        int elapsed = client.getTickCount() - this.getActionStartTick();
        int amount = 0;
        if (elapsed >= currentAction.getFirstActionTicks()) {
            amount++;
            elapsed -= currentAction.getFirstActionTicks();
        }
        amount += (elapsed / currentAction.getSubsequentActionTicks());
        return amount;
    }

    public long getApproximateCompletionTime() {
        int tick = client.getTickCount();
        float ticksRemaining = ((float) getActionEndTick() - tick);
        if (ticksRemaining <= 0)
            return 0;
        return (long) (ticksRemaining * TickManager.PERFECT_TICK_TIME) -
                (System.currentTimeMillis() - tickManager.getLastTickTime());
    }

    @Provides
    ActionProgressConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(ActionProgressConfig.class);
    }

}
