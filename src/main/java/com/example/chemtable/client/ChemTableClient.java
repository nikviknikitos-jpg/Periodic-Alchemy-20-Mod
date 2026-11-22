package com.example.chemtable.client;

import com.example.chemtable.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ChemTableClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.REACTION_GRENADE, ReactionGrenadeRenderer::new);
    }
}
