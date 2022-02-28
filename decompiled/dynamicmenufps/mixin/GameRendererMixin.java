/*    */ package dynamicmenufps.mixin;
/*    */ 
/*    */ import dynamicmenufps.DynamicMenuFPSMod;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_757;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_757.class})
/*    */ public class GameRendererMixin
/*    */ {
/*    */   @Shadow
/*    */   @Final
/*    */   private class_310 field_4015;
/*    */   
/*    */   @Inject(at = {@At("HEAD")}, method = {"render"}, cancellable = true)
/*    */   private void onRender(float tickDelta, long startTime, boolean tick, CallbackInfo callbackInfo) {
/* 24 */     if (this.field_4015.field_1755 instanceof net.minecraft.class_433 && !DynamicMenuFPSMod.checkForRender()) {
/* 25 */       callbackInfo.cancel();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(at = {@At("HEAD")}, method = {"renderWorld"}, cancellable = true)
/*    */   private void onRenderWorld(CallbackInfo callbackInfo) {
/* 34 */     if (this.field_4015.method_18506() instanceof net.minecraft.class_425)
/* 35 */       callbackInfo.cancel(); 
/*    */   }
/*    */ }