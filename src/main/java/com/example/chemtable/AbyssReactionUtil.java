package com.example.chemtable;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AbyssReactionUtil {
    public static void damage(ServerWorld sw, Vec3d center, double radius, float dmg, double kb) {
        Box box = new Box(center.x - radius, center.y - radius, center.z - radius,
                center.x + radius, center.y + radius, center.z + radius);
        for (LivingEntity e : sw.getEntitiesByClass(LivingEntity.class, box,
                e -> e.isAlive() && !((Object) e instanceof net.minecraft.entity.player.PlayerEntity))) {
            if (dmg > 0) e.damage(sw.getDamageSources().magic(), dmg);
            if (kb > 0) {
                Vec3d d = e.getPos().subtract(center);
                if (d.lengthSquared() < 1e-4) d = new Vec3d(0, 1, 0);
                d = d.normalize().multiply(kb);
                e.setVelocity(d.x, d.y + 0.5, d.z);
                e.velocityModified = true;
            }
        }
    }

    public static void explode(ServerWorld sw, Vec3d center, float power) {
        sw.createExplosion(null, center.x, center.y, center.z, power, World.ExplosionSourceType.TNT);
    }

    public static void crater(ServerWorld sw, Vec3d center, int r, Block block) {
        BlockPos c = BlockPos.ofFloored(center);
        for (int dx = -r; dx <= r; dx++) {
            for (int dy = -r; dy <= r; dy++) {
                for (int dz = -r; dz <= r; dz++) {
                    double d = Math.sqrt(dx*dx + dy*dy + dz*dz);
                    if (d <= r) {
                        sw.setBlockState(c.add(dx, dy, dz), block.getDefaultState());
                    }
                }
            }
        }
    }

    public static void box(ServerWorld sw, Vec3d center, Block block, int r) {
        BlockPos c = BlockPos.ofFloored(center);
        for (int dx = -r; dx <= r; dx++) {
            for (int dy = -r; dy <= r; dy++) {
                for (int dz = -r; dz <= r; dz++) {
                    sw.setBlockState(c.add(dx, dy, dz), block.getDefaultState());
                }
            }
        }
    }

    public static void transmute(ServerWorld sw, Vec3d center) {
        BlockPos c = BlockPos.ofFloored(center);
        for (int dx = -4; dx <= 4; dx++) {
            for (int dy = -3; dy <= 1; dy++) {
                for (int dz = -4; dz <= 4; dz++) {
                    BlockPos p = c.add(dx, dy, dz);
                    var state = sw.getBlockState(p);
                    if (state.isOf(Blocks.STONE) || state.isOf(Blocks.DEEPSLATE)) {
                        int r = sw.random.nextInt(3);
                        if (r == 0) sw.setBlockState(p, Blocks.DIAMOND_ORE.getDefaultState());
                        else if (r == 1) sw.setBlockState(p, Blocks.EMERALD_ORE.getDefaultState());
                        else sw.setBlockState(p, Blocks.GOLD_ORE.getDefaultState());
                    }
                }
            }
        }
        sw.spawnParticles(net.minecraft.particle.ParticleTypes.END_ROD,
                center.x, center.y, center.z, 100, 3, 2, 3, 0.1);
    }
}
