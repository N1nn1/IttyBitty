package com.ninni.itty_bitty.entity;

import com.ninni.itty_bitty.entity.variant.TetraVariant;
import com.ninni.itty_bitty.registry.IttyBittyItems;
import com.ninni.itty_bitty.registry.IttyBittySoundEvents;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

public class Tetra extends AbstractSchoolingFish {
    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Tetra.class, EntityDataSerializers.INT);
    public static final String BUCKET_VARIANT_TAG = "BucketVariantTag";

    public Tetra(EntityType<? extends AbstractSchoolingFish> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        if (mobSpawnType != MobSpawnType.BUCKET) {
            TetraVariant[] variants = TetraVariant.values();
            TetraVariant variant = Util.getRandom(variants, serverLevelAccessor.getRandom());
            this.setVariant(variant);
        }
        if (mobSpawnType == MobSpawnType.BUCKET && compoundTag != null && compoundTag.contains(BUCKET_VARIANT_TAG, 3)) {
            this.setVariant(TetraVariant.byId(compoundTag.getInt(BUCKET_VARIANT_TAG)));
            return spawnGroupData;
        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, TetraVariant.RUMMYNOSE.id());
    }

    public String getBodyType() {
        return TetraVariant.byId(this.entityData.get(VARIANT)).getBodyPlan();
    }

    public TetraVariant getVariant() {
        return TetraVariant.byId(this.entityData.get(VARIANT));
    }

    public void setVariant(TetraVariant variant) {
        this.entityData.set(VARIANT, variant.id());
    }

    @Override
    public void saveToBucketTag(ItemStack itemStack) {
        super.saveToBucketTag(itemStack);
        CompoundTag compoundTag = itemStack.getOrCreateTag();
        compoundTag.putInt(BUCKET_VARIANT_TAG, this.getVariant().id());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getVariant().id());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(TetraVariant.byId(compoundTag.getInt("Variant")));
    }

    public static boolean checkTetraSpawnRules(EntityType<Tetra> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        return levelAccessor.getFluidState(blockPos.below()).is(FluidTags.WATER) && levelAccessor.getBlockState(blockPos.above()).is(Blocks.WATER) || WaterAnimal.checkSurfaceWaterAnimalSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return IttyBittyItems.TETRA_BUCKET.getDefaultInstance();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return IttyBittySoundEvents.FISH_DEATH;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return IttyBittySoundEvents.FISH_SWIM;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return IttyBittySoundEvents.FISH_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return IttyBittySoundEvents.FISH_HURT;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return IttyBittySoundEvents.FISH_FLOP;
    }
}
