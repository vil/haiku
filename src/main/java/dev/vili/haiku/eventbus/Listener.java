/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.eventbus;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public final class Listener {
    private final Object listenerClass;
    private final Method method;
    private final Consumer<HaikuEvent> lambda;

    /**
     * Constructs a new Listener object.
     * <p>
     * This constructor is used to generate a Listener object that encapsulates a class method or a lambda
     * that responds to a triggered HaikuEvent within an instance of the provided listenerClass.
     *
     * @param listenerClass The object instance containing the method or lambda.
     * @param method        The method in the listenerClass that responds to the HaikuEvent.
     * @param lambda        The Consumer function / lambda that will consume a HaikuEvent.
     */
    public Listener(final Object listenerClass, final Method method, final Consumer<HaikuEvent> lambda) {
        this.listenerClass = listenerClass;
        this.method = method;
        this.lambda = lambda;
    }

    /**
     * The Listener class is a wrapper for a class method or a lambda that responds to a triggered HaikuEvent.
     * <p>
     * The Listener encapsulates a method to be triggered on a HaikuEvent or a lambda that will consume the HaikuEvent.
     * The method or lambda is contained within an instance of an object, also encapsulated into a Listener instance.
     * <p>
     * The instance variables:
     * - listenerClass: An instance of an object that contains the method or lambda to be triggered or consume the HaikuEvent.
     * - method: A method within the 'listenerClass' which is triggered on a HaikuEvent.
     * - lambda: A consumer function / lambda that will consume a HaikuEvent.
     *
     * <p>
     * This class also contains getter methods for these instance variables.
     */
    public Listener(final Object listenerClass, final Method method) {
        this(listenerClass, method, null);
    }


    /**
     * Returns the method linked with this Listener.
     * <p>
     * This method is a getter for the 'method' instance variable. The 'method' presents the actual logic linked with the Listener.
     *
     * @return The method linked with this Listener.
     */
    public Method getMethod() {
        return method;
    }


    /**
     * Retrieves the consumer function of the HaikuEvent.
     * <p>
     * This method is a getter for the 'lambda' instance variable. This instance variable is supposed to hold
     * the logic that will consume the HaikuEvent.
     *
     * @return The consumer function of the HaikuEvent.
     */
    public Consumer<HaikuEvent> getLambda() {
        return lambda;
    }

    /**
     * Retrieves the class that contains the listener.
     * <p>
     * This method is a getter for the 'listenerClass' instance variable. This instance variable is intended to hold
     * the actual class that contains the 'method' and the 'lambda' which represent the functionality of the listener.
     *
     * @return The class that contains the listener method and functionality.
     */
    public Object getListenerClass() {
        return listenerClass;
    }

}
