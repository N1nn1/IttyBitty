package com.ninni.itty_bitty.mixin;

import com.ninni.itty_bitty.block.BubbleBoxBlockEntity;
import com.ninni.itty_bitty.entity.Tetra;
import com.ninni.itty_bitty.registry.IttyBittyBlockEntityType;
import com.ninni.itty_bitty.registry.IttyBittyItems;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
        AbstractFish that = (AbstractFish) (Object) this;
        ItemStack output;

        for (ItemStack itemStack2 : player.getInventory().items) {
            if (itemStack2.is(IttyBittyItems.BUBBLEBOX)) {
                if (player.getItemInHand(interactionHand).is(IttyBittyItems.NET) && this.isAlive()) {

                    CompoundTag compoundTag = BlockItem.getBlockEntityData(itemStack2);
                    if (compoundTag == null) {
                        ListTag listTag = new ListTag();

                        CompoundTag compoundTag1 = itemStack2.getOrCreateTag();
                        compoundTag1.put(BubbleBoxBlockEntity.ITEMS_TAG, listTag);

                        BlockItem.setBlockEntityData(itemStack2, IttyBittyBlockEntityType.BUBBLEBOX, compoundTag1);
                    }
                    if (compoundTag.contains(BubbleBoxBlockEntity.ITEMS_TAG)) {
                        NonNullList<ItemStack> nonNullList = NonNullList.withSize(54, ItemStack.EMPTY);
                        ContainerHelper.loadAllItems(compoundTag, nonNullList);

                        for (int i = 1; i < nonNullList.size(); i++) {
                            if (nonNullList.get(i).isEmpty()) {
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

                                this.saveToBucketTag(output);
                                this.discard();
                                nonNullList.set(i, output);
                                ContainerHelper.saveAllItems(compoundTag, nonNullList);
                                cir.setReturnValue(InteractionResult.sidedSuccess(this.level().isClientSide));
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
