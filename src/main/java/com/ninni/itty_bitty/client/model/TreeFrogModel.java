package com.ninni.itty_bitty.client.model;

import com.ninni.itty_bitty.client.animation.TreeFrogAnimations;
import com.ninni.itty_bitty.entity.TreeFrog;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

import static net.minecraft.client.model.geom.PartNames.*;

public class TreeFrogModel<T extends TreeFrog> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart leftEye;
    private final ModelPart rightEye;
    private final ModelPart croakingBody;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart rightFoot;
    private final ModelPart leftFoot;

    public TreeFrogModel(ModelPart root) {
        this.root = root;

        this.body = root.getChild(BODY);
        this.leftArm = root.getChild(LEFT_ARM);
        this.rightArm = root.getChild(RIGHT_ARM);
        this.leftLeg = root.getChild(LEFT_LEG);
        this.rightLeg = root.getChild(RIGHT_LEG);

        this.leftEye = body.getChild(LEFT_EYE);
        this.rightEye = body.getChild(RIGHT_EYE);
        this.croakingBody = body.getChild(CROAKING_BODY);

        this.rightFoot = rightLeg.getChild(RIGHT_FOOT);
        this.leftFoot = leftLeg.getChild(LEFT_FOOT);
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animate(entity.blinkAnimationState, TreeFrogAnimations.BLINK, animationProgress);
        this.animate(entity.croakAnimationState, TreeFrogAnimations.CROAKING, animationProgress);
        this.animate(entity.cleanAnimationState, TreeFrogAnimations.CLEAN, animationProgress);

        this.body.y = Mth.cos(animationProgress * 0.1F) * 0.25F + 21.5F;

        float jumpRotation = Math.min(entity.airTicks * 0.2F, 1.0F);

        this.body.xRot = -jumpRotation/4;
        this.leftLeg.xRot = jumpRotation;
        this.leftLeg.yRot = -jumpRotation/2;
        this.rightLeg.xRot = jumpRotation;
        this.rightLeg.yRot = jumpRotation/2;
        this.leftFoot.xRot = jumpRotation;
        this.rightFoot.xRot = jumpRotation;
        this.leftArm.xRot -= jumpRotation;
        this.leftArm.y -= jumpRotation;
        this.rightArm.xRot -= jumpRotation;
        this.rightArm.y -= jumpRotation;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild(
                BODY,
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-3.0F, -3.0F, -7.0F, 6.0F, 4.0F, 8.0F),
                PartPose.offset(0.0F, 21.5F, 2.0F)
        );

        body.addOrReplaceChild(
                CROAKING_BODY,
                CubeListBuilder.create()
                        .texOffs(0, 17)
                        .addBox(-3.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(0.0F, 0.0F, -6.0F)
        );

        body.addOrReplaceChild(
                LEFT_EYE,
                CubeListBuilder.create()
                        .texOffs(20, 0)
                        .addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F),
                PartPose.offset(2.5F, -2.5F, -5.52F)
        );

        body.addOrReplaceChild(
                RIGHT_EYE,
                CubeListBuilder.create()
                        .texOffs(20, 0)
                        .mirror()
                        .addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F)
                        .mirror(false),
                PartPose.offset(-2.5F, -2.5F, -5.52F)
        );
        partdefinition.addOrReplaceChild(
                RIGHT_ARM, CubeListBuilder.create()
                        .texOffs(10, 12)
                        .mirror()
                        .addBox(-0.5F, 0.0F, -1.5F, 3.0F, 2.0F, 3.0F)
                        .mirror(false),
                PartPose.offset(-3.0F, 22.0F, -2.0F)
        );

        partdefinition.addOrReplaceChild(
                LEFT_ARM, CubeListBuilder.create()
                        .texOffs(10, 12)
                        .addBox(-2.5F, 0.0F, -1.5F, 3.0F, 2.0F, 3.0F),
                PartPose.offset(3.0F, 22.0F, -2.0F)
        );

        PartDefinition rightLeg = partdefinition.addOrReplaceChild(
                RIGHT_LEG, CubeListBuilder.create()
                        .texOffs(0, 12)
                        .mirror()
                        .addBox(-2.0F, 0.0F, -1.0F, 3.0F, 2.0F, 2.0F)
                        .mirror(false),
                PartPose.offset(-3.0F, 22.0F, 3.0F)
        );

        rightLeg.addOrReplaceChild(
                RIGHT_FOOT, CubeListBuilder.create()
                        .texOffs(-4, 0)
                        .mirror()
                        .addBox(-4.0F, -0.02F, -4.0F, 4.0F, 0.0F, 4.0F)
                        .mirror(false),
                PartPose.offset(-1.0F, 2.0F, 0.0F)
        );

        PartDefinition leftLeg = partdefinition.addOrReplaceChild(
                LEFT_LEG, CubeListBuilder.create()
                        .texOffs(0, 12)
                        .addBox(-1.0F, 0.0F, -1.0F, 3.0F, 2.0F, 2.0F),
                PartPose.offset(3.0F, 22.0F, 3.0F)
        );

        leftLeg.addOrReplaceChild(
                LEFT_FOOT, CubeListBuilder.create()
                        .texOffs(-4, 0)
                        .addBox(0.0F, 0.08F, -4.0F, 4.0F, 0.0F, 4.0F),
                PartPose.offset(1.0F, 1.9F, 0.0F)
        );


        return LayerDefinition.create(meshdefinition, 32, 32);
    }


    @Override
    public ModelPart root() {
        return this.root;
    }
}
