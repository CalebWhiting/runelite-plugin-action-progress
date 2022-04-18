package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.api.InventoryHelper;
import com.github.calebwhiting.runelite.api.data.IDQuery;
import com.github.calebwhiting.runelite.api.data.IDs;
import com.github.calebwhiting.runelite.api.data.Ingredient;
import com.github.calebwhiting.runelite.api.data.Recipe;
import com.github.calebwhiting.runelite.data.Cooking;
import com.github.calebwhiting.runelite.data.Herblore;
import com.github.calebwhiting.runelite.data.Magic;
import com.github.calebwhiting.runelite.data.Smithing;
import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionProgressPlugin;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.ScriptEvent;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.client.eventbus.Subscribe;

import java.util.Arrays;
import java.util.Objects;

import static com.github.calebwhiting.runelite.plugins.actionprogress.Action.*;
import static net.runelite.api.ItemID.*;

/**
 * Detects actions initiated from the chatbox crafting interface (Eg: Fletching, Glassblowing, Leather-work)
 */
@Slf4j
@Singleton
public class ChatboxDetector extends ActionDetector {

    /**
     * Indicates how many items are to be created in the crafting dialogue.
     */
    private static final int VAR_MAKE_AMOUNT = 200;

    /**
     * Indicates the selected product in the crafting dialogue.
     */
    private static final int VAR_SELECTED_INDEX = 2673;

    private static final int WIDGET_MAKE_PARENT = 270;
    private static final int WIDGET_MAKE_QUESTION = 5;
    private static final int WIDGET_MAKE_SLOT_START = 14;
    private static final int WIDGET_MAKE_SLOT_COUNT = 9;
    private static final int WIDGET_MAKE_SLOT_ITEM = 38;
    private static final int WIDGET_ID_CHATBOX_FIRST_MAKE_BUTTON = 17694734;
    private static final int CLIENTSCRIPT_SKILLMULTI_SETUP = 2046;
    private static final int CLIENTSCRIPT_SKILLMULTI_ITEMBUTTON_OP = 2050;
    private static final int CLIENTSCRIPT_SKILLMULTI_ITEMBUTTON_KEY = 2051;
    private static final int PROC_SKILLMULTI_ITEMBUTTON_TRIGGERED = 2052;

    private final int[] widgetProductIds = new int[WIDGET_MAKE_SLOT_COUNT];

    @Inject private Client client;
    @Inject private ActionProgressPlugin plugin;
    @Inject private InventoryHelper inventoryHelper;
    @Inject private ActionUtils actionUtils;

    private int selectedIndex = -1;
    private String question;

