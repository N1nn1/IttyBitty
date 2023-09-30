package com.ninni.itty_bitty.registry;

import com.ninni.itty_bitty.client.gui.screen.BubbleBoxMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import static com.ninni.itty_bitty.IttyBitty.MOD_ID;

public class IttyBittyMenuType {

    public static final MenuType<BubbleBoxMenu> BUBBLEBOX = simple("bubblebox", BubbleBoxMenu::new);

    private static <T extends AbstractContainerMenu> MenuType<T> simple(String id, MenuType.MenuSupplier<T> factory) {
        return Registry.register(BuiltInRegistries.MENU, new ResourceLocation(MOD_ID, id), new MenuType<>(factory, FeatureFlags.VANILLA_SET));
    }
}
