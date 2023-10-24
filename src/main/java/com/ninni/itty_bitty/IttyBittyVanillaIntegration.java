package com.ninni.itty_bitty;

import com.google.common.reflect.Reflection;
import com.ninni.itty_bitty.client.gui.screen.BubbleBoxScreen;
import com.ninni.itty_bitty.client.gui.screen.BugBoxScreen;
import com.ninni.itty_bitty.client.renderer.entity.TetraRenderer;
import com.ninni.itty_bitty.client.renderer.entity.TreeFrogRenderer;
import com.ninni.itty_bitty.registry.IttyBittyEntityType;
import com.ninni.itty_bitty.registry.IttyBittyModelLayers;
import com.ninni.itty_bitty.registry.IttyBittyMenuType;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;

public class IttyBittyVanillaIntegration {


    public static void serverInit() {
    }

    public static void clientInit() {
        registerModelLayers();
        registerScreens();
    }

    //server

    //client
    private static void registerModelLayers() {
        Reflection.initialize(IttyBittyModelLayers.class);
        EntityRendererRegistry.register(IttyBittyEntityType.TETRA, TetraRenderer::new);
        EntityRendererRegistry.register(IttyBittyEntityType.TREE_FROG, TreeFrogRenderer::new);
    }

    private static void registerScreens() {
        MenuScreens.register(IttyBittyMenuType.BUBBLEBOX, BubbleBoxScreen::new);
        MenuScreens.register(IttyBittyMenuType.BUGBOX, BugBoxScreen::new);
    }

}
