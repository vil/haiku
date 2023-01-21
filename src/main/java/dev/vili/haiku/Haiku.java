/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku;

import dev.vili.haiku.eventbus.EventBus;
import dev.vili.haiku.module.ModuleManager;
import dev.vili.haiku.setting.SettingManager;
import dev.vili.haiku.util.HaikuLogger;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

public class Haiku implements ModInitializer {
    private static Haiku INSTANCE;
    public static final String MOD_NAME = "Haiku";
    public static final String MOD_VERSION = "1.0";
    public static final MinecraftClient mc = MinecraftClient.getInstance();
    private final EventBus EVENT_BUS = new EventBus();
    private final ModuleManager MODULE_MANAGER = new ModuleManager();
    private final SettingManager SETTING_MANAGER = new SettingManager();

    public Haiku() {
        INSTANCE = this;
    }

    /**
     * Called when haiku is initialized.
     */
    @Override
    public void onInitialize() {
        HaikuLogger.logger.info(MOD_NAME + " v" + MOD_VERSION + " (phase 1) has initialized!");
    }

    /**
     * Called when Minecraft has finished loading.
     */
    public void postInitialize() {
        HaikuLogger.logger.info(MOD_NAME + " v" + MOD_VERSION + " (phase 2) has initialized!");
    }

    /**
     * Gets the instance of Haiku.
     * @return
     */
    public static Haiku getInstance() {
        return INSTANCE;
    }

    /**
     * Gets the event bus.
     * @return
     */
    public EventBus getEventBus() {
        return EVENT_BUS;
    }

    /**
     * Gets the module manager.
     * @return
     */
    public ModuleManager getModuleManager() {
        return MODULE_MANAGER;
    }

    /**
     * Gets the setting manager.
     * @return
     */
    public SettingManager getSettingManager() {
        return SETTING_MANAGER;
    }
}
