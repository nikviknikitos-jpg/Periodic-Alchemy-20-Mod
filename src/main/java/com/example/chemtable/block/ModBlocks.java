package com.example.chemtable.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static Block CHEM_REACTOR;

    public static void register() {
        CHEM_REACTOR = Registry.register(Registries.BLOCK,
                new Identifier("chemtable", "chem_reactor"),
                new ChemReactorBlock(Block.Settings.create().strength(3f).luminance(s -> 6)));
        Registry.register(Registries.ITEM, new Identifier("chemtable", "chem_reactor"),
                new BlockItem(CHEM_REACTOR, new Item.Settings()));
    }
}
