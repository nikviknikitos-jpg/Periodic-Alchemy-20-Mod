package com.example.chemtable.reaction;

import com.example.chemtable.AbyssReactionUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class ReactionEffects {
    public static void scheduleDirect(ServerWorld sw, BlockPos pos, String result, int ticks, ServerPlayerEntity runner) {
        new Thread(() -> {
            try {
                for (int t = 0; t < ticks; t += 5) {
                    final int tt = t;
                    sw.getServer().execute(() -> {
                        Vec3d center = new Vec3d(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
                        sw.spawnParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                                center.x, center.y, center.z, 10, 0.5, 0.5, 0.5, 0.02);
                        sw.spawnParticles(ParticleTypes.LARGE_SMOKE,
                                center.x, center.y, center.z, 6, 0.5, 0.5, 0.5, 0.02);
                        sw.spawnParticles(ParticleTypes.ELECTRIC_SPARK,
                                center.x, center.y, center.z, 4, 0.4, 0.4, 0.4, 0.05);
                        if (tt % 15 == 0) {
                            sw.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.5f, 0.7f);
                        }
                    });
                    Thread.sleep(250);
                }
                sw.getServer().execute(() -> {
                    Vec3d center = new Vec3d(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
                    apply(sw, center, result);
                });
            } catch (InterruptedException ignored) {}
        }, "ChemReaction").start();
    }

    public static void apply(ServerWorld sw, Vec3d center, String result) {
        switch (result) {
            case "r_water" -> {
                sw.spawnParticles(ParticleTypes.SPLASH, center.x, center.y, center.z, 200, 3, 0.5, 3, 0.1);
                AbyssReactionUtil.damage(sw, center, 5.0, 0, 0);
                for (var e : sw.getEntitiesByClass(net.minecraft.entity.LivingEntity.class,
                        new Box(center.x-6, center.y-3, center.z-6, center.x+6, center.y+3, center.z+6), e -> e.isAlive())) {
                    e.extinguish();
                }
            }
            case "r_detonator", "r_alkali" -> {
                sw.spawnParticles(ParticleTypes.FLASH, center.x, center.y, center.z, 1, 0, 0, 0, 0);
                AbyssReactionUtil.explode(sw, center, 3.0f);
                AbyssReactionUtil.damage(sw, center, 6.0, 25.0f, 1.0);
            }
            case "r_super_alkali" -> {
                AbyssReactionUtil.explode(sw, center, 5.5f);
                sw.spawnParticles(ParticleTypes.LAVA, center.x, center.y, center.z, 50, 1.5, 1, 1.5, 0.2);
                AbyssReactionUtil.damage(sw, center, 7.0, 35.0f, 1.5);
            }
            case "r_napalm", "r_hellfire" -> {
                for (int i = 0; i < 40; i++) {
                    double a = i * 0.15;
                    sw.spawnParticles(ParticleTypes.LAVA,
                            center.x + Math.cos(a) * 2.5, center.y + i * 0.05, center.z + Math.sin(a) * 2.5,
                            2, 0, 0, 0, 0.01);
                }
                AbyssReactionUtil.explode(sw, center, 4.0f);
                AbyssReactionUtil.damage(sw, center, 7.0, 25.0f, 1.0);
            }
            case "r_chlorine", "r_mustard" -> {
                sw.spawnParticles(ParticleTypes.WITCH, center.x, center.y, center.z, 120, 2, 1, 2, 0.05);
                for (var e : sw.getEntitiesByClass(net.minecraft.entity.LivingEntity.class,
                        new Box(center.x-8, center.y-4, center.z-8, center.x+8, center.y+4, center.z+8),
                        e -> e.isAlive() && !((Object) e instanceof ServerPlayerEntity))) {
                    e.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 300, 2));
                    e.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 300, 1));
                }
            }
            case "r_cryo", "r_abs_zero" -> {
                sw.spawnParticles(ParticleTypes.SNOWFLAKE, center.x, center.y, center.z, 200, 4, 1, 4, 0.05);
                AbyssReactionUtil.damage(sw, center, 8.0, 15.0f, 0.0);
                for (var e : sw.getEntitiesByClass(net.minecraft.entity.LivingEntity.class,
                        new Box(center.x-8, center.y-4, center.z-8, center.x+8, center.y+4, center.z+8),
                        e -> e.isAlive() && !((Object) e instanceof ServerPlayerEntity))) {
                    e.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 4));
                }
            }
            case "r_acid" -> {
                AbyssReactionUtil.damage(sw, center, 6.0, 30.0f, 0.5);
                sw.spawnParticles(ParticleTypes.WITCH, center.x, center.y, center.z, 80, 2, 1, 2, 0.05);
                AbyssReactionUtil.crater(sw, center, 4, Blocks.OBSIDIAN);
            }
            case "r_polymer" -> {
                AbyssReactionUtil.crater(sw, center, 3, Blocks.SMOOTH_QUARTZ);
            }
            case "r_obsidian_gel" -> {
                AbyssReactionUtil.box(sw, center, Blocks.OBSIDIAN, 1);
            }
            case "r_emp" -> {
                sw.spawnParticles(ParticleTypes.ELECTRIC_SPARK, center.x, center.y, center.z, 200, 3, 1, 3, 0.05);
                for (var e : sw.getEntitiesByClass(net.minecraft.entity.LivingEntity.class,
                        new Box(center.x-12, center.y-6, center.z-12, center.x+12, center.y+6, center.z+12),
                        e -> e.isAlive() && !((Object) e instanceof ServerPlayerEntity))) {
                    var lightning = net.minecraft.entity.EntityType.LIGHTNING_BOLT.create(sw);
                    if (lightning != null) {
                        lightning.refreshPositionAfterTeleport(e.getX(), e.getY(), e.getZ());
                        sw.spawnEntity(lightning);
                    }
                }
            }
            case "r_nuclear" -> {
                AbyssReactionUtil.explode(sw, center, 7.0f);
                for (int i = 0; i < 6; i++) {
                    double a = Math.PI * 2 * i / 6.0;
                    var lightning = net.minecraft.entity.EntityType.LIGHTNING_BOLT.create(sw);
                    if (lightning != null) {
                        lightning.refreshPositionAfterTeleport(center.x + Math.cos(a) * 6, center.y, center.z + Math.sin(a) * 6);
                        sw.spawnEntity(lightning);
                    }
                }
                sw.spawnParticles(ParticleTypes.SOUL_FIRE_FLAME, center.x, center.y, center.z, 400, 5, 5, 5, 0.2);
                AbyssReactionUtil.damage(sw, center, 12.0, 50.0f, 2.0);
            }
            case "elixir_transmute" -> {
                AbyssReactionUtil.transmute(sw, center);
            }
            default -> {}
        }
        sw.playSound(null, BlockPos.ofFloored(center), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 2.0f, 0.8f);
    }
}
