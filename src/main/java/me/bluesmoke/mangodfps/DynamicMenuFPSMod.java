package me.bluesmoke.mangodfps;

import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.util.concurrent.locks.LockSupport;

public class DynamicMenuFPSMod {
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
}