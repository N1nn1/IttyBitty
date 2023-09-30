package com.ninni.itty_bitty.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import static com.ninni.itty_bitty.IttyBitty.MOD_ID;

public interface IttyBittySoundEvents {

    SoundEvent FISH_AMBIENT = register("entity.fish.ambient");
    SoundEvent FISH_FLOP = register("entity.fish.flop");
    SoundEvent FISH_SWIM = register("entity.fish.swim");
    SoundEvent FISH_HURT = register("entity.fish.hurt");
    SoundEvent FISH_DEATH = register("entity.fish.death");

    static SoundEvent register(String name) {
        ResourceLocation id = new ResourceLocation(MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }
}
