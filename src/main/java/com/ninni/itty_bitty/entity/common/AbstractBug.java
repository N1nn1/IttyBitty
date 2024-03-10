package com.ninni.itty_bitty.entity.common;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractBug extends PathfinderMob implements TerrestrialCollectable {
    private static final EntityDataAccessor<Boolean> FROM_LIVE_ITEM = SynchedEntityData.defineId(AbstractBug.class, EntityDataSerializers.BOOLEAN);

    protected AbstractBug(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }


    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromLiveItem();
    }

    public boolean removeWhenFarAway(double d) {
        return !this.fromLiveItem() && !this.hasCustomName();
    }


    public boolean fromLiveItem() {
        return this.entityData.get(FROM_LIVE_ITEM);
    }

    public void setFromLiveItem(boolean bl) {
        this.entityData.set(FROM_LIVE_ITEM, bl);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_LIVE_ITEM, false);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("FromItem", this.fromLiveItem());
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setFromLiveItem(compoundTag.getBoolean("FromItem"));
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }
}
