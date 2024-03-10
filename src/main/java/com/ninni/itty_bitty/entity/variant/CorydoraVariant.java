package com.ninni.itty_bitty.entity.variant;

import com.mojang.serialization.Codec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum CorydoraVariant implements StringRepresentable {
    ADOLFOI(0, "adolfoi", "Corydoras adolfoi"),
    BRONZE(1, "bronze", "Corydoras aeneus"),
    PANDA(2, "panda", "Corydoras panda"),
    PEPPERED(3, "peppered", "Corydoras paleatus"),
    STERBAI(4, "sterbai", "Corydoras sterbai"),
    VENEZUELAN_BLACK(5, "venezuelan_black", "Corydoras schultzei"),
    VENEZUELAN_ORANGE(6, "venezuelan_orange", "Corydoras aeneus orange");

    private static final IntFunction<CorydoraVariant> BY_ID;
    public static final Codec<CorydoraVariant> CODEC;
    final int id;
    private final String name;
    private final String scientificName;

    CorydoraVariant(int j, String string, String scientificName) {
        this.id = j;
        this.name = string;
        this.scientificName = scientificName;
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

    public String getScientificName() {
        return scientificName;
    }
}