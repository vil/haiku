/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.module.modules;

import dev.vili.haiku.gui.HaikuOneGui;
import dev.vili.haiku.module.Module;
import org.lwjgl.glfw.GLFW;

public class OneGui extends Module {
    public OneGui() {
        super("OneGui", "One window, no tabs.", GLFW.GLFW_KEY_RIGHT_CONTROL, Category.RENDER);
    }

    @Override
    public void onEnable() {
        mc.setScreen(new HaikuOneGui());
    }

    @Override
    public void onDisable() {
        mc.setScreen(null);
    }

}
