package com.ninni.itty_bitty.mixin;

import net.minecraft.world.entity.animal.Bee;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Bee.class)
public interface BeeAccessor {
    @Accessor
    int getTicksWithoutNectarSinceExitingHive();
    @Accessor
    int getStayOutOfHiveCountdown();
    @Accessor
    int getNumCropsGrownSincePollination();
}
