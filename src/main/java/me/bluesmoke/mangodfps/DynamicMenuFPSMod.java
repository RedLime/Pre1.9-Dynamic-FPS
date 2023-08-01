package me.bluesmoke.mangodfps;

import net.fabricmc.api.ClientModInitializer;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.locks.LockSupport;

public class DynamicMenuFPSMod implements ClientModInitializer {
    public static Class<?> minecraftClient;
    public static Object minecraftClientInstance;
    private static long lastRender;

    public static boolean checkForRender() {
        long currentTime = System.nanoTime() / 1000000L;
        long timeSinceLastRender = currentTime - lastRender;

        if (!checkForRender(timeSinceLastRender)) return false;

        lastRender = currentTime;
        return true;
    }

    private static boolean checkForRender(long timeSinceLastRender) {
        Integer fpsOverride = fpsOverride();
        if (fpsOverride == null) {
            return true;
        }

        if (fpsOverride == 0) {
            idle(1000L);
            return false;
        }

        long frameTime = (1000 / fpsOverride);
        boolean shouldSkipRender = (timeSinceLastRender < frameTime);
        if (!shouldSkipRender) return true;

        idle(frameTime);
        return false;
    }

    private static void idle(long waitMillis) {
        waitMillis = Math.min(waitMillis, 30L);
        LockSupport.parkNanos("waiting to render", waitMillis * 1000000L);
    }

    @Nullable
    private static Integer fpsOverride() {
        if (Mouse.isInsideWindow()) return null;
        if (!Display.isActive()) return 1;
        return null;
    }

    @Override
    public void onInitializeClient() {
        try {
            // 1.6-1.12
            // class_1600 -> MinecraftClient
            DynamicMenuFPSMod.minecraftClient = Class.forName("net.minecraft.class_1600");
        } catch (ClassNotFoundException e) {
            try {
                // 1.3-1.5
                DynamicMenuFPSMod.minecraftClient = Class.forName("net.minecraft.client.Minecraft");
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException("net.minecraft.client.Minecraft / MinecraftClient class not found", ex);
            }
        }
        try {
            DynamicMenuFPSMod.minecraftClientInstance = DynamicMenuFPSMod.minecraftClient.getMethod("method_2965").invoke(null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("MinecraftClient::getInstance / Minecraft::getMinecraft method not found", e);
        }
    }
}