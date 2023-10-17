/*
 * Copyright (c) 2023. Vili and contributors.
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
import dev.vili.haiku.util.TPSUtil;
import org.lwjgl.glfw.GLFW;

public class Hud extends Module {
    public final BooleanSetting watermark = new BooleanSetting("Watermark", "Renders the Haiku watermark.", true);
    //public final StringSetting watermarkText = new StringSetting("Watermark Text", "The text of the watermark.", "Haiku");
    public final BooleanSetting arraylist = new BooleanSetting("Arraylist", "Renders the Haiku arraylist.", true);
    public final BooleanSetting ticks = new BooleanSetting("TPS", "Renders the ticks per second.", true);

    public Hud() {
        super("Hud", "Renders the Haiku hud.", GLFW.GLFW_KEY_UNKNOWN, Category.RENDER);
        this.addSettings(watermark, arraylist, ticks);
    }

    @HaikuSubscribe
    public void onRender(RenderInGameHudEvent event) {
        if (mc.world == null || mc.player == null) return;
        if (mc.getDebugHud().shouldShowDebugHud()) return;

        if (watermark.isEnabled()) {
            event.getContext().drawTextWithShadow(mc.textRenderer, Haiku.MOD_NAME + " v" + Haiku.MOD_VERSION,
                    2, 2, 0xFFFFFF);
        }

        if (ticks.isEnabled()) {
            event.getContext().drawTextWithShadow(mc.textRenderer, "Ticks per sec: " +
                    TPSUtil.INSTANCE.getTPS(), watermark.isEnabled() ? 70 : 2, 2, 0xFFFFFF);
        }

        int y = 5;

        if (arraylist.isEnabled()) {
            for (Module module : Haiku.getInstance().getModuleManager().getEnabledModules()) {
                event.getContext().drawTextWithShadow(mc.textRenderer, ">" + module.name, 2, y += 10, 0xFFFFFF);
            }
        }
    }
}
