package com.smoothscattering;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class SmoothScatteringPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(SmoothScatteringPlugin.class);
		RuneLite.main(args);
	}
}