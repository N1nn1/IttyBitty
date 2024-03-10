package com.ninni.itty_bitty.mixin.client;

import com.ninni.itty_bitty.IttyBittyClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

import static com.ninni.itty_bitty.IttyBitty.MOD_ID;

@Environment(EnvType.CLIENT)
@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {

    @Shadow protected abstract void loadTopLevel(ModelResourceLocation modelResourceLocation);
    @Shadow @Final private Map<ResourceLocation, UnbakedModel> topLevelModels;
    @Shadow public abstract UnbakedModel getModel(ResourceLocation resourceLocation);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void IB$init(BlockColors blockColors, ProfilerFiller profilerFiller, Map map, Map map2, CallbackInfo ci) {

        profilerFiller.push("missing_model");
        this.loadTopLevel(IttyBittyClient.BUBBLEBOX_IN_HAND_MODEL);
        this.loadTopLevel(IttyBittyClient.BUGBOX_IN_HAND_MODEL);
        this.topLevelModels.values().forEach(unbakedModel -> unbakedModel.resolveParents(this::getModel));
        profilerFiller.pop();
    }
}