    private static final ExRecipe[] MULTI_MATERIAL_PRODUCTS = {
            // @formatter:off
            new ExRecipe(CRAFT_LEATHER, GREEN_DHIDE_BODY, new Ingredient(GREEN_DRAGON_LEATHER, 3)),
            new ExRecipe(CRAFT_LEATHER, GREEN_DHIDE_CHAPS, new Ingredient(GREEN_DRAGON_LEATHER, 2)),
            new ExRecipe(CRAFT_LEATHER, BLUE_DHIDE_BODY, new Ingredient(BLUE_DRAGON_LEATHER, 3)),
            new ExRecipe(CRAFT_LEATHER, BLUE_DHIDE_CHAPS, new Ingredient(BLUE_DRAGON_LEATHER, 2)),
            new ExRecipe(CRAFT_LEATHER, RED_DHIDE_BODY, new Ingredient(RED_DRAGON_LEATHER, 3)),
            new ExRecipe(CRAFT_LEATHER, RED_DHIDE_CHAPS, new Ingredient(RED_DRAGON_LEATHER, 2)),
            new ExRecipe(CRAFT_LEATHER, BLACK_DHIDE_BODY, new Ingredient(BLACK_DRAGON_LEATHER, 3)),
            new ExRecipe(CRAFT_LEATHER, BLACK_DHIDE_CHAPS, new Ingredient(BLACK_DRAGON_LEATHER, 2)),
            new ExRecipe(CRAFT_LEATHER, SNAKESKIN_BANDANA, new Ingredient(SNAKESKIN, 5)),
            new ExRecipe(CRAFT_LEATHER, SNAKESKIN_BODY, new Ingredient(SNAKESKIN, 15)),
            new ExRecipe(CRAFT_LEATHER, SNAKESKIN_BOOTS, new Ingredient(SNAKESKIN, 6)),
            new ExRecipe(CRAFT_LEATHER, SNAKESKIN_CHAPS, new Ingredient(SNAKESKIN, 12)),
            new ExRecipe(CRAFT_LEATHER, SNAKESKIN_VAMBRACES, new Ingredient(SNAKESKIN, 8)),
            new ExRecipe(CRAFT_LEATHER, XERICIAN_HAT, new Ingredient(XERICIAN_FABRIC, 3)),
            new ExRecipe(CRAFT_LEATHER, XERICIAN_TOP, new Ingredient(XERICIAN_FABRIC, 5)),
            new ExRecipe(CRAFT_LEATHER, XERICIAN_ROBE, new Ingredient(XERICIAN_FABRIC, 4)),
            new ExRecipe(CRAFT_LEATHER, XERICIAN_ROBE, new Ingredient(XERICIAN_FABRIC, 4)),
            new ExRecipe(CRAFT_LEATHER, LEATHER_GLOVES, new Ingredient(LEATHER)),
            new ExRecipe(CRAFT_LEATHER, LEATHER_BOOTS, new Ingredient(LEATHER)),
            new ExRecipe(CRAFT_LEATHER, LEATHER_COWL, new Ingredient(LEATHER)),
            new ExRecipe(CRAFT_LEATHER, LEATHER_VAMBRACES, new Ingredient(LEATHER)),
            new ExRecipe(CRAFT_LEATHER, LEATHER_BODY, new Ingredient(LEATHER)),
            new ExRecipe(CRAFT_LEATHER, LEATHER_CHAPS, new Ingredient(LEATHER)),
            new ExRecipe(CRAFT_LEATHER, COIF, new Ingredient(LEATHER)),
            new ExRecipe(CRAFT_HARD_LEATHER, HARDLEATHER_BODY, new Ingredient(HARD_LEATHER, 1)),
            new ExRecipe(CRAFT_BATTLESTAVES, AIR_BATTLESTAFF, new Ingredient(AIR_ORB), new Ingredient(BATTLESTAFF)),
            new ExRecipe(CRAFT_BATTLESTAVES, FIRE_BATTLESTAFF, new Ingredient(FIRE_ORB), new Ingredient(BATTLESTAFF)),
            new ExRecipe(CRAFT_BATTLESTAVES, EARTH_BATTLESTAFF, new Ingredient(EARTH_ORB), new Ingredient(BATTLESTAFF)),
            new ExRecipe(CRAFT_BATTLESTAVES, WATER_BATTLESTAFF, new Ingredient(WATER_ORB), new Ingredient(BATTLESTAFF)),
            new ExRecipe(SMELTING, BRONZE_BAR, new Ingredient(TIN_ORE), new Ingredient(COPPER_ORE)),
            new ExRecipe(SMELTING, IRON_BAR, new Ingredient(IRON_ORE)),
            new ExRecipe(SMELTING, SILVER_BAR, new Ingredient(SILVER_ORE)),
            new ExRecipe(SMELTING, STEEL_BAR, new Ingredient(IRON_ORE), new Ingredient(COAL, 2)),
            new ExRecipe(SMELTING, GOLD_BAR, new Ingredient(GOLD_ORE)),
            new ExRecipe(SMELTING, MITHRIL_BAR, new Ingredient(MITHRIL_ORE), new Ingredient(COAL, 4)),
            new ExRecipe(SMELTING, ADAMANTITE_BAR, new Ingredient(ADAMANTITE_ORE), new Ingredient(COAL, 6)),
            new ExRecipe(SMELTING, RUNITE_BAR, new Ingredient(RUNITE_ORE), new Ingredient(COAL, 8)),
            new ExRecipe(SMELTING_CANNONBALLS, CANNONBALL, new Ingredient(STEEL_BAR)),
            new ExRecipe(CRAFT_CUT_GEMS, OPAL, new Ingredient(UNCUT_OPAL)),
            new ExRecipe(CRAFT_CUT_GEMS, JADE, new Ingredient(UNCUT_JADE)),
            new ExRecipe(CRAFT_CUT_GEMS, RED_TOPAZ, new Ingredient(UNCUT_RED_TOPAZ)),
            new ExRecipe(CRAFT_CUT_GEMS, SAPPHIRE, new Ingredient(UNCUT_SAPPHIRE)),
            new ExRecipe(CRAFT_CUT_GEMS, EMERALD, new Ingredient(UNCUT_EMERALD)),
            new ExRecipe(CRAFT_CUT_GEMS, RUBY, new Ingredient(UNCUT_RUBY)),
            new ExRecipe(CRAFT_CUT_GEMS, DIAMOND, new Ingredient(UNCUT_DIAMOND)),
            new ExRecipe(CRAFT_CUT_GEMS, DRAGONSTONE, new Ingredient(UNCUT_DRAGONSTONE)),
            new ExRecipe(CRAFT_CUT_GEMS, ONYX, new Ingredient(UNCUT_ONYX)),
            new ExRecipe(CRAFT_CUT_GEMS, ZENYTE, new Ingredient(UNCUT_ZENYTE)),
            new ExRecipe(CRAFT_STRING_JEWELLERY, STRUNG_RABBIT_FOOT, new Ingredient(RABBIT_FOOT), new Ingredient(BALL_OF_WOOL)),
            new ExRecipe(CRAFT_STRING_JEWELLERY, HOLY_SYMBOL, new Ingredient(UNSTRUNG_SYMBOL), new Ingredient(BALL_OF_WOOL)),
            new ExRecipe(CRAFT_STRING_JEWELLERY, UNHOLY_SYMBOL, new Ingredient(UNSTRUNG_EMBLEM), new Ingredient(BALL_OF_WOOL)),
            new ExRecipe(CRAFT_STRING_JEWELLERY, OPAL_AMULET, new Ingredient(OPAL_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new ExRecipe(CRAFT_STRING_JEWELLERY, JADE_AMULET, new Ingredient(JADE_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new ExRecipe(CRAFT_STRING_JEWELLERY, SAPPHIRE_AMULET, new Ingredient(SAPPHIRE_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new ExRecipe(CRAFT_STRING_JEWELLERY, TOPAZ_AMULET, new Ingredient(TOPAZ_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new ExRecipe(CRAFT_STRING_JEWELLERY, EMERALD_AMULET, new Ingredient(EMERALD_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new ExRecipe(CRAFT_STRING_JEWELLERY, RUBY_AMULET, new Ingredient(RUBY_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new ExRecipe(CRAFT_STRING_JEWELLERY, DIAMOND_AMULET, new Ingredient(DIAMOND_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new ExRecipe(CRAFT_STRING_JEWELLERY, DRAGONSTONE_AMULET, new Ingredient(DRAGONSTONE_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new ExRecipe(CRAFT_STRING_JEWELLERY, ONYX_AMULET, new Ingredient(ONYX_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new ExRecipe(CRAFT_STRING_JEWELLERY, ZENYTE_AMULET, new Ingredient(ZENYTE_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new ExRecipe(CRAFT_MOLTEN_GLASS, MOLTEN_GLASS, new Ingredient(BUCKET_OF_SAND), new Ingredient(SODA_ASH)),
            new ExRecipe(CRAFT_BLOW_GLASS, BEER_GLASS, new Ingredient(MOLTEN_GLASS)),
            new ExRecipe(CRAFT_BLOW_GLASS, EMPTY_CANDLE_LANTERN, new Ingredient(MOLTEN_GLASS)),
            new ExRecipe(CRAFT_BLOW_GLASS, EMPTY_OIL_LAMP, new Ingredient(MOLTEN_GLASS)),
            new ExRecipe(CRAFT_BLOW_GLASS, VIAL, new Ingredient(MOLTEN_GLASS)),
            new ExRecipe(CRAFT_BLOW_GLASS, EMPTY_FISHBOWL, new Ingredient(MOLTEN_GLASS)),
            new ExRecipe(CRAFT_BLOW_GLASS, UNPOWERED_ORB, new Ingredient(MOLTEN_GLASS)),
            new ExRecipe(CRAFT_BLOW_GLASS, LANTERN_LENS, new Ingredient(MOLTEN_GLASS)),
            new ExRecipe(CRAFT_BLOW_GLASS, EMPTY_LIGHT_ORB, new Ingredient(MOLTEN_GLASS)),
            new ExRecipe(FLETCH_CUT_BOW, LONGBOW_U, new Ingredient(LOGS)),
            new ExRecipe(FLETCH_CUT_BOW, OAK_LONGBOW_U, new Ingredient(OAK_LOGS)),
            new ExRecipe(FLETCH_CUT_BOW, WILLOW_LONGBOW_U, new Ingredient(WILLOW_LOGS)),
            new ExRecipe(FLETCH_CUT_BOW, MAPLE_LONGBOW_U, new Ingredient(MAPLE_LOGS)),
            new ExRecipe(FLETCH_CUT_BOW, YEW_LONGBOW_U, new Ingredient(YEW_LOGS)),
            new ExRecipe(FLETCH_CUT_BOW, MAGIC_LONGBOW_U, new Ingredient(MAGIC_LOGS)),
            new ExRecipe(FLETCH_CUT_BOW, SHORTBOW_U, new Ingredient(LOGS)),
            new ExRecipe(FLETCH_CUT_BOW, OAK_SHORTBOW_U, new Ingredient(OAK_LOGS)),
            new ExRecipe(FLETCH_CUT_BOW, WILLOW_SHORTBOW_U, new Ingredient(WILLOW_LOGS)),
            new ExRecipe(FLETCH_CUT_BOW, MAPLE_SHORTBOW_U, new Ingredient(MAPLE_LOGS)),
            new ExRecipe(FLETCH_CUT_BOW, YEW_SHORTBOW_U, new Ingredient(YEW_LOGS)),
            new ExRecipe(FLETCH_CUT_BOW, MAGIC_SHORTBOW_U, new Ingredient(MAGIC_LOGS)),
            new ExRecipe(FLETCH_STRING_BOW, LONGBOW, new Ingredient(LONGBOW_U), new Ingredient(BOW_STRING)),
            new ExRecipe(FLETCH_STRING_BOW, OAK_LONGBOW, new Ingredient(OAK_LONGBOW_U), new Ingredient(BOW_STRING)),
            new ExRecipe(FLETCH_STRING_BOW, WILLOW_LONGBOW, new Ingredient(WILLOW_LONGBOW_U), new Ingredient(BOW_STRING)),
            new ExRecipe(FLETCH_STRING_BOW, MAPLE_LONGBOW, new Ingredient(MAPLE_LONGBOW_U), new Ingredient(BOW_STRING)),
            new ExRecipe(FLETCH_STRING_BOW, YEW_LONGBOW, new Ingredient(YEW_LONGBOW_U), new Ingredient(BOW_STRING)),
            new ExRecipe(FLETCH_STRING_BOW, MAGIC_LONGBOW, new Ingredient(MAGIC_LONGBOW_U), new Ingredient(BOW_STRING)),
            new ExRecipe(FLETCH_STRING_BOW, SHORTBOW, new Ingredient(SHORTBOW_U), new Ingredient(BOW_STRING)),
            new ExRecipe(FLETCH_STRING_BOW, OAK_SHORTBOW, new Ingredient(OAK_SHORTBOW_U), new Ingredient(BOW_STRING)),
            new ExRecipe(FLETCH_STRING_BOW, WILLOW_SHORTBOW, new Ingredient(WILLOW_SHORTBOW_U), new Ingredient(BOW_STRING)),
            new ExRecipe(FLETCH_STRING_BOW, MAPLE_SHORTBOW, new Ingredient(MAPLE_SHORTBOW_U), new Ingredient(BOW_STRING)),
            new ExRecipe(FLETCH_STRING_BOW, YEW_SHORTBOW, new Ingredient(YEW_SHORTBOW_U), new Ingredient(BOW_STRING)),
            new ExRecipe(FLETCH_STRING_BOW, MAGIC_SHORTBOW, new Ingredient(MAGIC_SHORTBOW_U), new Ingredient(BOW_STRING)),
            // @formatter:on
    };

    @Getter
    private static class ExRecipe extends Recipe {

        private final Action action;

        public ExRecipe(Action action, int productId, Ingredient... requirements) {
            super(productId, requirements);
            this.action = action;
        }

    }

    @Subscribe
    public void onVarbitChanged(VarbitChanged evt) {
        if (evt.getIndex() == VAR_SELECTED_INDEX) {
            this.selectedIndex = this.client.getVarpValue(evt.getIndex());
        }
    }

    @Subscribe
    public void onScriptPreFired(ScriptPreFired evt) {
        if (evt.getScriptId() == CLIENTSCRIPT_SKILLMULTI_ITEMBUTTON_KEY || evt.getScriptId() == CLIENTSCRIPT_SKILLMULTI_ITEMBUTTON_OP) {
            ScriptEvent se = evt.getScriptEvent();
            Widget source = se == null ? null : se.getSource();
            if (source != null) {
                this.selectedIndex = (source.getId() - WIDGET_ID_CHATBOX_FIRST_MAKE_BUTTON);
            }
        }
    }

    @Subscribe
    public void onScriptPostFired(ScriptPostFired evt) {
        if (evt.getScriptId() == CLIENTSCRIPT_SKILLMULTI_SETUP) {
            log.debug("[proc_itembutton_draw] updating products");
            this.updateProducts();
        } else if (evt.getScriptId() == PROC_SKILLMULTI_ITEMBUTTON_TRIGGERED) {
            this.onQuestionAnswered();
        }
    }

    protected void unhandled(int itemId) {
        log.warn("[*] Unhandled chatbox action");
        log.warn(" |-> Question: {}", question);
        log.warn(" |-> Item ID: {} ({})", IDQuery.ofItems().getNameString(itemId), itemId);
    }

    @Override
    public void setup() {
        /*
         * Cooking
         */
        this.registerAction(
                COOKING_TOP_PIZZA,
                INCOMPLETE_PIZZA,
                UNCOOKED_PIZZA,
                PINEAPPLE_PIZZA,
                ANCHOVY_PIZZA,
                MEAT_PIZZA
        );
        this.registerAction(COOKING_MIX_GRAPES, UNFERMENTED_WINE, UNFERMENTED_WINE_1996, ZAMORAKS_UNFERMENTED_WINE);
        this.registerAction(COOKING_MIX_DOUGH, BREAD_DOUGH, PASTRY_DOUGH, PITTA_DOUGH, PIZZA_BASE);
        /*
         * Fletching
         */
        String[] tipMaterials = {
                "ONYX", "DRAGON(STONE)?", "DIAMOND", "RUNE", "RUBY", "EMERALD", "ADAMANT", "SAPPHIRE", "TOPAZ",
                "MITHRIL", "JADE", "STEEL", "IRON", "BRONZE", "BARBED", "PEARL", "OPAL", "BROAD", "AMETHYST", "SILVER",
                "RUNITE", "BLACK", "BLURITE", "OGRE", "KEBBIT"
        };
        this.registerAction(
                FLETCH_ATTACH_TIPS,
                IDQuery.ofItems().query(String.format(
                        "(%s)_(ARROW(S)?|BOLT(S)?|BRUTAL)",
                        String.join("|", Arrays.asList(tipMaterials))
                )).results().build()
        );
        this.registerAction(FLETCH_ATTACH_FEATHER, HEADLESS_ARROW, FLIGHTED_OGRE_ARROW);
        this.registerAction(FLETCH_CUT_ARROW_SHAFT, ARROW_SHAFT, BRUMA_KINDLING, OGRE_ARROW_SHAFT);
        this.registerAction(FLETCH_CUT_TIPS, IDQuery.ofItems().query(".*_BOLT_TIPS").results().build());
        /*
         * Herblore
         */
        this.registerAction(HERB_MIX_TAR, GUAM_TAR, MARRENTILL_TAR, TARROMIN_TAR, HARRALANDER_TAR);
        for (Recipe recipe : Herblore.UNFINISHED_POTIONS) {
            this.registerAction(HERB_MIX_UNFINISHED, recipe.getProductId());
        }
        for (Recipe recipe : Herblore.POTIONS) {
            this.registerAction(HERB_MIX_POTIONS, recipe.getProductId());
        }
        /*
         * Magic
         */
        this.registerAction(MAGIC_ENCHANT_BOLTS, IDQuery.ofItems().query("(.*)_BOLTS_E").results().build());
    }

    private void onQuestionAnswered() {
        int currentProductId = this.widgetProductIds[this.selectedIndex];
        int amount = this.getActionCount(currentProductId);
        String question = this.question == null ? "?" : this.question;
        switch (question) {
            case "How many would you like to cook?":
            case "What would you like to cook?":
                actionManager.setAction(COOKING, amount, currentProductId);
                break;
            case "How would you like to cut the pineapple?":
                if (currentProductId == PINEAPPLE_RING) {
                    amount = Math.min(amount, actionUtils.getActionsUntilFull(4, 1));
                }
                actionManager.setAction(COOKING_CUT_FRUIT, amount, currentProductId);
                break;
            case "How many would you like to charge?":
                Magic.ChargeOrbSpell spell = Magic.ChargeOrbSpell.byProduct(currentProductId);
                Objects.requireNonNull(spell, "No charge orb spell found for product: " + currentProductId);
                actionManager.setAction(
                        Action.MAGIC_CHARGE_ORB,
                        Math.min(amount, spell.getSpell().getAvailableCasts(client)),
                        currentProductId
                );
                break;
            case "How many would you like to string?": // Fletching/Stringing
            case "What would you like to string?": // Fletching/Stringing
            case "What would you like to make?": // Various
            case "What would you like to smelt?": // Smelting bars
            case "How many batches would you like?":
            case "How many bars would you like to smith?": // Cannonballs
            case "How many gems would you like to cut?": // Cutting gems
            case "How many do you wish to make?": // Various
            case "How many sets of 15 do you wish to complete?": // Arrows
            case "How many sets of 15 do you wish to feather?": // Headless arrows
            case "?":
            default:
                ExRecipe recipe = Recipe.forProduct(MULTI_MATERIAL_PRODUCTS, currentProductId);
                if (recipe != null) {
                    amount = Math.min(amount, recipe.getMakeProductCount(inventoryHelper));
                    actionManager.setAction(recipe.getAction(), amount, currentProductId);
                } else {
                    setActionByItemId(currentProductId, amount);
                }
                break;
        }
    }

    private void updateProducts() {
        for (int slotIndex = 0; slotIndex < WIDGET_MAKE_SLOT_COUNT; slotIndex++) {
            Widget slotWidget = this.client.getWidget(WIDGET_MAKE_PARENT, WIDGET_MAKE_SLOT_START + slotIndex);
            Widget container = slotWidget == null ? null : slotWidget.getChild(WIDGET_MAKE_SLOT_ITEM);
            int id = container == null ? -1 : container.getItemId();
            if (id != -1 && id != HOURGLASS && id != HOURGLASS_12841) {
                this.widgetProductIds[slotIndex] = id;
            }
        }
        Widget questionWidget = this.client.getWidget(WIDGET_MAKE_PARENT, WIDGET_MAKE_QUESTION);
        if (questionWidget != null) {
            this.question = questionWidget.getText();
        }
        log.info("updated products: {}", Arrays.toString(this.widgetProductIds));
    }

    private int getActionCount(int productId) {
        int n = this.client.getVarcIntValue(VAR_MAKE_AMOUNT);
        for (Smithing.Bar bar : Smithing.Bar.values()) {
            if (productId == bar.getItemId()) {
                return Math.min(n, bar.countAvailableOres(this.client));
            }
        }
        for (Cooking.Cookable entry : Cooking.Cookable.values()) {
            IDs raw = entry.getRaw(), cooked = entry.getCooked();
            if (cooked.contains(productId)) {
                int rawFish = this.inventoryHelper.getItemCount(raw::contains);
                return Math.min(n, rawFish);
            }
        }
        return n;
    }
} 