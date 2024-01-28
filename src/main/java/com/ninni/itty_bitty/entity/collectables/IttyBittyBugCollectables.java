package com.ninni.itty_bitty.entity.collectables;

import com.ninni.itty_bitty.registry.IttyBittyItems;
import com.ninni.itty_bitty.registry.IttyBittySoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;


public enum IttyBittyBugCollectables {
    SILVERFISH(EntityType.SILVERFISH, IttyBittyItems.LIVE_SILVERFISH, IttyBittySoundEvents.COLLECT_SILVERFISH, IttyBittySoundEvents.RELEASE_SILVERFISH),
    ENDERMITE(EntityType.ENDERMITE, IttyBittyItems.LIVE_ENDERMITE, IttyBittySoundEvents.COLLECT_ENDERMITE, IttyBittySoundEvents.RELEASE_ENDERMITE),
    BEE(EntityType.BEE, IttyBittyItems.LIVE_BEE, IttyBittySoundEvents.COLLECT_BUG, IttyBittySoundEvents.RELEASE_BUG);


    private final EntityType type;
    private final Item liveItem;
    private final SoundEvent collectSound;
    private final SoundEvent releaseSound;

    IttyBittyBugCollectables(EntityType type, Item liveItem, SoundEvent collectSound, SoundEvent releaseSound) {
        this.type = type;
        this.liveItem = liveItem;
        this.collectSound = collectSound;
        this.releaseSound = releaseSound;
    }

    public EntityType getType() {
        return this.type;
    }

    public Item getLiveItem() {
        return this.liveItem;
    }

    public SoundEvent getCollectSound() {
        return this.collectSound;
    }

    public SoundEvent getReleaseSound() {
        return this.releaseSound;
    }

    public static IttyBittyBugCollectables getByType(EntityType type) {
        if (type == EntityType.SILVERFISH) return IttyBittyBugCollectables.SILVERFISH;
        else if (type == EntityType.ENDERMITE) return IttyBittyBugCollectables.ENDERMITE;
        else if (type == EntityType.BEE) return IttyBittyBugCollectables.BEE;
        else return null;
    }
}
