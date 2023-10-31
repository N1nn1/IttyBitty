package com.ninni.itty_bitty;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class IttyBittyClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		IttyBittyVanillaIntegration.Client.clientInit();
	}
}