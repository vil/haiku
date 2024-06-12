/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.event.events;

import dev.vili.haiku.event.Event;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public class RenderEvent extends Event {
    protected float partialTicks;
    protected Matrix4f matrix4f;
    protected Matrix4f matrix4f2;
    protected MatrixStack matrixStack;

    public RenderEvent(float partialTicks, MatrixStack matrixStack, Matrix4f matrix4f, Matrix4f matrix4f2) {
        this.partialTicks = partialTicks;
        this.matrixStack = matrixStack;
        this.matrix4f = matrix4f;
        this.matrix4f2 = matrix4f2;
    }

    /**
     * Gets the partial ticks.
     *
     * @return partial ticks
     */
    public float getPartialTicks() {
        return partialTicks;
    }

    /**
     * Gets the matrix stack.
     *
     * @return matrix stack
     */
    public MatrixStack getMatrixStack() {
        return matrixStack;
    }

    /**
     * Types of render events.
     */
    public static class Post extends RenderEvent {
        public Post(float partialTicks, MatrixStack matrixStack, Matrix4f matrix4f, Matrix4f matrix4f2) {
            super(partialTicks, matrixStack, matrix4f, matrix4f2);
        }
    }

    public static class Pre extends RenderEvent {
        public Pre(float partialTicks, MatrixStack matrixStack, Matrix4f matrix4f, Matrix4f matrix4f2) {
            super(partialTicks, matrixStack, matrix4f, matrix4f2);
        }
    }

}
