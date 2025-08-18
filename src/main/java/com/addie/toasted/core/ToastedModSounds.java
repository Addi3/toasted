package com.addie.toasted.core;

import com.addie.toasted.ToastedMod;
import dev.amble.lib.container.impl.SoundContainer;
import net.minecraft.sound.SoundEvent;

public class ToastedModSounds implements SoundContainer {
    public static final SoundEvent TOASTER = create("toaster");
    public static final SoundEvent TOASTER_ACTIVE = create("toaster_active");


    public static SoundEvent create(String name) {
        return SoundEvent.of(ToastedMod.id(name));
    }
}

