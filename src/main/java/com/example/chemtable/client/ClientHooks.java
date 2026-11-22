package com.example.chemtable.client;

import net.minecraft.client.MinecraftClient;

public class ClientHooks {
    public static void openBook() {
        MinecraftClient.getInstance().setScreen(new ChemBookScreen());
    }
}
