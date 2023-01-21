/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.event.events;

import dev.vili.haiku.event.HaikuEvent;
import net.minecraft.client.util.math.MatrixStack;

public class RenderEvent extends HaikuEvent {
    protected float partialTicks;
    protected MatrixStack matrixStack;

    public RenderEvent(float partialTicks, MatrixStack matrixStack) {
        this.partialTicks = partialTicks;
        this.matrixStack = matrixStack;
    }

    /**
     * Types of render events.
     */
    public static class Post extends RenderEvent {
        public Post(float partialTicks, MatrixStack matrixStack) {
            super(partialTicks, matrixStack);
        }
    }

    public static class Pre extends RenderEvent {
        public Pre(float partialTicks, MatrixStack matrixStack) {
            super(partialTicks, matrixStack);
        }
    }



    /**
     * Gets the partial ticks.
     * @return
     */
    public float getPartialTicks() {
        return partialTicks;
    }

    /**
     * Gets the matrix stack.
     * @return
     */
    public MatrixStack getMatrixStack() {
        return matrixStack;
    }

}
