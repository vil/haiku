/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.event;

import dev.vili.haiku.eventbus.Event;
import net.minecraft.client.MinecraftClient;

public class HaikuEvent extends Event {
    public MinecraftClient mc = MinecraftClient.getInstance();

    public HaikuEvent() {}
}
