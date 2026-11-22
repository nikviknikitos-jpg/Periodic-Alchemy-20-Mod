package com.example.chemtable.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModItems {
    public static final Map<String, Item> ELEMENTS = new LinkedHashMap<>();
    public static final Map<String, Item> GRENADES = new LinkedHashMap<>();
    public static final Map<String, Item> ELIXIRS = new LinkedHashMap<>();
    public static Item STABILIZER, CHEM_BOOK;
    public static ItemGroup TAB;

    public static void register() {
        String[] elems = {"h", "o", "na", "cl", "p", "k", "n", "ca", "c", "s", "fe", "u"};
        for (String e : elems) {
            ELEMENTS.put(e, reg(e, new Item(new Item.Settings())));
        }
        STABILIZER = reg("stabilizer", new Item(new Item.Settings()));
        CHEM_BOOK = reg("chem_book", new ChemBookItem(new Item.Settings().maxCount(1)));

        String[] elixirs = {"elixir_titan", "elixir_swift", "elixir_vision", "elixir_magma"};
        for (String e : elixirs) ELIXIRS.put(e, reg(e, new ElixirItem(new Item.Settings().maxCount(16), e)));
        ELIXIRS.put("elixir_transmute", reg("elixir_transmute", new TransmuteItem(new Item.Settings().maxCount(16))));

        String[] grenades = {"r_water", "r_detonator", "r_alkali", "r_super_alkali", "r_napalm",
                "r_hellfire", "r_chlorine", "r_mustard", "r_cryo", "r_abs_zero", "r_acid",
                "r_polymer", "r_obsidian_gel", "r_emp", "r_nuclear"};
        for (String g : grenades) GRENADES.put(g, reg(g, new GrenadeItem(new Item.Settings().maxCount(16), g)));

        TAB = Registry.register(Registries.ITEM_GROUP, new Identifier("chemtable", "chem_tab"),
                FabricItemGroup.builder().displayName(Text.translatable("itemGroup.chemtable.chem_tab"))
                        .icon(() -> new ItemStack(STABILIZER))
                        .entries((ctx, entries) -> {
                            entries.add(ModBlocks.CHEM_REACTOR.asItem());
                            entries.add(CHEM_BOOK);
                            entries.add(STABILIZER);
                            for (Item i : ELEMENTS.values()) entries.add(i);
                            for (Item i : GRENADES.values()) entries.add(i);
                            for (Item i : ELIXIRS.values()) entries.add(i);
                        }).build());
    }

    private static Item reg(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier("chemtable", name), item);
    }
}
