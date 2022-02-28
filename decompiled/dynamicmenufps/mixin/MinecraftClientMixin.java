/*    */ package dynamicmenufps.mixin;
/*    */ 
/*    */ import dynamicmenufps.DynamicMenuFPSMod;
/*    */ import net.minecraft.class_1041;
/*    */ import net.minecraft.class_310;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ @Mixin({class_310.class})
/*    */ public class MinecraftClientMixin
/*    */   implements DynamicMenuFPSMod.WindowHolder {
/*    */   @Shadow
/*    */   @Final
/*    */   private class_1041 field_1704;
/*    */   
/*    */   public class_1041 getWindow() {
/* 18 */     return this.field_1704;
/*    */   }
/*    */ }
