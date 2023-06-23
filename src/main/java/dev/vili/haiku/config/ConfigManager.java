/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.config;

import dev.vili.haiku.Haiku;
import dev.vili.haiku.module.Module;
import dev.vili.haiku.setting.Setting;
import dev.vili.haiku.setting.settings.BooleanSetting;
import dev.vili.haiku.setting.settings.ModeSetting;
import dev.vili.haiku.setting.settings.NumberSetting;
import dev.vili.haiku.setting.settings.StringSetting;
import dev.vili.haiku.util.HaikuLogger;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class ConfigManager {
    private final File file;
    private final File mainDirectory;

    public ConfigManager() {
        mainDirectory = new File(MinecraftClient.getInstance().runDirectory, "haiku");

        if (!mainDirectory.exists()) {
            mainDirectory.mkdir();
        }

        file = new File(mainDirectory, "config.xml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the config file.
     */
    public File getFile() {
        return file;
    }

    /**
     * Gets the main directory.
     */
    public File getMainDirectory() {
        return mainDirectory;
    }

    /**
     * Saves the config.
     */
    public void save() {
        try {
            HaikuLogger.logger.info("Saving config...");
            Properties properties = new Properties();

            for (Module module : Haiku.getInstance().getModuleManager().getModules()) {
                properties.setProperty(module.getName() + ".enabled", String.valueOf(module.isEnabled()));

                for (Setting setting : module.settings) {
                    switch (setting.getClass().getSimpleName()) {
                        case "BooleanSetting" -> {
                            BooleanSetting booleanSetting = (BooleanSetting) setting;
                            properties.setProperty(module.getName() + "." + setting.getName(), String.valueOf(booleanSetting.isEnabled()));
                        }
                        case "NumberSetting" -> {
                            NumberSetting numberSetting = (NumberSetting) setting;
                            properties.setProperty(module.getName() + "." + setting.getName(), String.valueOf(numberSetting.getValue()));
                        }
                        case "StringSetting" -> {
                            StringSetting stringSetting = (StringSetting) setting;
                            properties.setProperty(module.getName() + "." + setting.getName(), String.valueOf(stringSetting.getString()));
                        }
                        case "ModeSetting" -> {
                            ModeSetting modeSetting = (ModeSetting) setting;
                            properties.setProperty(module.getName() + "." + setting.getName(), String.valueOf(modeSetting.getMode()));
                        }
                        case "KeybindSetting" ->
                                properties.setProperty(module.getName() + ".key", String.valueOf(module.getKey()));
                        default ->
                                HaikuLogger.logger.error("Unknown setting type: " + setting.getClass().getSimpleName());
                    }
                }

                properties.storeToXML(new FileOutputStream(file), null); // Save the config.
            }
        } catch (Exception e) {
            HaikuLogger.logger.error("Error while saving config!", e);
        }
    }

    /**
     * Loads the config.
     */
    public void load() {
        try {
            HaikuLogger.logger.info("Loading config...");
            Properties properties = new Properties();
            properties.loadFromXML(new FileInputStream(file));

            for (Module module : Haiku.getInstance().getModuleManager().getModules()) {
                if (Boolean.parseBoolean(properties.getProperty(module.getName() + ".enabled")) != module.isEnabled())
                    module.setEnabled(Boolean.parseBoolean(properties.getProperty(module.getName() + ".enabled"))); // Set the enabled state.

                for (Setting setting : module.settings) {
                    switch (setting.getClass().getSimpleName()) {
                        case "BooleanSetting" -> {
                            BooleanSetting booleanSetting = (BooleanSetting) setting;
                            if (Boolean.parseBoolean(properties.getProperty(module.getName() + "." + setting.getName())) != booleanSetting.isEnabled())
                                booleanSetting.setEnabled(Boolean.parseBoolean(properties.getProperty(module.getName() + "." + setting.getName())));
                        }
                        case "NumberSetting" -> {
                            NumberSetting numberSetting = (NumberSetting) setting;
                            if (Double.parseDouble(properties.getProperty(module.getName() + "." + setting.getName())) != numberSetting.getValue())
                                numberSetting.setValue(Double.parseDouble(properties.getProperty(module.getName() + "." + setting.getName())));
                        }
                        case "StringSetting" -> {
                            StringSetting stringSetting = (StringSetting) setting;
                            if (!properties.getProperty(module.getName() + "." + setting.getName()).equals(stringSetting.getString()))
                                stringSetting.setString(properties.getProperty(module.getName() + "." + setting.getName()));
                        }
                        case "ModeSetting" -> {
                            ModeSetting modeSetting = (ModeSetting) setting;
                            if (!properties.getProperty(module.getName() + "." + setting.getName()).equals(modeSetting.getMode()))
                                modeSetting.setMode(properties.getProperty(module.getName() + "." + setting.getName()));
                        }
                        case "KeybindSetting" -> {
                            if (properties.getProperty(module.getName() + ".key") != null)
                                module.setKey(Integer.parseInt(properties.getProperty(module.getName() + ".key"))); // Set the key.
                        }
                        default ->
                                HaikuLogger.logger.error("Unknown setting type: " + setting.getClass().getSimpleName());
                    }
                }
            }
        } catch (Exception e) {
            HaikuLogger.logger.error("Error while loading config!", e);
        }
    }
}
