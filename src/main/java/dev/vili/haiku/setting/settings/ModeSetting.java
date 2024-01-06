/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.setting.settings;

import dev.vili.haiku.setting.Setting;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting {
    public int index;
    public List<String> modes;

    public ModeSetting(String name, String description, String defaultMode, String... modes) {
        super(name, description);
        this.modes = Arrays.asList(modes);
        this.index = this.modes.indexOf(defaultMode);
    }

    /**
     * Gets the mode of the setting.
     */
    public String getMode() {
        return modes.get(index);
    }

    /**
     * Sets the mode of the setting.
     *
     * @param mode mode to set
     */
    public void setMode(String mode) {
        this.index = this.modes.indexOf(mode);
    }

    /**
     * Get the state of current mode.
     */
    public boolean equals(String mode) {
        return (this.index == this.modes.indexOf(mode));
    }

    /**
     * Cycles through the modes.
     */
    public void cycle() {
        if (this.index < this.modes.size() - 1) this.index++;
        else this.index = 0;
    }
}
