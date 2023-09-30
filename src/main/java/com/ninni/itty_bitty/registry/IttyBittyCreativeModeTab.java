package com.ninni.itty_bitty.registry;

import com.ninni.itty_bitty.IttyBitty;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import static com.ninni.itty_bitty.registry.IttyBittyItems.*;

public class IttyBittyCreativeModeTab {

    public static final CreativeModeTab ITEM_GROUP = register("item_group", FabricItemGroup.builder().icon(TETRA_BUCKET::getDefaultInstance).title(Component.translatable("itty_bitty.item_group")).displayItems((featureFlagSet, output) -> {
                output.accept(TETRA_SPAWN_EGG);
                output.accept(TETRA_BUCKET);
                output.accept(NET);
            }).build()
    );

    private static CreativeModeTab register(String id, CreativeModeTab tab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(IttyBitty.MOD_ID, id), tab);
    }
}
