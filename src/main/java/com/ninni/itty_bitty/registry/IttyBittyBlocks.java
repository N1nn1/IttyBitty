package com.ninni.itty_bitty.registry;

import com.ninni.itty_bitty.IttyBitty;
import com.ninni.itty_bitty.block.BubbleBoxBlock;
import com.ninni.itty_bitty.block.BugBoxBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;

public class IttyBittyBlocks {

    public static final Block BUBBLEBOX = register("bubblebox", new BubbleBoxBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOD).pushReaction(PushReaction.BLOCK).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0f)));
    public static final Block BUGBOX = register("bugbox", new BugBoxBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOD).pushReaction(PushReaction.BLOCK).ignitedByLava().instrument(NoteBlockInstrument.BASS).strength(2.0f, 3.0f)));

    private static Block register(String id, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(IttyBitty.MOD_ID, id), block);
    }
}
