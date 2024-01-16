/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.module.modules;

import dev.vili.haiku.event.events.RenderEvent;
import dev.vili.haiku.eventbus.HaikuSubscribe;
import dev.vili.haiku.module.Module;
import dev.vili.haiku.setting.settings.BooleanSetting;
import dev.vili.haiku.util.HaikuLogger;
import dev.vili.haiku.util.RenderUtil;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class RenderTest extends Module {
    public final BooleanSetting box3D = new BooleanSetting("3DBox", "Draws an 3D box around the entity.", false);
    public final BooleanSetting boxOutline = new BooleanSetting("OutlineBox", "Draws an outline box around the entity.", false);
    public final BooleanSetting outline2D = new BooleanSetting("2DOutline", "Draws an 2D outline around the entity.", false);
    public final BooleanSetting line = new BooleanSetting("Line", "Draws an line from player.", false);


    public RenderTest() {
        super("RenderTest", "Shows different types of rendering methods in RenderUtil.", GLFW.GLFW_KEY_UNKNOWN, Category.RENDER);
        this.addSettings(box3D, boxOutline, outline2D, line);
    }


    @Override
    public void onEnable() {
        super.onEnable();

        HaikuLogger.info("RenderTest will be rendering on the player. Change to third person view, to see them correctly.");
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @HaikuSubscribe
    public void onRender(RenderEvent.Pre event) {
        if (mc.world == null || mc.player == null) return;

        if (box3D.isEnabled()) {
            RenderUtil.draw3DBox(event.getMatrixStack(), RenderUtil.smoothen(mc.player, mc.player.getBoundingBox()), new Color(255, 0, 0, 255));
        } else if (boxOutline.isEnabled()) {
            RenderUtil.drawOutlineBox(event.getMatrixStack(), RenderUtil.smoothen(mc.player, mc.player.getBoundingBox()), new Color(255, 0, 0, 255));
        } else if (outline2D.isEnabled()) {
            RenderUtil.draw2DOutline(event.getMatrixStack(), mc.player, new Color(255, 0, 0, 255));
        } else if (line.isEnabled()) {
            Camera camera = mc.gameRenderer.getCamera();
            for (Entity entity : mc.world.getEntities()) {
                if (entity instanceof AnimalEntity) {
                    Vec3d start = new Vec3d(0, 0, 1)
                            .rotateX(-(float) Math.toRadians(camera.getPitch()))
                            .rotateY(-(float) Math.toRadians(camera.getYaw()));
                    Vec3d end = RenderUtil.smoothen(entity).add(0, entity.getStandingEyeHeight(), 0);
                    RenderUtil.draw3DLineFromPlayer(event.getMatrixStack(), start, end, new Color(255, 0, 0, 255));
                }
            }
        }
    }

}
