package net.kore.pronouns.bungeecord;

import net.kore.pronouns.api.InstanceHolder;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.plugin.Plugin;

import java.nio.file.Path;
import java.util.logging.Logger;

public class BungeecordPronouns extends Plugin {
    static Logger LOGGER;
    static Path DIR;

    @Override
    public void onEnable() {
        LOGGER = getLogger();
        DIR = getDataFolder().toPath();
        InstanceHolder.setPlatformProvider(new BungeecordPlatformProvider(BungeeAudiences.create(this)));
    }
}
