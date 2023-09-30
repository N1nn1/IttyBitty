package com.ninni.itty_bitty;

import com.google.common.reflect.Reflection;
import com.ninni.itty_bitty.registry.*;
import net.fabricmc.api.ModInitializer;


public class IttyBitty implements ModInitializer {
	public static final String MOD_ID = "itty_bitty";

	@Override
	public void onInitialize() {

		Reflection.initialize(
				IttyBittyItems.class,
				IttyBittyBlocks.class,
				IttyBittyBlockEntityType.class,
				IttyBittyMenuType.class,
				IttyBittyCreativeModeTab.class,
				IttyBittySoundEvents.class
		);

		IttyBittyVanillaIntegration.serverInit();
	}
}