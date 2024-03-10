package com.ninni.itty_bitty.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ninni.itty_bitty.IttyBitty;
import com.ninni.itty_bitty.client.model.BeetleModel;
import com.ninni.itty_bitty.entity.Beetle;
import com.ninni.itty_bitty.registry.IttyBittyModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class BeetleRenderer extends MobRenderer<Beetle, BeetleModel<Beetle>> {
    private final BeetleModel<Beetle> weevil = this.getModel();
    private final BeetleModel<Beetle> rove;
    private final BeetleModel<Beetle> scarab;

    public BeetleRenderer(EntityRendererProvider.Context context) {
        super(context, new BeetleModel<>(context.bakeLayer(IttyBittyModelLayers.BEETLE_WEEVIL)), 0.2F);
        this.rove = new BeetleModel<>(context.bakeLayer(IttyBittyModelLayers.BEETLE_ROVE));
        this.scarab = new BeetleModel<>(context.bakeLayer(IttyBittyModelLayers.BEETLE_SCARAB));
    }

    public void render(Beetle beetle, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        this.model = switch (beetle.getBodyType()) {
            case "weevil" -> this.weevil;
            case "rove" -> this.rove;
            case "scarab" -> this.scarab;
            default -> throw new IncompatibleClassChangeError();
        };


        super.render(beetle, f, g, poseStack, multiBufferSource, i);
    }

    @Nullable
    @Override
    protected RenderType getRenderType(Beetle livingEntity, boolean bl, boolean bl2, boolean bl3) {
        return RenderType.entityTranslucent(this.getTextureLocation(livingEntity));
    }

    public ResourceLocation getTextureLocation(Beetle beetle) {
        return new ResourceLocation(IttyBitty.MOD_ID, "textures/entity/beetle/" + beetle.getBodyType() + "/" + beetle.getVariant().getSerializedName() +".png");
    }
}
