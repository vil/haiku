/*
 * Copyright (c) 2023. Vili and contributors.
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

    public Listener(final Object listenerClass, final Method method, final Consumer<HaikuEvent> lambda) {
        this.listenerClass = listenerClass;
        this.method = method;
        this.lambda = lambda;
    }

    public Listener(final Object listenerClass, final Method method) {
        this(listenerClass, method, null);
    }

    /**
     * Gets the method of the listener.
     *
     * @return method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Gets the lambda of the listener.
     *
     * @return lambda
     */
    public Consumer<HaikuEvent> getLambda() {
        return lambda;
    }

    /**
     * Gets the listener class.
     *
     * @return listener class
     */
    public Object getListenerClass() {
        return listenerClass;
    }

}
