package net.kore.pronouns.fabric;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.kore.pronouns.common.modloader.Pronouns;
import net.fabricmc.api.ModInitializer;

public final class PronounsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Pronouns.init();

        ServerTickEvents.START_SERVER_TICK.register(server -> {
            if (Pronouns.SERVER == null) Pronouns.SERVER = server;
        });
    }
}
