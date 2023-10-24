package com.ninni.itty_bitty.entity.ai.goal;

import com.ninni.itty_bitty.entity.TreeFrog;
import com.ninni.itty_bitty.entity.ai.TreeFrogBehavior;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;

public class TreeFrogBehaviorGoal extends Goal {
    TreeFrogBehavior behavior;
    TreeFrog frog;
    int timer;

    public TreeFrogBehaviorGoal(TreeFrog frog, TreeFrogBehavior behavior) {
        this.frog = frog;
        this.behavior = behavior;
    }


    @Override
    public boolean canUse() {
        return frog.getBehavior() == TreeFrogBehavior.IDLE.getName() && !frog.isInWater() && frog.onGround();
    }

    @Override
    public boolean canContinueToUse() {
        return timer > 0 && !frog.isInWater() && frog.onGround();
    }

    @Override
    public void start() {
        super.start();
        timer = behavior.getLength();
        frog.setBehavior(behavior.getName());
    }

    @Override
    public void tick() {
        super.tick();
        timer--;
    }

    @Override
    public void stop() {
        super.stop();
        frog.setPose(Pose.STANDING);
        frog.setBehavior(TreeFrogBehavior.IDLE.getName());
    }
}