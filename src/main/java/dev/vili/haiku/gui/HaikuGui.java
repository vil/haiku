/*
 * Copyright (c) 2023. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.gui;

import dev.vili.haiku.Haiku;
import dev.vili.haiku.gui.tabs.ModuleTabs;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImFloat;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

/**
 * Haiku's click gui.
 * Uses ImGui, because its based.
 */
public class HaikuGui extends Screen {
    public static final ImFloat guiHeight = new ImFloat(1.0f);
    public static final ImFloat guiWidth = new ImFloat(1.0f);
    private final ImGuiImplGlfw implGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 implGl3 = new ImGuiImplGl3();
    private final MinecraftClient mc = MinecraftClient.getInstance();

    public HaikuGui() {
        super(Text.literal("Haiku"));
        long windowHandle = mc.getWindow().getHandle();
        ImGui.createContext();
        implGlfw.init(windowHandle, true);
        implGl3.init();
    }

    /**
     * Should the game be paused when the gui is open?
     */
    @Override
    public boolean shouldPause() {
        return false;
    }

    /**
     * Renders the gui.
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

        // Settings
        // Add input typing
        ImGui.getIO().addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);

        // Styling
        ImGui.getIO().setConfigWindowsMoveFromTitleBarOnly(true);
        ImGui.getStyle().setColor(ImGuiCol.TitleBgActive, 0, 0, 0, 255);

        // Window
        if (ImGui.begin(Haiku.MOD_NAME + " " + Haiku.MOD_VERSION + " ~~Made by Vili", ImGuiWindowFlags.NoResize)) {
            ImGui.setWindowSize(250, 120);
            ImGui.text("Welcome to Haiku!");
            ImGui.separator();
            ImGui.text("Haiku v" + Haiku.MOD_VERSION);
            ImGui.text("Minecraft " + SharedConstants.getGameVersion().getName());
            ImGui.text("Cmd prefix: " + Haiku.getInstance().getCommandManager().prefix);

            // Sliders to scale the gui.
            ImGui.sliderFloat("Gui Height", guiHeight.getData(), 0.5f, 2.0f);
            ImGui.sliderFloat("Gui Width", guiWidth.getData(), 0.5f, 2.0f);

            // Set the gui scale.
            ImGui.setWindowSize(250 * guiWidth.get(), 120 * guiHeight.get());

            // Render module tabs
            ModuleTabs.render();
        }

        // End window
        ImGui.end();

        // Render
        ImGui.render();
        implGl3.renderDrawData(ImGui.getDrawData());
        super.render(context, mouseX, mouseY, delta);
    }

    /**
     * Should the gui close when the escape key is pressed?
     */
    @Override
    public boolean shouldCloseOnEsc() {
        Haiku.getInstance().getModuleManager().getModule("Gui").setEnabled(false);
        return true;
    }

    /**
     * Called when the gui is closed.
     */
    @Override
    public void close() {
        mc.setScreen(null);
        Haiku.getInstance().getModuleManager().getModule("Gui").setEnabled(false);
        super.close();
    }
}
