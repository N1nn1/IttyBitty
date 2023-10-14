package com.ninni.itty_bitty.client.gui.screen;

import com.ninni.itty_bitty.IttyBittyTags;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BugBoxSlot extends Slot {
    public BugBoxSlot(Container container, int i, int j, int k) {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return itemStack.is(IttyBittyTags.LIVE_BUGS);
    }
}

