package net.kore.pronouns.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.kore.pronouns.api.InstanceHolder;
import net.kore.pronouns.common.modloader.ModPronounsCommand;
import net.kore.pronouns.common.modloader.Pronouns;
import net.fabricmc.api.ModInitializer;

public final class PronounsFabric implements ModInitializer, ClientModInitializer {
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            if (Pronouns.SERVER == null) Pronouns.SERVER = server;
        });

        InstanceHolder.setPlatformProvider(new FabricPlatformProvider());

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ModPronounsCommand.register(dispatcher);
        });
    }

    @Override
    public void onInitializeClient() {
        InstanceHolder.setPlatformProvider(new FabricClientPlatformProvider());
    }
}
