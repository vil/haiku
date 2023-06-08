/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.event.events;

import dev.vili.haiku.event.Event;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumers;
import net.minecraft.client.util.math.MatrixStack;

public class RenderInGameHudEvent extends Event {
    private final DrawContext context;

    public RenderInGameHudEvent(DrawContext context) {
        this.context = context;
    }

    /**
     * Gets the draw context.
     * @return context
     */
    public DrawContext getContext() {
        return context;
    }
}
