package com.ninni.itty_bitty.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ninni.itty_bitty.IttyBittyClient;
import com.ninni.itty_bitty.registry.IttyBittyItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.ninni.itty_bitty.IttyBitty.MOD_ID;

@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow @Final private ItemModelShaper itemModelShaper;
    @Shadow protected abstract void renderModelLists(BakedModel bakedModel, ItemStack itemStack, int i, int j, PoseStack poseStack, VertexConsumer vertexConsumer);

    @Unique private static final ModelResourceLocation BUBBLEBOX_MODEL = new ModelResourceLocation(MOD_ID, "bubblebox", "inventory");
    @Unique private static final ModelResourceLocation BUGBOX_MODEL = new ModelResourceLocation(MOD_ID, "bugbox", "inventory");

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void IB$render(ItemStack itemStack, ItemDisplayContext itemDisplayContext, boolean bl, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, BakedModel bakedModel, CallbackInfo ci) {
        boolean bl2 = itemDisplayContext == ItemDisplayContext.GUI || itemDisplayContext == ItemDisplayContext.FIXED;

        if (itemStack.is(IttyBittyItems.BUBBLEBOX) || itemStack.is(IttyBittyItems.BUGBOX)) {
            ci.cancel();
            poseStack.pushPose();
            if (bl2) {
                if (itemStack.is(IttyBittyItems.BUBBLEBOX)) {
                    bakedModel = this.itemModelShaper.getModelManager().getModel(BUBBLEBOX_MODEL);
                }
                if (itemStack.is(IttyBittyItems.BUGBOX)) {
                    bakedModel = this.itemModelShaper.getModelManager().getModel(BUGBOX_MODEL);
                }
            }
            bakedModel.getTransforms().getTransform(itemDisplayContext).apply(bl, poseStack);
            poseStack.translate(-0.5F, -0.5F, -0.5F);
            VertexConsumer vertexConsumer;
            boolean bl32 = itemDisplayContext == ItemDisplayContext.GUI || itemDisplayContext.firstPerson();
            RenderType renderType = ItemBlockRenderTypes.getRenderType(itemStack, bl32);
            vertexConsumer = bl32 ? ItemRenderer.getFoilBufferDirect(multiBufferSource, renderType, true, itemStack.hasFoil()) : ItemRenderer.getFoilBuffer(multiBufferSource, renderType, true, itemStack.hasFoil());
            this.renderModelLists(bakedModel, itemStack, i, j, poseStack, vertexConsumer);
            poseStack.popPose();
        }

    }

    @Inject(method = "getModel", at = @At("HEAD"), cancellable = true)
    public void IB$getModel(ItemStack itemStack, Level level, LivingEntity livingEntity, int i, CallbackInfoReturnable<BakedModel> cir) {
        if (itemStack.is(IttyBittyItems.BUBBLEBOX) || itemStack.is(IttyBittyItems.BUGBOX)) {
            cir.cancel();

            BakedModel bakedModel =
                    itemStack.is(IttyBittyItems.BUBBLEBOX) ? this.itemModelShaper.getModelManager().getModel(IttyBittyClient.BUBBLEBOX_IN_HAND_MODEL) :
                            (itemStack.is(IttyBittyItems.BUGBOX) ? this.itemModelShaper.getModelManager().getModel(IttyBittyClient.BUGBOX_IN_HAND_MODEL) : this.itemModelShaper.getItemModel(itemStack));


            ClientLevel clientLevel = level instanceof ClientLevel ? (ClientLevel)level : null;
            BakedModel bakedModel2 = bakedModel.getOverrides().resolve(bakedModel, itemStack, clientLevel, livingEntity, i);

            cir.setReturnValue(bakedModel2 == null ? this.itemModelShaper.getModelManager().getMissingModel() : bakedModel2);
        }
    }
}
