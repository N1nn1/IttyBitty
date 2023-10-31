package com.ninni.itty_bitty.entity.variant;

import com.mojang.serialization.Codec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum CorydoraVariant implements StringRepresentable {
    ADOLFOI(0, "adolfoi"),
    BRONZE(1, "bronze"),
    PANDA(2, "panda"),
    PEPPERED(3, "peppered"),
    STERBAI(4, "sterbai"),
    VENEZUELAN_BLACK(5, "venezuelan_black"),
    VENEZUELAN_ORANGE(6, "venezuelan_orange");

    private static final IntFunction<CorydoraVariant> BY_ID;
    public static final Codec<CorydoraVariant> CODEC;
    final int id;
    private final String name;

    CorydoraVariant(int j, String string) {
        this.id = j;
        this.name = string;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public int id() {
        return this.id;
    }

    public static CorydoraVariant byId(int i) {
        return BY_ID.apply(i);
    }

    static {
        BY_ID = ByIdMap.sparse(CorydoraVariant::id, CorydoraVariant.values(), ADOLFOI);
        CODEC = StringRepresentable.fromEnum(CorydoraVariant::values);
    }
}