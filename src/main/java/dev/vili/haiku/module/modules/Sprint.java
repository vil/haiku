/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.module.modules;

import dev.vili.haiku.event.events.TickEvent;
import dev.vili.haiku.eventbus.HaikuSubscribe;
import dev.vili.haiku.module.Module;
import org.lwjgl.glfw.GLFW;

/* Example module */
public class Sprint extends Module {

    public Sprint() {
        super("Sprint", "Automatically sprints for you.", GLFW.GLFW_KEY_R, Category.MOVEMENT);
    }

    @HaikuSubscribe
    public void onTick(TickEvent event) {
        if (mc.world == null || mc.player == null) return;

        if (mc.player.forwardSpeed > 0 && !mc.player.horizontalCollision && !mc.player.isSneaking()) {
            mc.player.setSprinting(true);
        }
    }
}
