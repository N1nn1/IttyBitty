package com.ninni.itty_bitty;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.model.ModelResourceLocation;
import org.spongepowered.asm.mixin.Unique;

import static com.ninni.itty_bitty.IttyBitty.MOD_ID;

@Environment(EnvType.CLIENT)
public class IttyBittyClient implements ClientModInitializer {
	public static final ModelResourceLocation BUBBLEBOX_IN_HAND_MODEL = new ModelResourceLocation(MOD_ID, "bubblebox_in_hand", "inventory");
	public static final ModelResourceLocation BUGBOX_IN_HAND_MODEL = new ModelResourceLocation(MOD_ID, "bugbox_in_hand", "inventory");

	@Override
	public void onInitializeClient() {
		IttyBittyVanillaIntegration.Client.clientInit();
	}
}