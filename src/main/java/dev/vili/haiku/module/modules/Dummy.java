/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.module.modules;

import dev.vili.haiku.module.Module;
import dev.vili.haiku.util.HaikuLogger;
import org.lwjgl.glfw.GLFW;

public class Dummy extends Module {

    public Dummy() {
        super("Dummy", "this is a test.", GLFW.GLFW_KEY_UNKNOWN, Category.PLAYER);
    }

    @Override
    public void onEnable() {
        HaikuLogger.info("Dummy module enabled!");
    }

    @Override
    public void onDisable() {
        HaikuLogger.info("Dummy module disabled!");
    }
}
