package com.ninni.itty_bitty.client.renderer.entity;

import com.ninni.itty_bitty.IttyBitty;
import com.ninni.itty_bitty.client.model.CorydoraModel;
import com.ninni.itty_bitty.entity.Corydora;
import com.ninni.itty_bitty.registry.IttyBittyModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class CorydoraRenderer extends MobRenderer<Corydora, CorydoraModel<Corydora>> {

    public CorydoraRenderer(EntityRendererProvider.Context context) {
        super(context, new CorydoraModel<>(context.bakeLayer(IttyBittyModelLayers.CORYDORA)), 0.2F);
    }


    @Nullable
    @Override
    protected RenderType getRenderType(Corydora livingEntity, boolean bl, boolean bl2, boolean bl3) {
        ResourceLocation resourceLocation = this.getTextureLocation(livingEntity);
        return RenderType.entityTranslucent(resourceLocation);
    }

    public ResourceLocation getTextureLocation(Corydora corydora) {
        return new ResourceLocation(IttyBitty.MOD_ID, "textures/entity/corydora/"+ corydora.getVariant().getSerializedName() +".png");
    }
}
