/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.eventbus;

public abstract class HaikuEvent {
    private boolean cancelled;
    private Era era;

    /**
     * Gets whether the event is cancelled.
     */
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Sets whether the event is cancelled.
     * @param cancelled
     */
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Cancels the event.
     */
    public void cancel() {
        setCancelled(true);
    }

    /**
     * Sets the era of the event.
     * @param era
     */
    public void setEra(Era era) {
        this.era = era;
    }

    /**
     * Gets the era of the event.
     */
    public Era getEra() {
        return era;
    }

    /**
     * Event eras.
     */
    public enum Era {
        PRE,
        POST
    }
}
