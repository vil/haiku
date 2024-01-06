/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
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
     */
    public int getKeyCode() {
        return code;
    }

    /**
     * Sets the key code.
     *
     * @param keyCode key code to set
     */
    public void setKeyCode(int keyCode) {
        this.code = keyCode;
    }
}
