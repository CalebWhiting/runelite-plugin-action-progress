package com.github.calebwhiting.runelite.api.event;

import lombok.Data;

/**
 * An event fired when an action is interrupted
 */
@Data
public class Interrupt
{

	private final Object source;

	private boolean consumed = false;

}
