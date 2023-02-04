/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.event.events;

import dev.vili.haiku.event.Event;
import net.minecraft.client.util.math.MatrixStack;

public class RenderInGameHudEvent extends Event {
    private final MatrixStack matrixStack;

    public RenderInGameHudEvent(MatrixStack matrixStack) {
        this.matrixStack = matrixStack;
    }

    /**
     * Gets the matrix stack.
     * @return
     */
    public MatrixStack getMatrixStack() {
        return matrixStack;
    }
}
