package com.ninni.itty_bitty.client.gui.screen;

import com.ninni.itty_bitty.registry.IttyBittyMenuType;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class BubbleBoxMenu extends AbstractContainerMenu {
    public final Container container;

    public BubbleBoxMenu(int i, Inventory inventory) {
        this(i, inventory, new SimpleContainer(54));
    }

    public BubbleBoxMenu(int i, Inventory inventory, Container container) {
        super(IttyBittyMenuType.BUBBLEBOX, i);
        int m;
        int l;
        ChestMenu.checkContainerSize(container, 54);
        this.container = container;
        container.startOpen(inventory.player);
        int k = 36;
        for (l = 0; l < 6; ++l) {
            for (m = 0; m < 9; ++m) {
                this.addSlot(new BubbleBoxSlot(container, m + l * 9, 8 + m * 18, 18 + l * 18));
            }
        }
        for (l = 0; l < 3; ++l) {
            for (m = 0; m < 9; ++m) {
                this.addSlot(new Slot(inventory, m + l * 9 + 9, 8 + m * 18, 103 + l * 18 + k));
            }
        }
        for (l = 0; l < 9; ++l) {
            this.addSlot(new Slot(inventory, l, 8 + l * 18, 161 + k));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (i < this.container.getContainerSize() ? !this.moveItemStackTo(itemStack2, this.container.getContainerSize(), this.slots.size(), true) : !this.moveItemStackTo(itemStack2, 0, this.container.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemStack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }
}
