/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.gui;

import dev.vili.haiku.Haiku;
import dev.vili.haiku.gui.tabs.LogsTab;
import dev.vili.haiku.module.Module;
import dev.vili.haiku.setting.Setting;
import dev.vili.haiku.setting.settings.*;
import dev.vili.haiku.util.HaikuLogger;
import imgui.ImGui;
import imgui.flag.*;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImBoolean;
import imgui.type.ImInt;
import imgui.type.ImString;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;

/**
 * Haiku's other click gui.
 * One window, no tabs.
 */
public class HaikuOneGui extends Screen {
    public static boolean isOpen = false;
    private static final HashMap<Setting, Object> settingsMap = new HashMap<>();
    private final HashMap<Module, ImBoolean> enabledMap = new HashMap<>();
    private final ImGuiImplGlfw implGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 implGl3 = new ImGuiImplGl3();
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private static boolean binding;
    private Module activeModule = null;
    private Module.Category selectedCategory;

    public HaikuOneGui() {
        super(Text.literal("Haiku"));
        long windowHandle = mc.getWindow().getHandle();
        ImGui.createContext();
        implGlfw.init(windowHandle, true);
        implGl3.init();

        // Settings
        ImGui.getIO().addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        ImGui.getIO().setConfigWindowsMoveFromTitleBarOnly(true);
        ImGui.getStyle().setColor(ImGuiCol.TitleBgActive, 0, 0, 0, 255);
    }

    /**
     * Should the game be paused when the gui is open?
     */
    @Override
    public boolean shouldPause() {
        return false;
    }


    /**
     * Renders the GUI.
     *
     * @param context The context to render in
     * @param mouseX  The mouse x position
     * @param mouseY  The mouse y position
     * @param delta   The delta time
     */
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Setup
        implGlfw.newFrame();
        ImGui.newFrame();

        // Window
        if (ImGui.begin("OneGui", ImGuiWindowFlags.NoResize)) {
            ImGui.setWindowSize(800, 600);
            ImGui.text("Minecraft " + SharedConstants.getGameVersion().getName() + " (" + SharedConstants.getGameVersion().getId() + ")" + " | " + mc.getSession().getUsername());
            ImGui.text("FPS: " + mc.fpsDebugString.split(" ")[0]);

            // Sidebar with module categories
            ImGui.beginChild("Categories", 200, 0, true);

            for (Module.Category category : Module.Category.values()) {
                if (ImGui.selectable(category.name(), category == selectedCategory)) {
                    selectedCategory = category;
                }
            }


            ImGui.endChild();

            // Main portion displaying modules for the selected category
            ImGui.sameLine();
            ImGui.beginChild("Modules");

            renderCategoryModules(selectedCategory);

            ImGui.endChild();

            // Render log tab
            LogsTab.render();
        }

        // End window
        ImGui.end();

        // Render
        ImGui.render();
        implGl3.renderDrawData(ImGui.getDrawData());

        // Draw info on the bottom right of the screen
        context.drawTextWithShadow(textRenderer, "Enable debug to see logs tab.",
                width - textRenderer.getWidth("Enable debug to see logs tab.") - 2, height - textRenderer.fontHeight - 2, 0xFFFFFFFF);

