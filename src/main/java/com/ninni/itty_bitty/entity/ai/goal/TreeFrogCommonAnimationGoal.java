package com.ninni.itty_bitty.entity.ai.goal;

import com.ninni.itty_bitty.entity.TreeFrog;
import com.ninni.itty_bitty.entity.ai.TreeFrogBehavior;
import net.minecraft.world.entity.Pose;

public class TreeFrogCommonAnimationGoal extends TreeFrogBehaviorGoal {

    public TreeFrogCommonAnimationGoal(TreeFrog frog) {
        super(frog, frog.getRandom().nextFloat() > 0.3f ? TreeFrogBehavior.CROAK : TreeFrogBehavior.BLINK);
    }

    @Override
    public boolean canUse() {
        return frog.getCommonAnimationCooldown() == 0 && super.canUse();
    }

    @Override
    public void start() {
        super.start();
        if (behavior.getName() == TreeFrogBehavior.CROAK.getName()) frog.setPose(Pose.CROAKING);
        else frog.setPose(Pose.DIGGING);
        frog.playSound(behavior.getSound(), 1.0f, frog.getVoicePitch());
    }

    @Override
    public void stop() {
        super.stop();
        frog.commonAnimationCooldown();
    }
}
