package com.ninni.itty_bitty.registry;

import com.ninni.itty_bitty.IttyBitty;
import com.ninni.itty_bitty.item.CollectedMobItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.material.Fluids;

public class IttyBittyItems {

    public static final Item TETRA_SPAWN_EGG = register("tetra_spawn_egg", new SpawnEggItem(IttyBittyEntityType.TETRA, 0xD8422E, 0x0EF9F4, new Item.Properties()));
    public static final Item TETRA_BUCKET = register("tetra_bucket", new MobBucketItem(IttyBittyEntityType.TETRA, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));

    public static final Item NET = register("net", new Item(new Item.Properties().stacksTo(1)));
    public static final Item BUBBLEBOX = register("bubblebox", new BlockItem(IttyBittyBlocks.BUBBLEBOX, new Item.Properties().stacksTo(1)));

    public static final Item LIVE_COD = register("live_cod", new CollectedMobItem(EntityType.COD, true, new Item.Properties().stacksTo(1)));
    public static final Item LIVE_SALMON = register("live_salmon", new CollectedMobItem(EntityType.SALMON, true, new Item.Properties().stacksTo(1)));
    public static final Item LIVE_PUFFERFISH = register("live_pufferfish", new CollectedMobItem(EntityType.PUFFERFISH, true, new Item.Properties().stacksTo(1)));
    public static final Item LIVE_TROPICAL_FISH = register("live_tropical_fish", new CollectedMobItem(EntityType.TROPICAL_FISH, true, new Item.Properties().stacksTo(1)));
    public static final Item LIVE_TETRA = register("live_tetra", new CollectedMobItem(IttyBittyEntityType.TETRA, true, new Item.Properties().stacksTo(1)));

    private static Item register(String id, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IttyBitty.MOD_ID, id), item);
    }
}
