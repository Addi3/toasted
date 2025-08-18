package com.addie.toasted.core;

import com.addie.toasted.core.blocks.ToasterBlock;
import dev.amble.lib.block.ABlockSettings;
import dev.amble.lib.container.impl.BlockContainer;
import dev.amble.lib.container.impl.NoBlockItem;
import dev.amble.lib.datagen.util.AutomaticModel;
import dev.amble.lib.datagen.util.NoBlockDrop;
import dev.amble.lib.datagen.util.NoEnglish;
import dev.amble.lib.datagen.util.PickaxeMineable;
import dev.amble.lib.item.AItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public class ToastedModBlocks extends BlockContainer {


    @NoEnglish
    @PickaxeMineable(tool = PickaxeMineable.Tool.STONE)
    public static final Block TOASTER = new ToasterBlock(ABlockSettings.create()
            .itemSettings(new AItemSettings().group(ToastedModItemGroups.MAIN)).nonOpaque().requiresTool()
            .strength(10.0F, 1.0F).pistonBehavior(PistonBehavior.NORMAL).sounds(BlockSoundGroup.METAL));

    @Override
    public Item.Settings createBlockItemSettings(Block block) {
        return new AItemSettings().group(ToastedModItemGroups.MAIN);
    }

    static {
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((group, entry) -> {
        });
    }
}

