/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
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
     */
    public ArrayList<Setting> getSettings() {
        return settings;
    }

    /**
     * Gets settings by name.
     *
     * @param module module to get settings from
     * @param name   name of setting
     */
    public Setting getSettingsByName(Module module, String name) {
        for (Setting setting : module.settings) {
            if (setting.name.equalsIgnoreCase(name)) {
                return setting;
            }
        }
        return null;
    }

    /**
     * Gets settings by module.
     *
     * @param module module to get settings from
     */
    public ArrayList<Setting> getSettingsByModule(Module module) {
        return new ArrayList<>(module.settings);
    }

}
