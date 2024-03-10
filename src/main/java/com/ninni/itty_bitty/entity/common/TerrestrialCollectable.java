package com.ninni.itty_bitty.entity.common;

import net.minecraft.nbt.CompoundTag;

public interface TerrestrialCollectable {
    boolean fromLiveItem();

    void setFromLiveItem(boolean bl);

    void saveToLiveItemTag(CompoundTag compoundTag);

    void loadFromLiveItemTag(CompoundTag compoundTag);
}
