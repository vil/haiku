/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * Rendering Util for Haiku.
 */
public class RenderUtil {
    public static final MinecraftClient mc = MinecraftClient.getInstance();


    /**
     * Draws a 3D box
     *
     * @param matrixStack the matrix stack
     * @param box the box to draw
     * @param color the color of the box
     */
    public static void draw3DBox(MatrixStack matrixStack, Box box, Color color) {
        float minX = (float) (box.minX - mc.getEntityRenderDispatcher().camera.getPos().getX());
        float minY = (float) (box.minY - mc.getEntityRenderDispatcher().camera.getPos().getY());
        float minZ = (float) (box.minZ - mc.getEntityRenderDispatcher().camera.getPos().getZ());
        float maxX = (float) (box.maxX - mc.getEntityRenderDispatcher().camera.getPos().getX());
        float maxY = (float) (box.maxY - mc.getEntityRenderDispatcher().camera.getPos().getY());
        float maxZ = (float) (box.maxZ - mc.getEntityRenderDispatcher().camera.getPos().getZ());
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        setup3D();
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.setShaderColor(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);

        bufferBuilder.begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION);
        {
            bufferBuilder.vertex(matrix, minX, minX, minZ).next();
            bufferBuilder.vertex(matrix, maxX, minY, minZ).next();

            bufferBuilder.vertex(matrix, maxX, minY, minZ).next();
            bufferBuilder.vertex(matrix, maxX, minY, maxZ).next();

            bufferBuilder.vertex(matrix, maxX, minY, maxZ).next();
            bufferBuilder.vertex(matrix, minX, minY, maxZ).next();

            bufferBuilder.vertex(matrix, minX, minY, maxZ).next();
            bufferBuilder.vertex(matrix, minX, minY, minZ).next();

            bufferBuilder.vertex(matrix, minX, minY, minZ).next();
            bufferBuilder.vertex(matrix, minX, maxY, minZ).next();

            bufferBuilder.vertex(matrix, maxX, minY, minZ).next();
            bufferBuilder.vertex(matrix, maxX, maxY, minZ).next();

            bufferBuilder.vertex(matrix, maxX, minY, maxZ).next();
            bufferBuilder.vertex(matrix, maxX, maxY, maxZ).next();

            bufferBuilder.vertex(matrix, minX, minY, maxZ).next();
            bufferBuilder.vertex(matrix, minX, maxY, maxZ).next();

            bufferBuilder.vertex(matrix, minX, maxY, minZ).next();
            bufferBuilder.vertex(matrix, maxX, maxY, minZ).next();

            bufferBuilder.vertex(matrix, maxX, maxY, minZ).next();
            bufferBuilder.vertex(matrix, maxX, maxY, maxZ).next();

            bufferBuilder.vertex(matrix, maxX, maxY, maxZ).next();
            bufferBuilder.vertex(matrix, minX, maxY, maxZ).next();

            bufferBuilder.vertex(matrix, minX, maxY, maxZ).next();
            bufferBuilder.vertex(matrix, minX, maxY, minZ).next();
        }
        tessellator.draw();
        clean3D();

        setup3D();
        RenderSystem.setShaderColor(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        {
            bufferBuilder.vertex(matrix, minX, minY, minZ).next();
            bufferBuilder.vertex(matrix, maxX, minY, minZ).next();
            bufferBuilder.vertex(matrix, maxX, minY, maxZ).next();
            bufferBuilder.vertex(matrix, minX, minY, maxZ).next();

            bufferBuilder.vertex(matrix, minX, maxY, minZ).next();
            bufferBuilder.vertex(matrix, minX, maxY, maxZ).next();
            bufferBuilder.vertex(matrix, maxX, maxY, maxZ).next();
            bufferBuilder.vertex(matrix, maxX, maxY, minZ).next();

            bufferBuilder.vertex(matrix, minX, minY, minZ).next();
            bufferBuilder.vertex(matrix, minX, maxY, minZ).next();
            bufferBuilder.vertex(matrix, maxX, maxY, minZ).next();
            bufferBuilder.vertex(matrix, maxX, minY, minZ).next();

            bufferBuilder.vertex(matrix, maxX, minY, minZ).next();
            bufferBuilder.vertex(matrix, maxX, maxY, minZ).next();
            bufferBuilder.vertex(matrix, maxX, maxY, maxZ).next();
            bufferBuilder.vertex(matrix, maxX, minY, maxZ).next();

            bufferBuilder.vertex(matrix, minX, minY, maxZ).next();
            bufferBuilder.vertex(matrix, maxX, minY, maxZ).next();
            bufferBuilder.vertex(matrix, maxX, maxY, maxZ).next();
            bufferBuilder.vertex(matrix, minX, maxY, maxZ).next();

            bufferBuilder.vertex(matrix, minX, minY, minZ).next();
            bufferBuilder.vertex(matrix, minX, minY, maxZ).next();
            bufferBuilder.vertex(matrix, minX, maxY, maxZ).next();
            bufferBuilder.vertex(matrix, minX, maxY, minZ).next();
        }
        tessellator.draw();
        clean3D();
    }

