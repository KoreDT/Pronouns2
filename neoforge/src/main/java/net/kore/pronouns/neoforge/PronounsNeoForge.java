package net.kore.pronouns.neoforge;

import net.kore.pronouns.api.InstanceHolder;
import net.kore.pronouns.api.PronounsMeta;
import net.kore.pronouns.common.modloader.ModPronounsCommand;
import net.kore.pronouns.common.modloader.Pronouns;
import net.kyori.adventure.platform.modcommon.MinecraftServerAudiences;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

@Mod(PronounsMeta.ID)
public final class PronounsNeoForge {
    private volatile MinecraftServerAudiences serverAdventure;

    public MinecraftServerAudiences serverAdventure() {
        if (this.serverAdventure == null) {
            throw new IllegalStateException("Tried to access Adventure without a running server!");
        }
        return this.serverAdventure;
    }

    public PronounsNeoForge(IEventBus modBus) {
        if (FMLLoader.getDist().isClient()) {
            InstanceHolder.setPlatformProvider(new NeoForgeClientPlatformProvider());
        } else {
            InstanceHolder.setPlatformProvider(new NeoForgePlatformProvider(this));
        }

        NeoForge.EVENT_BUS.addListener(this::registerCommands);
        NeoForge.EVENT_BUS.addListener((ServerStartingEvent e) -> this.serverAdventure = MinecraftServerAudiences.of(e.getServer()));
        NeoForge.EVENT_BUS.addListener((ServerStoppedEvent e) -> {
            this.serverAdventure.close(); // Not needed, but makes me happy
            this.serverAdventure = null;
        });
    }

    private void registerCommands(RegisterCommandsEvent event) {
        ModPronounsCommand.register(event.getDispatcher());
    }

    private void serverStart(ServerStartingEvent event) {
        Pronouns.SERVER = event.getServer();
    }
}
