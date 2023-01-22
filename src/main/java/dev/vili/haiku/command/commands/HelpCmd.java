/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.command.commands;

import dev.vili.haiku.Haiku;
import dev.vili.haiku.command.Command;
import dev.vili.haiku.util.HaikuLogger;

import java.util.stream.Collectors;

public class HelpCmd extends Command {

    public HelpCmd() {
        super("Help", "Shows a list of commands", "help", "h");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length == 0) {
            HaikuLogger.info("Commands: " + Haiku.getInstance().getCommandManager().commands.stream()
                            .map(Command::getName).collect(Collectors.joining(", ")));
        } else {
            for (Command cmd : Haiku.getInstance().getCommandManager().commands) {
                if (cmd.getName().equalsIgnoreCase(args[0])) {
                    HaikuLogger.info(cmd.getSyntax());
                    return;
                }
            }
            HaikuLogger.error("Command not found.");
        }
    }
}
