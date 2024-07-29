package net.kore.pronouns.velocity;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kore.pronouns.common.AbstractPlatformProvider;

import java.util.Optional;
import java.util.UUID;

public class VelocityPlatformProvider extends AbstractPlatformProvider {
    private final ProxyServer server;

    public VelocityPlatformProvider(ProxyServer server) {
        this.server = server;
    }

    @Override
    public String getDisplayName(UUID uuid) {
        Optional<Player> playerOptional = server.getPlayer(uuid);
        if (playerOptional.isEmpty()) return uuid.toString();
        return playerOptional.get().getUsername();
    }

    @Override
    public void printThrowable(Throwable throwable) {

    }
}
