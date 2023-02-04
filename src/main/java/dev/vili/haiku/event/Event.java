/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.event;

import dev.vili.haiku.eventbus.HaikuEvent;
import net.minecraft.client.MinecraftClient;

public class Event extends HaikuEvent {
    public MinecraftClient mc = MinecraftClient.getInstance();

    public Event() {}
}
