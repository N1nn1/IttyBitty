package com.ninni.itty_bitty;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.ninni.itty_bitty.IttyBitty.MOD_ID;

@SuppressWarnings("unused")
public interface IttyBittyTags {

    //Item tags
    TagKey<Item> LIVE_FISH = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "live_fish"));
    TagKey<Item> LIVE_BUGS = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "live_bugs"));
}
