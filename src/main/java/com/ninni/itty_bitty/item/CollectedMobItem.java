package com.ninni.itty_bitty.item;

import com.ninni.itty_bitty.entity.variant.TetraVariant;
import com.ninni.itty_bitty.registry.IttyBittyEntityType;
import com.ninni.itty_bitty.registry.IttyBittyItems;
import com.ninni.itty_bitty.registry.IttyBittySoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.animal.TropicalFish;
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
import java.util.Objects;

public class CollectedMobItem extends Item {
    private final EntityType<?> type;
    private final boolean fish;

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

            Mob bug = (Mob)type.create(useOnContext.getLevel());
            if (stack.hasCustomHoverName()) bug.setCustomName(stack.getHoverName());
            if (stack.hasTag()) {
                bug.load(stack.getTag());
            }
            bug.moveTo(pos.getX(), pos.getY(), pos.getZ(), Objects.requireNonNull(useOnContext.getPlayer()).getYRot(), 0.0f);
            useOnContext.getPlayer().playSound(IttyBittySoundEvents.RELEASE_BUG);
            useOnContext.getLevel().addFreshEntity(bug);
            useOnContext.getPlayer().setItemInHand(useOnContext.getHand(), ItemStack.EMPTY);
            return InteractionResult.SUCCESS;
        }

        return super.useOn(useOnContext);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack itemStack, ItemStack itemStack2, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess) {
        ItemStack output;

        if (itemStack2.is(Items.WATER_BUCKET) && itemStack.hasTag() && this.fish) {

            if (this.type.equals(EntityType.COD)) {
                output = new ItemStack(Items.COD_BUCKET);
                player.playSound(SoundEvents.BUCKET_FILL_FISH);
            } else if (this.type.equals(EntityType.SALMON)) {
                output = new ItemStack(Items.SALMON_BUCKET);
                player.playSound(SoundEvents.BUCKET_FILL_FISH);
            } else if (this.type.equals(EntityType.TROPICAL_FISH)) {
                output = new ItemStack(Items.TROPICAL_FISH_BUCKET);
                player.playSound(SoundEvents.BUCKET_FILL_FISH);
            } else if (this.type.equals(EntityType.PUFFERFISH)) {
                output = new ItemStack(Items.PUFFERFISH_BUCKET);
                player.playSound(SoundEvents.BUCKET_FILL_FISH);
            } else if (this.type.equals(EntityType.TADPOLE)) {
                output = new ItemStack(Items.TADPOLE_BUCKET);
                player.playSound(SoundEvents.BUCKET_FILL_TADPOLE);
            } else if (this.type.equals(EntityType.AXOLOTL)) {
                output = new ItemStack(Items.AXOLOTL_BUCKET);
                player.playSound(SoundEvents.BUCKET_FILL_AXOLOTL);
            } else if (this.type.equals(IttyBittyEntityType.TETRA)) {
                output = new ItemStack(IttyBittyItems.TETRA_BUCKET);
                player.playSound(SoundEvents.BUCKET_FILL_FISH);
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
        CompoundTag compoundTag;
        if (this.type == EntityType.TROPICAL_FISH && (compoundTag = itemStack.getTag()) != null && compoundTag.contains("BucketVariantTag", 3)) {
            int i = compoundTag.getInt("BucketVariantTag");
            ChatFormatting[] chatFormattings = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
            String string = "color.minecraft." + TropicalFish.getBaseColor(i);
            String string2 = "color.minecraft." + TropicalFish.getPatternColor(i);
            for (int j = 0; j < TropicalFish.COMMON_VARIANTS.size(); ++j) {
                if (i != TropicalFish.COMMON_VARIANTS.get(j).getPackedId()) continue;
                list.add(Component.translatable(TropicalFish.getPredefinedName(j)).withStyle(chatFormattings));
                return;
            }
            list.add(TropicalFish.getPattern(i).displayName().plainCopy().withStyle(chatFormattings));
            MutableComponent mutableComponent = Component.translatable(string);
            if (!string.equals(string2)) {
                mutableComponent.append(", ").append(Component.translatable(string2));
            }
            mutableComponent.withStyle(chatFormattings);
            list.add(mutableComponent);
        }

        if (this.type == IttyBittyEntityType.TETRA && (compoundTag = itemStack.getTag()) != null && compoundTag.contains("BucketVariantTag", 3)) {
            int i = compoundTag.getInt("BucketVariantTag");
            list.add(Component.translatable("entity.itty_bitty.tetra.type." + TetraVariant.byId(i).getType()).withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY));
            list.add(Component.translatable("entity.itty_bitty.tetra.variant." + TetraVariant.byId(i).getSerializedName()).withStyle(ChatFormatting.ITALIC, TetraVariant.byId(i).getColor()));
        }
    }
}
