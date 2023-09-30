package com.ninni.itty_bitty.client.model;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ninni.itty_bitty.entity.Tetra;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

@SuppressWarnings("FieldCanBeLocal, unused")
@Environment(value= EnvType.CLIENT)
public class TetraModel<T extends Tetra> extends HierarchicalModel<T> {
	private static final String DORSAL_FIN = "dorsalFin";
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart dorsalFin;
	private final ModelPart leftFin;
	private final ModelPart rightFin;

	public TetraModel(ModelPart root) {
		this.root = root;
		this.body = root.getChild(PartNames.BODY);
		this.tail = body.getChild(PartNames.TAIL);
		this.dorsalFin = body.getChild(DORSAL_FIN);
		this.leftFin = body.getChild(PartNames.LEFT_FIN);
		this.rightFin = body.getChild(PartNames.RIGHT_FIN);
	}

	public static LayerDefinition createTorpedoBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild(
				PartNames.BODY,
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-0.5F, -1.0F, -3.0F, 1.0F, 2.0F, 5.0F),
				PartPose.offset(0.0F, 23.0F, -0.5F)
		);

		PartDefinition tail = body.addOrReplaceChild(
				PartNames.TAIL,
				CubeListBuilder.create()
						.texOffs(0, 9)
						.addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 3.0F),
				PartPose.offset(0.0F, -0.5F, 2.0F)
		);

		PartDefinition dorsal = body.addOrReplaceChild(
				DORSAL_FIN,
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 2.0F),
				PartPose.offset(0.0F, -1.0F, 0.0F)
		);

		PartDefinition rightFin = body.addOrReplaceChild(
				PartNames.RIGHT_FIN,
				CubeListBuilder.create()
						.texOffs(0, 9)
						.addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F),
				PartPose.offset(-0.5F, 1.0F, -0.5F)
		);

		PartDefinition leftFin = body.addOrReplaceChild(
				PartNames.LEFT_FIN,
				CubeListBuilder.create()
						.texOffs(4, 9)
						.addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F),
				PartPose.offset(0.5F, 1.0F, -0.5F)
		);

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	public static LayerDefinition createDinnerplateBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild(PartNames.BODY,
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-0.5F, -3.0F, -3.0F, 1.0F, 6.0F, 6.0F)
						.texOffs(16, 0)
						.addBox(0.0F, -2.0F, 3.0F, 0.0F, 5.0F, 3.0F),
				PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, -0.7854F, 0.0F, 0.0F)
		);

		PartDefinition tail = body.addOrReplaceChild(PartNames.TAIL,
				CubeListBuilder.create()
						.texOffs(0, 13)
						.addBox(0.0F, -1.5F, -1.0F, 0.0F, 3.0F, 4.0F),
				PartPose.offsetAndRotation(0.0F, -3.0F, 3.0F, 0.7854F, 0.0F, 0.0F)
		);

		PartDefinition dorsal = body.addOrReplaceChild(
				DORSAL_FIN,
				CubeListBuilder.create()
						.texOffs(10, 8)
						.addBox(0.0F, -3.0F, -2.5F, 0.0F, 3.0F, 5.0F), 
				PartPose.offset(0.0F, -3.0F, -0.5F)
		);

		PartDefinition rightFin = body.addOrReplaceChild(
				PartNames.RIGHT_FIN,
				CubeListBuilder.create()
						.texOffs(9, 0)
						.addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 2.0F), 
				PartPose.offset(-0.5F, 1.5F, 0.0F)
		);

		PartDefinition leftFin = body.addOrReplaceChild(
				PartNames.LEFT_FIN,
				CubeListBuilder.create()
						.texOffs(0, 0)
						.mirror()
						.addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 2.0F)
						.mirror(false),
				PartPose.offset(0.5F, 1.5F, 0.0F)
		);

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	public static LayerDefinition createRocketBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild(
				PartNames.BODY,
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-1.0F, -2.5F, -2.5F, 2.0F, 3.0F, 6.0F)
						.texOffs(6, 10)
						.addBox(-1.0F, -2.5F, -3.5F, 2.0F, 2.0F, 1.0F)
						.texOffs(17, 0)
						.addBox(0.0F, 0.5F, 0.5F, 0.0F, 4.0F, 4.0F),
				PartPose.offset(0.0F, 23.5F, -1.5F)
		);

		PartDefinition tail = body.addOrReplaceChild(
				PartNames.TAIL,
				CubeListBuilder.create()
						.texOffs(0, 10)
						.addBox(0.0F, -1.5F, 0.0F, 0.0F, 5.0F, 5.0F),
				PartPose.offset(0.0F, -2.0F, 3.5F)
		);

		PartDefinition dorsal = body.addOrReplaceChild(
				DORSAL_FIN,
				CubeListBuilder.create()
						.texOffs(11, 10)
						.addBox(0.0F, -2.0F, -2.5F, 0.0F, 2.0F, 5.0F),
				PartPose.offset(0.0F, -2.5F, 1.0F)
		);

		PartDefinition rightFin = body.addOrReplaceChild(
				PartNames.RIGHT_FIN,
				CubeListBuilder.create()
						.texOffs(3, 0)
						.addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F),
				PartPose.offset(-1.0F, -0.5F, -1.0F)
		);

		PartDefinition leftFin = body.addOrReplaceChild(
				PartNames.LEFT_FIN,
				CubeListBuilder.create()
						.texOffs(0, 0)
						.mirror()
						.addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F)
						.mirror(false),
				PartPose.offset(1.0F, -0.5F, -1.0F)
		);

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		float speed = 1f;
		float degree = 2f;
		float pi = ((float)Math.PI);

		if (entity.getBodyType() == "dinnerplate") {
			this.rightFin.zRot = 0;
			this.leftFin.zRot = 0;
			this.leftFin.yRot = Mth.cos(animationProgress * speed * 0.4F + 1f) * degree * 0.8F * 0.25F + 0.8F;
			this.rightFin.yRot = Mth.cos(animationProgress * speed * 0.4F + 1f + pi) * degree * 0.8F * 0.25F - 0.8F;
			this.tail.yRot = Mth.cos(limbAngle * 1.2f * speed + pi) * degree * 0.8F * limbDistance;
			this.tail.zRot = Mth.cos(limbAngle * 1.2f * speed + pi) * degree * 0.8F * limbDistance;
		} else {
			this.rightFin.yRot = 0;
			this.leftFin.yRot = 0;
			this.tail.zRot = 0;
			this.tail.yRot = Mth.cos(limbAngle * 1.2f * speed + pi) * degree * 1.6f * limbDistance;
			this.rightFin.zRot = Mth.cos(animationProgress * speed * 0.4F + 1f) * degree * 0.8F * 0.25F + 0.4F;
			this.leftFin.zRot = Mth.cos(animationProgress * speed * 0.4F + 1f + pi) * degree * 0.8F * 0.25F - 0.4F;
		}
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}