/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.event.events;

import dev.vili.haiku.event.Event;
import net.minecraft.network.Packet;

@SuppressWarnings("rawtypes")
public class PacketEvent extends Event {
    private final Packet packet;
    private final Type type;

    public PacketEvent(Packet packet, Type type) {
        super();
        this.packet = packet;
        this.type = type;
    }

    /**
     * Returns the packet
     */
    public Packet getPacket() {
        return packet;
    }

    /**
     * Returns the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Event types
     */
    public enum Type {
        SEND,
        RECEIVE
    }

    /* Posted on ClientConnectionMixin */
}
