package net.kore.pronouns.meep;

import net.kore.meep.api.Meep;
import net.kore.meep.api.entity.Player;
import net.kore.meep.api.logger.Logger;
import net.kore.pronouns.common.PronounUtils;
import net.kore.pronouns.common.AbstractPlatformProvider;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MeepPlatformProvider extends AbstractPlatformProvider {
    @Override
    public String getDisplayName(UUID uuid) {
        Player player = Meep.get().getPlayer(uuid);
        if (player == null) return uuid.toString();
        return PlainTextComponentSerializer.plainText().serialize(player.displayName());
    }

    @Override
    public void printThrowable(Throwable throwable) {
        getLogger().error(PronounUtils.throwableString(throwable));
    }

    @Override
    public void info(String string) {
        getLogger().info(string);
    }

    @Override
    public void warn(String string) {
        getLogger().warn(string);
    }

    @Override
    public void error(String string) {
        getLogger().error(string);
    }

    private Logger getLogger() {
        return MeepPronouns.INSTANCE.getLogger();
    }

    @Override
    public Path getDataPath() {
        return MeepPronouns.INSTANCE.getConfigDir().toPath();
    }

    @Override
    public boolean isOp(UUID uuid) {
        // Cannot implement currently, no OfflinePlayer nor OP checks in Meep
        return false; // Is this check needed here? We already know we don't have access to the player
    }

    @Override
    public CompletableFuture<Boolean> hasPermission(UUID uuid, String permission) {
        Player player = Meep.get().getPlayer(uuid);
        if (player == null) return CompletableFuture.completedFuture(isOp(uuid));
        return CompletableFuture.completedFuture(player.hasPermission(permission));
    }
}
