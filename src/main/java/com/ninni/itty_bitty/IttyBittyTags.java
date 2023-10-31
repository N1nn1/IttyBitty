package com.ninni.itty_bitty;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;

import static com.ninni.itty_bitty.IttyBitty.MOD_ID;

@SuppressWarnings("unused")
public interface IttyBittyTags {

    //Item tags
    TagKey<Item> NETS = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "nets"));
    TagKey<Item> COLLECTABLE_FISH_BUCKETS = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "collectable_fish_buckets"));
    TagKey<Item> LIVE_FISH = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "live_fish"));
    TagKey<Item> LIVE_BUGS = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "live_bugs"));

    //Biome tags
    TagKey<Biome> TETRA_SPAWNS = TagKey.create(Registries.BIOME, new ResourceLocation(MOD_ID, "tetra_spawns"));
    TagKey<Biome> CORYDORA_SPAWNS = TagKey.create(Registries.BIOME, new ResourceLocation(MOD_ID, "corydora_spawns"));
}
