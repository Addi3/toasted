package com.addie.toasted.core;

import com.addie.toasted.ToastedMod;
import dev.amble.lib.container.impl.ItemGroupContainer;
import dev.amble.lib.itemgroup.AItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ToastedModItemGroups implements ItemGroupContainer {
    public static final AItemGroup MAIN = AItemGroup.builder(ToastedMod.id("item_group"))
            .icon(() -> new ItemStack(ToastedModBlocks.TOASTER))
            .entries((context, entries) -> {
            })
            .build();
}

