package net.kore.pronouns.paper;

import net.kore.pronouns.common.PronounUtils;
import net.kore.pronouns.common.AbstractPlatformProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PaperPlatformProvider extends AbstractPlatformProvider {
    @Override
    public String getDisplayName(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return uuid.toString();
        return PlainTextComponentSerializer.plainText().serialize(player.displayName());
    }

    public void sendMessage(UUID uuid, String minimessage) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) player.sendMessage(MiniMessage.miniMessage().deserialize(minimessage));
    }

    @Override
    public void printThrowable(Throwable throwable) {
        PaperPronouns.getPlugin(PaperPronouns.class).getLogger().severe(PronounUtils.throwableString(throwable));
    }

    @Override
    public void info(String string) {
        PaperPronouns.getPlugin(PaperPronouns.class).getLogger().info(string);
    }

    @Override
    public void warn(String string) {
        PaperPronouns.getPlugin(PaperPronouns.class).getLogger().warning(string);
    }

    @Override
    public void error(String string) {
        PaperPronouns.getPlugin(PaperPronouns.class).getLogger().severe(string);
    }

    @Override
    public Path getDataPath() {
        return PaperPronouns.getPlugin(PaperPronouns.class).getDataPath();
    }

    @Override
    public boolean isOp(UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        return offlinePlayer.isOp();
    }

    @Override
    public CompletableFuture<Boolean> hasPermission(UUID uuid, String permission) { // Bukkit has a pretty solid API, but we prefer LuckPerms here
        if (Bukkit.getPluginManager().isPluginEnabled("LuckPerms"))
            return super.hasPermission(uuid, permission);
        else {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if (offlinePlayer.isOnline() && offlinePlayer.getPlayer() != null) {
                return CompletableFuture.completedFuture(offlinePlayer.getPlayer().hasPermission(permission));
            } else return CompletableFuture.completedFuture(isOp(uuid));
        }
    }
}
