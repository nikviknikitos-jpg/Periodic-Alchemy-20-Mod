package com.example.chemtable.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ElixirItem extends Item {
    private final String kind;
    public ElixirItem(Settings s, String kind) { super(s); this.kind = kind; }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user instanceof ServerPlayerEntity sp) {
            switch (kind) {
                case "elixir_titan" -> {
                    sp.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 45 * 20, 3, false, true));
                    sp.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 45 * 20, 1, false, true));
                }
                case "elixir_swift" -> {
                    sp.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 45 * 20, 2, false, true));
                    sp.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 45 * 20, 2, false, true));
                }
                case "elixir_vision" -> {
                    sp.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 60 * 20, 0, false, true));
                    sp.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 60 * 20, 0, false, true));
                }
                case "elixir_magma" -> {
                    sp.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 45 * 20, 0, false, true));
                    sp.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 45 * 20, 1, false, true));
                }
                default -> {}
            }
            sp.playSound(SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.PLAYERS, 1.0f, 1.2f);
            sp.getStackInHand(hand).decrement(1);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
