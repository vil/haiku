/*
 * Copyright (c) 2023. Vili (https://vili.dev) - All rights reserved
 */

package dev.vili.haiku.mixin;

import dev.vili.haiku.Haiku;
import dev.vili.haiku.event.events.KeyEvent;
import dev.vili.haiku.module.Module;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {

    @Inject(method = "onKey", at = @At(value = "INVOKE", target = "net/minecraft/client/util/InputUtil.isKeyPressed(JI)Z", ordinal = 5), cancellable = true)
    private void onKey(long window, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
        if (Haiku.mc.currentScreen != null) return;
        if (action == 2) action = 1;
        switch (action) {
            case 0 -> {
                KeyEvent event = new KeyEvent(key, scanCode, KeyEvent.Status.RELEASED);
                Haiku.getInstance().getEventBus().post(event);
                if (event.isCancelled()) ci.cancel();
            }
            case 1 -> {
                KeyEvent event = new KeyEvent(key, scanCode, KeyEvent.Status.PRESSED);
                Haiku.getInstance().getModuleManager().getModules().stream().filter(m -> m.getKey() == key).forEach(Module::toggle);
                Haiku.getInstance().getEventBus().post(event);
                if (event.isCancelled()) ci.cancel();
            }
        }
    }
}
