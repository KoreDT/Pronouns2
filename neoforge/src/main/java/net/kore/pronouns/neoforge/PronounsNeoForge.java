package net.kore.pronouns.neoforge;

import net.kore.pronouns.common.modloader.Pronouns;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Pronouns.MOD_ID)
public final class PronounsNeoForge {
    public static MinecraftServer server;

    public PronounsNeoForge(IEventBus modBus) {
        Pronouns.init();
    }
}
