/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
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
     * @return tick-rate
     */
    public double getTPS() {
        return ticks;
    }
}
