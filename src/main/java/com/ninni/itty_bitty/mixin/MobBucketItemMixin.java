package com.ninni.itty_bitty.mixin;

import com.ninni.itty_bitty.item.IttyBittyTooltipUtil;
import com.ninni.itty_bitty.entity.collectables.IttyBittyFishCollectables;
import com.ninni.itty_bitty.registry.IttyBittyTags;
import com.ninni.itty_bitty.client.gui.screen.BubbleBoxSlot;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(MobBucketItem.class)
public abstract class MobBucketItemMixin extends BucketItem {
    @Shadow @Final private EntityType<?> type;

    public MobBucketItemMixin(Fluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @Inject(method = "appendHoverText", at = @At("HEAD"))
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo ci) {
        IttyBittyTooltipUtil.ittyBittyTooltips(itemStack, type, list, false);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack itemStack, Slot slot, ClickAction clickAction, Player player) {
        ItemStack output;

        if (itemStack.is(IttyBittyTags.COLLECTABLE_FISH_BUCKETS) && itemStack.hasTag() && slot instanceof BubbleBoxSlot && slot.getItem().isEmpty() && player.getInventory().getFreeSlot() != -1) {
            IttyBittyFishCollectables type = IttyBittyFishCollectables.getByType(this.type);

            if (type != null) {
                output = new ItemStack(type.getLiveItem());
                player.playSound(type.getEmptySound());
            } else {
                throw new IncompatibleClassChangeError();
            }
            output.setTag(itemStack.getTag());
            slot.set(output);
            itemStack.shrink(1);
            player.addItem(Items.WATER_BUCKET.getDefaultInstance());
            return true;
        }


        return super.overrideStackedOnOther(itemStack, slot, clickAction, player);
    }
}
