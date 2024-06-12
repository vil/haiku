/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.vili.haiku.Haiku;
import dev.vili.haiku.event.events.RenderEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Unique
    private final MatrixStack matrixStack = new MatrixStack();

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render_head(float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
        RenderEvent.Post event = new RenderEvent.Post(tickDelta, matrixStack, matrix4f, matrix4f2);
        Haiku.getInstance().getEventBus().post(event);

        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void render_return(float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
        RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, MinecraftClient.IS_SYSTEM_MAC);
        RenderEvent.Pre event = new RenderEvent.Pre(tickDelta, matrixStack, matrix4f, matrix4f2);
        Haiku.getInstance().getEventBus().post(event);
    }
}
