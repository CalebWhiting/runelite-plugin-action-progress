package com.github.calebwhiting.runelite.dev;

import com.google.inject.Singleton;
import net.runelite.api.Skill;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("actiondiagnostics")
public interface ActionDiagnosticsConfig extends Config  {

    @ConfigItem(
            name = "History capacity",
            description = "The amount of events to remember",
            keyName = "history-capacity",
            position = 1
    )
    default int capacity() {
        return 50000;
    }

    @ConfigItem(
            name = "Trigger",
            description = "What will be the indicator that an action has happened",
            keyName = "action-trigger",
            position = 1
    )
    default Trigger trigger() {
        return Trigger.EXPERIENCE;
    }

    @ConfigItem(
            name = "Action ticks",
            description = "How many ticks the action takes",
            keyName = "action-ticks",
            position = 2
    )
    default int ticks() {
        return 3;
    }

    @ConfigItem(
            name = "Unique events",
            description = "Only show unique events",
            keyName = "unique-events",
            position = 3
    )
    default boolean onlyUniqueEvents() {
        return false;
    }

    @ConfigSection(
            name = "Experience Trigger Input",
            description = "Which experience drops should be used as a trigger",
            position = 4
    )
    String experienceTriggerInput = "Experience Trigger Input";

    @ConfigSection(
            name = "Inventory Trigger Input",
            description = "Which item should be used as a trigger",
            position = 5
    )
    String inventoryTriggerInput = "Inventory Trigger Input";

    @ConfigSection(
            name = "Inventory Stack Trigger Input",
            description = "Which item should be used as a trigger",
            position = 6
    )
    String inventoryStackTriggerInput = "Inventory Stack Trigger Input";

    @ConfigItem(
            name = "Skill",
            keyName = "experience.skill",
            description = "Which skill will be used as a trigger",
            section = experienceTriggerInput
    )
    default Skill experienceTriggerSkill() {
        return Skill.OVERALL;
    }

    @ConfigItem(
            name = "Experience Size",
            keyName = "experience.amount",
            description = "The exact size of the experience drop to use as a trigger",
            section = experienceTriggerInput
    )
    default int experienceTriggerSize() {
        return 0;
    }

    @ConfigItem(
            name = "Item ID",
            keyName = "inventory.item-id",
            description = "The item ID to use as a trigger",
            section = inventoryTriggerInput
    )
    default int inventoryTriggerItemId() {
        return 0;
    }


    @ConfigItem(
            name = "Item ID",
            keyName = "inventory-stack.item-id",
            description = "The item ID to use as a trigger",
            section = inventoryStackTriggerInput
    )
    default int inventoryStackTriggerItemId() {
        return 0;
    }


}