    /**
     * Draws an outlined box
     *
     * @param stack the matrix stack
     * @param box the box to outline
     * @param color the color of the outline
     */
    public static void drawOutlineBox(MatrixStack stack, Box box, Color color) {
        float minX = (float) (box.minX - mc.getEntityRenderDispatcher().camera.getPos().getX());
        float minY = (float) (box.minY - mc.getEntityRenderDispatcher().camera.getPos().getY());
        float minZ = (float) (box.minZ - mc.getEntityRenderDispatcher().camera.getPos().getZ());
        float maxX = (float) (box.maxX - mc.getEntityRenderDispatcher().camera.getPos().getX());
        float maxY = (float) (box.maxY - mc.getEntityRenderDispatcher().camera.getPos().getY());
        float maxZ = (float) (box.maxZ - mc.getEntityRenderDispatcher().camera.getPos().getZ());

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        setup3D();

        RenderSystem.setShaderColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        RenderSystem.setShader(GameRenderer::getRenderTypeLinesProgram);
        RenderSystem.defaultBlendFunc();

        bufferBuilder.begin(VertexFormat.DrawMode.LINES, VertexFormats.LINES);

        WorldRenderer.drawBox(stack, bufferBuilder, minX, minY, minZ, maxX, maxY, maxZ, color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);

        tessellator.draw();
        clean3D();
    }


    /**
     * Draws a 2D outline around an entity.
     *
     * @param matrixStack the matrix stack
     * @param entity the entity to outline
     * @param color the color of the outline
     */
    public static void draw2DOutline(MatrixStack matrixStack, Entity entity, Color color) {
        Camera c = mc.gameRenderer.getCamera();
        Vec3d camPos = c.getPos();
        Vec3d start = entity.getPos().subtract(camPos);
        float x = (float) start.x;
        float y = (float) start.y;
        float z = (float) start.z;

        // calculate the rotation of the outline based on the camera angle
        double r = Math.toRadians(-c.getYaw() + 90);
        float sin = (float) (Math.sin(r) * (entity.getWidth() / 1.5));
        float cos = (float) (Math.cos(r) * (entity.getWidth() / 1.5));

        // push the matrix onto the stack
        matrixStack.push();

        // get the position matrix from the matrix stack
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();

        // get the tessellator instance
        Tessellator tessellator = Tessellator.getInstance();

        // get the buffer builder from the tessellator
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        // set the shader to use for drawing the outline
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        // enable depth testing and set the depth function to always
        GL11.glDepthFunc(GL11.GL_ALWAYS);

        // set the color to use for the outline
        RenderSystem.setShaderColor(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);

        // set the default blend function
        RenderSystem.defaultBlendFunc();

        // enable blending
        RenderSystem.enableBlend();

        // begin drawing lines with the buffer builder
        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES,
                VertexFormats.POSITION_COLOR);

