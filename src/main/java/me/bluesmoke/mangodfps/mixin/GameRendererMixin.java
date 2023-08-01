package me.bluesmoke.mangodfps.mixin;

import me.bluesmoke.mangodfps.DynamicMenuFPSMod;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.InvocationTargetException;

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
    private void onRender(CallbackInfo ci) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> minecraftClient;
        try {
            // 1.6-1.12
            // class_1600 -> MinecraftClient
            minecraftClient = Class.forName("net.minecraft.class_1600");
        } catch (ClassNotFoundException e) {
            // 1.3-1.5
            minecraftClient = Class.forName("net.minecraft.client.Minecraft");
        }
        // method_2965 -> Minecraft::getMinecraft, MinecraftClient::getInstance
        // field_3816 -> Minecraft#currentScreen, MinecraftClient#currentScreen
        Screen currentScreen = (Screen) minecraftClient.getDeclaredField("field_3816").get(
                minecraftClient.getMethod("method_2965").invoke(null)
        );

        if (currentScreen instanceof GameMenuScreen && !DynamicMenuFPSMod.checkForRender()) {
            ci.cancel();
        }
    }
}
