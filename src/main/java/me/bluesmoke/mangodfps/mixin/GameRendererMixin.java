package me.bluesmoke.mangodfps.mixin;

import me.bluesmoke.mangodfps.DynamicMenuFPSMod;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    // GameRenderer::render
    //  1.3-1.7 -> method_1331
    //  1.8-1.12 -> method_9775

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(
            method = {
                    "method_1331",
                    "method_9775"
            },
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true,
            remap = false
    )
    private void onRender(CallbackInfo ci) throws NoSuchFieldException, IllegalAccessException {

        // method_2965 -> Minecraft::getMinecraft, MinecraftClient::getInstance
        // field_3816 -> Minecraft#currentScreen, MinecraftClient#currentScreen
        Screen currentScreen = (Screen) DynamicMenuFPSMod.minecraftClient.getDeclaredField("field_3816").get(
                DynamicMenuFPSMod.minecraftClientInstance
        );

        if (currentScreen instanceof GameMenuScreen && !DynamicMenuFPSMod.checkForRender()) {
            ci.cancel();
        }
    }
}
