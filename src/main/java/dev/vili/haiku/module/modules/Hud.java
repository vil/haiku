/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.module.modules;

import dev.vili.haiku.Haiku;
import dev.vili.haiku.event.events.RenderInGameHudEvent;
import dev.vili.haiku.eventbus.HaikuSubscribe;
import dev.vili.haiku.module.Module;
import dev.vili.haiku.setting.settings.BooleanSetting;
import dev.vili.haiku.setting.settings.StringSetting;
import dev.vili.haiku.util.TPSUtil;
import org.lwjgl.glfw.GLFW;

public class Hud extends Module {
    public final BooleanSetting watermark = new BooleanSetting("Watermark", "Renders the Haiku watermark.", true);
    public final StringSetting watermarkText = new StringSetting("Watermark Text", "The text of the watermark.");
    public final BooleanSetting arraylist = new BooleanSetting("Arraylist", "Renders the Haiku arraylist.", true);
    public final BooleanSetting ticks = new BooleanSetting("TPS", "Renders the ticks per second.", true);
    public final BooleanSetting fps = new BooleanSetting("FPS", "Renders the frames per second.", true);

    public Hud() {
        super("Hud", "Renders the Haiku hud.", GLFW.GLFW_KEY_UNKNOWN, Category.RENDER);
        this.addSettings(watermark, watermarkText, arraylist, ticks, fps);
    }

    @HaikuSubscribe
    public void onRender(RenderInGameHudEvent event) {
        if (mc.world == null || mc.player == null) return;
        if (mc.getDebugHud().shouldShowDebugHud()) return;

        // TODO rewrite the whole hud thingy

        if (watermark.isEnabled()) {
            event.getContext().drawTextWithShadow(mc.textRenderer, watermarkText.getString() == null ? Haiku.MOD_NAME : watermarkText.getString() + " v" + Haiku.MOD_VERSION,
                    2, 2, 0xFFFFFF);
        }

        if (fps.isEnabled()) {
            String fps = mc.fpsDebugString.split(" ")[0];
            int x = 2;

            if (watermark.isEnabled()) x = (watermarkText.getString().length() * 15);

            event.getContext().drawTextWithShadow(mc.textRenderer, "(" + fps + " fps)", x, 2, 0xFFFFFF);
        }

        int screenWidth = mc.getWindow().getScaledWidth();
        if (ticks.isEnabled()) {
           String tpsInfo = "Ticks per sec: " + TPSUtil.INSTANCE.getTPS();
           int tpsInfoWidth = mc.textRenderer.getWidth(tpsInfo);
           event.getContext().drawTextWithShadow(mc.textRenderer, tpsInfo, screenWidth - tpsInfoWidth - 2, 2, 0xFFFFFF);
        }

        int y = 5;

        if (arraylist.isEnabled()) {
            for (Module module : Haiku.getInstance().getModuleManager().getEnabledModules()) {
                event.getContext().drawTextWithShadow(mc.textRenderer, ">" + module.name, 2, y += 10, 0xFFFFFF);
            }
        }
    }
}
