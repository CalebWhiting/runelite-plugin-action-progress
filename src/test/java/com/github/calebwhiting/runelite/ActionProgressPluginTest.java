package com.github.calebwhiting.runelite;

import com.github.calebwhiting.runelite.dev.ActionDiagnosticsPlugin;
import com.github.calebwhiting.runelite.plugins.actionprogress.ActionProgressPlugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

@SuppressWarnings("unchecked")
public class ActionProgressPluginTest
{

	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ActionProgressPlugin.class, ActionDiagnosticsPlugin.class);
		RuneLite.main(args);
	}

}