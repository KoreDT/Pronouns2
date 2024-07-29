package net.kore.pronouns.bungeecord;

import net.kore.pronouns.api.InstanceHolder;
import net.md_5.bungee.api.plugin.Plugin;

public final class BungeecordPronouns extends Plugin {
    @Override
    public void onEnable() {
        InstanceHolder.setPlatformProvider(new BungeecordPlatformProvider(getProxy()));
    }
}
