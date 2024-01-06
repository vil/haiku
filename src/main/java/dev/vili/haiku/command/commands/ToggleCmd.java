/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.command.commands;

import dev.vili.haiku.Haiku;
import dev.vili.haiku.command.Command;
import dev.vili.haiku.module.Module;
import dev.vili.haiku.util.HaikuLogger;

public class ToggleCmd extends Command {

    public ToggleCmd() {
        super("Toggle", "Toggle a module.", "toggle <mod>", "t");
    }
    
    
    @Override
    public void onCommand(String[] args, String command) {
        if (args.length == 0) {
            HaikuLogger.error("Please specify a module.");
            return;
        }

        String moduleName = args[0];
        Module module = Haiku.getInstance().getModuleManager().getModule(String.valueOf(moduleName));

        if (module == null) {
            HaikuLogger.error("Module not found.");
            return;
        }

        module.toggle();
    }
}