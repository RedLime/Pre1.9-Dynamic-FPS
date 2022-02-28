/*    */ package dynamicmenufps.mixin;
/*    */ 
/*    */ import java.util.concurrent.locks.LockSupport;
/*    */ import net.minecraft.class_1255;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Overwrite;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_1255.class})
/*    */ public final class ThreadExecutorMixin
/*    */ {
/*    */   @Overwrite
/*    */   public void method_20813() {
/* 18 */     LockSupport.parkNanos("waiting for tasks", 500000L);
/*    */   }
/*    */ }
