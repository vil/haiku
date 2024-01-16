/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.module;

import dev.vili.haiku.Haiku;
import dev.vili.haiku.event.events.KeyEvent;
import dev.vili.haiku.eventbus.HaikuSubscribe;
import dev.vili.haiku.module.modules.*;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public final ArrayList<Module> modules;

    public ModuleManager() {
        modules = new ArrayList<>();

        /* Add modules here */
        modules.add(new Dummy());
        modules.add(new Fly());
        modules.add(new Gui());
        modules.add(new Hud());
        modules.add(new OneGui());
        modules.add(new RenderTest());
        modules.add(new Sprint());
    }

    /**
     * Gets the modules.
     */
    public ArrayList<Module> getModules() {
        return modules;
    }

    /**
     * Gets enabled modules.
     */
    public ArrayList<Module> getEnabledModules() {
        ArrayList<Module> enabledModules = new ArrayList<>();
        for (Module module : modules) {
            if (module.isEnabled())
                enabledModules.add(module);
        }
        return enabledModules;
    }

    /**
     * Gets the module by name.
     *
     * @param name name of the module
     */
    public Module getModule(String name) {
        return modules.stream().filter(mm -> mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    /**
     * Gets the modules state
     *
     * @param name name of the module
     */
    public boolean isModuleEnabled(String name) {
        Module mod = modules.stream().filter(mm -> mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        return mod.isEnabled();
    }

    /**
     * Gets the modules by category.
     *
     * @param category category of the module
     */
    public List<Module> getModulesByCategory(Module.Category category) {
        List<Module> cats = new ArrayList<>();
        for (Module m : modules) {
            if (m.getCategory() == category) cats.add(m);
        }
        return cats;
    }

    @HaikuSubscribe
    public void onKeyPress(KeyEvent event) {
        if (InputUtil.isKeyPressed(Haiku.mc.getWindow().getHandle(), GLFW.GLFW_KEY_F3)) return;
        modules.stream().filter(m -> m.getKey() == event.getKey()).forEach(Module::toggle);
    }
}
