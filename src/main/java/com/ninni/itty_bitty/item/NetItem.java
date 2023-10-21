package com.ninni.itty_bitty.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NetItem extends Item {
    public boolean golden;

    public NetItem(boolean golden, Properties properties) {
        super(properties);
        this.golden = golden;
    }


    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        if (golden) {
            ChatFormatting[] chatFormattings = new ChatFormatting[]{ChatFormatting.GRAY};
            ChatFormatting[] chatFormattings2 = new ChatFormatting[]{ChatFormatting.BLUE};
            list.add(Component.translatable(""));
            list.add(Component.translatable("item.itty_bitty.golden_net.when").withStyle(chatFormattings));
            list.add(Component.translatable("item.itty_bitty.golden_net.reach").withStyle(chatFormattings2));
        }
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return golden;
    }

    @Override
    public boolean canAttackBlock(BlockState blockState, Level level, BlockPos blockPos, Player player) {
        return false;
    }
}
