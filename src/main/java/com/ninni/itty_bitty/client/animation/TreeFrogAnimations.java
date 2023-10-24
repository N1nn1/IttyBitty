package com.ninni.itty_bitty.client.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import static net.minecraft.client.model.geom.PartNames.*;

@Environment(EnvType.CLIENT)
public class TreeFrogAnimations {

    public static final AnimationDefinition BLINK = AnimationDefinition.Builder.withLength(0.375f)
            .addAnimation(LEFT_EYE,
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.125f, KeyframeAnimations.posVec(0f, -1.45f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(LEFT_EYE,
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.125f, KeyframeAnimations.scaleVec(1f, 0.05f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(RIGHT_EYE,
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.125f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -1.45f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(RIGHT_EYE,
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0.125f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.scaleVec(1f, 0.05f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.375f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    
    public static final AnimationDefinition CROAKING = AnimationDefinition.Builder.withLength(0.5f)
            .addAnimation(CROAKING_BODY,
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, KeyframeAnimations.posVec(0f, -0.2f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4167667f, KeyframeAnimations.posVec(0f, -0.2f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation(CROAKING_BODY,
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, KeyframeAnimations.scaleVec(1.3f, 1.3f, 1.4f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4167667f, KeyframeAnimations.scaleVec(1.3f, 1.3f, 1.4f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .build();
    
    public static final AnimationDefinition CLEAN = AnimationDefinition.Builder.withLength(1.125f)
            .addAnimation(LEFT_EYE,
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.20834334f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -0.3f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -0.2f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.625f, KeyframeAnimations.posVec(0f, -0.3f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -0.2f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.875f, KeyframeAnimations.posVec(0f, -0.3f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.posVec(0f, -0.2f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.125f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation(LEFT_EYE,
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0.20834334f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.375f, KeyframeAnimations.scaleVec(1f, 0.8f, 1f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.scaleVec(1f, 0.9f, 1f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.625f, KeyframeAnimations.scaleVec(1f, 0.8f, 1f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.75f, KeyframeAnimations.scaleVec(1f, 0.9f, 1f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.875f, KeyframeAnimations.scaleVec(1f, 0.8f, 1f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.scaleVec(1f, 0.9f, 1f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.125f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation(LEFT_ARM,
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.20834334f, KeyframeAnimations.degreeVec(-17.5f, 0f, -22.5f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.375f, KeyframeAnimations.degreeVec(-153.56f, -58.96f, 37.37f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(-119.4f, -56.56f, 41.32f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.625f, KeyframeAnimations.degreeVec(-153.56f, -58.96f, 37.37f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.75f, KeyframeAnimations.degreeVec(-119.4f, -56.56f, 41.32f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.875f, KeyframeAnimations.degreeVec(-153.56f, -58.96f, 37.37f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.degreeVec(-119.4f, -56.56f, 41.32f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.125f, KeyframeAnimations.degreeVec(-17.5f, 0f, -22.5f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.2083433f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .build();
}
