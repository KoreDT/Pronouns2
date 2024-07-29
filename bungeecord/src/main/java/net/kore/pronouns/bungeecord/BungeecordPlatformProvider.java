package net.kore.pronouns.bungeecord;

import net.kore.pronouns.api.PronounUtils;
import net.kore.pronouns.common.AbstractPlatformProvider;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class BungeecordPlatformProvider extends AbstractPlatformProvider {
    private final ProxyServer server;

    public BungeecordPlatformProvider(ProxyServer server) {
        this.server = server;
    }

    @Override
    public String getDisplayName(UUID uuid) {
        ProxiedPlayer player = server.getPlayer(uuid);
        if (player == null) return uuid.toString();
        return player.getDisplayName();
    }

    @Override
    public void printThrowable(Throwable throwable) {
        server.getLogger().severe(PronounUtils.throwableString(throwable));
    }
}
