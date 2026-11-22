package com.example.chemtable.block;

import com.example.chemtable.reaction.ReactorData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChemReactorBlock extends Block {
    public ChemReactorBlock(Settings s) { super(s); }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                              Hand hand, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;
        if (!(player instanceof ServerPlayerEntity sp)) return ActionResult.PASS;
        ItemStack stack = player.getStackInHand(hand);
        var data = ReactorData.get((ServerWorld) world, pos);

        if (stack.getItem() == com.example.chemtable.item.ModItems.STABILIZER) {
            data.stabilizer = true;
            sp.sendMessage(Text.literal("§3[Стабилизатор добавлен — реакция запечатается в колбу]"), false);
            return ActionResult.SUCCESS;
        }

        String elem = com.example.chemtable.reaction.ReactionRegistry.elementOf(stack);
        if (elem != null) {
            if (data.elements.size() >= 4) {
                sp.sendMessage(Text.literal("§cВ реакторе уже 4 элемента!"), false);
                return ActionResult.SUCCESS;
            }
            data.elements.add(elem);
            sp.sendMessage(Text.literal("§bВ камере [" + data.elements.size() + "/4]: "
                    + String.join(" + ", data.elements)), false);
            return ActionResult.SUCCESS;
        }

        if (stack.isEmpty() && player.isSneaking()) {
            com.example.chemtable.reaction.ReactionRunner.run(sp, (ServerWorld) world, pos, data);
            ReactorData.clear((ServerWorld) world, pos);
            return ActionResult.SUCCESS;
        }

        return ActionResult.SUCCESS;
    }
}
