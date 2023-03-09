/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.gui.tabs;

import dev.vili.haiku.Haiku;
import dev.vili.haiku.gui.HaikuGui;
import dev.vili.haiku.setting.Setting;
import dev.vili.haiku.setting.settings.*;
import dev.vili.haiku.setting.settings.KeybindSetting;
import dev.vili.haiku.module.Module;
import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;
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

    /**
     * Renders the module tabs.
     */
    public static void render() {
        /* Code is a mess. */
        ImGui.getIO().addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        for (Module module : Haiku.getInstance().getModuleManager().modules) {
            showSettingsMap.put(module, showSettingsMap.getOrDefault(module, false));
            enabledMap.put(module, new ImBoolean(module.isEnabled()));

            for (Module.Category category : Module.Category.values()) {
                categoryMap.put(category, false);
            }

            for (Setting setting : module.settings) {
                if (setting instanceof BooleanSetting) {
                    settingsMap.put(setting, new ImBoolean(((BooleanSetting) setting).isEnabled()));
                } else if (setting instanceof NumberSetting) {
                    settingsMap.put(setting, new float[]{(float) ((NumberSetting) setting).getValue()});
                } else if (setting instanceof ModeSetting) {
                    settingsMap.put(setting, new ImInt(((ModeSetting) setting).index));
                } else if (setting instanceof KeybindSetting) {
                    settingsMap.put(setting, new ImInt(((KeybindSetting) setting).getKeyCode()));
                } else if (setting instanceof StringSetting) {
                    settingsMap.put(setting, new ImString(((StringSetting) setting).getString()));
                }
            }
        }

        for (Module.Category category : Module.Category.values()) {
            ImGui.begin(category.name(), ImGuiWindowFlags.NoResize);
            if (!categoryMap.get(category)) {
                ImGui.setWindowSize(250 * HaikuGui.guiWidth.get(), 300 * HaikuGui.guiHeight.get());
                categoryMap.put(category, true);
            }

            for (Module module : Haiku.getInstance().getModuleManager().getModulesByCategory(category)) {
                ImGui.checkbox(module.getName(), enabledMap.get(module));
                if (ImGui.isItemClicked(1)) showSettingsMap.put(module, !showSettingsMap.get(module));
                ImGui.sameLine(220);
                if (ImGui.isItemHovered()) ImGui.setTooltip(module.getDescription());
                ImGui.text(((module.settings.isEmpty()) ? "" : (showSettingsMap.get(module)) ? "^" : "v"));

                if (showSettingsMap.get(module)) {
                    for (Setting setting : module.settings) {
                        if (module.settings != null) {
                            if (setting instanceof BooleanSetting) {
                                ImGui.checkbox(setting.name, (ImBoolean) settingsMap.get(setting));
                                if (((BooleanSetting) setting).isEnabled() != ((ImBoolean) settingsMap.get(setting)).get())
                                    ((BooleanSetting) setting).setEnabled(((ImBoolean) settingsMap.get(setting)).get());
                            } else if (setting instanceof NumberSetting) {
                                ImGui.sliderFloat(setting.name, (float[]) settingsMap.get(setting), (float) ((NumberSetting) setting).getMinimum(),
                                        (float) ((NumberSetting) setting).getMaximum());
                                float[] temp = (float[]) settingsMap.get(setting);
                                if (temp[0] != (float) ((NumberSetting) setting).getValue())
                                    ((NumberSetting) setting).setValue(temp[0]);
                            } else if (setting instanceof ModeSetting) {
                                String[] temp = ((ModeSetting) setting).modes.toArray(new String[0]);
                                ImGui.combo(setting.name, (ImInt) settingsMap.get(setting), temp);
                                if (((ImInt) settingsMap.get(setting)).get() != ((ModeSetting) setting).modes.indexOf(((ModeSetting) setting).getMode()))
                                    ((ModeSetting) setting).setMode(((ModeSetting) setting).modes.get(((ImInt) settingsMap.get(setting)).get()));
                            } else if (setting instanceof KeybindSetting) {
                                if (binding) {
                                    ImGui.text("Press a key to bind");
                                    for (int i = 0; i < 512; i++) {
                                        if (ImGui.isKeyPressed(i)) {
                                            // If key is escape or backspace or delete, set value to -1
                                            if (i == GLFW.GLFW_KEY_ESCAPE || i == GLFW.GLFW_KEY_BACKSPACE || i == GLFW.GLFW_KEY_DELETE) {
                                                ((KeybindSetting) setting).setKeyCode(-1);
                                            } else ((KeybindSetting) setting).setKeyCode(i);

                                            binding = false;
                                        }
                                    }
                                } else {
                                    String name = ((KeybindSetting) setting).getKeyCode() < 0 ? "NONE"
                                            : InputUtil.fromKeyCode(((KeybindSetting) setting).getKeyCode(), -1).getLocalizedText().getString();
                                    if (ImGui.button("Bind: " + name)) binding = true;
                                }
                            }
                        }
                        if (ImGui.isItemHovered()) ImGui.setTooltip(setting.getDescription());
                    }
                }
                if (enabledMap.get(module).get() != module.isEnabled()) module.toggle();
            }
            ImGui.end();
        }
    }
}
// Some parts of the code is from vmod.
// Leaked lulw
