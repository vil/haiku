/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.mixin;

import dev.vili.haiku.Haiku;
import dev.vili.haiku.event.events.PacketEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {

    @Inject(method = "channelRead0*", at = @At("HEAD"), cancellable = true)
    private void packetReceive(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
        PacketEvent event = new PacketEvent(packet, PacketEvent.Type.RECEIVE);
        Haiku.getInstance().getEventBus().post(event);
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "send(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void packetSend(Packet<?> packet, CallbackInfo ci) {
        /* This is for the client commands */
        if (packet instanceof ChatMessageC2SPacket pack) {
            if (pack.chatMessage().startsWith(Haiku.getInstance().getCommandManager().prefix)) {
                Haiku.getInstance().getCommandManager().execute(pack.chatMessage());
                ci.cancel();
            }
        }

        PacketEvent event = new PacketEvent(packet, PacketEvent.Type.SEND);
        Haiku.getInstance().getEventBus().post(event);
        if (event.isCancelled()) ci.cancel();
    }

}
