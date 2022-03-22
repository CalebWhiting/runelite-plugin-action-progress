package com.github.calebwhiting.runelite.plugins.actionprogress;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("actionprogress")
public interface ActionProgressConfig extends Config {

    @ConfigSection(
            name="Specific Actions",
            description = "Enable/Disable monitoring specific actions. " +
                    "These are features that may be an annoyance/inaccurate depending on how you play.",
            position = 2)
    String specificActionsSection = "Specific Actions";

    @ConfigItem(
            name = "Notify when finished",
            keyName = "notifyWhenFinished",
            description = "Enable/Disable notifications when actions are completed or interrupted",
            position = 0)
    default boolean notifyWhenFinished() {
        return true;
    }

    @ConfigItem(
            name = "Show single actions",
            keyName = "showSingleActions",
            description = "Show/Hide single actions",
            position = 1)
    default boolean ignoreSingleActions() {
        return true;
    }

    @ConfigItem(
            name = "Enchant jewellery spells",
            keyName = "monitorEnchantJewellerySpells",
            description = "Enable/Disable monitoring jewellery enchantment spells",
            position = 3,
            section = specificActionsSection
    )
    default boolean monitorEnchantJewellerySpells() {
        return true;
    }

    @ConfigItem(
            name = "Enchant bolt spells",
            keyName = "monitorEnchantBoltSpells",
            description = "Enable/Disable monitoring bolt enchantment spells",
            position = 4,
            section = specificActionsSection
    )
    default boolean monitorEnchantBoltSpells() {
        return true;
    }

}
