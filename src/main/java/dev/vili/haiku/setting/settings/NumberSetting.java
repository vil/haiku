/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.setting.settings;

import dev.vili.haiku.setting.Setting;

public class NumberSetting extends Setting {
    public double value;
    public double minimum;
    public double maximum;
    public double increment;

    public NumberSetting(String name, String description, double value, double minimum, double maximum, double increment) {
        super(name, description);
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
        this.increment = increment;
    }

    /**
     * Gets the value of the setting.
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets the value of the setting.
     *
     * @param value value to set
     */
    public void setValue(double value) {
        double precision = 1.0D / this.increment;
        this.value = Math.round(Math.max(this.minimum, Math.min(this.maximum, value)) * precision) / precision;
    }

    /**
     * Increments the value of the setting.
     *
     * @param positive whether to increment positively or negatively
     */
    public void increment(boolean positive) {
        setValue(getValue() + (positive ? 1 : -1) * increment);
    }

    /**
     * Gets the minimum value of the setting.
     */
    public double getMinimum() {
        return this.minimum;
    }

    /**
     * Sets the minimum value of the setting.
     *
     * @param minimum minimum value to set
     */
    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    /**
     * Gets the maximum value of the setting.
     */
    public double getMaximum() {
        return this.maximum;
    }

    /**
     * Sets the maximum value of the setting.
     *
     * @param maximum maximum value to set
     */
    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    /**
     * Gets the increment value of the setting.
     */
    public double getIncrement() {
        return this.increment;
    }

    /**
     * Sets the increment value of the setting.
     *
     * @param increment increment value to set
     */
    public void setIncrement(double increment) {
        this.increment = increment;
    }
}
