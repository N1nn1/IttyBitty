package com.ninni.itty_bitty.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ninni.itty_bitty.entity.Beetle;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import static net.minecraft.client.model.geom.PartNames.*;

public class BeetleModel<T extends Beetle> extends HierarchicalModel<T> {
    private static final String LIMBS = "limbs";
    private static final String ANTENNAE = "antennae";
    private static final String RIGHT_FORE_LEG = "right_fore_leg";
    private static final String LEFT_FORE_LEG = "left_fore_leg";
    private static final String RIGHT_ANTENNA = "right_antenna";
    private static final String RIGHT_MANDIBLE = "right_mandible";
    private static final String LEFT_ANTENNA = "left_antenna";
    private static final String LEFT_MANDIBLE = "left_mandible";
    private static final String ABDOMEN = "abdomen";

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leftAntenna;
    private final ModelPart rightAntenna;
    private final ModelPart antennae;
    private final ModelPart leftMandible;
    private final ModelPart rightMandible;
    private final ModelPart limbs;
    private final ModelPart leftHindLeg;
    private final ModelPart leftMidLeg;
    private final ModelPart leftForeLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart rightMidLeg;
    private final ModelPart rightForeLeg;
    private final ModelPart abdomen;


    public BeetleModel(ModelPart modelPart) {
        this.root = modelPart;

        this.body = this.root.getChild(BODY);

        this.head = this.body.getChild(HEAD);
        this.limbs = this.body.getChild(LIMBS);
        this.abdomen = this.body.getChild(ABDOMEN);

        this.leftAntenna = this.head.getChild(LEFT_ANTENNA);
        this.rightAntenna = this.head.getChild(RIGHT_ANTENNA);
        this.leftMandible = this.head.getChild(LEFT_MANDIBLE);
        this.rightMandible = this.head.getChild(RIGHT_MANDIBLE);
        this.antennae = this.head.getChild(ANTENNAE);

        this.leftHindLeg = this.limbs.getChild(LEFT_HIND_LEG);
        this.leftMidLeg = this.limbs.getChild(LEFT_MID_LEG);
        this.leftForeLeg = this.limbs.getChild(LEFT_FORE_LEG);
        this.rightHindLeg = this.limbs.getChild(RIGHT_HIND_LEG);
        this.rightMidLeg = this.limbs.getChild(RIGHT_MID_LEG);
        this.rightForeLeg = this.limbs.getChild(RIGHT_FORE_LEG);
    }

    @Override
    public void setupAnim(Beetle entity, float f, float g, float h, float i, float j) {
    }

