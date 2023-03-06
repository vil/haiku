/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
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
                properties.setProperty(module.getName() + ".key", String.valueOf(module.getKey()));

                for (Setting setting : module.settings) {
                    if (setting instanceof BooleanSetting) {
                        properties.setProperty(module.getName() + "." + setting.getName(), String.valueOf(((BooleanSetting) setting).isEnabled()));
                    }
                    else if (setting instanceof NumberSetting) {
                        properties.setProperty(module.getName() + "." + setting.getName(), String.valueOf(((NumberSetting) setting).getValue()));
                    }
                    else if (setting instanceof StringSetting) {
                        properties.setProperty(module.getName() + "." + setting.getName(), String.valueOf(((dev.vili.haiku.setting.settings.StringSetting) setting).getString()));
                    }
                    else if (setting instanceof ModeSetting) {
                        properties.setProperty(module.getName() + "." + setting.getName(), String.valueOf(((ModeSetting) setting).getMode()));
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
                if (properties.getProperty(module.getName() + ".key") != null)
                    module.setKey(Integer.parseInt(properties.getProperty(module.getName() + ".key"))); // Set the key.

                for (Setting setting : module.settings) {
                    if (setting instanceof BooleanSetting) {
                        ((BooleanSetting) setting).setEnabled(Boolean.parseBoolean(properties.getProperty(module.getName() + "." + setting.getName())));
                    }
                    else if (setting instanceof NumberSetting) {
                        ((NumberSetting) setting).setValue(Double.parseDouble(properties.getProperty(module.getName() + "." + setting.getName())));
                    }
                    else if (setting instanceof StringSetting) {
                        ((StringSetting) setting).setString(properties.getProperty(module.getName() + "." + setting.getName()));
                    }
                    else if (setting instanceof ModeSetting) {
                        ((ModeSetting) setting).setMode(properties.getProperty(module.getName() + "." + setting.getName()));
                    }
                }
            }
        } catch (Exception e) {
            HaikuLogger.logger.error("Error while loading config!", e);
        }
    }
}
