package com.ninni.itty_bitty.entity;

import com.ninni.itty_bitty.entity.common.AbstractBug;
import com.ninni.itty_bitty.entity.variant.BeetleVariant;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class Beetle extends AbstractBug implements VariantHolder<BeetleVariant> {
    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Beetle.class, EntityDataSerializers.INT);
    public static final String LIVE_ITEM_VARIANT_TAG = "LiveItemVariantTag";

    public Beetle(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3f).add(Attributes.MAX_HEALTH, 5.0);
    }


    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        if (mobSpawnType == MobSpawnType.BUCKET && compoundTag != null && compoundTag.contains(LIVE_ITEM_VARIANT_TAG, 3)) {
        } else {
            BeetleVariant[] variants = BeetleVariant.values();
            BeetleVariant variant = Util.getRandom(variants, serverLevelAccessor.getRandom());
            this.setVariant(variant);
        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getVariant().id());
    }

    @Override
    public boolean onClimbable() {
        return false;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, BeetleVariant.ACORN.id());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(BeetleVariant.byId(compoundTag.getInt("Variant")));
    }

    public String getBodyType() {
        return BeetleVariant.byId(this.entityData.get(VARIANT)).getBodyPlan();
    }

    @Override
    public BeetleVariant getVariant() {
        return BeetleVariant.byId(this.entityData.get(VARIANT));
    }
    @Override
    public void setVariant(BeetleVariant variant) {
        this.entityData.set(VARIANT, variant.id());
    }

    public static boolean checkAnimalSpawnRules(EntityType<? extends AbstractBug> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        return levelAccessor.getBlockState(blockPos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON);
    }

    @Override
    public void saveToLiveItemTag(CompoundTag tag) {
        tag.putInt(LIVE_ITEM_VARIANT_TAG, this.getVariant().id());
    }

    @Override
    public void loadFromLiveItemTag(CompoundTag compoundTag) {
        if (compoundTag != null && compoundTag.contains(LIVE_ITEM_VARIANT_TAG, 3)) {
            this.setVariant(BeetleVariant.byId(compoundTag.getInt(LIVE_ITEM_VARIANT_TAG)));
        }
    }
}
