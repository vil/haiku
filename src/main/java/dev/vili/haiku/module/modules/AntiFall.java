/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.module.modules;

import dev.vili.haiku.event.events.MoveEvent;
import dev.vili.haiku.eventbus.HaikuSubscribe;
import dev.vili.haiku.module.Module;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.lwjgl.glfw.GLFW;

public class AntiFall extends Module {
    
    public AntiFall() {
        super("AntiFall", "Prevents fall damage.", GLFW.GLFW_KEY_UNKNOWN, Category.MOVEMENT);
    }
    
    @HaikuSubscribe
    public void onMove(MoveEvent event) {
        if (mc.player == null) return;
        
        // Prevent falldamage
        if (mc.player.fallDistance > 2.5f) {
			if (mc.player.isFallFlying()) return;
			mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
		}
    }
}