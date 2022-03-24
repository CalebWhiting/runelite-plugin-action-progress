package com.github.calebwhiting.runelite.api;

import com.google.inject.Inject;
import lombok.Getter;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.Subscribe;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * Calculates the average tick time, in an ideal world this would always be 400ms,
 * however in practice it fluctuates depending upon various factors including client
 * and server load.
 */
public class TickManager {

    /**
     * The perfect tick time
     */
    public static final long PERFECT_TICK_TIME = 600;

    /**
     * Number of ticks in 10 seconds assuming perfect performance
     */
    private static final int DEFAULT_BUFFER_SIZE = 16;

    @Getter
    private long averageTickTime;

    @Getter
    private long lastTickTime;

    private final Queue<Long> tickTimes;
    private long totalTickTime;

    @Getter
    private boolean paused = true;
    private final int bufferSize;

    public TickManager(int bufferSize) {
        if (bufferSize <= 1) {
            throw new IllegalArgumentException("bufferSize must be more than 1");
        }
        this.bufferSize = bufferSize;
        this.averageTickTime = PERFECT_TICK_TIME;
        this.tickTimes = new LinkedList<>();
        for (int i = 0; i < bufferSize; i++) this.tickTimes.add(PERFECT_TICK_TIME);
        this.totalTickTime = (tickTimes.size() * PERFECT_TICK_TIME);
        this.lastTickTime = -1;
    }

    @Inject
    public TickManager() {
        this(DEFAULT_BUFFER_SIZE);
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged evt) {
        if (evt.getGameState() != GameState.LOGGED_IN) {
            this.paused = true;
        } else {
            this.paused = false;
            if (this.lastTickTime == -1) {
                this.lastTickTime = System.currentTimeMillis();
            }
        }
    }

    @Subscribe
    public void onGameTick(GameTick evt) {
        long now = System.currentTimeMillis();
        if (paused) {
            this.lastTickTime = now;
            return;
        }
        long tickTime = now - this.lastTickTime;

        // we should always have {bufferSize} elements in the queue
        if (this.tickTimes.size() != bufferSize) {
            throw new IllegalStateException();
        }

        long toRemove = Objects.requireNonNull(this.tickTimes.poll());
        this.totalTickTime = this.totalTickTime - toRemove + tickTime;
        this.averageTickTime = totalTickTime / bufferSize;

        this.tickTimes.offer(tickTime);
        this.lastTickTime = now;
    }


}
