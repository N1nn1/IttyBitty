package com.ninni.itty_bitty.entity.variant;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum TetraVariant implements StringRepresentable {
    RUMMYNOSE(0, "rummynose", "torpedo", ChatFormatting.DARK_RED),
    GLOW_LIGHT(1, "glow_light", "torpedo", ChatFormatting.GOLD),
    BLACK_NEON(2, "black_neon", "torpedo", ChatFormatting.DARK_GREEN),
    GREEN_NEON(3, "green_neon", "torpedo", ChatFormatting.AQUA),
    RED_NEON(4, "red_neon", "torpedo", ChatFormatting.BLUE),
    RUBY(5, "ruby", "torpedo", ChatFormatting.RED),
    GOLDEN(6, "golden", "torpedo", ChatFormatting.GOLD),
    BLACK_WIDOW(7, "black_widow", "dinnerplate", ChatFormatting.GRAY),
    COLUMBIAN(8, "columbian", "dinnerplate", ChatFormatting.GRAY),
    YELLOW_PHANTOM(9, "yellow_phantom", "dinnerplate", ChatFormatting.YELLOW),
    BLACK_PHANTOM(10, "black_phantom", "dinnerplate", ChatFormatting.GRAY),
    RED_PHANTOM(11, "red_phantom", "dinnerplate", ChatFormatting.DARK_RED),
    DIAMOND(12, "diamond", "dinnerplate", ChatFormatting.WHITE),
    CONGO(13, "congo", "rocket", ChatFormatting.RED),
    BLACK_EMPEROR(14, "black_emperor", "rocket", ChatFormatting.GRAY),
    BLUE_EMPEROR(15, "blue_emperor", "rocket", ChatFormatting.DARK_PURPLE),
    EMPEROR(16, "emperor", "rocket", ChatFormatting.YELLOW),
    RAINBOW(17, "rainbow", "rocket", ChatFormatting.GREEN),
    BUENOS_AIRES(18, "buenos_aires", "rocket", ChatFormatting.DARK_GREEN);

    private static final IntFunction<TetraVariant> BY_ID;
    public static final Codec<TetraVariant> CODEC;
    final int id;
    private final String name;
    private final String type;
    private final ChatFormatting color;

    TetraVariant(int j, String string, String type, ChatFormatting color) {
        this.id = j;
        this.name = string;
        this.type = type;
        this.color = color;
    }

    public ChatFormatting getColor() {
        return this.color;
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