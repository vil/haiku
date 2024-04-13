/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.gui;

import dev.vili.haiku.Haiku;
import dev.vili.haiku.gui.tabs.LogsTab;
import dev.vili.haiku.gui.tabs.ModuleTabs;
import dev.vili.haiku.gui.tabs.GizmoTab;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImBoolean;
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
    public static boolean isOpen = false;
    public static final ImFloat guiHeight = new ImFloat(1.0f);
    public static final ImFloat guiWidth = new ImFloat(1.0f);
    public static final ImBoolean showGizmo = new ImBoolean(false);
    private final ImGuiImplGlfw implGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 implGl3 = new ImGuiImplGl3();
    private final MinecraftClient mc = MinecraftClient.getInstance();

    public HaikuGui() {
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

        // Window
        if (ImGui.begin("Gui", ImGuiWindowFlags.NoResize)) {
            ImGui.setWindowSize(250, 120);
            ImGui.text("Welcome to Haiku!");
            ImGui.separator();
            ImGui.text("Haiku v" + Haiku.MOD_VERSION);
            ImGui.text("Minecraft " + SharedConstants.getGameVersion().getName());
            ImGui.text("Cmd prefix: " + Haiku.getInstance().getCommandManager().prefix);

            // Sliders to scale the gui.
            ImGui.sliderFloat("Gui Height", guiHeight.getData(), 0.5f, 2.0f);
            ImGui.sliderFloat("Gui Width", guiWidth.getData(), 0.5f, 2.0f);
            ImGui.checkbox("Show Gizmo", showGizmo);

            // Set the gui scale.
            ImGui.setWindowSize(250 * guiWidth.get(), 120 * guiHeight.get());

            // Render module tabs
            ModuleTabs.render();
            // Render log tab
            LogsTab.render();
            // Render gizmo
            if (showGizmo.get()) GizmoTab.render();
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
        HaikuGui.isOpen = false;
        mc.setScreen(null);
        implGl3.dispose();
        implGlfw.dispose();
        super.close();
    }
}
