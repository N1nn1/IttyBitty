package com.ninni.itty_bitty.entity.variant;

import com.mojang.serialization.Codec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum TreeFrogVariant implements StringRepresentable {
    AMAZON_MILK(0, "amazon_milk"),
    GLASS(1, "glass"),
    HARLEQUIN(2, "harlequin"),
    HYLA_ARBOREA(3, "hyla_arborea"),
    LEMUR_LEAF(4, "lemur_leaf"),
    RED_EYED(5, "red_eyed"),
    SYLVIAS(6, "sylvias"),
    WHITES(7, "whites");

    private static final IntFunction<TreeFrogVariant> BY_ID;
    public static final Codec<TreeFrogVariant> CODEC;
    final int id;
    private final String name;

    TreeFrogVariant(int j, String string) {
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

    public static TreeFrogVariant byId(int i) {
        return BY_ID.apply(i);
    }

    static {
        BY_ID = ByIdMap.sparse(TreeFrogVariant::id, TreeFrogVariant.values(), RED_EYED);
        CODEC = StringRepresentable.fromEnum(TreeFrogVariant::values);
    }
}