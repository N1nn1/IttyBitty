package com.ninni.itty_bitty.entity.collectables;

import com.ninni.itty_bitty.registry.IttyBittyEntityType;
import com.ninni.itty_bitty.registry.IttyBittyItems;
import com.ninni.itty_bitty.registry.IttyBittySoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;


public enum IttyBittyFishCollectables {
    COD(EntityType.COD, IttyBittyItems.LIVE_COD, Items.COD_BUCKET, SoundEvents.BUCKET_FILL_FISH, SoundEvents.BUCKET_EMPTY_FISH, IttyBittySoundEvents.COLLECT_FISH),
    SALMON(EntityType.SALMON, IttyBittyItems.LIVE_SALMON, Items.SALMON_BUCKET, SoundEvents.BUCKET_FILL_FISH, SoundEvents.BUCKET_EMPTY_FISH, IttyBittySoundEvents.COLLECT_FISH),
    PUFFERFISH(EntityType.PUFFERFISH, IttyBittyItems.LIVE_PUFFERFISH, Items.PUFFERFISH_BUCKET, SoundEvents.BUCKET_FILL_FISH, SoundEvents.BUCKET_EMPTY_FISH, IttyBittySoundEvents.COLLECT_FISH),
    TROPICAL_FISH(EntityType.TROPICAL_FISH, IttyBittyItems.LIVE_TROPICAL_FISH, Items.TROPICAL_FISH_BUCKET, SoundEvents.BUCKET_FILL_FISH, SoundEvents.BUCKET_EMPTY_FISH, IttyBittySoundEvents.COLLECT_FISH),
    AXOLOTL(EntityType.AXOLOTL, IttyBittyItems.LIVE_AXOLOTL, Items.AXOLOTL_BUCKET, SoundEvents.BUCKET_FILL_AXOLOTL, SoundEvents.BUCKET_EMPTY_AXOLOTL, IttyBittySoundEvents.COLLECT_AXOLOTL),
    TADPOLE(EntityType.TADPOLE, IttyBittyItems.LIVE_TADPOLE, Items.TADPOLE_BUCKET, SoundEvents.BUCKET_FILL_TADPOLE, SoundEvents.BUCKET_EMPTY_TADPOLE, IttyBittySoundEvents.COLLECT_TADPOLE),
    TETRA(IttyBittyEntityType.TETRA, IttyBittyItems.LIVE_TETRA, IttyBittyItems.TETRA_BUCKET, SoundEvents.BUCKET_FILL_FISH, SoundEvents.BUCKET_EMPTY_FISH, IttyBittySoundEvents.COLLECT_FISH);


    private final EntityType type;
    private final Item liveItem;
    private final Item bucketItem;
    private final SoundEvent fillSound;
    private final SoundEvent emptySound;
    private final SoundEvent collectSound;

    IttyBittyFishCollectables(EntityType type, Item liveItem, Item bucketItem, SoundEvent fillSound, SoundEvent emptySound, SoundEvent collectSound) {
        this.type = type;
        this.liveItem = liveItem;
        this.bucketItem = bucketItem;
        this.fillSound = fillSound;
        this.emptySound = emptySound;
        this.collectSound = collectSound;
    }

    public EntityType getType() {
        return this.type;
    }

    public Item getLiveItem() {
        return this.liveItem;
    }

    public Item getBucketItem() {
        return this.bucketItem;
    }

    public SoundEvent getFillSound() {
        return this.fillSound;
    }

    public SoundEvent getEmptySound() {
        return this.emptySound;
    }

    public SoundEvent getCollectSound() {
        return this.collectSound;
    }

    public static IttyBittyFishCollectables getByType(EntityType type) {
        if (type == EntityType.COD ) return IttyBittyFishCollectables.COD;
        else if (type == EntityType.SALMON ) return IttyBittyFishCollectables.SALMON;
        else if (type == EntityType.PUFFERFISH ) return IttyBittyFishCollectables.PUFFERFISH;
        else if (type == EntityType.TROPICAL_FISH ) return IttyBittyFishCollectables.TROPICAL_FISH;
        else if (type == EntityType.AXOLOTL ) return IttyBittyFishCollectables.AXOLOTL;
        else if (type == EntityType.TADPOLE ) return IttyBittyFishCollectables.TADPOLE;
        else if (type == IttyBittyEntityType.TETRA ) return IttyBittyFishCollectables.TETRA;
        else return null;
    }
}
