package net.kore.pronouns.minestom;

import net.kore.pronouns.common.AbstractPlatformProvider;
import net.kore.pronouns.common.PronounUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;

import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MinestomPlatformProvider extends AbstractPlatformProvider {
    private final Instance instance;
    private final Path configDir;
    private final MinestomPronouns.LoggerAdapter loggerAdapter;

    public MinestomPlatformProvider(Instance instance, Path configDir, MinestomPronouns.LoggerAdapter loggerAdapter) {
        this.instance = instance;
        this.configDir = configDir;
        this.loggerAdapter = loggerAdapter;
    }

    @Override
    public String getDisplayName(UUID uuid) {
        Player player = instance.getPlayerByUuid(uuid);
        if (player == null) return uuid.toString();
        if (player.getDisplayName() == null) return player.getUsername();
        return PlainTextComponentSerializer.plainText().serialize(player.getDisplayName());
    }

    @Override
    public void printThrowable(Throwable throwable) {
        error(PronounUtils.throwableString(throwable));
    }

    @Override
    public void info(String string) {
        loggerAdapter.info(string);
    }

    @Override
    public void warn(String string) {
        loggerAdapter.warn(string);
    }

    @Override
    public void error(String string) {
        loggerAdapter.error(string);
    }

    @Override
    public Path getDataPath() {
        return configDir;
    }

    @Override
    public boolean isOp(UUID uuid) {
        return false; // Minestom has no concept of OP
    }

    @Override
    public CompletableFuture<Boolean> hasPermission(UUID uuid, String permission) { // We need to override here, not because the platform has a better implementation, but because LuckPerms doesn't exist on Minestom yet
        Player player = instance.getPlayerByUuid(uuid);
        if (player == null) return CompletableFuture.completedFuture(isOp(uuid));
        return CompletableFuture.completedFuture(player.hasPermission(permission));
    }
}