        // draw the outline as a series of connected lines
        bufferBuilder.vertex(matrix, x + sin, y, z + cos)
                .color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F)
                .next();
        bufferBuilder.vertex(matrix, x - sin, y, z - cos)
                .color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F)
                .next();
        bufferBuilder.vertex(matrix, x - sin, y, z - cos)
                .color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F)
                .next();
        bufferBuilder.vertex(matrix, x - sin, y + entity.getHeight(), z - cos)
                .color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F)
                .next();
        bufferBuilder.vertex(matrix, x - sin, y + entity.getHeight(), z - cos)
                .color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F)
                .next();
        bufferBuilder.vertex(matrix, x + sin, y + entity.getHeight(), z + cos)
                .color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F)
                .next();
        bufferBuilder.vertex(matrix, x + sin, y + entity.getHeight(), z + cos)
                .color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F)
                .next();
        bufferBuilder.vertex(matrix, x + sin, y, z + cos)
                .color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F)
                .next();
        bufferBuilder.vertex(matrix, x + sin, y, z + cos)
                .color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F)
                .next();

        // end the line drawing
        tessellator.draw();

        // disable depth testing and set the depth function to less than or equal
        GL11.glDepthFunc(GL11.GL_LEQUAL);

        // disable blending
        RenderSystem.disableBlend();

        // pop the matrix from the stack
        matrixStack.pop();
    }

    /**
     * Draws a line from player.
     *
     * @param matrixStack the matrix stack
     * @param start the start point of the line
     * @param end the end point of the line
     * @param color the color of the line
     */
    public static void draw3DLineFromPlayer(MatrixStack matrixStack, Vec3d start, Vec3d end, Color color) {
        Camera camera = mc.gameRenderer.getCamera();
        float startX = (float) start.x;
        float startY = (float) start.y;
        float startZ = (float) start.z;
        float endX = (float) (end.x - camera.getPos().x);
        float endY = (float) (end.y - camera.getPos().y);
        float endZ = (float) (end.z - camera.getPos().z);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        setup3D();
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.setShaderColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        {
            bufferBuilder.vertex(matrix, startX, startY, startZ).color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F).next();
            bufferBuilder.vertex(matrix, endX, endY, endZ).color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F).next();
        }
        tessellator.draw();
        clean3D();
    }

    /**
     * Draws a line somewhere in the world.
     *
     * @param matrixStack the matrix stack
     * @param start the start point of the line
     * @param end the end point of the line
     * @param color the color of the line
     */
    public static void draw3DLineInWorld(MatrixStack matrixStack, Vec3d start, Vec3d end, Color color) {
        Camera camera = mc.gameRenderer.getCamera();
        float startX = (float) (start.x - camera.getPos().x);
        float startY = (float) (start.y - camera.getPos().y);
        float startZ = (float) (start.z - camera.getPos().z);
        float endX = (float) (end.x - camera.getPos().x);
        float endY = (float) (end.y - camera.getPos().y);
        float endZ = (float) (end.z - camera.getPos().z);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        setup3D();
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.setShaderColor(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);

        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        {
            bufferBuilder.vertex(matrix, startX, startY, startZ).color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F).next();
            bufferBuilder.vertex(matrix, endX, endY, endZ).color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F).next();
        }
        tessellator.draw();
        clean3D();
    }

    /**
     * Draws a rect.
     *
     * @param x the x coordinate of the top-left corner of the rectangle
     * @param y the y coordinate of the top-left corner of the rectangle
     * @param w the width of the rectangle
     * @param h the height of the rectangle
     * @param color the color of the rectangle
     */
    public static void drawRect(float x, float y, float w, float h, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        setup3D();
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.setShaderColor(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
        RenderSystem.defaultBlendFunc();

        bufferbuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        {
            bufferbuilder.vertex(x, h, 0.0D).color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F).next();
            bufferbuilder.vertex(w, h, 0.0D).color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F).next();
            bufferbuilder.vertex(w, y, 0.0D).color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F).next();
            bufferbuilder.vertex(x, y, 0.0D).color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F).next();
        }
        tessellator.draw();
        clean3D();
    }

    /**
     * Get the interpolation offset.
     *
     * @param e the entity
     * @return the interpolation offset
     */
    public static Vec3d getInterpolationOffset(Entity e) {
        if (MinecraftClient.getInstance().isPaused()) return Vec3d.ZERO;
        double tickDelta = MinecraftClient.getInstance().getTickDelta();
        return new Vec3d(e.getX() - MathHelper.lerp(tickDelta, e.lastRenderX, e.getX()), e.getY() - MathHelper.lerp(tickDelta, e.lastRenderY, e.getY()), e.getZ() - MathHelper.lerp(tickDelta, e.lastRenderZ, e.getZ()));
    }

    /**
     * Smooths the movement of an entity.
     *
     * @param e the entity
     * @return the smoothed movement vector
     */
    public static Vec3d smoothen(Entity e) {
        return e.getPos().subtract(RenderUtil.getInterpolationOffset(e));
    }

    /**
     * Smooths the movement of an entity.
     *
     * @param e the entity
     * @param b the bounding box
     * @return the smoothed movement vector
     */
    public static Box smoothen(Entity e, Box b) {
        return Box.of(RenderUtil.smoothen(e), b.getLengthX(), b.getLengthY(), b.getLengthZ()).offset(0, e.getHeight() / 2f, 0);
    }

    /**
     * Setup 3D.
     **/
    public static void setup() {
        RenderSystem.deleteTexture(0);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }

    public static void setup3D() {
        setup();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
    }

    /**
     * Clean 3D.
     **/
    public static void clean() {
        RenderSystem.disableBlend();
        RenderSystem.bindTexture(0);
    }

    public static void clean3D() {
        clean();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
    }
}
