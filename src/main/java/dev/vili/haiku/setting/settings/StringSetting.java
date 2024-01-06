/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.setting.settings;

import dev.vili.haiku.setting.Setting;
import imgui.ImGui;
import imgui.type.ImString;

/* TODO make it work */
public class StringSetting extends Setting {
    public ImString imString;

    public StringSetting(String name, String description) {
        super(name, description);
        this.imString = new ImString();
    }

    /**
     * Gets the value of the setting.
     */
    public String getString() {
        return imString.get();
    }

    /**
     * Sets the value of the setting.
     *
     * @param value value to set
     */
    public void setString(String value) {
        imString.set(value);
    }

    /**
     * Display the setting using ImGui.
     */
    public void display() {
        ImGui.inputText(name, imString);
    }
}
