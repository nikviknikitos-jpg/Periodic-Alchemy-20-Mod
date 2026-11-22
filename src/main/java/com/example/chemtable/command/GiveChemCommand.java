package com.example.chemtable.command;

import com.example.chemtable.block.ModBlocks;
import com.example.chemtable.item.ModItems;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class GiveChemCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("givechem").executes(ctx -> {
            ServerPlayerEntity p = ctx.getSource().getPlayer();
            if (p != null) {
                p.getInventory().insertStack(new ItemStack(ModBlocks.CHEM_REACTOR));
                p.getInventory().insertStack(new ItemStack(ModItems.CHEM_BOOK));
                p.getInventory().insertStack(new ItemStack(ModItems.STABILIZER, 64));
                for (var it : ModItems.ELEMENTS.values()) {
                    p.getInventory().insertStack(new ItemStack(it, 32));
                }
                for (var it : ModItems.GRENADES.values()) {
                    p.getInventory().insertStack(new ItemStack(it, 8));
                }
                for (var it : ModItems.ELIXIRS.values()) {
                    p.getInventory().insertStack(new ItemStack(it, 8));
                }
                ctx.getSource().sendFeedback(() -> Text.literal("Chem kit given"), false);
                return 1;
            }
            return 0;
        }));
    }
}
