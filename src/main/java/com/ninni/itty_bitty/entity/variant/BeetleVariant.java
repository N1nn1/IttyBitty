package com.ninni.itty_bitty.entity.variant;

import com.mojang.serialization.Codec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum BeetleVariant implements StringRepresentable {
    ACORN(0, "acorn", "weevil", "temp"),
    BLUE(1, "blue", "weevil", "temp"),
    FUNGUS(2, "fungus", "weevil", "temp"),
    JEWEL(3, "jewel", "weevil", "temp"),
    PALM(4, "palm", "weevil", "temp"),
    VINE(5, "vine", "weevil", "temp"),
    DEVILS_COACH_HORSE(6, "devils_coach_horse", "rove", "temp"),
    GOLDCREST(7, "goldcrest", "rove", "Staphylinus erythropterus"),
    SPLENDID(8, "splendid", "rove", "Plochionocerus splendens"),
    PRECIOUS(9, "precious", "rove", "Phanolinus pretiosus");

    private static final IntFunction<BeetleVariant> BY_ID;
    public static final Codec<BeetleVariant> CODEC;
    final int id;
    private final String name;
    private final String type;
    private final String scientificName;

    BeetleVariant(int j, String string, String type, String scientificName) {
        this.id = j;
        this.name = string;
        this.type = type;
        this.scientificName = scientificName;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public String getScientificName() {
        return this.scientificName;
    }

    public String getBodyPlan() {
        return this.type;
    }

    public int id() {
        return this.id;
    }

    public static BeetleVariant byId(int i) {
        return BY_ID.apply(i);
    }

    static {
        BY_ID = ByIdMap.sparse(BeetleVariant::id, BeetleVariant.values(), ACORN);
        CODEC = StringRepresentable.fromEnum(BeetleVariant::values);
    }
}