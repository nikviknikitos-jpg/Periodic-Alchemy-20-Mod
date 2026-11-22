package com.example.chemtable.registry;

import com.example.chemtable.entity.ReactionGrenadeEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static EntityType<ReactionGrenadeEntity> REACTION_GRENADE;

    public static void register() {
        REACTION_GRENADE = Registry.register(Registries.ENTITY_TYPE,
                new Identifier("chemtable", "reaction_grenade"),
                FabricEntityTypeBuilder.<ReactionGrenadeEntity>create(SpawnGroup.MISC, ReactionGrenadeEntity::new)
                        .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    }
}
