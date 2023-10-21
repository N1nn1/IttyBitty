package com.ninni.itty_bitty.mixin;

import net.minecraft.world.entity.animal.Bee;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Bee.class)
public interface BeeAccessor {
    @Accessor
    int getNumCropsGrownSincePollination();
    @Accessor
    int getStayOutOfHiveCountdown();
    @Accessor
    int getTicksWithoutNectarSinceExitingHive();

    @Invoker
    void callSetHasNectar(boolean bl);
}
