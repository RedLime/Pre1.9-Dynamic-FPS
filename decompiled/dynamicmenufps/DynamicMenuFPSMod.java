/*    */ package dynamicmenufps;
/*    */ 
/*    */ import java.util.concurrent.locks.LockSupport;
/*    */ import javax.annotation.Nullable;
/*    */ import net.fabricmc.api.ModInitializer;
/*    */ import net.minecraft.class_1041;
/*    */ import net.minecraft.class_156;
/*    */ import net.minecraft.class_310;
/*    */ import org.lwjgl.glfw.GLFW;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DynamicMenuFPSMod
/*    */   implements ModInitializer
/*    */ {
/*    */   private static long lastRender;
/*    */   
/*    */   public void onInitialize() {}
/*    */   
/*    */   public static boolean checkForRender() {
/* 24 */     long currentTime = class_156.method_658();
/* 25 */     long timeSinceLastRender = currentTime - lastRender;
/*    */     
/* 27 */     if (!checkForRender(timeSinceLastRender)) return false;
/*    */     
/* 29 */     lastRender = currentTime;
/* 30 */     return true;
/*    */   }
/*    */   
/*    */   private static boolean checkForRender(long timeSinceLastRender) {
/* 34 */     Integer fpsOverride = fpsOverride();
/* 35 */     if (fpsOverride == null) {
/* 36 */       return true;
/*    */     }
/*    */     
/* 39 */     if (fpsOverride.intValue() == 0) {
/* 40 */       idle(1000L);
/* 41 */       return false;
/*    */     } 
/*    */     
/* 44 */     long frameTime = (1000 / fpsOverride.intValue());
/* 45 */     boolean shouldSkipRender = (timeSinceLastRender < frameTime);
/* 46 */     if (!shouldSkipRender) return true;
/*    */     
/* 48 */     idle(frameTime);
/* 49 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void idle(long waitMillis) {
/* 57 */     waitMillis = Math.min(waitMillis, 30L);
/* 58 */     LockSupport.parkNanos("waiting to render", waitMillis * 1000000L);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   private static Integer fpsOverride() {
/* 63 */     class_310 client = class_310.method_1551();
/* 64 */     class_1041 window = ((WindowHolder)client).getWindow();
/*    */     
/* 66 */     boolean isHovered = (GLFW.glfwGetWindowAttrib(window.method_4490(), 131083) != 0);
/* 67 */     if (isHovered) return null;
/*    */     
/* 69 */     if (!client.method_1569()) return Integer.valueOf(1);
/*    */     
/* 71 */     return null;
/*    */   }
/*    */   
/*    */   public static interface WindowHolder {
/*    */     class_1041 getWindow();
/*    */   }
/*    */ }
