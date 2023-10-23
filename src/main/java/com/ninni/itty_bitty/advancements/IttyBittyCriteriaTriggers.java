package com.ninni.itty_bitty.advancements;

import net.minecraft.advancements.CriteriaTriggers;

import static com.ninni.itty_bitty.IttyBitty.MOD_ID;

public class IttyBittyCriteriaTriggers {

    public static IttyBittyCriterionTrigger CATCH_BUG;
    public static IttyBittyCriterionTrigger CATCH_FISH;

    public static void init() {
        CATCH_BUG = CriteriaTriggers.register("catch_bug", new IttyBittyCriterionTrigger());
        CATCH_FISH = CriteriaTriggers.register("catch_fish", new IttyBittyCriterionTrigger());
    }
}