package net.kore.pronouns.bungeecord;

import net.kore.pronouns.common.PronounUtils;
import net.kore.pronouns.common.AbstractPlatformProvider;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class BungeecordPlatformProvider extends AbstractPlatformProvider {
    private final BungeeAudiences adventure;
    private final MiniMessage miniMessage;

    public BungeecordPlatformProvider(BungeeAudiences adventure) {
        this.adventure = adventure;
        miniMessage = MiniMessage.miniMessage();
    }

    @Override
    public String getDisplayName(UUID uuid) {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
        if (player == null) return uuid.toString();
        return player.getDisplayName();
    }

    public void sendMessage(UUID uuid, String minimessage) {
        adventure.player(uuid).sendMessage(miniMessage.deserialize(minimessage));
    }

    @Override
    public void printThrowable(Throwable throwable) {
        BungeecordPronouns.LOGGER.severe(PronounUtils.throwableString(throwable));
    }

    @Override
    public void info(String string) {
        BungeecordPronouns.LOGGER.info(string);
    }

    @Override
    public void warn(String string) {
        BungeecordPronouns.LOGGER.warning(string);
    }

    @Override
    public void error(String string) {
        BungeecordPronouns.LOGGER.severe(string);
    }

    @Override
    public Path getDataPath() {
        return BungeecordPronouns.DIR;
    }

    @Override
    public CompletableFuture<Boolean> hasPermission(UUID uuid, String permission) {
        Plugin plugin = ProxyServer.getInstance().getPluginManager().getPlugin("LuckPerms");
        if (plugin != null)
            return super.hasPermission(uuid, permission);
        else {
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
            if (player == null) return CompletableFuture.completedFuture(isOp(uuid));
            else return CompletableFuture.completedFuture(player.hasPermission(permission));
        }
    }

    @Override
    public boolean isOp(UUID uuid) {
        return false; // We don't know on a proxy, but Bungeecord has a permission system which shouldn't fail, the player just needs to exist
    }
}
