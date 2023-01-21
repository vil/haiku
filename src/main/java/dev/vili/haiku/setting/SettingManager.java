/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.setting;

import dev.vili.haiku.module.Module;

import java.util.ArrayList;

public class SettingManager {
    private final ArrayList<Setting> settings;

    public SettingManager() {
        settings = new ArrayList<>();
    }

    /**
     * Gets the settings.
     * @return
     */
    public ArrayList<Setting> getSettings() {
        return settings;
    }

    /**
     * Gets a setting by name.
     * @param module
     * @param name
     * @return
     */
    public Setting getSettingsByName(Module module, String name) {
        for (Setting setting : module.settings) {
            if (setting.name.equalsIgnoreCase(name)) {
                return setting;
            }
        }
        return null;
    }

}
