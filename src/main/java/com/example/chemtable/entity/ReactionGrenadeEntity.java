package com.example.chemtable.entity;

import com.example.chemtable.reaction.ReactionEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ReactionGrenadeEntity extends ThrownItemEntity {
    private String recipe = "r_water";

    public ReactionGrenadeEntity(EntityType<? extends ReactionGrenadeEntity> type, World world) {
        super(type, world);
    }

    public void init(String recipe) { this.recipe = recipe; }

    @Override
    protected Item getDefaultItem() { return Items.GLASS_BOTTLE; }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!getWorld().isClient && getWorld() instanceof ServerWorld sw) {
            ReactionEffects.apply(sw, getPos(), recipe);
            sw.playSound(null, getBlockPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1.5f, 1.1f);
            this.discard();
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("Recipe", recipe);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        recipe = nbt.getString("Recipe");
    }
}
