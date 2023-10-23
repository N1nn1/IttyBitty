package com.ninni.itty_bitty.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import static com.ninni.itty_bitty.IttyBitty.MOD_ID;

public interface IttyBittySoundEvents {

//Generic Fish sounds
    SoundEvent FISH_AMBIENT = register("entity.fish.ambient");
    SoundEvent FISH_FLOP = register("entity.fish.flop");
    SoundEvent FISH_SWIM = register("entity.fish.swim");
    SoundEvent FISH_HURT = register("entity.fish.hurt");
    SoundEvent FISH_DEATH = register("entity.fish.death");

//Collectable Mob sounds
    SoundEvent COLLECT_FISH = register("item.net.fish");
    SoundEvent COLLECT_TADPOLE = register("item.net.tadpole");
    SoundEvent COLLECT_AXOLOTL = register("item.net.axolotl");

    SoundEvent COLLECT_BUG = register("item.net.bug");
    SoundEvent COLLECT_SILVERFISH = register("item.net.silverfish");
    SoundEvent COLLECT_ENDERMITE = register("item.net.endermite");

    SoundEvent COLLECT_FAIL = register("item.net.fail");

    SoundEvent RELEASE_BUG = register("item.collected_mob.release_bug");
    SoundEvent RELEASE_SILVERFISH = register("item.collected_mob.release_silverfish");
    SoundEvent RELEASE_ENDERMITE = register("item.collected_mob.release_endermite");

    static SoundEvent register(String name) {
        ResourceLocation id = new ResourceLocation(MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }
}
