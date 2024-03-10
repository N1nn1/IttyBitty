package com.ninni.itty_bitty.entity.variant;

import com.mojang.serialization.Codec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum TetraVariant implements StringRepresentable {
    RUMMYNOSE(0, "rummynose", "torpedo", "Hemigrammus rhodostomus"),
    GLOW_LIGHT(1, "glow_light", "torpedo", "Hemigrammus erythrozonus"),
    BLACK_NEON(2, "black_neon", "torpedo", "Hyphessobrycon herbertaxelrodi"),
    GREEN_NEON(3, "green_neon", "torpedo", "Paracheirodon simulans"),
    RED_NEON(4, "red_neon", "torpedo", "Paracheirodon axelrodi"),
    RUBY(5, "ruby", "torpedo", "Axelrodia riesei"),
    GOLDEN(6, "golden", "torpedo", "Hyphessobrycon moniliger"),
    BLACK_WIDOW(7, "black_widow", "dinnerplate", "Gymnocorymbus ternetzi"),
    COLUMBIAN(8, "columbian", "dinnerplate", "Hyphessobrycon columbianus"),
    YELLOW_PHANTOM(9, "yellow_phantom", "dinnerplate", "Hyphessobrycon roseus"),
    BLACK_PHANTOM(10, "black_phantom", "dinnerplate", "Hyphessobrycon megalopterus"),
    RED_PHANTOM(11, "red_phantom", "dinnerplate", "Hyphessobrycon sweglesi"),
    DIAMOND(12, "diamond", "dinnerplate", "Moenkhausia pittieri"),
    CONGO(13, "congo", "rocket", "Phenacogrammus interruptus"),
    BLACK_EMPEROR(14, "black_emperor", "rocket", "Nematobrycon palmeri"),
    BLUE_EMPEROR(15, "blue_emperor", "rocket", "Inpaichthys kerri"),
    EMPEROR(16, "emperor", "rocket", "Nematobrycon palmeri"),
    SUPER_BLUE_EMPEROR(17, "super_blue_emperor", "rocket", "Inpaichthys kerri"),
    RAINBOW(18, "rainbow", "rocket", "Nematobrycon lacortei"),
    BUENOS_AIRES(19, "buenos_aires", "rocket", "Psalidodon anisitsi");

    private static final IntFunction<TetraVariant> BY_ID;
    public static final Codec<TetraVariant> CODEC;
    final int id;
    private final String name;
    private final String type;
    private final String scientificName;

    TetraVariant(int j, String string, String type, String scientificName) {
        this.id = j;
        this.name = string;
        this.type = type;
        this.scientificName = scientificName;
    }

    public String getType() {
        return this.type;
    }

    public String getScientificName() {
        return scientificName;
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