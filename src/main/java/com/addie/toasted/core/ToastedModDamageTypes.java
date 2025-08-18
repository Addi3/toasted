package com.addie.toasted.core;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

public class ToastedModDamageTypes {
    public static final RegistryKey<DamageType> TOASTER =
            RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("toasted", "toaster"));

    public static DamageSource toaster(ServerWorld world) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(TOASTER));
    }
}
