package com.ninni.itty_bitty.entity;

import com.ninni.itty_bitty.entity.ai.TreeFrogBehavior;
import com.ninni.itty_bitty.entity.ai.goal.TreeFrogCommonAnimationGoal;
import com.ninni.itty_bitty.entity.ai.goal.TreeFrogRareAnimationGoal;
import com.ninni.itty_bitty.entity.variant.TreeFrogVariant;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class TreeFrog extends Animal implements VariantHolder<TreeFrogVariant> {
    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(TreeFrog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> BEHAVIOR = SynchedEntityData.defineId(TreeFrog.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Integer> COMMON_ANIMATION_COOLDOWN = SynchedEntityData.defineId(TreeFrog.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> RARE_ANIMATION_COOLDOWN = SynchedEntityData.defineId(TreeFrog.class, EntityDataSerializers.INT);
    public final AnimationState blinkAnimationState = new AnimationState();
    public final AnimationState croakAnimationState = new AnimationState();
    public final AnimationState cleanAnimationState = new AnimationState();
    public int airTicks;

    public TreeFrog(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FrogMoveControl(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new TreeFrogFloatGoal(this));
        this.goalSelector.addGoal(1, new TreeFrogRandomDirectionGoal(this));
        this.goalSelector.addGoal(2, new TreeFrogKeepOnJumpingGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new TreeFrogCommonAnimationGoal(this));
        this.goalSelector.addGoal(6, new TreeFrogRareAnimationGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3f).add(Attributes.MAX_HEALTH, 10.0);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        TreeFrogVariant[] variants = TreeFrogVariant.values();
        TreeFrogVariant variant = Util.getRandom(variants, serverLevelAccessor.getRandom());
        this.setVariant(variant);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getCommonAnimationCooldown() > 0) this.setCommonAnimationCooldown(this.getCommonAnimationCooldown()-1);
        if (this.getRareAnimationCooldown() > 0) this.setRareAnimationCooldown(this.getRareAnimationCooldown()-1);
    }

    @Override
    protected int calculateFallDamage(float f, float g) {
        return super.calculateFallDamage(f, g) - 15;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == Pose.CROAKING) this.croakAnimationState.start(this.tickCount);
            if (this.getPose() == Pose.DIGGING) this.blinkAnimationState.start(this.tickCount);
            if (this.getPose() == Pose.ROARING) this.cleanAnimationState.start(this.tickCount);
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.isInWater()) {
            if (this.onGround()) airTicks = 0;
            else airTicks++;
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, TreeFrogVariant.RED_EYED.id());
        this.entityData.define(BEHAVIOR, TreeFrogBehavior.IDLE.getName());
        this.entityData.define(COMMON_ANIMATION_COOLDOWN, 2 * 20 + random.nextInt(12 * 20));
        this.entityData.define(RARE_ANIMATION_COOLDOWN, 30 * 20 + random.nextInt(60 * 2 * 20));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getVariant().id());
        compoundTag.putString("Behavior", this.getBehavior());
        compoundTag.putInt("CommonAnimationCooldown", this.getCommonAnimationCooldown());
        compoundTag.putInt("RearUpCooldown", this.getRareAnimationCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(TreeFrogVariant.byId(compoundTag.getInt("Variant")));
        this.setBehavior(compoundTag.getString("Behavior"));
        this.setCommonAnimationCooldown(compoundTag.getInt("CommonAnimationCooldown"));
        this.setRareAnimationCooldown(compoundTag.getInt("RareAnimationCooldown"));
    }

    public int getCommonAnimationCooldown() {
        return this.entityData.get(COMMON_ANIMATION_COOLDOWN);
    }
    public void setCommonAnimationCooldown(int cooldown) {
        this.entityData.set(COMMON_ANIMATION_COOLDOWN, cooldown);
    }
    public void commonAnimationCooldown() {
        this.entityData.set(COMMON_ANIMATION_COOLDOWN, 6 * 20 + random.nextInt(60 * 20));
    }

    public int getRareAnimationCooldown() {
        return this.entityData.get(RARE_ANIMATION_COOLDOWN);
    }
    public void setRareAnimationCooldown(int cooldown) {
        this.entityData.set(RARE_ANIMATION_COOLDOWN, cooldown);
    }
    public void rareAnimationCooldown() {
        this.entityData.set(RARE_ANIMATION_COOLDOWN, 30 * 20 + random.nextInt(60 * 2 * 20));
    }

    public String getBehavior() {
        return this.entityData.get(BEHAVIOR);
    }
    public void setBehavior(String behavior) {
        this.entityData.set(BEHAVIOR, behavior);
    }

    @Override
    public TreeFrogVariant getVariant() {
        return TreeFrogVariant.byId(this.entityData.get(VARIANT));
    }
    @Override
    public void setVariant(TreeFrogVariant variant) {
        this.entityData.set(VARIANT, variant.id());
    }

    @Override
    protected void jumpFromGround() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, this.getJumpPower(), vec3.z);
        this.hasImpulse = true;
    }

    protected int getJumpDelay() {
        return this.random.nextInt(20) + 10;
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.FROG_LONG_JUMP;
    }

    static class FrogMoveControl
            extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final TreeFrog frog;
        private boolean isAggressive;

        public FrogMoveControl(TreeFrog frog) {
            super(frog);
            this.frog = frog;
            this.yRot = 180.0f * frog.getYRot() / (float)Math.PI;
        }

        public void setDirection(float f, boolean bl) {
            this.yRot = f;
            this.isAggressive = bl;
        }

        public void setWantedMovement(double d) {
            this.speedModifier = d;
            this.operation = MoveControl.Operation.MOVE_TO;
        }

        @Override
        public void tick() {

            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0f));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();
            if (this.operation != MoveControl.Operation.MOVE_TO) {
                this.mob.setZza(0.0f);
                return;
            }
            this.operation = MoveControl.Operation.WAIT;
            if (this.mob.onGround()) {
                this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                if (this.jumpDelay-- <= 0) {
                    this.jumpDelay = this.frog.getJumpDelay();
                    if (this.isAggressive) {
                        this.jumpDelay /= 3;
                    }
                    this.frog.getJumpControl().jump();
                    this.frog.playSound(this.frog.getJumpSound(), this.frog.getSoundVolume(), this.frog.getVoicePitch());
                } else {
                    this.frog.xxa = 0.0f;
                    this.frog.zza = 0.0f;
                    this.mob.setSpeed(0.0f);
                }
            } else {
                this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            }
        }
    }
    static class TreeFrogFloatGoal extends Goal {
        private final TreeFrog frog;

        public TreeFrogFloatGoal(TreeFrog frog) {
            this.frog = frog;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
            frog.getNavigation().setCanFloat(true);
        }

        @Override
        public boolean canUse() {
            return (this.frog.isInWater() || this.frog.isInLava()) && this.frog.getMoveControl() instanceof TreeFrog.FrogMoveControl;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            MoveControl moveControl;
            if (this.frog.getRandom().nextFloat() < 0.8f) {
                this.frog.getJumpControl().jump();
            }
            if ((moveControl = this.frog.getMoveControl()) instanceof TreeFrog.FrogMoveControl) {
                TreeFrog.FrogMoveControl frogMoveControl = (TreeFrog.FrogMoveControl)moveControl;
                frogMoveControl.setWantedMovement(1.2);
            }
        }
    }
    
    static class TreeFrogRandomDirectionGoal extends Goal {
        private final TreeFrog frog;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public TreeFrogRandomDirectionGoal(TreeFrog frog) {
            this.frog = frog;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return this.frog.getTarget() == null && (this.frog.onGround() || this.frog.isInWater() || this.frog.isInLava() || this.frog.hasEffect(MobEffects.LEVITATION)) && this.frog.getMoveControl() instanceof TreeFrog.FrogMoveControl;
        }

        @Override
        public void tick() {
            MoveControl moveControl;
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = this.adjustedTickDelay(40 + this.frog.getRandom().nextInt(60));
                this.chosenDegrees = this.frog.getRandom().nextInt(360);
            }
            if ((moveControl = this.frog.getMoveControl()) instanceof TreeFrog.FrogMoveControl) {
                TreeFrog.FrogMoveControl frogMoveControl = (TreeFrog.FrogMoveControl)moveControl;
                frogMoveControl.setDirection(this.chosenDegrees, false);
            }
        }
    }

    static class TreeFrogKeepOnJumpingGoal extends Goal {
        private final TreeFrog frog;

        public TreeFrogKeepOnJumpingGoal(TreeFrog frog) {
            this.frog = frog;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !this.frog.isPassenger();
        }

        @Override
        public void tick() {
            MoveControl moveControl = this.frog.getMoveControl();
            if (moveControl instanceof FrogMoveControl frogMoveControl) {
                frogMoveControl.setWantedMovement(1.0);
            }
        }
    }
}
