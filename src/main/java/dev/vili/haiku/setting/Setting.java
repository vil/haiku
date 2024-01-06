/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.setting;

public class Setting {
    public String name;
    public String description;

    public Setting(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Gets the name of the setting.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the setting.
     */
    public String getDescription() {
        return description;
    }
}
