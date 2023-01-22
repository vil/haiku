/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.module;

import dev.vili.haiku.Haiku;
import dev.vili.haiku.event.events.KeyEvent;
import dev.vili.haiku.eventbus.HaikuSubscribe;
import dev.vili.haiku.module.modules.*;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class ModuleManager {
    public final ArrayList<Module> modules;

    public ModuleManager() {
        modules = new ArrayList<>();

        /* Add modules here */
        modules.add(new Hud());
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
     * Gets the modules state
     * @param name
     */
    public boolean isModuleEnabled(String name) {
        Module mod = modules.stream().filter(mm -> mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        return mod.isEnabled();
    }

    @HaikuSubscribe
    public void onKeyPress(KeyEvent event) {
        if (InputUtil.isKeyPressed(Haiku.mc.getWindow().getHandle(), GLFW.GLFW_KEY_F3)) return;
        modules.stream().filter(m -> m.getKey() == event.getKey()).forEach(Module::toggle);
    }
}
