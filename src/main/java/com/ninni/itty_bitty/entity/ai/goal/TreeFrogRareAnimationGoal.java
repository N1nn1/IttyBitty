package com.ninni.itty_bitty.entity.ai.goal;


import com.ninni.itty_bitty.entity.TreeFrog;
import com.ninni.itty_bitty.entity.ai.TreeFrogBehavior;
import net.minecraft.world.entity.Pose;

public class TreeFrogRareAnimationGoal extends TreeFrogBehaviorGoal {

    public TreeFrogRareAnimationGoal(TreeFrog frog) {
        super(frog, TreeFrogBehavior.CLEAN);
    }

    @Override
    public boolean canUse() {
        return frog.getRareAnimationCooldown() == 0  && super.canUse();
    }

    @Override
    public void start() {
        super.start();
        frog.setPose(Pose.ROARING);
        frog.playSound(behavior.getSound(), 1.0f, 1.0f);
    }

    @Override
    public void stop() {
        super.stop();
        frog.rareAnimationCooldown();
    }
}
