package com.ninni.itty_bitty.item;

import com.ninni.itty_bitty.entity.collectables.IttyBittyBugCollectables;
import com.ninni.itty_bitty.entity.collectables.IttyBittyFishCollectables;
import com.ninni.itty_bitty.entity.common.AbstractBug;
import com.ninni.itty_bitty.entity.common.TerrestrialCollectable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CollectedMobItem extends Item {
    public final EntityType<?> type;
    public final boolean fish;

    public CollectedMobItem(EntityType<?> entityType, boolean fish, Properties properties) {
        super(properties);
        this.type = entityType;
        this.fish = fish;
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        if (!this.fish) {
            BlockPlaceContext blockPlaceContext = new BlockPlaceContext(useOnContext);
            BlockPos pos = blockPlaceContext.getClickedPos();
            ItemStack stack = useOnContext.getItemInHand();

            if (useOnContext.getLevel() instanceof ServerLevel serverLevel) {
                Entity bug = this.type.spawn(serverLevel, stack, useOnContext.getPlayer(), pos, MobSpawnType.BUCKET, true, false);
                if (stack.hasCustomHoverName()) bug.setCustomName(stack.getHoverName());
                if (stack.hasTag()) {
                    bug.load(stack.getTag());
                    if (bug instanceof TerrestrialCollectable collectable) {
                        collectable.loadFromLiveItemTag(stack.getTag());
                    }
                    if (bug instanceof AbstractBug abstractBug) {
                        abstractBug.setFromLiveItem(true);
                    }
                }
                bug.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                serverLevel.addFreshEntity(bug);
            }

            useOnContext.getPlayer().playSound(IttyBittyBugCollectables.getByType(this.type).getReleaseSound());
            useOnContext.getPlayer().setItemInHand(useOnContext.getHand(), ItemStack.EMPTY);
            return InteractionResult.SUCCESS;
        }

        return super.useOn(useOnContext);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack itemStack, ItemStack itemStack2, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess) {
        ItemStack output;

        if (itemStack2.is(Items.WATER_BUCKET) && itemStack.hasTag() && this.fish) {
            IttyBittyFishCollectables type = IttyBittyFishCollectables.getByType(this.type);

            if (type != null) {
                output = new ItemStack(type.getBucketItem());
                player.playSound(type.getFillSound());
            } else {
                throw new IncompatibleClassChangeError();
            }

            output.setTag(itemStack.getTag());
            slotAccess.set(output);
            itemStack.shrink(1);
            itemStack2.shrink(1);
            return true;
        }

        return super.overrideOtherStackedOnMe(itemStack, itemStack2, slot, clickAction, player, slotAccess);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        IttyBittyTooltipUtil.ittyBittyTooltips(itemStack, type, list, true);
    }
}
