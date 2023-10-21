package com.ninni.itty_bitty;

import com.google.common.reflect.Reflection;
import com.ninni.itty_bitty.registry.*;
import net.fabricmc.api.ModInitializer;

//TODO recipe for Bubblebox doesnt take the bucket with it
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