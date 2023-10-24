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
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class TreeFrog extends Animal implements VariantHolder<TreeFrogVariant> {
    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(TreeFrog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> BEHAVIOR = SynchedEntityData.defineId(TreeFrog.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Integer> COMMON_ANIMATION_COOLDOWN = SynchedEntityData.defineId(TreeFrog.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> RARE_ANIMATION_COOLDOWN = SynchedEntityData.defineId(TreeFrog.class, EntityDataSerializers.INT);
    public final AnimationState blinkAnimationState = new AnimationState();
    public final AnimationState croakAnimationState = new AnimationState();
    public final AnimationState cleanAnimationState = new AnimationState();
    private int jumpTicks;
    private int jumpDuration;
    private boolean wasOnGround;
    private int jumpDelayTicks;

    public TreeFrog(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.jumpControl = new FrogJumpControl(this);
        this.moveControl = new FrogMoveControl(this);
        this.setSpeedModifier(0.0);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new TreeFrogCommonAnimationGoal(this));
        this.goalSelector.addGoal(7, new TreeFrogRareAnimationGoal(this));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1));
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
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == Pose.CROAKING) this.croakAnimationState.start(this.tickCount);
            if (this.getPose() == Pose.DIGGING) this.blinkAnimationState.start(this.tickCount);
            if (this.getPose() == Pose.ROARING) this.cleanAnimationState.start(this.tickCount);
        }
        super.onSyncedDataUpdated(entityDataAccessor);
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

    public void setSpeedModifier(double d) {
        this.getNavigation().setSpeedModifier(d);
        this.moveControl.setWantedPosition(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ(), d);
    }

    @Override
    protected float getJumpPower() {
        Path path;
        float f = 0.4f;
        if (this.horizontalCollision || this.moveControl.hasWanted() && this.moveControl.getWantedY() > this.getY() + 0.5) {
            f = 0.6f;
        }
        if ((path = this.navigation.getPath()) != null && !path.isDone()) {
            Vec3 vec3 = path.getNextEntityPos(this);
            if (vec3.y > this.getY() + 0.5) {
                f = 0.6f;
            }
        }
        if (this.moveControl.getSpeedModifier() <= 0.6) {
            f = -0.1f;
            jumpDuration = 0;
        }
        return f + this.getJumpBoostPower();
    }

    @Override
    protected void jumpFromGround() {
        super.jumpFromGround();
        double d = this.moveControl.getSpeedModifier();
        if (d > 0.0 && (this.getDeltaMovement().horizontalDistanceSqr()) < 0.01) {
            this.moveRelative(0.1f, new Vec3(0.0, 0.0, 1.0));
        }
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)1);
        }
    }

    public float getJumpCompletion(float f) {
        if (this.jumpDuration == 0) {
            return 0.0f;
        }
        return ((float)this.jumpTicks + f) / (float)this.jumpDuration;
    }

    @Override
    public void setJumping(boolean bl) {
        super.setJumping(bl);
        if (bl) {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) * 0.8f);
        }
    }

    public void startJumping() {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    @Override
    public void customServerAiStep() {
        if (this.jumpDelayTicks > 0) {
            --this.jumpDelayTicks;
        }
        if (this.onGround()) {
            TreeFrog.FrogJumpControl frogJumpControl;
            if (!this.wasOnGround) {
                this.setJumping(false);
                this.checkLandingDelay();
            }
            if (!(frogJumpControl = (TreeFrog.FrogJumpControl)this.jumpControl).wantJump()) {
                if (this.moveControl.hasWanted() && this.jumpDelayTicks == 0) {
                    Path path = this.navigation.getPath();
                    Vec3 vec3 = new Vec3(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ());
                    if (path != null && !path.isDone()) {
                        vec3 = path.getNextEntityPos(this);
                    }
                    this.facePoint(vec3.x, vec3.z);
                    this.startJumping();
                }
            } else if (!frogJumpControl.canJump()) {
                this.enableJumpControl();
            }
        }
        this.wasOnGround = this.onGround();
    }

    private void facePoint(double d, double e) {
        this.setYRot((float)(Mth.atan2(e - this.getZ(), d - this.getX()) * 57.2957763671875) - 90.0f);
    }
    private void enableJumpControl() {
        ((TreeFrog.FrogJumpControl)this.jumpControl).setCanJump(true);
    }

    private void disableJumpControl() {
        ((TreeFrog.FrogJumpControl)this.jumpControl).setCanJump(false);
    }

    private void setLandingDelay() {
        this.jumpDelayTicks = this.moveControl.getSpeedModifier() < 2.2 ? 10 : 1;
    }

    private void checkLandingDelay() {
        this.setLandingDelay();
        this.disableJumpControl();
    }
    @Override
    public void aiStep() {
        super.aiStep();

        if (this.jumpTicks != this.jumpDuration) {
            ++this.jumpTicks;
        } else if (this.jumpDuration != 0) {
            this.jumpTicks = 0;
            this.jumpDuration = 0;
            this.setJumping(false);
        }
    }

    @Override
    public void handleEntityEvent(byte b) {
        if (b == 1) {
            this.spawnSprintParticle();
            this.jumpDuration = 10;
            this.jumpTicks = 0;
        } else {
            super.handleEntityEvent(b);
        }
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.FROG_LONG_JUMP;
    }

    public static class FrogJumpControl
            extends JumpControl {
        private final TreeFrog frog;
        private boolean canJump;

        public FrogJumpControl(TreeFrog frog) {
            super(frog);
            this.frog = frog;
        }

        public boolean wantJump() {
            return this.jump;
        }

        public boolean canJump() {
            return this.canJump;
        }

        public void setCanJump(boolean bl) {
            this.canJump = bl;
        }

        @Override
        public void tick() {
            if (this.jump) {
                this.frog.startJumping();
                this.jump = false;
            }
        }
    }

    static class FrogMoveControl
            extends MoveControl {
        private final TreeFrog frog;
        private double nextJumpSpeed;

        public FrogMoveControl(TreeFrog frog) {
            super(frog);
            this.frog = frog;
        }

        @Override
        public void tick() {
            if (this.frog.onGround() && !this.frog.jumping && !((TreeFrog.FrogJumpControl)this.frog.jumpControl).wantJump()) {
                this.frog.setSpeedModifier(0.0);
            } else if (this.hasWanted()) {
                this.frog.setSpeedModifier(this.nextJumpSpeed);
            }
            super.tick();
        }

        @Override
        public void setWantedPosition(double d, double e, double f, double g) {
            if (this.frog.isInWater()) {
                g = 1.5;
            }
            super.setWantedPosition(d, e, f, g);
            if (g > 0.0) {
                this.nextJumpSpeed = g;
            }
        }
    }
}
