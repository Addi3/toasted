package com.addie.toasted.core;

import net.minecraft.item.FoodComponent;

public class ToastedModFoodComponenets {

    public static final FoodComponent TOAST = new FoodComponent.Builder()
            .hunger(2)
            .saturationModifier(0.1f)
            .snack()
            .build();
}

