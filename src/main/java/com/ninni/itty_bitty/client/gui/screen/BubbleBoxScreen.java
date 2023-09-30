package com.ninni.itty_bitty.client.gui.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.ninni.itty_bitty.IttyBitty.MOD_ID;

@Environment(value= EnvType.CLIENT)
public class BubbleBoxScreen extends AbstractContainerScreen<BubbleBoxMenu> {
    private static final ResourceLocation CONTAINER_TEXTURE = new ResourceLocation(MOD_ID, "textures/gui/container/bubblebox.png");

    public BubbleBoxScreen(BubbleBoxMenu bubbleBoxMenu, Inventory inventory, Component component) {
        super(bubbleBoxMenu, inventory, component);
        this.imageHeight = 114 + 6 * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        this.renderTooltip(guiGraphics, i, j);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(CONTAINER_TEXTURE, k, l, 0, 0, this.imageWidth, 6 * 18 + 17);
        guiGraphics.blit(CONTAINER_TEXTURE, k, l + 6 * 18 + 17, 0, 126, this.imageWidth, 96);
    }
}

