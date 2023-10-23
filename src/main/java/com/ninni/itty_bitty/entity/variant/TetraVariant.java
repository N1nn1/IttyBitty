package com.ninni.itty_bitty.entity.variant;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum TetraVariant implements StringRepresentable {
    RUMMYNOSE(0, "rummynose", "torpedo"),
    GLOW_LIGHT(1, "glow_light", "torpedo"),
    BLACK_NEON(2, "black_neon", "torpedo"),
    GREEN_NEON(3, "green_neon", "torpedo"),
    RED_NEON(4, "red_neon", "torpedo"),
    RUBY(5, "ruby", "torpedo"),
    GOLDEN(6, "golden", "torpedo"),
    BLACK_WIDOW(7, "black_widow", "dinnerplate"),
    COLUMBIAN(8, "columbian", "dinnerplate"),
    YELLOW_PHANTOM(9, "yellow_phantom", "dinnerplate"),
    BLACK_PHANTOM(10, "black_phantom", "dinnerplate"),
    RED_PHANTOM(11, "red_phantom", "dinnerplate"),
    DIAMOND(12, "diamond", "dinnerplate"),
    CONGO(13, "congo", "rocket"),
    BLACK_EMPEROR(14, "black_emperor", "rocket"),
    BLUE_EMPEROR(15, "blue_emperor", "rocket"),
    EMPEROR(16, "emperor", "rocket"),
    RAINBOW(17, "rainbow", "rocket"),
    BUENOS_AIRES(18, "buenos_aires", "rocket");

    private static final IntFunction<TetraVariant> BY_ID;
    public static final Codec<TetraVariant> CODEC;
    final int id;
    private final String name;
    private final String type;

    TetraVariant(int j, String string, String type) {
        this.id = j;
        this.name = string;
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public String getBodyPlan() {
        return this.type;
    }

    public int id() {
        return this.id;
    }

    public static TetraVariant byId(int i) {
        return BY_ID.apply(i);
    }

    static {
        BY_ID = ByIdMap.sparse(TetraVariant::id, TetraVariant.values(), RUMMYNOSE);
        CODEC = StringRepresentable.fromEnum(TetraVariant::values);
    }
}