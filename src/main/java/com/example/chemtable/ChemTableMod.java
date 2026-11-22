package com.example.chemtable;

import com.example.chemtable.block.ModBlocks;
import com.example.chemtable.command.GiveChemCommand;
import com.example.chemtable.item.ModItems;
import com.example.chemtable.registry.ModEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChemTableMod implements ModInitializer {
    public static final String MODID = "chemtable";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        ModEntities.register();
        ModItems.register();
        ModBlocks.register();
        FabricDefaultAttributeRegistry.register(ModEntities.REACTION_GRENADE, net.minecraft.entity.EntityType.createLivingAttributes().build());
        CommandRegistrationCallback.EVENT.register((d, ra, e) -> GiveChemCommand.register(d));
        ServerTickEvents.END_WORLD_TICK.register(ReactionTickHandler::tick);
        LOGGER.info("ChemTableMod initialized");
    }
}
