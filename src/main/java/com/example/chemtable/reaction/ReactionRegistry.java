package com.example.chemtable.reaction;

import com.example.chemtable.item.ModItems;
import net.minecraft.item.ItemStack;

import java.util.*;

public class ReactionRegistry {
    public static final Map<String, String> RESULT = new LinkedHashMap<>();

    static {
        add("r_water", "h", "h", "o");
        add("r_detonator", "h", "o");
        add("r_alkali", "na", "h", "o");
        add("r_super_alkali", "k", "na", "h", "o");
        add("r_napalm", "p", "o");
        add("r_hellfire", "p", "s", "o", "c");
        add("r_chlorine", "cl", "cl");
        add("r_chlorine", "k", "cl");
        add("r_mustard", "c", "h", "cl", "s");
        add("r_cryo", "n", "h");
        add("r_abs_zero", "n", "o", "cl");
        add("r_acid", "h", "cl", "n", "o");
        add("r_polymer", "ca", "c", "o");
        add("r_obsidian_gel", "ca", "fe", "c", "o");
        add("r_emp", "fe", "k", "cl");
        add("r_nuclear", "u", "h", "o", "n");
        add("elixir_titan", "fe", "ca", "c");
        add("elixir_swift", "n", "o", "h");
        add("elixir_vision", "p", "k", "o");
        add("elixir_magma", "s", "fe", "o");
        add("elixir_transmute", "u", "fe", "c", "p");
    }

    private static void add(String result, String... elems) {
        String key = normalize(Arrays.asList(elems));
        RESULT.put(key, result);
    }

    public static String match(List<String> elements) {
        return RESULT.get(normalize(elements));
    }

    private static String normalize(List<String> elements) {
        List<String> copy = new ArrayList<>(elements);
        Collections.sort(copy);
        return String.join("+", copy);
    }

    public static String elementOf(ItemStack stack) {
        for (var e : ModItems.ELEMENTS.entrySet()) {
            if (stack.getItem() == e.getValue()) return e.getKey();
        }
        return null;
    }
}
