package com.github.calebwhiting.runelite.plugins.actionprogress.detect;

import com.github.calebwhiting.runelite.api.InventoryManager;
import com.github.calebwhiting.runelite.data.*;
import com.github.calebwhiting.runelite.plugins.actionprogress.Action;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionProgressPlugin;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionUtils;
import com.github.calebwhiting.runelite.plugins.actionprogress.Product;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.ScriptEvent;
import net.runelite.api.events.ScriptPostFired;
import net.runelite.api.events.ScriptPreFired;
import net.runelite.api.events.VarbitChanged;
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
public class ChatboxDetector extends ActionDetector
{

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

	private static final int MAKE_X_SETUP = 2046;
	private static final int MAKE_X_BUTTON_CLICK = 2050;
	private static final int MAKE_X_BUTTON_KEY = 2051;
	private static final int MAKE_X_BUTTON_TRIGGERED = 2052;

	private static final Product[] MULTI_MATERIAL_PRODUCTS = {
			// @formatter:off
            new Product(CRAFT_LEATHER, GREEN_DHIDE_BODY, new Ingredient(GREEN_DRAGON_LEATHER, 3)),
            new Product(CRAFT_LEATHER, GREEN_DHIDE_CHAPS, new Ingredient(GREEN_DRAGON_LEATHER, 2)),
            new Product(CRAFT_LEATHER, BLUE_DHIDE_BODY, new Ingredient(BLUE_DRAGON_LEATHER, 3)),
            new Product(CRAFT_LEATHER, BLUE_DHIDE_CHAPS, new Ingredient(BLUE_DRAGON_LEATHER, 2)),
            new Product(CRAFT_LEATHER, RED_DHIDE_BODY, new Ingredient(RED_DRAGON_LEATHER, 3)),
            new Product(CRAFT_LEATHER, RED_DHIDE_CHAPS, new Ingredient(RED_DRAGON_LEATHER, 2)),
            new Product(CRAFT_LEATHER, BLACK_DHIDE_BODY, new Ingredient(BLACK_DRAGON_LEATHER, 3)),
            new Product(CRAFT_LEATHER, BLACK_DHIDE_CHAPS, new Ingredient(BLACK_DRAGON_LEATHER, 2)),
            new Product(CRAFT_LEATHER, SNAKESKIN_BANDANA, new Ingredient(SNAKESKIN, 5)),
            new Product(CRAFT_LEATHER, SNAKESKIN_BODY, new Ingredient(SNAKESKIN, 15)),
            new Product(CRAFT_LEATHER, SNAKESKIN_BOOTS, new Ingredient(SNAKESKIN, 6)),
            new Product(CRAFT_LEATHER, SNAKESKIN_CHAPS, new Ingredient(SNAKESKIN, 12)),
            new Product(CRAFT_LEATHER, SNAKESKIN_VAMBRACES, new Ingredient(SNAKESKIN, 8)),
            new Product(CRAFT_LEATHER, XERICIAN_HAT, new Ingredient(XERICIAN_FABRIC, 3)),
            new Product(CRAFT_LEATHER, XERICIAN_TOP, new Ingredient(XERICIAN_FABRIC, 5)),
            new Product(CRAFT_LEATHER, XERICIAN_ROBE, new Ingredient(XERICIAN_FABRIC, 4)),
            new Product(CRAFT_LEATHER, XERICIAN_ROBE, new Ingredient(XERICIAN_FABRIC, 4)),
            new Product(CRAFT_LEATHER, LEATHER_GLOVES, new Ingredient(LEATHER)),
            new Product(CRAFT_LEATHER, LEATHER_BOOTS, new Ingredient(LEATHER)),
            new Product(CRAFT_LEATHER, LEATHER_COWL, new Ingredient(LEATHER)),
            new Product(CRAFT_LEATHER, LEATHER_VAMBRACES, new Ingredient(LEATHER)),
            new Product(CRAFT_LEATHER, LEATHER_BODY, new Ingredient(LEATHER)),
            new Product(CRAFT_LEATHER, LEATHER_CHAPS, new Ingredient(LEATHER)),
            new Product(CRAFT_LEATHER, COIF, new Ingredient(LEATHER)),
            new Product(CRAFT_HARD_LEATHER, HARDLEATHER_BODY, new Ingredient(HARD_LEATHER, 1)),
            new Product(CRAFT_BATTLESTAVES, AIR_BATTLESTAFF, new Ingredient(AIR_ORB), new Ingredient(BATTLESTAFF)),
            new Product(CRAFT_BATTLESTAVES, FIRE_BATTLESTAFF, new Ingredient(FIRE_ORB), new Ingredient(BATTLESTAFF)),
            new Product(CRAFT_BATTLESTAVES, EARTH_BATTLESTAFF, new Ingredient(EARTH_ORB), new Ingredient(BATTLESTAFF)),
            new Product(CRAFT_BATTLESTAVES, WATER_BATTLESTAFF, new Ingredient(WATER_ORB), new Ingredient(BATTLESTAFF)),
            new Product(SMELTING, BRONZE_BAR, new Ingredient(TIN_ORE), new Ingredient(COPPER_ORE)),
            new Product(SMELTING, IRON_BAR, new Ingredient(IRON_ORE)),
            new Product(SMELTING, SILVER_BAR, new Ingredient(SILVER_ORE)),
            new Product(SMELTING, STEEL_BAR, new Ingredient(IRON_ORE), new Ingredient(COAL, 2)),
            new Product(SMELTING, GOLD_BAR, new Ingredient(GOLD_ORE)),
            new Product(SMELTING, MITHRIL_BAR, new Ingredient(MITHRIL_ORE), new Ingredient(COAL, 4)),
            new Product(SMELTING, ADAMANTITE_BAR, new Ingredient(ADAMANTITE_ORE), new Ingredient(COAL, 6)),
            new Product(SMELTING, RUNITE_BAR, new Ingredient(RUNITE_ORE), new Ingredient(COAL, 8)),
            new Product(SMELTING_CANNONBALLS, CANNONBALL, new Ingredient[]{new Ingredient(STEEL_BAR)}, new Ingredient(DOUBLE_AMMO_MOULD)),
            new Product(CRAFT_CUT_GEMS, OPAL, true, new Ingredient(UNCUT_OPAL)),
            new Product(CRAFT_CUT_GEMS, JADE, true, new Ingredient(UNCUT_JADE)),
            new Product(CRAFT_CUT_GEMS, RED_TOPAZ, true, new Ingredient(UNCUT_RED_TOPAZ)),
            new Product(CRAFT_CUT_GEMS, SAPPHIRE, true, new Ingredient(UNCUT_SAPPHIRE)),
            new Product(CRAFT_CUT_GEMS, EMERALD, true, new Ingredient(UNCUT_EMERALD)),
            new Product(CRAFT_CUT_GEMS, RUBY, true, new Ingredient(UNCUT_RUBY)),
            new Product(CRAFT_CUT_GEMS, DIAMOND, true, new Ingredient(UNCUT_DIAMOND)),
            new Product(CRAFT_CUT_GEMS, DRAGONSTONE, true, new Ingredient(UNCUT_DRAGONSTONE)),
            new Product(CRAFT_CUT_GEMS, ONYX, true, new Ingredient(UNCUT_ONYX)),
            new Product(CRAFT_CUT_GEMS, ZENYTE, true, new Ingredient(UNCUT_ZENYTE)),
            new Product(CRAFT_STRING_JEWELLERY, STRUNG_RABBIT_FOOT, new Ingredient(RABBIT_FOOT), new Ingredient(BALL_OF_WOOL)),
            new Product(CRAFT_STRING_JEWELLERY, HOLY_SYMBOL, new Ingredient(UNSTRUNG_SYMBOL), new Ingredient(BALL_OF_WOOL)),
            new Product(CRAFT_STRING_JEWELLERY, UNHOLY_SYMBOL, new Ingredient(UNSTRUNG_EMBLEM), new Ingredient(BALL_OF_WOOL)),
            new Product(CRAFT_STRING_JEWELLERY, OPAL_AMULET, new Ingredient(OPAL_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new Product(CRAFT_STRING_JEWELLERY, JADE_AMULET, new Ingredient(JADE_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new Product(CRAFT_STRING_JEWELLERY, SAPPHIRE_AMULET, new Ingredient(SAPPHIRE_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new Product(CRAFT_STRING_JEWELLERY, TOPAZ_AMULET, new Ingredient(TOPAZ_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new Product(CRAFT_STRING_JEWELLERY, EMERALD_AMULET, new Ingredient(EMERALD_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new Product(CRAFT_STRING_JEWELLERY, RUBY_AMULET, new Ingredient(RUBY_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new Product(CRAFT_STRING_JEWELLERY, DIAMOND_AMULET, new Ingredient(DIAMOND_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new Product(CRAFT_STRING_JEWELLERY, DRAGONSTONE_AMULET, new Ingredient(DRAGONSTONE_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new Product(CRAFT_STRING_JEWELLERY, ONYX_AMULET, new Ingredient(ONYX_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new Product(CRAFT_STRING_JEWELLERY, ZENYTE_AMULET, new Ingredient(ZENYTE_AMULET_U), new Ingredient(BALL_OF_WOOL)),
            new Product(CRAFT_MOLTEN_GLASS, MOLTEN_GLASS, new Ingredient(BUCKET_OF_SAND), new Ingredient(SODA_ASH)),
            new Product(CRAFT_BLOW_GLASS, BEER_GLASS, new Ingredient(MOLTEN_GLASS)),
            new Product(CRAFT_BLOW_GLASS, EMPTY_CANDLE_LANTERN, new Ingredient(MOLTEN_GLASS)),
            new Product(CRAFT_BLOW_GLASS, EMPTY_OIL_LAMP, new Ingredient(MOLTEN_GLASS)),
            new Product(CRAFT_BLOW_GLASS, VIAL, new Ingredient(MOLTEN_GLASS)),
            new Product(CRAFT_BLOW_GLASS, EMPTY_FISHBOWL, new Ingredient(MOLTEN_GLASS)),
            new Product(CRAFT_BLOW_GLASS, UNPOWERED_ORB, new Ingredient(MOLTEN_GLASS)),
            new Product(CRAFT_BLOW_GLASS, LANTERN_LENS, new Ingredient(MOLTEN_GLASS)),
            new Product(CRAFT_BLOW_GLASS, EMPTY_LIGHT_ORB, new Ingredient(MOLTEN_GLASS)),
			new Product(CRAFT_LOOM, BASKET, new Ingredient(WILLOW_BRANCH, 6)),
			new Product(CRAFT_LOOM, EMPTY_SACK, new Ingredient(JUTE_FIBRE, 4)),
			new Product(CRAFT_LOOM_DRIFT_NET, DRIFT_NET, new Ingredient(JUTE_FIBRE, 2)),
			new Product(CRAFT_LOOM, STRIP_OF_CLOTH, new Ingredient(BALL_OF_WOOL, 4)),
			new Product(CRAFT_SHIELD, HARD_LEATHER_SHIELD, new Ingredient(GREEN_DRAGON_LEATHER, 2), new Ingredient (MAPLE_SHIELD), new Ingredient(BRONZE_NAILS, 15)),
			new Product(CRAFT_SHIELD, SNAKESKIN_SHIELD, new Ingredient(SNAKESKIN, 2), new Ingredient (WILLOW_SHIELD), new Ingredient(IRON_NAILS, 15)),
			new Product(CRAFT_SHIELD, GREEN_DHIDE_SHIELD, new Ingredient(GREEN_DRAGON_LEATHER, 2), new Ingredient (MAPLE_SHIELD), new Ingredient(STEEL_NAILS, 15)),
			new Product(CRAFT_SHIELD, BLUE_DHIDE_SHIELD, new Ingredient(BLUE_DRAGON_LEATHER, 2), new Ingredient (YEW_SHIELD), new Ingredient(MITHRIL_NAILS, 15)),
			new Product(CRAFT_SHIELD, RED_DHIDE_SHIELD, new Ingredient(RED_DRAGON_LEATHER, 2), new Ingredient (MAGIC_SHIELD), new Ingredient(ADAMANTITE_NAILS, 15)),
			new Product(CRAFT_SHIELD, BLACK_DHIDE_SHIELD, new Ingredient(BLACK_DRAGON_LEATHER, 2), new Ingredient (REDWOOD_SHIELD), new Ingredient(RUNE_NAILS, 15)),
            new Product(FLETCH_CUT_BOW, LONGBOW_U, new Ingredient(LOGS)),
            new Product(FLETCH_CUT_BOW, OAK_LONGBOW_U, new Ingredient(OAK_LOGS)),
            new Product(FLETCH_CUT_BOW, WILLOW_LONGBOW_U, new Ingredient(WILLOW_LOGS)),
            new Product(FLETCH_CUT_BOW, MAPLE_LONGBOW_U, new Ingredient(MAPLE_LOGS)),
            new Product(FLETCH_CUT_BOW, YEW_LONGBOW_U, new Ingredient(YEW_LOGS)),
            new Product(FLETCH_CUT_BOW, MAGIC_LONGBOW_U, new Ingredient(MAGIC_LOGS)),
            new Product(FLETCH_CUT_BOW, SHORTBOW_U, new Ingredient(LOGS)),
            new Product(FLETCH_CUT_BOW, OAK_SHORTBOW_U, new Ingredient(OAK_LOGS)),
            new Product(FLETCH_CUT_BOW, WILLOW_SHORTBOW_U, new Ingredient(WILLOW_LOGS)),
            new Product(FLETCH_CUT_BOW, MAPLE_SHORTBOW_U, new Ingredient(MAPLE_LOGS)),
            new Product(FLETCH_CUT_BOW, YEW_SHORTBOW_U, new Ingredient(YEW_LOGS)),
            new Product(FLETCH_CUT_BOW, MAGIC_SHORTBOW_U, new Ingredient(MAGIC_LOGS)),
            new Product(FLETCH_STRING_BOW, LONGBOW, new Ingredient(LONGBOW_U), new Ingredient(BOW_STRING)),
            new Product(FLETCH_STRING_BOW, OAK_LONGBOW, new Ingredient(OAK_LONGBOW_U), new Ingredient(BOW_STRING)),
            new Product(FLETCH_STRING_BOW, WILLOW_LONGBOW, new Ingredient(WILLOW_LONGBOW_U), new Ingredient(BOW_STRING)),
            new Product(FLETCH_STRING_BOW, MAPLE_LONGBOW, new Ingredient(MAPLE_LONGBOW_U), new Ingredient(BOW_STRING)),
            new Product(FLETCH_STRING_BOW, YEW_LONGBOW, new Ingredient(YEW_LONGBOW_U), new Ingredient(BOW_STRING)),
            new Product(FLETCH_STRING_BOW, MAGIC_LONGBOW, new Ingredient(MAGIC_LONGBOW_U), new Ingredient(BOW_STRING)),
            new Product(FLETCH_STRING_BOW, SHORTBOW, new Ingredient(SHORTBOW_U), new Ingredient(BOW_STRING)),
            new Product(FLETCH_STRING_BOW, OAK_SHORTBOW, new Ingredient(OAK_SHORTBOW_U), new Ingredient(BOW_STRING)),
            new Product(FLETCH_STRING_BOW, WILLOW_SHORTBOW, new Ingredient(WILLOW_SHORTBOW_U), new Ingredient(BOW_STRING)),
            new Product(FLETCH_STRING_BOW, MAPLE_SHORTBOW, new Ingredient(MAPLE_SHORTBOW_U), new Ingredient(BOW_STRING)),
            new Product(FLETCH_STRING_BOW, YEW_SHORTBOW, new Ingredient(YEW_SHORTBOW_U), new Ingredient(BOW_STRING)),
            new Product(FLETCH_STRING_BOW, MAGIC_SHORTBOW, new Ingredient(MAGIC_SHORTBOW_U), new Ingredient(BOW_STRING)),
			new Product(FLETCH_SHIELD, OAK_SHIELD, new Ingredient(OAK_LOGS, 2)),
			new Product(FLETCH_SHIELD, WILLOW_SHIELD, new Ingredient(WILLOW_LOGS, 2)),
			new Product(FLETCH_SHIELD, MAPLE_SHIELD, new Ingredient(MAPLE_LOGS, 2)),
			new Product(FLETCH_SHIELD, YEW_SHIELD, new Ingredient(YEW_LOGS, 2)),
			new Product(FLETCH_SHIELD, MAGIC_SHIELD, new Ingredient(MAGIC_LOGS, 2)),
			new Product(FLETCH_SHIELD, REDWOOD_SHIELD, new Ingredient(REDWOOD_LOGS, 2)),
			new Product(FLETCH_CUT_CROSSBOW, WOODEN_STOCK, new Ingredient(LOGS)),
			new Product(FLETCH_CUT_CROSSBOW, OAK_STOCK, new Ingredient(OAK_LOGS)),
			new Product(FLETCH_CUT_CROSSBOW, WILLOW_STOCK, new Ingredient(WILLOW_LOGS)),
			new Product(FLETCH_CUT_CROSSBOW, TEAK_STOCK, new Ingredient(TEAK_LOGS)),
			new Product(FLETCH_CUT_CROSSBOW, MAPLE_STOCK, new Ingredient(MAPLE_LOGS)),
			new Product(FLETCH_CUT_CROSSBOW, MAHOGANY_STOCK, new Ingredient(MAHOGANY_LOGS)),
			new Product(FLETCH_CUT_CROSSBOW, YEW_STOCK, new Ingredient(YEW_LOGS)),
			new Product(FLETCH_CUT_CROSSBOW, MAGIC_STOCK, new Ingredient(MAGIC_LOGS)),
			new Product(FLETCH_ATTACH_CROSSBOW, BRONZE_CROSSBOW_U, new Ingredient(WOODEN_STOCK), new Ingredient(BRONZE_LIMBS)),
			new Product(FLETCH_ATTACH_CROSSBOW, BLURITE_CROSSBOW_U, new Ingredient(OAK_STOCK), new Ingredient(BLURITE_LIMBS)),
			new Product(FLETCH_ATTACH_CROSSBOW, IRON_CROSSBOW_U, new Ingredient(WILLOW_STOCK), new Ingredient(IRON_LIMBS)),
			new Product(FLETCH_ATTACH_CROSSBOW, STEEL_CROSSBOW_U, new Ingredient(TEAK_STOCK), new Ingredient(STEEL_LIMBS)),
			new Product(FLETCH_ATTACH_CROSSBOW, MITHRIL_CROSSBOW_U, new Ingredient(MAPLE_STOCK), new Ingredient(MITHRIL_LIMBS)),
			new Product(FLETCH_ATTACH_CROSSBOW, ADAMANT_CROSSBOW_U, new Ingredient(MAHOGANY_STOCK), new Ingredient(ADAMANTITE_LIMBS)),
			new Product(FLETCH_ATTACH_CROSSBOW, RUNITE_CROSSBOW_U, new Ingredient(YEW_STOCK), new Ingredient(RUNITE_LIMBS)),
			new Product(FLETCH_ATTACH_CROSSBOW, DRAGON_CROSSBOW_U, new Ingredient(MAGIC_STOCK), new Ingredient(DRAGON_LIMBS)),
			new Product(FLETCH_STRING_CROSSBOW, BRONZE_CROSSBOW, new Ingredient(BRONZE_CROSSBOW_U), new Ingredient(CROSSBOW_STRING)),
			new Product(FLETCH_STRING_CROSSBOW, BLURITE_CROSSBOW, new Ingredient(BLURITE_CROSSBOW_U), new Ingredient(CROSSBOW_STRING)),
			new Product(FLETCH_STRING_CROSSBOW, IRON_CROSSBOW, new Ingredient(IRON_CROSSBOW_U), new Ingredient(CROSSBOW_STRING)),
			new Product(FLETCH_STRING_CROSSBOW, STEEL_CROSSBOW, new Ingredient(STEEL_CROSSBOW_U), new Ingredient(CROSSBOW_STRING)),
			new Product(FLETCH_STRING_CROSSBOW, MITHRIL_CROSSBOW, new Ingredient(MITHRIL_CROSSBOW_U), new Ingredient(CROSSBOW_STRING)),
			new Product(FLETCH_STRING_CROSSBOW, ADAMANT_CROSSBOW,new Ingredient(ADAMANT_CROSSBOW_U), new Ingredient(CROSSBOW_STRING)),
			new Product(FLETCH_STRING_CROSSBOW, RUNE_CROSSBOW, new Ingredient(RUNITE_CROSSBOW_U), new Ingredient(CROSSBOW_STRING)),
			new Product(FLETCH_STRING_CROSSBOW, DRAGON_CROSSBOW, new Ingredient(DRAGON_CROSSBOW_U), new Ingredient(CROSSBOW_STRING)),
            // @formatter:on
	};

	private final int[] widgetProductIds = new int[WIDGET_MAKE_SLOT_COUNT];

	@Inject private Client client;

	@Inject private ActionProgressPlugin plugin;

	@Inject private InventoryManager inventoryManager;

	@Inject private ActionUtils actionUtils;

	private int selectedIndex = -1;

	private String question;

	@Subscribe
	public void onVarbitChanged(VarbitChanged evt)
	{
		if (evt.getIndex() == VAR_SELECTED_INDEX) {
			this.selectedIndex = this.client.getVarpValue(evt.getIndex());
		}
	}

	@Subscribe
	public void onScriptPreFired(ScriptPreFired evt)
	{
		if (evt.getScriptId() == MAKE_X_BUTTON_KEY ||
			evt.getScriptId() == MAKE_X_BUTTON_CLICK) {
			ScriptEvent se = evt.getScriptEvent();
			Widget source = se == null ? null : se.getSource();
			if (source != null) {
				this.selectedIndex = (source.getId() - WIDGET_ID_CHATBOX_FIRST_MAKE_BUTTON);
			}
		}
	}

	@Subscribe
	public void onScriptPostFired(ScriptPostFired evt)
	{
		if (evt.getScriptId() == MAKE_X_SETUP) {
			log.debug("[proc_itembutton_draw] updating products");
			this.updateProducts();
		} else if (evt.getScriptId() == MAKE_X_BUTTON_TRIGGERED) {
			this.onQuestionAnswered();
		}
	}

	protected void unhandled(int itemId)
	{
		log.warn("[*] Unhandled chatbox action");
		log.warn(" |-> Question: {}", this.question);
		log.warn(" |-> Item ID: {}", itemId);
	}

	@Override
	public void setup()
	{
		/*
		 * Cooking
		 */
		this.registerAction(COOKING_TOP_PIZZA, INCOMPLETE_PIZZA, UNCOOKED_PIZZA, PINEAPPLE_PIZZA, ANCHOVY_PIZZA,
				MEAT_PIZZA
		);
		this.registerAction(COOKING_MIX_GRAPES, UNFERMENTED_WINE, UNFERMENTED_WINE_1996, ZAMORAKS_UNFERMENTED_WINE);
		this.registerAction(COOKING_MIX_DOUGH, BREAD_DOUGH, PASTRY_DOUGH, PITTA_DOUGH, PIZZA_BASE);
		/*
		 * Fletching
		 */
		this.registerAction(FLETCH_ATTACH_TIPS, Fletching.UNENCHANTED_BOLTS_AND_ARROWS);
		this.registerAction(FLETCH_ATTACH_FEATHER, HEADLESS_ARROW, FLIGHTED_OGRE_ARROW);
		this.registerAction(FLETCH_CUT_ARROW_SHAFT, ARROW_SHAFT, BRUMA_KINDLING, OGRE_ARROW_SHAFT);
		this.registerAction(FLETCH_CUT_TIPS, Fletching.BOLT_TIPS);
		/*
		 * Herblore
		 */
		this.registerAction(HERB_MIX_TAR, GUAM_TAR, MARRENTILL_TAR, TARROMIN_TAR, HARRALANDER_TAR);
		for (Recipe recipe : Herblore.UNFINISHED_POTIONS) {
			this.registerAction(HERB_MIX_UNFINISHED, recipe.getProductId());
		}
		for (Recipe recipe : Herblore.POTIONS) {
			this.registerAction(HERB_MIX_POTIONS, recipe.getIsSelectingIngredientAsProduct() ? recipe.getRequirements()[0].getItemId() : recipe.getProductId());
		}
		/*
		 * Magic
		 */
		this.registerAction(MAGIC_ENCHANT_BOLTS, Fletching.ENCHANTED_BOLTS);
	}

	private void onQuestionAnswered()
	{
		int currentProductId = this.widgetProductIds[this.selectedIndex];
		int amount = this.getActionCount(currentProductId);
		String question = this.question == null ? "?" : this.question;
		switch (question) {
			case "How many would you like to cook?":
			case "What would you like to cook?":
				this.actionManager.setAction(COOKING, amount, currentProductId);
				break;
			case "How would you like to cut the pineapple?":
				if (currentProductId == PINEAPPLE_RING) {
					amount = Math.min(amount, this.actionUtils.getActionsUntilFull(4, 1));
				}
				this.actionManager.setAction(COOKING_CUT_FRUIT, amount, currentProductId);
				break;
			case "How many would you like to charge?":
				Magic.ChargeOrbSpell spell = Magic.ChargeOrbSpell.byProduct(currentProductId);
				Objects.requireNonNull(spell, "No charge orb spell found for product: " + currentProductId);
				this.actionManager.setAction(
						Action.MAGIC_CHARGE_ORB,
						Math.min(amount, spell.getSpell().getAvailableCasts(this.client)),
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
				Product recipe = Recipe.forProduct(MULTI_MATERIAL_PRODUCTS, currentProductId);
				if (recipe != null) {
					amount = Math.min(amount, recipe.getMakeProductCount(this.inventoryManager));
					this.actionManager.setAction(recipe.getAction(), amount, recipe.getIsSelectingIngredientAsProduct() ? recipe.getProductId() : currentProductId);
				} else {
					this.setActionByItemId(currentProductId, amount);
				}
				break;
		}
	}

	private void updateProducts()
	{
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
		log.debug("updated products: {}", Arrays.toString(this.widgetProductIds));
	}

	private int getActionCount(int productId)
	{
		int n = this.client.getVarcIntValue(VAR_MAKE_AMOUNT);
		for (Smithing.Bar bar : Smithing.Bar.values()) {
			if (productId == bar.getItemId()) {
				return Math.min(n, bar.countAvailableOres(this.client));
			}
		}
		for (Cooking.Cookable entry : Cooking.Cookable.values()) {
			IDs raw = entry.getRaw(), cooked = entry.getCooked();
			if (cooked.contains(productId)) {
				int rawFish = this.inventoryManager.getItemCount(raw::contains);
				return Math.min(n, rawFish);
			}
		}
		return n;
	}

}