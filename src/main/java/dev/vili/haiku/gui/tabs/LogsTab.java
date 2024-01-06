/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.gui.tabs;

import dev.vili.haiku.Haiku;
import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import net.minecraft.client.MinecraftClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogsTab {
    // Get the latest log file
    private static final File MINECRAFT_LOG_FILE = new File(MinecraftClient.getInstance().runDirectory, "logs/latest.log");

    /**
     * Renders the game logs tab only if F3 debug menu is enabled.
     */
    public static void render() {
        if (!Haiku.mc.getDebugHud().shouldShowDebugHud()) return; // Render only if debug mode is enabled

        ImGui.setWindowSize(600, 400);
        ImGui.begin("Logs", new ImBoolean(true), ImGuiWindowFlags.NoCollapse);

        ImGui.text("Current Time: " + getLocalTime());
        ImGui.separator();

        try {
            // Read Minecraft's log file
            BufferedReader reader = new BufferedReader(new FileReader(MINECRAFT_LOG_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                ImGui.text(line);
            }
            reader.close();
        } catch (IOException e) {
            // Handle file reading errors
            ImGui.text("Error reading Minecraft log file: " + e.getMessage());
        }

        ImGui.end();
    }

    /**
     * Gets the current system time in the HH:mm:ss format.
     *
     * @return formatted time (HH:mm:ss).
     */
    private static String getLocalTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
}
