/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.setting.settings;

import dev.vili.haiku.setting.Setting;

/* TODO make it work */
public class StringSetting extends Setting {
    public String string;

    public StringSetting(String name, String description, String string) {
        super(name, description);
        this.string = string;
    }

    /**
     * Gets the value of the setting.
     */
    public String getString() {
        return string;
    }

    /**
     * Sets the value of the setting.
     *
     * @param string value to set
     */
    public void setString(String string) {
        this.string = string;
    }
}
