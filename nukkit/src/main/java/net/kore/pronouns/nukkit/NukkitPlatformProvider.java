package net.kore.pronouns.nukkit;

import cn.nukkit.Server;
import cn.nukkit.player.Player;
import net.kore.pronouns.common.AbstractPlatformProvider;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

public class NukkitPlatformProvider extends AbstractPlatformProvider {
    private final NukkitPronouns plugin;

    public NukkitPlatformProvider(NukkitPronouns plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isOp(UUID uuid) {
        Optional<Player> optionalPlayer = plugin.getServer().getPlayer(uuid);
        return optionalPlayer.map(Player::isOp).orElse(false);
    }

    @Override
    public String getDisplayName(UUID uuid) {
        Optional<Player> optionalPlayer = plugin.getServer().getPlayer(uuid);
        return optionalPlayer.map(Player::getDisplayName).orElse(uuid.toString());
    }

    @Override
    public void printThrowable(Throwable throwable) {
        plugin.getLogger().error("Pronouns has thrown an error!", throwable);
    }

    @Override
    public void info(String string) {
        plugin.getLogger().info(string);
    }

    @Override
    public void warn(String string) {
        plugin.getLogger().warn(string);
    }

    @Override
    public void error(String string) {
        plugin.getLogger().error(string);
    }

    @Override
    public Path getDataPath() {
        return plugin.getDataFolder().toPath();
    }
}
