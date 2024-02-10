package com.ninni.itty_bitty.registry;

import com.ninni.itty_bitty.IttyBitty;
import com.ninni.itty_bitty.item.CollectedMobItem;
import com.ninni.itty_bitty.item.NetItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;

public class IttyBittyItems {

    public static final Item KELPLASTIC = register("kelplastic", new Item(new Item.Properties()));

    public static final Item NET = register("net", new NetItem(false, new Item.Properties().stacksTo(1)));
    public static final Item GOLDEN_NET = register("golden_net", new NetItem(true, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final Item BUBBLEBOX = register("bubblebox", new BlockItem(IttyBittyBlocks.BUBBLEBOX, new Item.Properties().stacksTo(1)));
    public static final Item BUGBOX = register("bugbox", new BlockItem(IttyBittyBlocks.BUGBOX, new Item.Properties().stacksTo(1)));

    public static final Item CORYDORA_SPAWN_EGG = register("corydora_spawn_egg", new SpawnEggItem(IttyBittyEntityType.CORYDORA, 0x263434, 0x937B6C, new Item.Properties()));
    public static final Item CORYDORA_BUCKET = register("corydora_bucket", new MobBucketItem(IttyBittyEntityType.CORYDORA, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));

    public static final Item TETRA_SPAWN_EGG = register("tetra_spawn_egg", new SpawnEggItem(IttyBittyEntityType.TETRA, 0xD8422E, 0x0EF9F4, new Item.Properties()));
    public static final Item TETRA_BUCKET = register("tetra_bucket", new MobBucketItem(IttyBittyEntityType.TETRA, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));

    public static final Item LIVE_COD = register("live_cod", new CollectedMobItem(EntityType.COD, true, new Item.Properties().stacksTo(1)));
    public static final Item LIVE_SALMON = register("live_salmon", new CollectedMobItem(EntityType.SALMON, true, new Item.Properties().stacksTo(1)));
    public static final Item LIVE_PUFFERFISH = register("live_pufferfish", new CollectedMobItem(EntityType.PUFFERFISH, true, new Item.Properties().stacksTo(1)));
    public static final Item LIVE_TROPICAL_FISH = register("live_tropical_fish", new CollectedMobItem(EntityType.TROPICAL_FISH, true, new Item.Properties().stacksTo(1)));
    public static final Item LIVE_TADPOLE = register("live_tadpole", new CollectedMobItem(EntityType.TADPOLE, true, new Item.Properties().stacksTo(1)));
    public static final Item LIVE_AXOLOTL = register("live_axolotl", new CollectedMobItem(EntityType.AXOLOTL, true, new Item.Properties().stacksTo(1)));
    public static final Item LIVE_CORYDORA = register("live_corydora", new CollectedMobItem(IttyBittyEntityType.CORYDORA, true, new Item.Properties().stacksTo(1)));
    public static final Item LIVE_TETRA = register("live_tetra", new CollectedMobItem(IttyBittyEntityType.TETRA, true, new Item.Properties().stacksTo(1)));

    public static final Item LIVE_SILVERFISH = register("live_silverfish", new CollectedMobItem(EntityType.SILVERFISH, false, new Item.Properties().stacksTo(1)));
    public static final Item LIVE_ENDERMITE = register("live_endermite", new CollectedMobItem(EntityType.ENDERMITE, false, new Item.Properties().stacksTo(1)));
    public static final Item LIVE_BEE = register("live_bee", new CollectedMobItem(EntityType.BEE, false, new Item.Properties().stacksTo(1)));

    private static Item register(String id, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IttyBitty.MOD_ID, id), item);
    }
}
