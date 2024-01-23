/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku;

import dev.vili.haiku.command.CommandManager;
import dev.vili.haiku.config.ConfigManager;
import dev.vili.haiku.eventbus.EventBus;
import dev.vili.haiku.module.ModuleManager;
import dev.vili.haiku.setting.SettingManager;
import dev.vili.haiku.util.HaikuLogger;
import dev.vili.haiku.util.TPSUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;

/**
 * Main class for haiku.
 */
public class Haiku implements ModInitializer {
    public static final String MOD_NAME = "Haiku";
    public static final String MOD_VERSION = "1.0";
    public static final MinecraftClient mc = MinecraftClient.getInstance();
    private static Haiku INSTANCE;
    private final EventBus EVENT_BUS = new EventBus();
    private final ModuleManager MODULE_MANAGER = new ModuleManager();
    private final CommandManager COMMAND_MANAGER = new CommandManager();
    private final SettingManager SETTING_MANAGER = new SettingManager();
    private final ConfigManager CONFIG_MANAGER = new ConfigManager();

    public Haiku() {
        INSTANCE = this;
    }

    /**
     * Gets the instance of Haiku.
     */
    public static Haiku getInstance() {
        return INSTANCE;
    }

    /**
     * Called when haiku is initialized.
     */
    @Override
    public void onInitialize() {
        HaikuLogger.logger.info(MOD_NAME + " v" + MOD_VERSION + " (phase 1) has initialized!");
        CONFIG_MANAGER.load();
        HaikuLogger.logger.info("Loaded config!");

        // Save configs on shutdown
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            CONFIG_MANAGER.save();
            HaikuLogger.logger.info("Saved config!");
        });
    }

    /**
     * Called when Minecraft has finished loading.
     *
     * @see dev.vili.haiku.mixin.MinecraftClientMixin
     */
    public void postInitialize() {
        EVENT_BUS.register(TPSUtil.INSTANCE);
        HaikuLogger.logger.info("Registered TickRateUtil!");
        HaikuLogger.logger.info(MOD_NAME + " v" + MOD_VERSION + " (phase 2) has initialized!");
    }

    /**
     * Gets the event bus.
     */
    public EventBus getEventBus() {
        return EVENT_BUS;
    }

    /**
     * Gets the module manager.
     */
    public ModuleManager getModuleManager() {
        return MODULE_MANAGER;
    }

    /**
     * Gets the command manager.
     */
    public CommandManager getCommandManager() {
        return COMMAND_MANAGER;
    }

    /**
     * Gets the setting manager.
     */
    public SettingManager getSettingManager() {
        return SETTING_MANAGER;
    }

    /**
     * Gets the config manager.
     */
    public ConfigManager getConfigManager() {
        return CONFIG_MANAGER;
    }
}
