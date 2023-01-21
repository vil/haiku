/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.setting.settings;

import dev.vili.haiku.setting.Setting;

public class KeybindSetting extends Setting {
    public int code;

    public KeybindSetting(int keyCode) {
        super("KeyBind", "Sets a keybind for the module.");
        this.code = keyCode;
    }

    /**
     * Gets the key code.
     * @return
     */
    public int getKeyCode() {
        return code;
    }

    /**
     * Sets the key code.
     * @param keyCode
     */
    public void setKeyCode(int keyCode) {
        this.code = keyCode;
    }
}
