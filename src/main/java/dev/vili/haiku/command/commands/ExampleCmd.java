/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.command.commands;

import dev.vili.haiku.command.Command;

public class ExampleCmd extends Command {

    public ExampleCmd() {
        super("Example", "Example command.", "example", "e");
    }


    @Override
    public void onCommand(String[] args, String command) {
        /* Command code here. */
    }
}
