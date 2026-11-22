package com.example.chemtable.reaction;

import com.example.chemtable.item.ModItems;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class ReactionRunner {
    public static void run(ServerPlayerEntity sp, ServerWorld sw, BlockPos pos, ReactorData data) {
        if (data.elements.isEmpty()) {
            sp.sendMessage(Text.literal("§cРеактор пуст."), false);
            return;
        }
        String result = ReactionRegistry.match(data.elements);
        if (result == null) {
            sp.sendMessage(Text.literal("§cНеизвестная реакция."), false);
            return;
        }
        if (data.stabilizer) {
            Item item = "elixir_transmute".equals(result) ? ModItems.ELIXIRS.get(result) : ModItems.GRENADES.get(result);
            if (item == null) item = ModItems.GRENADES.get("r_water");
            ItemStack stack = new ItemStack(item, 1);
            ItemEntity drop = new ItemEntity(sw, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, stack);
            sw.spawnEntity(drop);
            sw.playSound(null, pos, SoundEvents.BLOCK_GLASS_PLACE, SoundCategory.BLOCKS, 1.0f, 1.5f);
            sp.sendMessage(Text.literal("§bРеакция запечатана в гранату: " + result), false);
            return;
        }
        sp.sendMessage(Text.literal("§c[ОПАСНО] Реакция идет прямо в реакторе! Отойдите на 5 блоков!"), false);
        sw.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 2.0f, 0.8f);
        ReactionEffects.scheduleDirect(sw, pos, result, 60, sp);
    }
}
