package com.example.chemtable.reaction;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.*;

public class ReactorData {
    public final List<String> elements = new ArrayList<>();
    public boolean stabilizer = false;

    private static final Map<String, ReactorData> ALL = new HashMap<>();

    public static ReactorData get(ServerWorld w, BlockPos pos) {
        return ALL.computeIfAbsent(w.getRegistryKey().getValue() + "@" + pos.asLong(), k -> new ReactorData());
    }

    public static void clear(ServerWorld w, BlockPos pos) {
        ALL.remove(w.getRegistryKey().getValue() + "@" + pos.asLong());
    }
}
