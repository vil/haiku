/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.util;

import dev.vili.haiku.Haiku;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HaikuLogger {
    public static final Logger logger = LogManager.getFormatterLogger("haiku");
    public static int INFO_COLOR = Formatting.LIGHT_PURPLE.getColorValue();
    public static int WARN_COLOR = Formatting.YELLOW.getColorValue();
    public static int ERROR_COLOR = Formatting.RED.getColorValue();

    /**
     * Logs a message to the console or the chat.
     *
     * @param s The message to log.
     */

    public static void info(String s) {
        info(Text.literal(s));
    }

    public static void info(Text t) {
        try {
            MinecraftClient.getInstance().inGameHud.getChatHud()
                    .addMessage(getText(INFO_COLOR)
                            .append(((MutableText) t).styled(s -> s.withColor(INFO_COLOR))));
        } catch (Exception e) {
            logger.log(Level.INFO, t.getString());
        }
    }

    /**
     * Logs a warning to the console or the chat.
     *
     * @param s The warning to log.
     */

    public static void warn(String s) {
        warn(Text.literal(s));
    }

    public static void warn(Text t) {
        try {
            MinecraftClient.getInstance().inGameHud.getChatHud()
                    .addMessage(getText(WARN_COLOR)
                            .append(((MutableText) t).styled(s -> s.withColor(WARN_COLOR))));
        } catch (Exception e) {
            logger.log(Level.WARN, t.getString());
        }
    }

    /**
     * Logs an error to the console or the chat.
     *
     * @param s The error to log.
     */

    public static void error(String s) {
        error(Text.literal(s));
    }

    public static void error(Text t) {
        try {
            MinecraftClient.getInstance().inGameHud.getChatHud()
                    .addMessage(getText(ERROR_COLOR)
                            .append(((MutableText) t).styled(s -> s.withColor(ERROR_COLOR))));
        } catch (Exception e) {
            logger.log(Level.ERROR, t.getString());
        }
    }

    /**
     * Logs a message to the console or the chat without the prefix.
     *
     * @param s The message to log.
     */
    public static void noPrefix(String s) {
        noPrefix(Text.literal(s));
    }

    public static void noPrefix(Text text) {
        try {
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(text);
        } catch (Exception e) {
            logger.log(Level.INFO, text.getString());
        }
    }

    private static MutableText getText(int color) {
        return Text.literal(Formatting.GREEN + Haiku.mc.getSession().getUsername() + "@" + Haiku.MOD_NAME + Formatting.BLUE + " ~ $  ").styled(s -> s.withColor(color));
    }
}
