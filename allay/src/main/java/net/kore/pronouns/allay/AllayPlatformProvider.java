package net.kore.pronouns.allay;

import net.kore.pronouns.common.AbstractPlatformProvider;
import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.server.Server;

import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

public class AllayPlatformProvider extends AbstractPlatformProvider {
    private final AllayPronouns plugin;

    public AllayPlatformProvider(AllayPronouns plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isOp(UUID uuid) {
        Map<UUID, EntityPlayer> players = Server.getInstance().getOnlinePlayers();
        return players.containsKey(uuid) && players.get(uuid).isOp();
    }

    @Override
    public String getDisplayName(UUID uuid) {
        Map<UUID, EntityPlayer> players = Server.getInstance().getOnlinePlayers();
        return players.containsKey(uuid) ? players.get(uuid).getDisplayName() : uuid.toString();
    }

    @Override
    public void printThrowable(Throwable throwable) {
        plugin.getPluginLogger().error("Pronouns has thrown an error!", throwable);
    }

    @Override
    public void info(String string) {
        plugin.getPluginLogger().info(string);
    }

    @Override
    public void warn(String string) {
        plugin.getPluginLogger().warn(string);
    }

    @Override
    public void error(String string) {
        plugin.getPluginLogger().error(string);
    }

    @Override
    public Path getDataPath() {
        return plugin.getPluginContainer().dataFolder();
    }
}
