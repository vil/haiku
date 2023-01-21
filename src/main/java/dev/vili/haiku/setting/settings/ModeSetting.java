/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
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
     * @return
     */
    public String getMode() {
        return modes.get(index);
    }

    /**
     * Sets the mode of the setting.
     * @param mode
     */
    public void setMode(String mode) {
        this.index = this.modes.indexOf(mode);
    }

    /**
     * Get the state of current mode.
     * @return
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
