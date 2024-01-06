/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.gui.tabs;

import dev.vili.haiku.Haiku;
import dev.vili.haiku.gui.HaikuGui;
import dev.vili.haiku.gui.HaikuOneGui;
import dev.vili.haiku.module.Module;
import dev.vili.haiku.setting.Setting;
import dev.vili.haiku.setting.settings.*;
import dev.vili.haiku.util.HaikuLogger;
import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImInt;
import imgui.type.ImString;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;

public class ModuleTabs {
    private static final HashMap<Module, ImBoolean> enabledMap = new HashMap<>();
    private static final HashMap<Setting, Object> settingsMap = new HashMap<>();
    private static final HashMap<Module.Category, Boolean> categoryMap = new HashMap<>();
    private static final HashMap<Module, Boolean> showSettingsMap = new HashMap<>();
    private static boolean binding;
    private static Module activeModule;

    /**
     * Renders the module tabs.
     */
    public static void render() {
        for (Module module : Haiku.getInstance().getModuleManager().modules) {
            showSettingsMap.put(module, showSettingsMap.getOrDefault(module, false));
            enabledMap.put(module, new ImBoolean(module.isEnabled()));

            for (Module.Category category : Module.Category.values()) {
                categoryMap.put(category, false);
            }

            for (Setting setting : module.settings) {
                switch (setting.getClass().getSimpleName()) {
                    case "BooleanSetting" -> settingsMap.put(setting, new ImBoolean(((BooleanSetting) setting).isEnabled()));
                    case "NumberSetting" -> settingsMap.put(setting, new float[]{(float) ((NumberSetting) setting).getValue()});
                    case "ModeSetting" -> settingsMap.put(setting, new ImInt(((ModeSetting) setting).index));
                    case "KeybindSetting" -> settingsMap.put(setting, new ImInt(((KeybindSetting) setting).getKeyCode()));
                    case "StringSetting" -> settingsMap.put(setting, new ImString(((StringSetting) setting).getString()));
                    default -> HaikuLogger.logger.warn("Unknown setting type: " + setting.getClass().getSimpleName());
                }
            }
        }

        for (Module.Category category : Module.Category.values()) {
            if (categoryMap.get(category)) {
                continue;
            }

            ImGui.begin(category.name(), ImGuiWindowFlags.NoResize);
            if (!categoryMap.get(category)) {
                ImGui.setWindowSize(250 * HaikuGui.guiWidth.get(), 300 * HaikuGui.guiHeight.get());
                categoryMap.put(category, true);
            }

            renderCategoryModules(category);

            ImGui.end();
        }
    }

    /**
     * Renders the modules for a given category.
     *
     * @param category The category to render modules for
     */
    private static void renderCategoryModules(Module.Category category) {
        for (Module module : Haiku.getInstance().getModuleManager().getModulesByCategory(category)) {
            ImGui.checkbox(module.getName(), enabledMap.get(module));
            if (ImGui.isItemClicked(1)) {
                showSettingsMap.put(module, !showSettingsMap.get(module));
            }
            ImGui.sameLine(220);
            if (ImGui.isItemHovered()) {
                ImGui.setTooltip(module.getDescription());
            }
            ImGui.text(((module.settings.isEmpty()) ? "" : (showSettingsMap.get(module)) ? "^" : "v"));

            if (showSettingsMap.get(module)) {
                ImGui.indent();
                renderModuleSettings(module);
                ImGui.unindent();
            }

            if (enabledMap.get(module).get() != module.isEnabled()) {
                module.toggle();
            }
        }
    }

    /**
     * Renders the settings for a given module.
     *
     * @param module The module to render settings for
     */
    private static void renderModuleSettings(Module module) {
        for (Setting setting : module.settings) {
            if (module.settings != null) {
                switch (setting.getClass().getSimpleName()) {
                    case "BooleanSetting" -> {
                        ImGui.checkbox(setting.name, (ImBoolean) settingsMap.get(setting));
                        if (((BooleanSetting) setting).isEnabled() != ((ImBoolean) settingsMap.get(setting)).get()) {
                            ((BooleanSetting) setting).setEnabled(((ImBoolean) settingsMap.get(setting)).get());
                        }
                    }
                    case "NumberSetting" -> {
                        ImGui.sliderFloat(setting.name, (float[]) settingsMap.get(setting), (float) ((NumberSetting) setting).getMinimum(),
                                (float) ((NumberSetting) setting).getMaximum());
                        float[] temp = (float[]) settingsMap.get(setting);
                        if (temp[0] != (float) ((NumberSetting) setting).getValue()) {
                            ((NumberSetting) setting).setValue(temp[0]);
                        }
                    }
                    case "ModeSetting" -> {
                        String[] temp = ((ModeSetting) setting).modes.toArray(new String[0]);
                        ImGui.combo(setting.name, (ImInt) settingsMap.get(setting), temp);
                        if (((ImInt) settingsMap.get(setting)).get() != ((ModeSetting) setting).modes.indexOf(((ModeSetting) setting).getMode())) {
                            ((ModeSetting) setting).setMode(((ModeSetting) setting).modes.get(((ImInt) settingsMap.get(setting)).get()));
                        }
                    }
                    case "StringSetting" -> {
                        ImGui.inputText(setting.name, (ImString) settingsMap.get(setting), ImGuiInputTextFlags.CallbackResize);
                        String temp = ((ImString) settingsMap.get(setting)).get();
                        if (!temp.equals(((StringSetting) setting).getString())) {
                            ((StringSetting) setting).setString(temp);
                        }
                    }
                    case "KeybindSetting" -> {
                        if (binding && activeModule == module) { // Check if the module is active for keybind changes
                            ImGui.text("Press a key to bind");
                            for (int i = 0; i < 512; i++) {
                                if (ImGui.isKeyPressed(i)) {
                                    if (i == GLFW.GLFW_KEY_ESCAPE || i == GLFW.GLFW_KEY_BACKSPACE || i == GLFW.GLFW_KEY_DELETE) {
                                        ((KeybindSetting) setting).setKeyCode(-1);
                                    } else {
                                        ((KeybindSetting) setting).setKeyCode(i);
                                    }
                                    binding = false;
                                }
                            }
                        } else {
                            String name = ((KeybindSetting) setting).getKeyCode() < 0 ? "NONE"
                                    : InputUtil.fromKeyCode(((KeybindSetting) setting).getKeyCode(), -1).getLocalizedText().getString();
                            if (ImGui.button("Bind: " + name)) {
                                activeModule = module; // Set the active module for keybind changes
                                binding = true;
                            }
                        }
                    }
                    default -> HaikuLogger.logger.warn("Unknown setting type: " + setting.getClass().getSimpleName());
                }
            }
            if (ImGui.isItemHovered()) {
                ImGui.setTooltip(setting.getDescription());
            }
        }
    }
}