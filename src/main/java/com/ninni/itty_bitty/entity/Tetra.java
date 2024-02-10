package com.ninni.itty_bitty.entity;

import com.mojang.datafixers.DataFixUtils;
import com.ninni.itty_bitty.entity.common.IttyBittySchoolingFish;
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
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class Tetra extends IttyBittySchoolingFish {
    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Tetra.class, EntityDataSerializers.INT);
    public static final String BUCKET_VARIANT_TAG = "BucketVariantTag";

    public Tetra(EntityType<? extends IttyBittySchoolingFish> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 2F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new SchoolingGoal(this));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        if (mobSpawnType == MobSpawnType.BUCKET && compoundTag != null && compoundTag.contains(BUCKET_VARIANT_TAG, 3)) {
            this.setVariant(TetraVariant.byId(compoundTag.getInt(BUCKET_VARIANT_TAG)));
        } else {

            TetraVariant variant;

            if (spawnGroupData instanceof Tetra.TetraGroupData) {
                Tetra.TetraGroupData groupData = (Tetra.TetraGroupData)spawnGroupData;
                variant = groupData.variant;
            } else {
                variant = Util.getRandom(TetraVariant.values(), serverLevelAccessor.getRandom());
                spawnGroupData = new Tetra.TetraGroupData(variant);
            }

            this.setVariant(variant);
        }
        return spawnGroupData;
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


    private static class TetraGroupData implements SpawnGroupData {
        final TetraVariant variant;

        TetraGroupData(TetraVariant variant) {
            this.variant = variant;
        }
    }

    public static class SchoolingGoal extends Goal {
        private final Tetra tetra;
        private int timeToRecalcPath;
        private int nextStartTick;

        public SchoolingGoal(Tetra schoolingFish) {
            this.tetra = schoolingFish;
            this.nextStartTick = this.nextStartTick(schoolingFish);
        }

        protected int nextStartTick(Tetra schoolingFish) {
            return reducedTickDelay(200 + schoolingFish.getRandom().nextInt(200) % 20);
        }

        public boolean canUse() {
            if (this.tetra.hasFollowers()) {
                return false;
            } else if (this.tetra.isFollower()) {
                return true;
            } else if (this.nextStartTick > 0) {
                --this.nextStartTick;
                return false;
            } else {
                this.nextStartTick = this.nextStartTick(this.tetra);
                Predicate<Tetra> predicate = (schoolingFishx) -> (schoolingFishx.canBeFollowed() || !schoolingFishx.isFollower()) && schoolingFishx.getVariant() == this.tetra.getVariant();
                List<? extends Tetra> list = this.tetra.level().getEntitiesOfClass(Tetra.class, this.tetra.getBoundingBox().inflate(8.0, 8.0, 8.0), predicate);
                Tetra tetra1 = DataFixUtils.orElse(list.stream().filter(Tetra::canBeFollowed).findAny(), this.tetra);
                tetra1.addFollowers(list.stream().filter((schoolingFishx) -> !schoolingFishx.isFollower()));
                return this.tetra.isFollower();
            }
        }

        public boolean canContinueToUse() {
            return this.tetra.isFollower() && this.tetra.inRangeOfLeader();
        }

        public void start() {
            this.timeToRecalcPath = 0;
        }

        public void stop() {
            this.tetra.stopFollowing();
        }

        public void tick() {
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(10);
                this.tetra.pathToLeader();
            }
        }
    }
}
