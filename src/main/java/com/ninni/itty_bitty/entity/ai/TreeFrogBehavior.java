package com.ninni.itty_bitty.entity.ai;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public enum TreeFrogBehavior {
    IDLE("Idling", SoundEvents.EMPTY, 0),
    BLINK("Blinking", SoundEvents.EMPTY, 3),
    CROAK("Croaking", SoundEvents.FROG_AMBIENT, 3),
    CLEAN("Cleaning", SoundEvents.EMPTY, 10);
    private final String name;
    private final SoundEvent sound;
    private final int length;

    TreeFrogBehavior(String name, SoundEvent sound, int length) {
        this.name = name;
        this.sound = sound;
        this.length = length;
    }

    public String getName() {
        return name;
    }
    public SoundEvent getSound() {
        return sound;
    }
    public int getLength() {
        return length;
    }
}