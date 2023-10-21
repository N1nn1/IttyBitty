package com.ninni.itty_bitty.mixin;

import com.ninni.itty_bitty.registry.IttyBittyItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeMixin {


    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "getPickRange", at = @At("HEAD"), cancellable = true)
    public void collect(CallbackInfoReturnable<Float> cir) {
        if (this.minecraft.player.getMainHandItem().is(IttyBittyItems.GOLDEN_NET)) {
            cir.setReturnValue(6f);
        }
    }

    @Inject(method = "hasFarPickRange", at = @At("HEAD"), cancellable = true)
    public void collect2(CallbackInfoReturnable<Boolean> cir) {
        if (this.minecraft.player.getMainHandItem().is(IttyBittyItems.GOLDEN_NET)) cir.setReturnValue(true);
    }
}
