package com.ninni.itty_bitty.entity;

import com.ninni.itty_bitty.entity.variant.CorydoraVariant;
import com.ninni.itty_bitty.registry.IttyBittyItems;
import com.ninni.itty_bitty.registry.IttyBittySoundEvents;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Corydora extends AbstractFish {
    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Corydora.class, EntityDataSerializers.INT);
    public static final String BUCKET_VARIANT_TAG = "BucketVariantTag";

    public Corydora(EntityType<? extends AbstractFish> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new WaterRandomStrollGoal(this, 0.8));
    }

    public float getWalkTargetValue(BlockPos blockPos, LevelReader levelReader) {
        return levelReader.getBlockState(blockPos).getBlock().defaultBlockState().is(Blocks.WATER) && levelReader.getBlockState(blockPos.below()).getBlock().defaultBlockState().isSolid() ? 10.0F : 0.0F;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        if (mobSpawnType != MobSpawnType.BUCKET) {
            CorydoraVariant[] variants = CorydoraVariant.values();
            CorydoraVariant variant = Util.getRandom(variants, serverLevelAccessor.getRandom());
            this.setVariant(variant);
        }
        if (mobSpawnType == MobSpawnType.BUCKET && compoundTag != null && compoundTag.contains(BUCKET_VARIANT_TAG, 3)) {
            this.setVariant(CorydoraVariant.byId(compoundTag.getInt(BUCKET_VARIANT_TAG)));
            return spawnGroupData;
        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Override
    public void travel(Vec3 vec3) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01F, vec3);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.85));
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.05F, 0.0));

        } else {
            super.travel(vec3);
        }

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, CorydoraVariant.ADOLFOI.id());
    }

    public CorydoraVariant getVariant() {
        return CorydoraVariant.byId(this.entityData.get(VARIANT));
    }

    public void setVariant(CorydoraVariant variant) {
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
        this.setVariant(CorydoraVariant.byId(compoundTag.getInt("Variant")));
    }

    public static boolean checkCorydoraSpawnRules(EntityType<Corydora> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        return levelAccessor.getFluidState(blockPos.below()).is(FluidTags.WATER) && levelAccessor.getBlockState(blockPos.above()).is(Blocks.WATER) || WaterAnimal.checkSurfaceWaterAnimalSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return IttyBittyItems.CORYDORA_BUCKET.getDefaultInstance();
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

    public static class WaterRandomStrollGoal extends RandomStrollGoal {
        protected final float probability;

        public WaterRandomStrollGoal(PathfinderMob pathfinderMob, double d) {
            this(pathfinderMob, d, 0.001f);
        }

        public WaterRandomStrollGoal(PathfinderMob pathfinderMob, double d, float f) {
            super(pathfinderMob, d);
            this.probability = f;
        }

        @Override
        @Nullable
        protected Vec3 getPosition() {
            Vec3 pos = DefaultRandomPos.getPos(this.mob, 10, 7);
            if (this.mob.getRandom().nextFloat() >= this.probability && pos != null) {
                if (this.mob.level().getBlockState(new BlockPos((int)pos.x, (int)pos.y, (int)pos.z)).is(Blocks.WATER)
                        && this.mob.level().getBlockState(new BlockPos((int)pos.x, (int)pos.y, (int)pos.z).below()).isSolid()) {
                    return pos;
                }
            } else {
                stop();
            }
            return super.getPosition();
        }
    }

}
