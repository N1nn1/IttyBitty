package com.ninni.itty_bitty.item;

import com.google.common.collect.Maps;
import com.ninni.itty_bitty.entity.variant.BeetleVariant;
import com.ninni.itty_bitty.entity.variant.CorydoraVariant;
import com.ninni.itty_bitty.entity.variant.TetraVariant;
import com.ninni.itty_bitty.registry.IttyBittyEntityType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.List;

public class IttyBittyTooltipUtil {
    public static HashMap<EntityType<?>, String> VANILLA_SCIENTIFIC_NAMES = Maps.newHashMap();



    public static void ittyBittyTooltips(ItemStack itemStack, EntityType<?> type, List<Component> list, boolean isCollectedItem) {
        CompoundTag compoundTag;

        if ((compoundTag = itemStack.getTag()) != null && compoundTag.contains("BucketVariantTag", 3)) {
            int i = compoundTag.getInt("BucketVariantTag");

            if (type == IttyBittyEntityType.TETRA) {
                list.add(Component.translatable("entity.itty_bitty.tetra.type." + TetraVariant.byId(i).getType()).withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY));
                list.add(Component.translatable("entity.itty_bitty.tetra.variant." + TetraVariant.byId(i).getSerializedName()).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
                addScientificName(list, TetraVariant.byId(i).getScientificName());
            }

            if (type == IttyBittyEntityType.CORYDORA) {
                list.add(Component.translatable("entity.itty_bitty.corydora.variant." + CorydoraVariant.byId(i).getSerializedName()).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
                addScientificName(list, CorydoraVariant.byId(i).getScientificName());
            }

        }

        if (isCollectedItem) {
            if ((compoundTag = itemStack.getTag()) != null && compoundTag.contains("LiveItemVariantTag", 3)) {
                int i = compoundTag.getInt("LiveItemVariantTag");

                if (type == IttyBittyEntityType.BEETLE) {
                    list.add(Component.translatable("entity.itty_bitty.beetle.type." + BeetleVariant.byId(i).getType()).withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY));
                    list.add(Component.translatable("entity.itty_bitty.beetle.variant." + BeetleVariant.byId(i).getSerializedName()).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
                    addScientificName(list, BeetleVariant.byId(i).getScientificName());
                }
            }

            addTropicalFish(itemStack, type, list);

            if ((compoundTag = itemStack.getTag()) != null && compoundTag.getInt("Age") < 0) {
                list.add(Component.translatable("entity.itty_bitty.collected_mob.baby").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
            }
        }

        addVanillaScientificName(list, type, itemStack);
    }

    public static void addVanillaScientificName(List<Component> list, EntityType<?> type, ItemStack itemStack) {
        CompoundTag compoundTag;
        if (type == EntityType.AXOLOTL && Screen.hasShiftDown() && (compoundTag = itemStack.getTag()) != null &&  compoundTag.contains("Variant", 3)) {
            String name = "A. mexicanum x A. tigrinum";
            if (compoundTag.getInt("Variant") == 1) name = "Ambystoma mexicanum";
            if (compoundTag.getInt("Variant") == 4) name = "Ambystoma tetragonus";
            list.add(Component.translatable("entity.itty_bitty.collected_mob.info_expanded", name).withStyle(ChatFormatting.BLUE));
        }
        if (VANILLA_SCIENTIFIC_NAMES.containsKey(type) && Screen.hasShiftDown()) {
            list.add(Component.translatable("entity.itty_bitty.collected_mob.info_expanded", VANILLA_SCIENTIFIC_NAMES.get(type)).withStyle(ChatFormatting.BLUE));
        }
    }

    public static void addScientificName(List<Component> list, String name) {
        if (Screen.hasShiftDown()) {
            list.add(Component.translatable("entity.itty_bitty.collected_mob.info_expanded", name).withStyle(ChatFormatting.BLUE));
        }
    }

    public static void addTropicalFish(ItemStack itemStack, EntityType<?> type, List<Component> list) {
        CompoundTag compoundTag;

        if (type == EntityType.TROPICAL_FISH && (compoundTag = itemStack.getTag()) != null && compoundTag.contains("BucketVariantTag", 3)) {
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
            if (!string.equals(string2)) mutableComponent.append(", ").append(Component.translatable(string2));
            mutableComponent.withStyle(chatFormattings);
            list.add(mutableComponent);
        }
    }

    public static void addVanillaScientificName(EntityType<?> entity, String name) {
        VANILLA_SCIENTIFIC_NAMES.put(entity, name);
    }

    static {
        addVanillaScientificName(EntityType.BEE, "Apis mellifera");
        addVanillaScientificName(EntityType.SALMON, "Oncorhynchus nerka");
        addVanillaScientificName(EntityType.COD, "Gadus morhua");
        addVanillaScientificName(EntityType.PUFFERFISH, "Diodon holocanthus");
        addVanillaScientificName(EntityType.TROPICAL_FISH, "temp");
        addVanillaScientificName(EntityType.TADPOLE, "temp");
        addVanillaScientificName(EntityType.ENDERMITE, "Aceria enderiforme");
        addVanillaScientificName(EntityType.SILVERFISH, "Petraceria insensilis");
    }
}
