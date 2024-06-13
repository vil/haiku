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
    protected Matrix4f positionMatrix;

    public RenderEvent(float partialTicks, Matrix4f matrix4f, Matrix4f positionMatrix) {
        this.partialTicks = partialTicks;
        this.matrix4f = matrix4f;
        this.positionMatrix = positionMatrix;
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
     * Gets the matrix.
     *
     * @return matrix4f
     */
    public Matrix4f getMatrix4f() {
        return matrix4f;
    }

    /**
     * Types of render events.
     */
    public static class Post extends RenderEvent {
        public Post(float partialTicks, Matrix4f matrix4f, Matrix4f positionMatrix) {
            super(partialTicks, matrix4f, positionMatrix);
        }
    }

    public static class Pre extends RenderEvent {
        public Pre(float partialTicks, Matrix4f matrix4f, Matrix4f positionMatrix) {
            super(partialTicks, matrix4f, positionMatrix);
        }
    }

}
