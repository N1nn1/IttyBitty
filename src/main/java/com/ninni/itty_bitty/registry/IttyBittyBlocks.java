package com.ninni.itty_bitty.registry;

import com.ninni.itty_bitty.IttyBitty;
import com.ninni.itty_bitty.block.BubbleBoxBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class IttyBittyBlocks {

    public static final Block BUBBLEBOX = register("bubblebox", new BubbleBoxBlock(BlockBehaviour.Properties.of()));

    private static Block register(String id, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(IttyBitty.MOD_ID, id), block);
    }
}
