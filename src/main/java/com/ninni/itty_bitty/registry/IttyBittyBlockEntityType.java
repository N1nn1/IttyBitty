package com.ninni.itty_bitty.registry;

import com.ninni.itty_bitty.block.BubbleBoxBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static com.ninni.itty_bitty.IttyBitty.MOD_ID;

public class IttyBittyBlockEntityType {

    public static final BlockEntityType<BubbleBoxBlockEntity> BUBBLEBOX = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, "bubblebox"),
            FabricBlockEntityTypeBuilder.create(BubbleBoxBlockEntity::new,
                    IttyBittyBlocks.BUBBLEBOX
            ).build(null)
    );

    public static final BlockEntityType<BubbleBoxBlockEntity> BUGBOX = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(MOD_ID, "bugbox"),
            FabricBlockEntityTypeBuilder.create(BubbleBoxBlockEntity::new,
                    IttyBittyBlocks.BUGBOX
            ).build(null)
    );
}
