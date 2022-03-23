package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.data.Smithing;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionProgressPlugin;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.client.eventbus.Subscribe;

import java.util.Arrays;

/**
 * Detects actions initiated from the chatbox crafting interface (Eg: Fletching, Glassblowing, Leatherwork)
 */
@Slf4j
public class ChatboxActionDetector {

    /**
     * Indicates how many items are to be created in the crafting dialogue.
     */
    private static final int VAR_MAKE_AMOUNT = 200;

    /**
     * Indicates the selected product in the crafting dialogue.
     */
    private static final int VAR_SELECTED_INDEX = 2673;

    private static final int WIDGET_MAKE_PARENT = 270;
    private static final int WIDGET_MAKE_SLOT_START = 14;
    private static final int WIDGET_MAKE_SLOT_COUNT = 9;
    private static final int WIDGET_MAKE_SLOT_ITEM = 38;

    // 2046 [clientscript,skillmulti_setup]
    private static final int CLIENTSCRIPT_SKILLMULTI_SETUP = 2046;
    private static final int CLIENTSCRIPT_SKILLMULTI_ITEMBUTTON_OP = 2050;
    private static final int CLIENTSCRIPT_SKILLMULTI_ITEMBUTTON_KEY = 2051;
    private static final int PROC_SKILLMULTI_ITEMBUTTON_TRIGGERED = 2052;
    private static final int PROC_SKILLMULTI_ITEMBUTTON_DRAW = 2054;

    @Inject
    private Client client;

    @Inject
    private ActionProgressPlugin plugin;

    private final int[] widgetProductIds = new int[WIDGET_MAKE_SLOT_COUNT];

    private int selectedIndex = -1;

    @Subscribe
    public void onVarbitChanged(VarbitChanged evt) {
        if (evt.getIndex() == VAR_SELECTED_INDEX) {
            this.selectedIndex = client.getVarpValue(evt.getIndex());
            log.info("Selected index changed: {}", this.selectedIndex);
        }
    }

    @Subscribe
    public void onScriptPreFired(ScriptPreFired evt) {
        if (evt.getScriptId() == CLIENTSCRIPT_SKILLMULTI_ITEMBUTTON_KEY ||
                evt.getScriptId() == CLIENTSCRIPT_SKILLMULTI_ITEMBUTTON_OP) {
            ScriptEvent se = evt.getScriptEvent();
            Widget source;
            if (se != null && (source = se.getSource()) != null) {
                selectedIndex = (source.getId() - 17694734);
            }
        }
    }

    @Subscribe
    public void onScriptPostFired(ScriptPostFired evt) {
        if (evt.getScriptId() == CLIENTSCRIPT_SKILLMULTI_SETUP) {
            log.info("[proc_itembutton_draw] updating products");
            updateProducts();
        } else if (evt.getScriptId() == PROC_SKILLMULTI_ITEMBUTTON_TRIGGERED) {
            int currentProductId = widgetProductIds[selectedIndex];
            int amount = getActionCount(currentProductId);
            log.info("[proc_itembutton_triggered] starting action (product={}, amount={})", currentProductId, amount);
            plugin.setAction(currentProductId, amount);
        }
    }

    private void updateProducts() {
        for (int index = 0; index < WIDGET_MAKE_SLOT_COUNT; index++) {
            Widget slot = client.getWidget(WIDGET_MAKE_PARENT, WIDGET_MAKE_SLOT_START + index);
            Widget container;
            int id;
            if (slot == null || (container = slot.getChild(WIDGET_MAKE_SLOT_ITEM)) == null) {
                continue;
            }
            if ((id = container.getItemId()) == -1) {
                continue;
            }
            if (id == ItemID.HOURGLASS) {
                log.info("hourglass[1] at index {}", index);
                continue;
            }
            if (id == ItemID.HOURGLASS_12841) {
                log.info("hourglass[2] at index {}", index);
                continue;
            }
            this.widgetProductIds[index] = id;
        }
        log.info("updated products: {}", Arrays.toString(this.widgetProductIds));
    }

    private int getActionCount(int currentProductId) {
        int currentProductAmount = client.getVarcIntValue(VAR_MAKE_AMOUNT);
        for (Smithing.Bar bar : Smithing.Bar.values()) {
            if (currentProductId == bar.getItemId()) {
                return Math.min(currentProductAmount, bar.countAvailableOres(client));
            }
        }
        return currentProductAmount;
    }

}
