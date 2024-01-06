/*
 * Copyright (c) 2024. Vili and contributors.
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 *  file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package dev.vili.haiku.mixin;

import dev.vili.haiku.Haiku;
import dev.vili.haiku.gui.HaikuGui;
import dev.vili.haiku.gui.HaikuOneGui;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {

    @Inject(method = "renderInGameBackground", at = @At("HEAD"), cancellable = true)
    private void renderInGameBackground(DrawContext context, CallbackInfo ci) {
        /*
         * Cancel the dark gradient from rendering in the gui.
         */
        if (Haiku.mc.currentScreen instanceof HaikuGui || Haiku.mc.currentScreen instanceof HaikuOneGui) {
            ci.cancel();
        }
    }
}
