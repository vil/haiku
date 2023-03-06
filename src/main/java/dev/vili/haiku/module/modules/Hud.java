/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.module.modules;

import dev.vili.haiku.Haiku;
import dev.vili.haiku.event.events.RenderInGameHudEvent;
import dev.vili.haiku.eventbus.HaikuSubscribe;
import dev.vili.haiku.module.Module;
import dev.vili.haiku.setting.settings.BooleanSetting;
import dev.vili.haiku.setting.settings.StringSetting;
import net.minecraft.client.gui.DrawableHelper;
import org.lwjgl.glfw.GLFW;

public class Hud extends Module {
    public final BooleanSetting watermark = new BooleanSetting("Watermark", "Renders the Haiku watermark.", true);
    //public final StringSetting watermarkText = new StringSetting("Watermark Text", "The text of the watermark.", "Haiku");
    public final BooleanSetting arraylist = new BooleanSetting("Arraylist", "Renders the Haiku arraylist.", true);

    public Hud() {
        super("Hud", "Renders the Haiku hud.", GLFW.GLFW_KEY_UNKNOWN, Category.RENDER);
        this.addSettings(watermark, arraylist);
    }

    @HaikuSubscribe
    public void onRender(RenderInGameHudEvent event) {
        if (mc.world == null || mc.player == null) return;

        if (watermark.isEnabled()) {
            DrawableHelper.drawStringWithShadow(event.getMatrixStack(), mc.textRenderer, Haiku.MOD_NAME + " v" + Haiku.MOD_VERSION,
                    2, 2, 0xFFFFFF);
        }

        int y = 5;

        if (arraylist.isEnabled()) {
            for (Module module : Haiku.getInstance().getModuleManager().getEnabledModules()) {
                DrawableHelper.drawStringWithShadow(event.getMatrixStack(), mc.textRenderer, module.name, 2, y += 10, 0xFFFFFF);
            }
        }
    }
}
