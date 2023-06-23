/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.module.modules;

import dev.vili.haiku.gui.HaikuGui;
import dev.vili.haiku.module.Module;
import org.lwjgl.glfw.GLFW;

public class Gui extends Module {

    public Gui() {
        super("Gui", "Haiku gui.", GLFW.GLFW_KEY_RIGHT_SHIFT, Category.RENDER);
    }

    @Override
    public void onEnable() {
        mc.setScreen(new HaikuGui());
    }

    @Override
    public void onDisable() {
        mc.setScreen(null);
    }
}
