/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.eventbus;

public abstract class HaikuEvent {
    private boolean cancelled;
    private Era era;


    /**
     * Checks if the HaikuEvent is cancelled.
     *
     * @return a boolean value indicating the cancellation status. If true, then the event is cancelled, otherwise, the event is not cancelled.
     */
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Sets the cancellation status for the HaikuEvent.
     *
     * @param cancelled Boolean value representing whether the event should be cancelled or not. If set to true, the event will be cancelled.
     */
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }


    /**
     * Changes the event status to "cancelled". This method directly calls the setCancelled method with the parameter set as true.
     */
    public void cancel() {
        setCancelled(true);
    }


    /**
     * Retrieves the era associated with the current instance of the haiku event.
     *
     * @return Era instance indicates the timing of an event in the haiku system. It can be either a PRE-era (before the event happens)
     * or a POST-era (after the event has occurred).
     */
    public Era getEra() {
        return era;
    }

    /**
     * Sets the era for the haiku event.
     *
     * @param era Enum representing the era of the event in the haiku system.
     */
    public void setEra(Era era) {
        this.era = era;
    }

    /**
     * The Era enum represents the timing of an event in a Haiku system.
     * It has two main types of eras: PRE and POST.
     * PRE refers to the era before the event happens.
     * POST refers to the era after the event has occurred.
     */
    public enum Era {
        PRE,
        POST
    }
}
