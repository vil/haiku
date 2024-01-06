/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.eventbus;

/**
 * The IEventBus interface.
 * <p>
 * This interface represents a channel for passing event messages between the components of an application. Implementations of this
 * interface provide methods for managing (registering and unregistering) event subscriber objects and for posting events to the bus.
 * <p>
 * WARNING: This is a public top-level interface, please handle with caution to prevent incorrect mutations or unwanted side effects.
 * <p>
 * Future maintainers should ensure that all implementations of this interface adhere to the proper behavior of the data flow.
 */
public interface IEventBus {

    /**
     * Registers an object to the event bus.
     *
     * @param registerClass the object to be registered to the event bus.
     * This object starts to receive events as soon as it's registered.
     */
    void register(Object registerClass);

    /**
     * Unregisters an object from the event bus.
     *
     * @param registerClass the object to be removed from the event bus.
     * This object stops receiving events once it's unregistered.
     */
    void unregister(Object registerClass);

    /**
     * Posts an event to the event bus.
     *
     * @param event the event to be posted to the event bus.
     * All registered objects that can consume this event receive it as soon as it's posted.
     */
    void post(HaikuEvent event);
}
