package com.fullcompass;

import com.google.inject.Provides;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

@Slf4j
@PluginDescriptor(
	name = "Full Compass"
)
public class FullCompassPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private FullCompassConfig config;

	@Inject
	private SpriteManager spriteManager;

	@Override
	protected void startUp() throws Exception
	{
		File spriteFile = new File("src/main/images/compass.png");
		log.info(spriteFile.getAbsolutePath());
		BufferedImage compassImage = ImageIO.read(spriteFile);

		if (compassImage != null)
		{
			SpritePixels compass = ImageUtil.getImageSpritePixels(compassImage, client);
			client.setCompass(compass);
		}
		log.info("Example started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		BufferedImage compassImage = spriteManager.getSprite(SpriteID.COMPASS_TEXTURE, 0);
		if (compassImage != null)
		{
			SpritePixels compass = ImageUtil.getImageSpritePixels(compassImage, client);
			client.setCompass(compass);
		}
		log.info("Example stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{

	}

	@Provides
	FullCompassConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(FullCompassConfig.class);
	}
}
