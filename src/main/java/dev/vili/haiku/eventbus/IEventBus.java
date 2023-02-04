/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.eventbus;

public interface IEventBus {
    void register(Object registerClass);
    void unregister(Object registerClass);
    void post(HaikuEvent event);
}
