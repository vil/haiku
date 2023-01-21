/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.util;

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
     * @param s
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
      * @param s
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
     * @param s
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
     * @param s
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
        return Text.literal("haiku$~ ").styled(s -> s.withColor(color));
    }
}
