package com.ninni.itty_bitty.registry;

import com.ninni.itty_bitty.client.model.CorydoraModel;
import com.ninni.itty_bitty.client.model.TetraModel;
import com.ninni.itty_bitty.client.model.TreeFrogModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

import static com.ninni.itty_bitty.IttyBitty.MOD_ID;

public interface IttyBittyModelLayers {

    ModelLayerLocation CORYDORA = main("corydora", CorydoraModel::createBodyLayer);
    ModelLayerLocation TETRA_TORPEDO = main("tetra_torpedo", TetraModel::createTorpedoBodyLayer);
    ModelLayerLocation TETRA_DINNERPLATE = main("tetra_dinnerplate", TetraModel::createDinnerplateBodyLayer);
    ModelLayerLocation TETRA_ROCKET = main("tetra_rocket", TetraModel::createRocketBodyLayer);
    ModelLayerLocation TREE_FROG = main("tree_frog", TreeFrogModel::createBodyLayer);

    private static ModelLayerLocation register(String id, String name, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        ModelLayerLocation layer = new ModelLayerLocation(new ResourceLocation(MOD_ID, id), name);
        EntityModelLayerRegistry.registerModelLayer(layer, provider);
        return layer;
    }

    private static ModelLayerLocation main(String id, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        return register(id, "main", provider);
    }
}
