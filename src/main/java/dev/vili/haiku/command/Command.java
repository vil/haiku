/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.command;

import net.minecraft.client.MinecraftClient;

import java.util.Arrays;
import java.util.List;

public abstract class Command {
    public static MinecraftClient mc = MinecraftClient.getInstance();
    public String name, description, syntax;
    public List<String> aliases;

    public Command(String name, String description, String syntax, String... aliases) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
        this.aliases = Arrays.asList(aliases);
    }

    /**
     * Called when the command is executed
     * @param args
     * @param command
     */
    public void onCommand(String[] args, String command) {}

    /**
     * Gets the command name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the command description
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the command syntax
     * @return
     */
    public String getSyntax() {
        return syntax;
    }

    /**
     * Gets the command aliases
     * @return
     */
    public List<String> getAliases() {
        return aliases;
    }

    /**
     * Sets the command name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the command description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the command syntax
     * @param syntax
     */
    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    /**
     * Sets the command aliases
     * @param aliases
     */
    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }
}
