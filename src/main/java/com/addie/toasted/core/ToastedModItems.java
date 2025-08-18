package com.addie.toasted.core;

import dev.amble.lib.container.impl.ItemContainer;
import dev.amble.lib.datagen.util.AutomaticModel;
import dev.amble.lib.datagen.util.NoEnglish;
import dev.amble.lib.item.AItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import org.jetbrains.annotations.Nullable;


public class ToastedModItems extends ItemContainer {

    @AutomaticModel
    @NoEnglish
    public static final Item TOAST = new Item(new AItemSettings().group(ToastedModItemGroups.MAIN).food(ToastedModFoodComponenets.TOAST));

    @AutomaticModel
    @NoEnglish
    public static final Item BURNT_TOAST = new Item(new AItemSettings().group(ToastedModItemGroups.MAIN).food(ToastedModFoodComponenets.TOAST));



    @Override
    public @Nullable ItemGroup getDefaultGroup() {
        return ToastedModItemGroups.MAIN;}
}

