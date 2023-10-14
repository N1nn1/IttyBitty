package com.ninni.itty_bitty.block;

import com.ninni.itty_bitty.client.gui.screen.BugBoxMenu;
import com.ninni.itty_bitty.registry.IttyBittyBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;


public class BugBoxBlockEntity extends RandomizableContainerBlockEntity {
    public static final String ITEMS_TAG = "Items";
    private NonNullList<ItemStack> itemStacks = NonNullList.withSize(54, ItemStack.EMPTY);
    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter(){

        @Override
        protected void onOpen(Level level, BlockPos blockPos, BlockState blockState) {
            BugBoxBlockEntity.this.playSound(SoundEvents.BARREL_OPEN);
            BugBoxBlockEntity.this.updateBlockState(blockState, true);
        }

        @Override
        protected void onClose(Level level, BlockPos blockPos, BlockState blockState) {
            BugBoxBlockEntity.this.playSound(SoundEvents.BARREL_CLOSE);
            BugBoxBlockEntity.this.updateBlockState(blockState, false);
        }

        @Override
        protected void openerCountChanged(Level level, BlockPos blockPos, BlockState blockState, int i, int j) {
        }

        @Override
        protected boolean isOwnContainer(Player player) {
            if (player.containerMenu instanceof BugBoxMenu) {
                Container container = ((BugBoxMenu)player.containerMenu).container;
                return container == BugBoxBlockEntity.this;
            }
            return false;
        }
    };

    public BugBoxBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(IttyBittyBlockEntityType.BUGBOX, blockPos, blockState);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("itty_bitty.container.bugbox");
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.itemStacks = nonNullList;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.itemStacks;
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new BugBoxMenu(i, inventory, this);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.loadFromTag(compoundTag);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        if (!this.trySaveLootTable(compoundTag)) {
            ContainerHelper.saveAllItems(compoundTag, this.itemStacks, false);
        }
    }

    public void loadFromTag(CompoundTag compoundTag) {
        this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(compoundTag) && compoundTag.contains(ITEMS_TAG, 9)) {
            ContainerHelper.loadAllItems(compoundTag, this.itemStacks);
        }
    }

    @Override
    public int getContainerSize() {
        return this.itemStacks.size();
    }

    @Override
    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    void updateBlockState(BlockState blockState, boolean bl) {
        this.level.setBlock(this.getBlockPos(), blockState.setValue(BarrelBlock.OPEN, bl), 3);
    }

    void playSound(SoundEvent soundEvent) {
        double d = (double)this.worldPosition.getX() + 0.5;
        double e = (double)this.worldPosition.getY() + 0.5;
        double f = (double)this.worldPosition.getZ() + 0.5;
        this.level.playSound(null, d, e, f, soundEvent, SoundSource.BLOCKS, 0.5f, this.level.random.nextFloat() * 0.1f + 0.9f);
    }
}
