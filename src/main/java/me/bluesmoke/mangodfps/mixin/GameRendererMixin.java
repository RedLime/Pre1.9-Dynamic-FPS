package me.bluesmoke.mangodfps.mixin;

import me.bluesmoke.mangodfps.DynamicMenuFPSMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow
    private MinecraftClient client;

    //  1.7.x - method_1331
    //  1.8-1.12.2 - method_9775
    @Inject(method = {"method_1331", "method_9775"}, at = @At("HEAD"), cancellable = true, remap = false)
    private void onRender(CallbackInfo ci) {
        if (this.client.currentScreen instanceof GameMenuScreen && !DynamicMenuFPSMod.checkForRender()) {
            ci.cancel();
        }
    }
}
