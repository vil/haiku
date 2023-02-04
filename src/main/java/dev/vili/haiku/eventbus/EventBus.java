/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.eventbus;

import java.lang.invoke.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class EventBus implements IEventBus {
    private final Map<Class<? extends HaikuEvent>, CopyOnWriteArrayList<Listener>> listeners = new ConcurrentHashMap<>();

    /**
     * Registers a object.
     * @param registerClass
     */
    @Override
    public void register(Object registerClass) {
        Arrays.stream(registerClass.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(HaikuSubscribe.class))
                .filter(method -> method.getParameterCount() == 1)
                .forEach(method -> {
                    if (!method.canAccess(registerClass)) method.setAccessible(true);

                    @SuppressWarnings("unchecked") Class<? extends HaikuEvent> event =
                            (Class<? extends HaikuEvent>) method.getParameterTypes()[0];

                    Consumer<HaikuEvent> lambda = null;
                    if (method.getDeclaredAnnotation(HaikuSubscribe.class).lambda()) lambda = getLambda(registerClass, method, event);
                    if (!listeners.containsKey(event)) listeners.put(event, new CopyOnWriteArrayList<>());

                    listeners.get(event).add(new Listener(registerClass, method, lambda));
                });
    }

    /**
     * Unregisters a object.
     * @param registerClass
     */
    @Override
    public void unregister(Object registerClass) {
        listeners.values().forEach(arrayList -> arrayList.removeIf(listener -> listener.getListenerClass().equals(registerClass)));
    }

    /**
     * Posts an event.
     * @param event
     */
    @Override
    public void post(HaikuEvent event) {
        List<Listener> listenersList = listeners.get(event.getClass());
        if (listenersList != null) for (Listener listener : listenersList) {
            if (event.isCancelled()) return;
            if (listener.getLambda() != null)
                listener.getLambda().accept(event);
            else {
                try {
                    listener.getMethod().invoke(listener.getListenerClass(), event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Gets the lambda of the listener.
     * @param object
     * @param method
     * @param event
     * @return event lambda
     */
    protected Consumer<HaikuEvent> getLambda(Object object, Method method, Class<? extends HaikuEvent> event) {
        Consumer<HaikuEvent> eventLambda = null;
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodType subscription = MethodType.methodType(void.class, event);
            MethodHandle target = lookup.findVirtual(object.getClass(), method.getName(), subscription);
            CallSite site = LambdaMetafactory.metafactory(
                    lookup,
                    "accept",
                    MethodType.methodType(Consumer.class, object.getClass()),
                    subscription.changeParameterType(0, Object.class),
                    target,
                    subscription);

            MethodHandle factory = site.getTarget();
            eventLambda = (Consumer<HaikuEvent>) factory.bindTo(object).invokeExact();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return eventLambda;
    }
}
