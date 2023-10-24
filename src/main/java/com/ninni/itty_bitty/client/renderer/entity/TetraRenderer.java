package com.ninni.itty_bitty.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ninni.itty_bitty.IttyBitty;
import com.ninni.itty_bitty.client.model.TetraModel;
import com.ninni.itty_bitty.entity.Tetra;
import com.ninni.itty_bitty.registry.IttyBittyModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class TetraRenderer extends MobRenderer<Tetra, TetraModel<Tetra>> {
    private final TetraModel<Tetra> torpedo = this.getModel();
    private final TetraModel<Tetra> dinnerplate;
    private final TetraModel<Tetra> rocket;

    public TetraRenderer(EntityRendererProvider.Context context) {
        super(context, new TetraModel<>(context.bakeLayer(IttyBittyModelLayers.TETRA_TORPEDO)), 0.2F);
        this.dinnerplate = new TetraModel<>(context.bakeLayer(IttyBittyModelLayers.TETRA_DINNERPLATE));
        this.rocket = new TetraModel<>(context.bakeLayer(IttyBittyModelLayers.TETRA_ROCKET));
    }

    public void render(Tetra tetra, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        this.model = switch (tetra.getBodyType()) {
            case "torpedo" -> this.torpedo;
            case "dinnerplate" -> this.dinnerplate;
            case "rocket" -> this.rocket;
            default -> throw new IncompatibleClassChangeError();
        };

        super.render(tetra, f, g, poseStack, multiBufferSource, i);
    }

    @Nullable
    @Override
    protected RenderType getRenderType(Tetra livingEntity, boolean bl, boolean bl2, boolean bl3) {
        ResourceLocation resourceLocation = this.getTextureLocation(livingEntity);
        return RenderType.entityTranslucent(resourceLocation);
    }

    public ResourceLocation getTextureLocation(Tetra tetra) {
        return new ResourceLocation(IttyBitty.MOD_ID, "textures/entity/tetra/"+ tetra.getVariant().getSerializedName() +".png");
    }

    protected void setupRotations(Tetra tetra, PoseStack poseStack, float f, float g, float h) {
        super.setupRotations(tetra, poseStack, f, g, h);
        float i = 4.3F * Mth.sin(0.6F * f);
        poseStack.mulPose(Axis.YP.rotationDegrees(i));
        if (!tetra.isInWater()) {
            poseStack.translate(0.1F, 0.1F, 0.0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }

    }
}
