/*
 * Copyright (c) 2021, Cyborger1
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.smoothscattering;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.events.AnimationChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Smooth Scattering",
	description = "Do a smooth dance when scattering ashes",
	tags = {"Smooth", "Dance", "Scatter", "Ash"}
)
public class SmoothScatteringPlugin extends Plugin
{
	private static int ASH_SCATTER_ANIMATION_ID = 2295;
	private static int SMOOTH_DANCE_ANIMATION_ID = 7533;
	private static int DUST_GRAPHIC_ID = 567;

	@Inject
	private Client client;

	@Inject
	private SmoothScatteringConfig config;

	@Provides
	SmoothScatteringConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(SmoothScatteringConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
	}

	@Override
	protected void shutDown() throws Exception
	{
	}

	@Subscribe
	public void onAnimationChanged(AnimationChanged event)
	{
		if (!(event.getActor() instanceof Player))
		{
			return;
		}

		Player target = (Player)event.getActor();

		if (target.getAnimation() != ASH_SCATTER_ANIMATION_ID)
		{
			return;
		}

		if (config.affectOtherPlayers() || target == client.getLocalPlayer())
		{
			target.setAnimation(SMOOTH_DANCE_ANIMATION_ID);
			target.setAnimationFrame(0);
			if (config.addDustEffect())
			{
				target.setGraphic(DUST_GRAPHIC_ID);
				target.setAnimationFrame(0);
			}
		}
	}
}
