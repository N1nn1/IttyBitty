package com.ninni.itty_bitty.mixin;

import com.ninni.itty_bitty.entity.Tetra;
import com.ninni.itty_bitty.registry.IttyBittyItems;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFish.class)
public abstract class AbstractFishMixin extends WaterAnimal implements Bucketable {

    protected AbstractFishMixin(EntityType<? extends WaterAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    public void collect(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {

        //TODO fix
        ItemStack itemStack = player.getItemInHand(interactionHand);
        Level level = this.level();
        ItemStack output;
        AbstractFish that = (AbstractFish) (Object) this;
        int x = player.getInventory().findSlotMatchingItem(IttyBittyItems.BUBBLEBOX.getDefaultInstance());
        ItemStack bubbleStack = player.getInventory().getItem(x);

        for (ItemStack itemStack2 : player.getInventory().items) {
            if (itemStack2.is(IttyBittyItems.BUBBLEBOX)) {
                if (itemStack.is(IttyBittyItems.NET) && this.isAlive()) {

                    CompoundTag compoundTag2 = BlockItem.getBlockEntityData(bubbleStack);
                    CompoundTag compoundTag = bubbleStack.getOrCreateTag();
                    compoundTag.put("Items", compoundTag2);
                    if (compoundTag.contains("Items", 9)) {
                        NonNullList<ItemStack> nonNullList = NonNullList.withSize(54, ItemStack.EMPTY);
                        ContainerHelper.loadAllItems(compoundTag, nonNullList);

                        for (ItemStack itemStack3 : nonNullList) {
                            if (itemStack3.isEmpty()) {
                                if (that instanceof Cod) {
                                    output = new ItemStack(IttyBittyItems.LIVE_COD);
                                } else if (that instanceof Salmon) {
                                    output = new ItemStack(IttyBittyItems.LIVE_SALMON);
                                } else if (that instanceof TropicalFish) {
                                    output = new ItemStack(IttyBittyItems.LIVE_TROPICAL_FISH);
                                } else if (that instanceof Pufferfish) {
                                    output = new ItemStack(IttyBittyItems.LIVE_PUFFERFISH);
                                } else if (that instanceof Tetra) {
                                    output = new ItemStack(IttyBittyItems.LIVE_TETRA);
                                } else {
                                    throw new IncompatibleClassChangeError();
                                }


                                (this).saveToBucketTag(output);
                                nonNullList.add(output);

                                this.discard();
                                cir.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide));
                            }
                        }
                    }


                }
            }
        }
    }
}
