package com.ninni.itty_bitty.mixin;

import com.ninni.itty_bitty.IttyBittyTags;
import com.ninni.itty_bitty.block.BugBoxBlockEntity;
import com.ninni.itty_bitty.registry.IttyBittyBlockEntityType;
import com.ninni.itty_bitty.registry.IttyBittyItems;
import com.ninni.itty_bitty.registry.IttyBittySoundEvents;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity implements Targeting {

    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void collect(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack output;
        Mob that = (Mob) (Object) this;

        if (this.getLiveBug(false) != null) {

            for (ItemStack itemStack2 : player.getInventory().items) {
                if (itemStack2.is(IttyBittyItems.BUGBOX)) {
                    if (player.getMainHandItem().is(IttyBittyTags.NETS) && this.isAlive()) {

                        CompoundTag compoundTag = BlockItem.getBlockEntityData(itemStack2);

                        if (compoundTag != null && compoundTag.contains(BugBoxBlockEntity.ITEMS_TAG)) {
                            NonNullList<ItemStack> nonNullList = NonNullList.withSize(54, ItemStack.EMPTY);
                            ContainerHelper.loadAllItems(compoundTag, nonNullList);
                            for (int i = 0; i < nonNullList.size(); i++) {
                                if (nonNullList.get(i).isEmpty()) {
                                    output = getLiveBug(true);
                                    saveDefaultDataToItemTag(that, output);
                                    this.discard();
                                    nonNullList.set(i, output);
                                    ContainerHelper.saveAllItems(compoundTag, nonNullList);
                                    cir.setReturnValue(InteractionResult.sidedSuccess(this.level().isClientSide));
                                    break;
                                }
                            }
                        }
                        if (compoundTag == null || !compoundTag.contains(BugBoxBlockEntity.ITEMS_TAG)) {
                            CompoundTag compoundTag1 = new CompoundTag();
                            NonNullList<ItemStack> nonNullList = NonNullList.withSize(54, ItemStack.EMPTY);
                            output = getLiveBug(true);
                            saveDefaultDataToItemTag(that, output);
                            this.discard();
                            nonNullList.set(0, output);
                            ContainerHelper.saveAllItems(compoundTag1, nonNullList);
                            BlockItem.setBlockEntityData(itemStack2, IttyBittyBlockEntityType.BUGBOX, compoundTag1);
                            cir.setReturnValue(InteractionResult.sidedSuccess(this.level().isClientSide));

                        }
                    }
                }
            }

            if (!player.getInventory().hasAnyMatching(itemStack -> itemStack.is(IttyBittyItems.BUGBOX)) && player.getMainHandItem().is(IttyBittyTags.NETS) && this.isAlive()) {
                if (player.getInventory().getFreeSlot() != -1) {
                    output = getLiveBug(true);
                    saveDefaultDataToItemTag(that, output);
                    this.discard();
                    player.getInventory().add(output);
                    cir.setReturnValue(InteractionResult.sidedSuccess(this.level().isClientSide));
                } else {
                    this.playSound(IttyBittySoundEvents.COLLECT_FAIL);
                    player.displayClientMessage(Component.translatable("net.inventory.full"), true);
                    cir.setReturnValue(InteractionResult.sidedSuccess(this.level().isClientSide));

                }
            }

        }

    }

    private static void saveDefaultDataToItemTag(Mob mob, ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getOrCreateTag();
        if (mob.hasCustomName()) {
            itemStack.setHoverName(mob.getCustomName());
        }

        mob.save(compoundTag);


        compoundTag.putFloat("Health", mob.getHealth());
    }

    private ItemStack getLiveBug(boolean bl) {
        Mob that = (Mob) (Object) this;
        if (that instanceof Endermite) {
            if (bl) this.playSound(IttyBittySoundEvents.COLLECT_ENDERMITE);
            return new ItemStack(IttyBittyItems.LIVE_ENDERMITE);
        } else if (that instanceof Silverfish) {
            if (bl) this.playSound(IttyBittySoundEvents.COLLECT_SILVERFISH);
            return new ItemStack(IttyBittyItems.LIVE_SILVERFISH);
        } else if (that instanceof Bee) {
            if (bl) this.playSound(IttyBittySoundEvents.COLLECT_BUG);
            return new ItemStack(IttyBittyItems.LIVE_BEE);
        }
        return null;
    }
}
