package com.github.calebwhiting.runelite.data;

import static net.runelite.api.ItemID.*;

public interface Cooking {

    ItemGroup GROUP_COOKED_FISH = new ItemGroup(
            SHRIMPS,
            SARDINE,
            SALMON,
            TROUT,
            COD,
            HERRING,
            PIKE,
            MACKEREL,
            TUNA,
            BASS,
            SWORDFISH,
            LOBSTER,
            SHARK,
            LAVA_EEL,
            MANTA_RAY,
            MONKFISH,
            DARK_CRAB,
            SACRED_EEL,
            ANGLERFISH,
            COOKED_KARAMBWAN,
            POISON_KARAMBWAN,
            COOKED_SLIMY_EEL,
            RAINBOW_FISH,
            HARPOONFISH
    );

    ItemGroup GROUP_UNCOOKED_FISH = new ItemGroup(
            RAW_SHRIMPS,
            RAW_SARDINE,
            RAW_SALMON,
            RAW_TROUT,
            RAW_COD,
            RAW_HERRING,
            RAW_PIKE,
            RAW_MACKEREL,
            RAW_TUNA,
            RAW_BASS,
            RAW_SWORDFISH,
            RAW_LOBSTER,
            RAW_SHARK,
            RAW_LAVA_EEL,
            RAW_MANTA_RAY,
            RAW_MONKFISH,
            RAW_DARK_CRAB,
            RAW_ANGLERFISH,
            RAW_KARAMBWAN,
            RAW_SLIMY_EEL,
            RAW_RAINBOW_FISH,
            RAW_HARPOONFISH
    );

    ItemGroup GROUP_COOKED_PIES = new ItemGroup(
            APPLE_PIE,
            REDBERRY_PIE,
            MEAT_PIE,
            BOTANICAL_PIE,
            MUSHROOM_PIE,
            DRAGONFRUIT_PIE,
            ADMIRAL_PIE,
            FISH_PIE,
            GARDEN_PIE,
            SUMMER_PIE,
            WILD_PIE
    );

    ItemGroup GROUP_UNCOOKED_PIES = new ItemGroup(
            UNCOOKED_APPLE_PIE,
            UNCOOKED_BERRY_PIE,
            UNCOOKED_MEAT_PIE,
            UNCOOKED_BOTANICAL_PIE,
            UNCOOKED_MUSHROOM_PIE,
            UNCOOKED_DRAGONFRUIT_PIE,
            RAW_ADMIRAL_PIE,
            RAW_FISH_PIE,
            RAW_GARDEN_PIE,
            RAW_SUMMER_PIE,
            RAW_WILD_PIE
    );

    ItemGroup GROUP_COOKED_MEAT = new ItemGroup(
            UGTHANKI_MEAT,
            THIN_SNAIL_MEAT,
            LEAN_SNAIL_MEAT,
            FAT_SNAIL_MEAT,
            COOKED_MYSTERY_MEAT,
            COOKED_CHICKEN,
            COOKED_MEAT,
            COOKED_OOMLIE_WRAP,
            COOKED_CHOMPY,
            COOKED_RABBIT,
            COOKED_CRAB_MEAT,
            COOKED_JUBBLY,
            ROAST_BIRD_MEAT,
            ROAST_BEAST_MEAT,
            ROAST_RABBIT,
            KEBBIT,
            SPIDER_ON_STICK_6297
    );

    ItemGroup GROUP_UNCOOKED_MEAT = new ItemGroup(
            RAW_UGTHANKI_MEAT,
            THIN_SNAIL,
            LEAN_SNAIL,
            FAT_SNAIL,
            RAW_MYSTERY_MEAT,
            RAW_CHICKEN,
            RAW_BEEF,
            RAW_BEAR_MEAT,
            RAW_BEAST_MEAT,
            RAW_BIRD_MEAT,
            RAW_RAT_MEAT,
            RAW_YAK_MEAT,
            RAW_OOMLIE,
            RAW_CHOMPY,
            RAW_RABBIT,
            CRAB_MEAT,
            RAW_JUBBLY,
            RAW_RABBIT,
            SPIDER_ON_STICK
    );

    ItemGroup GROUP_COOKED_VEGETABLES = new ItemGroup(
            BAKED_POTATO,
            COOKED_SWEETCORN
    );

    ItemGroup GROUP_UNCOOKED_VEGETABLES = new ItemGroup(
            POTATO,
            SWEETCORN
    );

    ItemGroup GROUP_ALL_COOKED = new ItemGroup()
            .and(GROUP_COOKED_FISH)
            .and(GROUP_COOKED_MEAT)
            .and(GROUP_COOKED_PIES)
            .and(GROUP_COOKED_VEGETABLES);

    ItemGroup GROUP_ALL_UNCOOKED = new ItemGroup()
            .and(GROUP_UNCOOKED_FISH)
            .and(GROUP_UNCOOKED_MEAT)
            .and(GROUP_UNCOOKED_PIES)
            .and(GROUP_UNCOOKED_VEGETABLES);


}
