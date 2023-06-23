/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
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
