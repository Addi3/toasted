package com.addie.toasted;

import com.addie.toasted.core.ToastedModBlocks;
import com.addie.toasted.core.ToastedModItemGroups;
import com.addie.toasted.core.ToastedModItems;
import dev.amble.lib.datagen.lang.AmbleLanguageProvider;
import dev.amble.lib.datagen.lang.LanguageType;
import dev.amble.lib.datagen.loot.AmbleBlockLootTable;
import dev.amble.lib.datagen.model.AmbleModelProvider;
import dev.amble.lib.datagen.sound.AmbleSoundProvider;
import dev.amble.lib.datagen.tag.AmbleBlockTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import static net.minecraft.data.server.recipe.RecipeProvider.conditionsFromItem;
import static net.minecraft.data.server.recipe.RecipeProvider.hasItem;

public class ToastedModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator gen) {
        FabricDataGenerator.Pack pack = gen.createPack();

        genLang(pack);
        genSounds(pack);
        genModels(pack);
        generateRecipes(pack);
        genLoot(pack);
        genTags(pack);
	}

    private void genTags(FabricDataGenerator.Pack pack) {
        pack.addProvider((((output, registriesFuture) -> new AmbleBlockTagProvider(output, registriesFuture).withBlocks(ToastedModBlocks.class))));
    }
    private void genLoot(FabricDataGenerator.Pack pack) {
        pack.addProvider((((output, registriesFuture) -> new AmbleBlockLootTable(output).withBlocks(ToastedModBlocks.class))));
    }

    private void genModels(FabricDataGenerator.Pack pack) {
        pack.addProvider((((output, registriesFuture) -> {
            AmbleModelProvider provider = new AmbleModelProvider(output);

            provider.withBlocks(ToastedModBlocks.class);
            provider.withItems(ToastedModItems.class);

            return provider;
        })));
    }

    private void genLang(FabricDataGenerator.Pack pack) {
        genEnglish(pack);
    }

    public void generateRecipes(FabricDataGenerator.Pack pack) {
        pack.addProvider((((output, registriesFuture) -> {
            ToastedModRecipeProvider provider = new ToastedModRecipeProvider(output);


            provider.addShapedRecipe(
                    ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, ToastedModBlocks.TOASTER)
                            .pattern("ICI")
                            .pattern("ICI")
                            .pattern("SRS")
                            .input('I', Items.IRON_INGOT)
                            .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                            .input('C', Items.COPPER_INGOT)
                            .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                            .input('S', Items.DRIED_KELP)
                            .criterion(hasItem(Items.DRIED_KELP), conditionsFromItem(Items.DRIED_KELP))
                            .input('R', Items.REDSTONE)
                            .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE)));

            return provider;
        })));
    }


    private void genEnglish(FabricDataGenerator.Pack pack) {
        pack.addProvider((((output, registriesFuture) -> {
            AmbleLanguageProvider provider = new AmbleLanguageProvider(output, LanguageType.EN_US);

            //Blocks
            provider.addTranslation(ToastedModBlocks.TOASTER, "Toaster");

            //Items
            provider.addTranslation(ToastedModItems.TOAST, "Toast");
            provider.addTranslation(ToastedModItems.BURNT_TOAST, "Burnt Toast");

            //Misc
            provider.addTranslation("death.attack.toaster", "%1$s was electrocuted by a toaster");
            provider.addTranslation("death.attack.toaster.player", "%1$s was electrocuted by a toaster while fighting %2$s");
            provider.addTranslation(ToastedModItemGroups.MAIN, "Toasted");

            return provider;
        })));
    }
    private void genSounds(FabricDataGenerator.Pack pack) {
        pack.addProvider((((output, registriesFuture) -> {
            AmbleSoundProvider provider = new AmbleSoundProvider(output);

            return provider;
        })));
    }
}
