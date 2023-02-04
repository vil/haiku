package dev.vili.haiku.setting.settings;

import dev.vili.haiku.setting.Setting;

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
     * @param string
     */
    public void setString(String string) {
        this.string = string;
    }
}
