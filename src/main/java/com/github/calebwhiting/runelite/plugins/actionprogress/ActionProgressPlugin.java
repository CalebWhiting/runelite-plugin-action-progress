package com.github.calebwhiting.runelite.plugins.actionprogress;

import com.github.calebwhiting.runelite.api.InterruptManager;
import com.github.calebwhiting.runelite.api.InventoryManager;
import com.github.calebwhiting.runelite.api.LocalPlayerManager;
import com.github.calebwhiting.runelite.api.TickManager;
import com.github.calebwhiting.runelite.plugins.actionprogress.detect.*;
import com.github.calebwhiting.runelite.plugins.actionprogress.event.ActionStartedEvent;
import com.github.calebwhiting.runelite.plugins.actionprogress.event.ActionStoppedEvent;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

@Slf4j
@Singleton
@Getter
@PluginDescriptor(name = "Action Progress")
public class ActionProgressPlugin extends Plugin
{

	private static final Class<?>[] DETECTORS = new Class[]{
			ChatboxDetector.class, UseItemOnItemDetector.class, EnchantSpellDetector.class, LunarSpellDetector.class,
			FurnaceCastingDetector.class, LecternDetector.class, SandpitDetector.class, SmithingDetector.class,
			TemporossDetector.class, TemporossRewardPoolDetector.class, ItemClickDetector.class,
			// WintertodtDetector.class
	};

	private final Collection<Object> eventHandlers = new LinkedList<>();

	@Inject private ActionProgressConfig config;

	@Inject private ActionProgressOverlay overlay;

	@Inject private ActionManager actionManager;

	@Inject private OverlayManager overlayManager;

	@Inject private ItemManager itemManager;

	@Inject private SpriteManager spriteManager;

	@Inject private Notifier notifier;

	@Inject private EventBus eventBus;

	@Inject private ClientThread clientThread;

	@Inject private Client client;

	@Getter private String currentActionName;

	@Getter private Image currentProductImage;

	@Override
	protected void startUp() throws Exception
	{
		log.debug("starting up");
		super.startUp();
		this.overlay.setResizable(true);
		this.currentProductImage = this.itemManager.getImage(1050); //Santa hat
		this.overlayManager.add(this.overlay);
		Collections.addAll(this.eventHandlers, this.injector.getInstance(TickManager.class),
				this.injector.getInstance(InterruptManager.class),
				this.injector.getInstance(InventoryManager.class),
				this.injector.getInstance(ActionManager.class),
				this.injector.getInstance(LocalPlayerManager.class)
		);
		for (Class<?> detector : DETECTORS) {
			log.debug("initializing detector {}", detector);
			ActionDetector instance = (ActionDetector) this.injector.getInstance(detector);
			this.eventHandlers.add(instance);
			this.clientThread.invoke(instance::setup);
		}
		this.eventHandlers.forEach(this.eventBus::register);
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.debug("shutting down");
		super.shutDown();
		this.overlayManager.remove(this.overlay);
		this.eventHandlers.forEach(this.eventBus::unregister);
		for (Class<?> detector : DETECTORS) {
			log.debug("shutDown detector {}", detector);
			ActionDetector instance = (ActionDetector) this.injector.getInstance(detector);
			this.eventHandlers.add(instance);
			this.clientThread.invoke(instance::shutDown);
		}
		this.eventHandlers.clear();
	}

	@Subscribe
	public void onActionStartedEvent(ActionStartedEvent evt)
	{
		this.currentActionName = evt.getAction().getDescription();
		if (this.config.showProductIcons() && evt.getProductId() != -1) {
			log.debug("fetching item sprite");
			this.currentProductImage = this.itemManager.getImage(evt.getProductId());
		} else {
			log.debug("fetching action sprite");
			this.currentProductImage = evt.getAction()
										  .getIconSource()
										  .toBufferedImage(this.itemManager, this.spriteManager);
		}
	}

	@Subscribe
	public void onActionStoppedEvent(ActionStoppedEvent evt)
	{
		if (this.client.getTickCount() <= evt.getStartTick() + 1) {
			log.debug("ignoring fast failure");
			return;
		}
		if (this.config.notifyWhenFinished() && !evt.isInterrupted()) {
			this.notifier.notify("All of your items have been processed!");
		}
	}

	@Provides
	ActionProgressConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ActionProgressConfig.class);
	}

}