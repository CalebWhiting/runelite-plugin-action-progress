package com.github.calebwhiting.runelite;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionProgressPlugin;

public class ActionProgressPluginTest
{
	public static void main(String[] args) throws Exception
	{
		//noinspection unchecked
		ExternalPluginManager.loadBuiltin(ActionProgressPlugin.class);
		RuneLite.main(args);
	}
}