    public static LayerDefinition createRoveBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild(BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, -2.5F, 3.0F, 2.0F, 5.0F), PartPose.offset(0.0F, 22.0F, 1.5F));

        PartDefinition head = body.addOrReplaceChild(HEAD, CubeListBuilder.create().texOffs(18, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F)
                .texOffs(11, 8).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 1.0F, 2.0F), PartPose.offset(0.0F, -1.0F, -2.5F));

        head.addOrReplaceChild(RIGHT_ANTENNA, CubeListBuilder.create().texOffs(12, 0).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 0.0F, 3.0F), PartPose.offset(-1.0F, 0.0F, -3.0F));

        head.addOrReplaceChild(RIGHT_MANDIBLE, CubeListBuilder.create().texOffs(14, 12).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 1.0F), PartPose.offset(-0.5F, 1.0F, -3.0F));

        head.addOrReplaceChild(LEFT_ANTENNA, CubeListBuilder.create().texOffs(12, 0).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 0.0F, 3.0F), PartPose.offset(1.0F, 0.0F, -3.0F));

        head.addOrReplaceChild(LEFT_MANDIBLE, CubeListBuilder.create().texOffs(14, 12).mirror().addBox(0.0F, 0.0F, -1.0F, 2.0F, 0.0F, 1.0F).mirror(false), PartPose.offset(0.5F, 1.0F, -3.0F));

        head.addOrReplaceChild(ANTENNAE, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition limbs = body.addOrReplaceChild(LIMBS, CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, -1.5F));

        limbs.addOrReplaceChild(RIGHT_HIND_LEG, CubeListBuilder.create().texOffs(0, 14).addBox(-3.0F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F), PartPose.offsetAndRotation(-1.5F, -1.5F, 2.0F, 0.0F, 0.7854F, 0.0F));

        limbs.addOrReplaceChild(RIGHT_MID_LEG, CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F), PartPose.offsetAndRotation(-1.5F, -1.5F, 0.0F, 0.0F, 0.7854F, 0.0F));

        limbs.addOrReplaceChild(RIGHT_FORE_LEG, CubeListBuilder.create().texOffs(0, 18).addBox(-2.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F), PartPose.offsetAndRotation(-1.5F, -1.5F, -1.0F, 0.0F, -0.7854F, 0.0F));

        limbs.addOrReplaceChild(LEFT_FORE_LEG, CubeListBuilder.create().texOffs(0, 18).mirror().addBox(0.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F).mirror(false), PartPose.offsetAndRotation(1.5F, -1.5F, -1.0F, 0.0F, 0.7854F, 0.0F));

        limbs.addOrReplaceChild(LEFT_MID_LEG, CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F).mirror(false), PartPose.offsetAndRotation(1.5F, -1.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

        limbs.addOrReplaceChild(LEFT_HIND_LEG, CubeListBuilder.create().texOffs(0, 14).mirror().addBox(0.0F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F).mirror(false), PartPose.offsetAndRotation(1.5F, -1.5F, 2.0F, 0.0F, -0.7854F, 0.0F));

        body.addOrReplaceChild(ABDOMEN, CubeListBuilder.create().texOffs(0, 8).addBox(-1.5F, -3.0F, -1.0F, 3.0F, 3.0F, 2.0F), PartPose.offset(0.0F, -1.0F, 1.5F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }
    public static LayerDefinition createWeevilBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild(BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 4.0F), PartPose.offset(0.0F, 22.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild(HEAD, CubeListBuilder.create().texOffs(11, 11).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 3.0F, 3.0F), PartPose.offset(0.0F, -1.0F, -2.0F));

        head.addOrReplaceChild(ANTENNAE, CubeListBuilder.create().texOffs(6, 7).addBox(-2.5F, 0.0F, -3.0F, 5.0F, 0.0F, 3.0F), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild(RIGHT_ANTENNA, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild(LEFT_ANTENNA, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild(LEFT_MANDIBLE, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild(RIGHT_MANDIBLE, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition limbs = body.addOrReplaceChild(LIMBS, CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

        limbs.addOrReplaceChild(RIGHT_HIND_LEG, CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, -2.0F, -1.0F, 0.0F, 4.0F, 5.0F), PartPose.offsetAndRotation(-1.5F, -2.0F, 2.0F, 0.0F, -0.4363F, 0.0F));

        limbs.addOrReplaceChild(RIGHT_FORE_LEG, CubeListBuilder.create().texOffs(0, 7).addBox(-2.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F), PartPose.offsetAndRotation(-1.5F, -1.5F, -1.0F, 0.0F, -0.3927F, 0.0F));

        limbs.addOrReplaceChild(RIGHT_MID_LEG, CubeListBuilder.create().texOffs(11, 1).addBox(-2.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F), PartPose.offsetAndRotation(-1.5F, -1.0F, 0.0F, 0.0F, 0.4363F, 0.0F));

        limbs.addOrReplaceChild(LEFT_HIND_LEG, CubeListBuilder.create().texOffs(0, 7).mirror().addBox(0.0F, -2.0F, -1.0F, 0.0F, 4.0F, 5.0F).mirror(false), PartPose.offsetAndRotation(1.5F, -2.0F, 2.0F, 0.0F, 0.4363F, 0.0F));

        limbs.addOrReplaceChild(LEFT_FORE_LEG, CubeListBuilder.create().texOffs(0, 7).mirror().addBox(0.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F).mirror(false), PartPose.offsetAndRotation(1.5F, -1.5F, -1.0F, 0.0F, 0.3927F, 0.0F));

        limbs.addOrReplaceChild(LEFT_MID_LEG, CubeListBuilder.create().texOffs(11, 1).mirror().addBox(0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F).mirror(false), PartPose.offsetAndRotation(1.5F, -1.0F, 0.0F, 0.0F, -0.4363F, 0.0F));

        body.addOrReplaceChild(ABDOMEN, CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    public static LayerDefinition createScarabBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild(BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 4.0F), PartPose.offset(0.0F, 22.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild(HEAD, CubeListBuilder.create().texOffs(11, 11).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 3.0F, 3.0F), PartPose.offset(0.0F, -1.0F, -2.0F));

        head.addOrReplaceChild(ANTENNAE, CubeListBuilder.create().texOffs(6, 7).addBox(-2.5F, 0.0F, -3.0F, 5.0F, 0.0F, 3.0F), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild(RIGHT_ANTENNA, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild(LEFT_ANTENNA, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild(LEFT_MANDIBLE, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild(RIGHT_MANDIBLE, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition limbs = body.addOrReplaceChild(LIMBS, CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

        limbs.addOrReplaceChild(RIGHT_HIND_LEG, CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, -2.0F, -1.0F, 0.0F, 4.0F, 5.0F), PartPose.offsetAndRotation(-1.5F, -2.0F, 2.0F, 0.0F, -0.4363F, 0.0F));

        limbs.addOrReplaceChild(RIGHT_FORE_LEG, CubeListBuilder.create().texOffs(0, 7).addBox(-2.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F), PartPose.offsetAndRotation(-1.5F, -1.5F, -1.0F, 0.0F, -0.3927F, 0.0F));

        limbs.addOrReplaceChild(RIGHT_MID_LEG, CubeListBuilder.create().texOffs(11, 1).addBox(-2.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F), PartPose.offsetAndRotation(-1.5F, -1.0F, 0.0F, 0.0F, 0.4363F, 0.0F));

        limbs.addOrReplaceChild(LEFT_HIND_LEG, CubeListBuilder.create().texOffs(0, 7).mirror().addBox(0.0F, -2.0F, -1.0F, 0.0F, 4.0F, 5.0F).mirror(false), PartPose.offsetAndRotation(1.5F, -2.0F, 2.0F, 0.0F, 0.4363F, 0.0F));

        limbs.addOrReplaceChild(LEFT_FORE_LEG, CubeListBuilder.create().texOffs(0, 7).mirror().addBox(0.0F, -1.5F, 0.0F, 2.0F, 3.0F, 0.0F).mirror(false), PartPose.offsetAndRotation(1.5F, -1.5F, -1.0F, 0.0F, 0.3927F, 0.0F));

        limbs.addOrReplaceChild(LEFT_MID_LEG, CubeListBuilder.create().texOffs(11, 1).mirror().addBox(0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F).mirror(false), PartPose.offsetAndRotation(1.5F, -1.0F, 0.0F, 0.0F, -0.4363F, 0.0F));

        body.addOrReplaceChild(ABDOMEN, CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }
    
    @Override
    public ModelPart root() {
        return this.root;
    }
}