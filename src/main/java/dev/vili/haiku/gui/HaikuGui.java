/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.gui;

import dev.vili.haiku.Haiku;
import dev.vili.haiku.gui.tabs.ModuleTabs;
import dev.vili.haiku.module.Module;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

/**
 * Haiku's click gui.
 * Uses ImGui, because its based.
 */
public class HaikuGui extends Screen {
    MinecraftClient mc = MinecraftClient.getInstance();
    private final ImGuiImplGlfw implGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 implGl3 = new ImGuiImplGl3();

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
     * @param matrixStack
     * @param mouseX
     * @param mouseY
     * @param delta
     */
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        // Setup
        implGlfw.newFrame();
        ImGui.newFrame();

        // Styling
        ImGui.getIO().setConfigWindowsMoveFromTitleBarOnly(true);
        ImGui.getStyle().setColor(ImGuiCol.TitleBgActive, 255, 255, 255, 125);

        // Window
        if (ImGui.begin(Haiku.MOD_NAME + " " + Haiku.MOD_VERSION, ImGuiWindowFlags.NoResize)) {
            ImGui.setWindowSize(250, 120);
            ImGui.text("Welcome to Haiku!");
            ImGui.separator();
            ImGui.text("Haiku v" + Haiku.MOD_VERSION);
            ImGui.text("Minecraft " + SharedConstants.getGameVersion().getName());
            ImGui.separator();
            ImGui.text("vili.dev | vili#0001");

            // Render module tabs
            ModuleTabs.render();
        }

        // End window
        ImGui.end();

        // Render
        ImGui.render();
        implGl3.renderDrawData(ImGui.getDrawData());
        super.render(matrixStack, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        Haiku.getInstance().getModuleManager().getModule("Gui").setEnabled(false);
        return true;
    }

    @Override
    public void close() {
        mc.setScreen(null);
        Haiku.getInstance().getModuleManager().getModule("Gui").setEnabled(false);
        super.close();
    }
}
