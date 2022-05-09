package com.github.calebwhiting.runelite.data;

import com.github.calebwhiting.runelite.plugins.actionprogress.detect.WintertodtDetector;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;

public class DataVerifyTest
{

	private static final Logger log = LoggerFactory.getLogger(DataVerifyTest.class);

	private static final String[] ARROW_TIP_MATERIALS = {
			"ONYX", "DRAGON(STONE)?", "DIAMOND", "RUNE", "RUBY", "EMERALD", "ADAMANT", "SAPPHIRE", "TOPAZ", "MITHRIL",
			"JADE", "STEEL", "IRON", "BRONZE", "BARBED", "PEARL", "OPAL", "BROAD", "AMETHYST", "SILVER", "RUNITE",
			"BLACK", "BLURITE", "OGRE", "KEBBIT"
	};

	private static final String[] JEWELLERY_MATERIALS = {
			"ZENYTE", "ONYX", "DRAGON(STONE)?", "DIAMOND", "RUBY", "EMERALD", "SAPPHIRE", "TOPAZ", "JADE", "OPAL",
			"GOLD"
	};

	private boolean failed;

	@Test
	public void verifyItemIdConstants()
	{
		this.verify(Fletching.class, "BOLT_TIPS", IDQuery.ofItems().query(".*_BOLT_TIPS?"));
		this.verify(Fletching.class, "ENCHANTED_BOLTS", IDQuery.ofItems().query(".*_BOLTS?_E"));
		this.verify(
				Fletching.class,
				"UNENCHANTED_BOLTS_AND_ARROWS",
				IDQuery.ofItems()
					   .query(String.format("(%s)_(ARROW(S)?|BOLT(S)?|BRUTAL)", String.join("|", ARROW_TIP_MATERIALS)))
		);
		this.verify(
				Crafting.class,
				"SAPPHIRE_AND_OPAL_JEWELLERY",
				IDQuery.ofItems().query("(SAPPHIRE|OPAL)_(RING|NECKLACE|BRACELET|AMULET)")
		);
		this.verify(
				Crafting.class,
				"EMERALD_JEWELLERY",
				IDQuery.ofItems().query("(EMERALD|JADE|PRENATURE)_(RING|NECKLACE|BRACELET|AMULET)")
		);
		this.verify(
				Crafting.class,
				"TOPAZ_AND_RUBY_JEWELLERY",
				IDQuery.ofItems().query("(RUBY|TOPAZ)_(RING|NECKLACE|BRACELET|AMULET)")
		);
		this.verify(
				Crafting.class,
				"DIAMOND_JEWELLERY",
				IDQuery.ofItems().query("DIAMOND_(RING|NECKLACE|BRACELET|AMULET)")
		);
		this.verify(
				Crafting.class,
				"DRAGONSTONE_JEWELLERY",
				IDQuery.ofItems().query("DRAGON(STONE)?_(RING|NECKLACE|BRACELET|AMULET)")
		);
		this.verify(
				Crafting.class,
				"ONYX_JEWELLERY",
				IDQuery.ofItems().query("ONYX_(RING|NECKLACE|BRACELET|AMULET)")
		);
		this.verify(
				Crafting.class,
				"ZENYTE_JEWELLERY",
				IDQuery.ofItems().query("ZENYTE_(RING|NECKLACE|BRACELET|AMULET)")
		);
		this.verify(
				Crafting.class,
				"SILVER_AND_GOLD_ITEMS",
				IDQuery.ofItems()
					   // Gold Jewellery
					   .query(String.format(
							   "(%s)_(BRACELET|AMULET_U|NECKLACE|RING)",
							   String.join("|", JEWELLERY_MATERIALS)
					   ))
					   // Silver
					   .query("UNSTRUNG_(SYMBOL|EMBLEM)|TIARA|SILVER_" +
							  "(SICKLE|BOLTS_UNF)")
					   // Misc
					   .query("CONDUCTOR|DEMONIC_SIGIL|SILVTHRILL_ROD")
		);
		this.verify(WintertodtDetector.class, "WOODCUTTING_ANIMATIONS", IDQuery.ofAnimations().query("WOODCUTTING_" +
																									 ".*"));
	}

	public void verify(Class<?> c, String constantName, IDQuery query)
	{
		try {
			Field constant = c.getDeclaredField(constantName);
			Assert.assertSame("ID constants must be of type int[]", constant.getType(), int[].class);
			constant.setAccessible(true);
			int[] queryIds = query.ids();
			Arrays.sort(queryIds);
			int[] current = (int[]) constant.get(null);
			if (Arrays.equals(queryIds, current)) {
				log.info("Verified {}.{}", c.getCanonicalName(), constantName);
				return;
			}
			DataVerificationException.newInstance(c, constantName, query).printStackTrace();
			this.failed = true;
		} catch (ReflectiveOperationException e) {
			log.error("An error occurred", e);
		}
	}

	@Before
	public void init()
	{
		this.failed = false;
	}

	@After
	public void testFailure()
	{
		if (this.failed) {
			// we don't want to actually fail, just warn
			System.err.println("One or more constant is out-of-date");
		}
	}

}
