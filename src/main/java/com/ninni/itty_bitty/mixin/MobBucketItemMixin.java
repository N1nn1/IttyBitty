package com.ninni.itty_bitty.mixin;

import com.ninni.itty_bitty.entity.variant.TetraVariant;
import com.ninni.itty_bitty.registry.IttyBittyEntityType;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
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
        CompoundTag compoundTag;
        if (this.type == IttyBittyEntityType.TETRA && (compoundTag = itemStack.getTag()) != null && compoundTag.contains("BucketVariantTag", 3)) {
            int i = compoundTag.getInt("BucketVariantTag");
            ChatFormatting[] chatFormattings = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
            list.add(Component.translatable("entity.itty_bitty.tetra.variant." + TetraVariant.byId(i).getSerializedName()).withStyle(chatFormattings));
        }
    }
}
