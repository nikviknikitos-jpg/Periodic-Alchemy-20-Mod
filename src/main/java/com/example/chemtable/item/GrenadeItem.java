package com.example.chemtable.item;

import com.example.chemtable.entity.ReactionGrenadeEntity;
import com.example.chemtable.registry.ModEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GrenadeItem extends Item {
    public final String recipe;
    public GrenadeItem(Settings s, String recipe) { super(s); this.recipe = recipe; }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user instanceof ServerPlayerEntity sp && world instanceof ServerWorld sw) {
            ReactionGrenadeEntity e = ModEntities.REACTION_GRENADE.create(sw);
            if (e != null) {
                e.init(recipe);
                e.setOwner(sp);
                e.setItem(new ItemStack(this));
                e.refreshPositionAndAngles(sp.getX(), sp.getY() + 1.2, sp.getZ(), 0, 0);
                e.setVelocity(sp.getRotationVec(1.0f).multiply(1.2));
                sw.spawnEntity(e);
                sp.getStackInHand(hand).decrement(1);
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
