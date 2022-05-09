package com.github.calebwhiting.runelite.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.Subscribe;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * Calculates the average tick time, in an ideal world this would always be 600ms,
 * however in practice it fluctuates depending upon various factors including client
 * and server load.
 */
@Singleton
@Slf4j
public class TickManager
{

	/**
	 * The perfect tick time
	 */
	public static final long PERFECT_TICK_TIME = 600L;

	/**
	 * Number of ticks to remember
	 */
	private static final int DEFAULT_BUFFER_SIZE = 1000;

	private final int bufferSize;
	private final Queue<Long> tickTimes;
	@Inject private Client client;
	@Getter private int lastTick;
	@Getter private long averageTickTime;
	@Getter private long lastTickTime;
	@Getter private boolean paused = true;
	private long tickTimesTotal;

	private TickManager(int bufferSize)
	{
		if (bufferSize <= 1) {
			throw new IllegalArgumentException("bufferSize must be more than 1");
		}
		this.bufferSize = bufferSize;
		this.averageTickTime = PERFECT_TICK_TIME;
		this.tickTimes = new LinkedList<>();
		for (int i = 0; i < bufferSize; i++) {
			this.tickTimes.add(PERFECT_TICK_TIME);
		}
		this.tickTimesTotal = (this.tickTimes.size() * PERFECT_TICK_TIME);
		this.lastTickTime = -1;
	}

	@Inject
	public TickManager()
	{
		this(DEFAULT_BUFFER_SIZE);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged evt)
	{
		this.paused = evt.getGameState() != GameState.LOGGED_IN;
		if (this.paused || this.lastTickTime != -1) {
			return;
		}
		this.lastTickTime = System.currentTimeMillis();
	}

	@Subscribe
	public void onGameTick(GameTick evt)
	{
		long now = System.currentTimeMillis();
		if (!this.paused) {
			long tickTime = now - this.lastTickTime;
			/* we should always have {bufferSize} elements in the queue */
			if (this.tickTimes.size() != this.bufferSize) {
				throw new IllegalStateException("Somehow the tick time queue has become corrupted!");
			}
			long toRemove = Objects.requireNonNull(this.tickTimes.poll());
			this.tickTimesTotal = this.tickTimesTotal - toRemove + tickTime;
			this.averageTickTime = this.tickTimesTotal / this.bufferSize;
			this.tickTimes.offer(tickTime);
			this.lastTick = this.client.getTickCount();
		}
		this.lastTickTime = now;
	}

}