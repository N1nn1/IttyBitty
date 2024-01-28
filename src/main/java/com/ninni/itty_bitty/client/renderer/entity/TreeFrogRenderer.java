package com.ninni.itty_bitty.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ninni.itty_bitty.IttyBitty;
import com.ninni.itty_bitty.client.model.TreeFrogModel;
import com.ninni.itty_bitty.entity.TreeFrog;
import com.ninni.itty_bitty.registry.IttyBittyModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class TreeFrogRenderer extends MobRenderer<TreeFrog, TreeFrogModel<TreeFrog>> {

    public TreeFrogRenderer(EntityRendererProvider.Context context) {
        super(context, new TreeFrogModel<>(context.bakeLayer(IttyBittyModelLayers.TREE_FROG)), 0.3F);
    }

    @Nullable
    @Override
    protected RenderType getRenderType(TreeFrog livingEntity, boolean bl, boolean bl2, boolean bl3) {
        ResourceLocation resourceLocation = this.getTextureLocation(livingEntity);
        return RenderType.entityTranslucent(resourceLocation);
    }

    public ResourceLocation getTextureLocation(TreeFrog frog) {
        return new ResourceLocation(IttyBitty.MOD_ID, "textures/entity/tree_frog/"+ frog.getVariant().getSerializedName() +".png");
    }

    @Override
    protected void scale(TreeFrog livingEntity, PoseStack poseStack, float f) {
        super.scale(livingEntity, poseStack, 2);
    }
}
