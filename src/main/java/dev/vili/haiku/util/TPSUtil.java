/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.util;

import dev.vili.haiku.event.events.PacketEvent;
import dev.vili.haiku.eventbus.HaikuSubscribe;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.util.math.MathHelper;

public class TPSUtil {
    public static TPSUtil INSTANCE = new TPSUtil();
    private static double ticks = 0;
    private static long prevTime = 0;

    @HaikuSubscribe
    public void onPacket(PacketEvent event) {
        if (event.getType() == PacketEvent.Type.RECEIVE) {
            if (event.getPacket() instanceof WorldTimeUpdateS2CPacket) {
                long time = System.currentTimeMillis();
                long timeOffset = Math.abs(1000 - (time - prevTime)) + 1000;
                ticks = (MathHelper.clamp(20 / (timeOffset / 1000d), 0, 20) * 100d) / 100d;
                prevTime = time;
            }
        }
    }


    /**
     * Returns the ticks per-second.
     *
     * @return ticks
     */
    public double getTPS() {
        // Return TPS with 2 decimal places
        return Math.round(ticks * 100d) / 100d;
    }
}
