/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.gui;

import dev.vili.haiku.Haiku;
import dev.vili.haiku.gui.tabs.ModuleTabs;
import dev.vili.haiku.module.Module;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImFloat;
import imgui.type.ImInt;
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
    public static final ImFloat guiHeight = new ImFloat(1.0f);
    public static final ImFloat guiWidth = new ImFloat(1.0f);

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

        // Settings
        // Add input typing
        ImGui.getIO().addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);

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
            ImGui.text("Cmd prefix: " + Haiku.getInstance().getCommandManager().prefix);

            // Sliders to scale the gui.
            ImGui.sliderFloat("Gui Height", guiHeight.getData(), 0.5f, 2.0f);
            ImGui.sliderFloat("Gui Width", guiWidth.getData(), 0.5f, 2.0f);

            // Set the gui scale.
            ImGui.setWindowSize(250 * guiWidth.get(), 120 * guiHeight.get());

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
