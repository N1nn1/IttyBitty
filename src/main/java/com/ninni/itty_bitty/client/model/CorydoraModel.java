package com.ninni.itty_bitty.client.model;


import com.ninni.itty_bitty.entity.Corydora;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@SuppressWarnings("FieldCanBeLocal, unused")
@Environment(value= EnvType.CLIENT)
public class CorydoraModel<T extends Corydora> extends HierarchicalModel<T> {
	private static final String DORSAL_FIN = "dorsalFin";
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart dorsalFin;
	private final ModelPart leftFin;
	private final ModelPart rightFin;

	public CorydoraModel(ModelPart root) {
		this.root = root;

		this.body = root.getChild(PartNames.BODY);

		this.tail = body.getChild(PartNames.TAIL);
		this.dorsalFin = body.getChild(DORSAL_FIN);
		this.leftFin = body.getChild(PartNames.LEFT_FIN);
		this.rightFin = body.getChild(PartNames.RIGHT_FIN);
	}

	@Override
	public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		float speed = 1f;
		float degree = 2f;
		float pi = ((float)Math.PI);

		this.rightFin.yRot = 0;
		this.leftFin.yRot = 0;
		this.tail.zRot = 0;
		this.tail.yRot = Mth.cos(limbAngle * 1.2f * speed + pi) * degree * 1.6f * limbDistance;

	}
	
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild(
				PartNames.BODY,
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(-1.5F, -3.0F, -2.5F, 3.0F, 3.0F, 5.0F)
						.texOffs(0, 8)
						.addBox(-1.5F, 0.0F, -4.5F, 3.0F, 0.0F, 2.0F),
				PartPose.offset(0.0F, 24.0F, -0.25F)
		);

		body.addOrReplaceChild(
				PartNames.RIGHT_FIN,
				CubeListBuilder.create()
						.texOffs(-2, 0)
						.addBox(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F),
				PartPose.offset(-1.5F, 0.0F, -0.5F)
		);

		body.addOrReplaceChild(
				PartNames.LEFT_FIN,
				CubeListBuilder.create()
						.texOffs(-2, 2)
						.addBox(0.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F),
				PartPose.offset(1.5F, 0.0F, -0.5F)
		);

		body.addOrReplaceChild(
				PartNames.TAIL,
				CubeListBuilder.create()
						.texOffs(0, 7)
						.addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 3.0F),
				PartPose.offset(0.0F, -1.5F, 2.5F)
		);

		body.addOrReplaceChild(
				DORSAL_FIN,
				CubeListBuilder.create()
						.texOffs(0, 12)
						.addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F),
				PartPose.offset(0.0F, -3.0F, 0.5F)
		);

		return LayerDefinition.create(meshdefinition, 16, 16);
	}
	

	@Override
	public ModelPart root() {
		return this.root;
	}
}