        super.render(context, mouseX, mouseY, delta);
    }

    /**
     * Renders the modules for a given category.
     *
     * @param category The category to render modules for
     */
    private void renderCategoryModules(Module.Category category) {
        for (Module module : Haiku.getInstance().getModuleManager().getModulesByCategory(category)) {
            enabledMap.put(module, new ImBoolean(module.isEnabled()));

            if (ImGui.collapsingHeader(module.getName(), ImGuiTreeNodeFlags.CollapsingHeader)) {
                ImGui.indent();
                renderModule(module);
                ImGui.unindent();
            }
            if (ImGui.isItemHovered()) {
                ImGui.setTooltip(module.getDescription());
            }
        }
    }

    /**
     * Renders a module and its settings.
     *
     * @param module The module to render
     */
    private void renderModule(Module module) {
        ImGui.checkbox("Toggle " + module.getName(), enabledMap.get(module));
        if (enabledMap.get(module).get() != module.isEnabled()) {
            module.toggle();
        }

        for (Setting setting : module.settings) {
            switch (setting.getClass().getSimpleName()) {
                case "BooleanSetting" -> {
                    BooleanSetting booleanSetting = (BooleanSetting) setting;
                    ImBoolean booleanValue = (ImBoolean) settingsMap.getOrDefault(setting, new ImBoolean(booleanSetting.isEnabled()));
                    settingsMap.put(setting, booleanValue);
                    ImGui.checkbox(setting.getName(), booleanValue);
                    if (booleanSetting.isEnabled() != booleanValue.get()) {
                        booleanSetting.setEnabled(booleanValue.get());
                    }
                }
                case "NumberSetting" -> {
                    NumberSetting numberSetting = (NumberSetting) setting;
                    float[] floatValue = (float[]) settingsMap.getOrDefault(setting, new float[]{(float) numberSetting.getValue()});
                    settingsMap.put(setting, floatValue);
                    ImGui.sliderFloat(setting.getName(), floatValue, (float) numberSetting.getMinimum(),
                            (float) numberSetting.getMaximum());
                    if (floatValue[0] != (float) numberSetting.getValue()) {
                        numberSetting.setValue(floatValue[0]);
                    }
                }
                case "ModeSetting" -> {
                    ModeSetting modeSetting = (ModeSetting) setting;
                    ImInt modeIndex = (ImInt) settingsMap.getOrDefault(setting, new ImInt(modeSetting.modes.indexOf(modeSetting.getMode())));
                    settingsMap.put(setting, modeIndex);
                    String[] modeNames = modeSetting.modes.toArray(new String[0]);
                    ImGui.combo(setting.getName(), modeIndex, modeNames);
                    if (modeIndex.get() != modeSetting.modes.indexOf(modeSetting.getMode())) {
                        modeSetting.setMode(modeSetting.modes.get(modeIndex.get()));
                    }
                }
                case "KeybindSetting" -> {
                    KeybindSetting keybindSetting = (KeybindSetting) setting;
                    if (binding && activeModule == module) { // Check if the module is active for keybinding changes
                        ImGui.text("Press a key to bind");
                        for (int i = 0; i < 512; i++) {
                            if (ImGui.isKeyPressed(i)) {
                                if (i == GLFW.GLFW_KEY_ESCAPE || i == GLFW.GLFW_KEY_BACKSPACE || i == GLFW.GLFW_KEY_DELETE) {
                                    keybindSetting.setKeyCode(-1);
                                } else {
                                    keybindSetting.setKeyCode(i);
                                }
                                binding = false;
                            }
                        }
                    } else {
                        String name = keybindSetting.getKeyCode() < 0 ? "NONE"
                                : InputUtil.fromKeyCode(keybindSetting.getKeyCode(), -1).getLocalizedText().getString();
                        if (ImGui.button("Bind: " + name)) {
                            activeModule = module; // Set the active module for keybind changes
                            binding = true;
                        }
                    }
                }
                // TODO This still doesn't work properly. If gui is reopened, it doesn't allow typing or interacting with it.
                case "StringSetting" -> {
                    StringSetting stringSetting = (StringSetting) setting;
                    ImString stringValue = (ImString) settingsMap.getOrDefault(setting, new ImString(stringSetting.getString()));

                    if (ImGui.inputText(setting.getName(), stringValue, ImGuiInputTextFlags.CallbackResize)) {
                        settingsMap.put(setting, stringValue);
                        stringSetting.setString(stringValue.get());
                    }
                }
                default -> HaikuLogger.logger.warn("Unknown setting type: " + setting.getClass().getSimpleName());
            }
            if (ImGui.isItemHovered()) {
                ImGui.setTooltip(setting.getDescription());
            }
        }
    }

    /**
     * Should the gui close when the escape key is pressed?
     */
    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    /**
     * Called when the gui is closed.
     */
    @Override
    public void close() {
        HaikuOneGui.isOpen = false;
        mc.setScreen(null);
        implGl3.dispose();
        implGlfw.dispose();
        super.close();
    }
}
