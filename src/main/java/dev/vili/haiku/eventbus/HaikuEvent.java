/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
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
     *
     * @param cancelled whether the event is cancelled
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
     * Gets the era of the event.
     */
    public Era getEra() {
        return era;
    }

    /**
     * Sets the era of the event.
     *
     * @param era era of the event
     */
    public void setEra(Era era) {
        this.era = era;
    }

    /**
     * Event eras.
     */
    public enum Era {
        PRE,
        POST
    }
}
