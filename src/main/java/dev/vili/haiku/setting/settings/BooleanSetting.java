/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.setting.settings;

import dev.vili.haiku.setting.Setting;

public class BooleanSetting extends Setting {
    public boolean enabled;

    public BooleanSetting(String name, String description, boolean enabled) {
        super(name, description);
        this.enabled = enabled;
    }

    /**
     * Gets the enabled state of the setting.
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Sets the enabled state of the setting.
     *
     * @param enabled enabled state to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Toggles the enabled state of the setting.
     */
    public void toggle() {
        this.enabled = !this.enabled;
    }
}
