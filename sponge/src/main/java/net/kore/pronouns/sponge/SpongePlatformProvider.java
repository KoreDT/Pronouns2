package net.kore.pronouns.sponge;

import net.kore.pronouns.common.AbstractPlatformProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class SpongePlatformProvider extends AbstractPlatformProvider {
    private final Server server;

    public SpongePlatformProvider(Server server) {
        this.server = server;
    }

    @Override
    public String getDisplayName(UUID uuid) {
        Optional<ServerPlayer> playerOptional = server.player(uuid);
        return playerOptional.map(serverPlayer -> PlainTextComponentSerializer.plainText().serialize(
                serverPlayer.displayName().get()
        )).orElseGet(uuid::toString);
    }

    @Override
    public void printThrowable(Throwable throwable) {
        SpongePronouns.INSTANCE.logger.error("Pronouns has thrown an error!", throwable);
    }

    @Override
    public void info(String string) {
        SpongePronouns.INSTANCE.logger.info(string);
    }

    @Override
    public void warn(String string) {
        SpongePronouns.INSTANCE.logger.warn(string);
    }

    @Override
    public void error(String string) {
        SpongePronouns.INSTANCE.logger.error(string);
    }

    @Override
    public Path getDataPath() {
        return SpongePronouns.INSTANCE.dataDirectory;
    }

    @Override
    public boolean isOp(UUID uuid) {
        return false; // There is no concept of OP, but Sponge has a solid PermissionAPI
    }

    @Override
    public CompletableFuture<Boolean> hasPermission(UUID uuid, String permission) {
        Optional<ServerPlayer> playerOptional = server.player(uuid);
        return playerOptional.map(serverPlayer -> CompletableFuture.completedFuture(serverPlayer.hasPermission(permission)))
                .orElseGet(() -> {
                    try {
                        LuckPerms luckPerms = LuckPermsProvider.get();
                        UserManager userManager = luckPerms.getUserManager();
                        CompletableFuture<User> userFuture = userManager.loadUser(uuid);
                        return CompletableFuture.supplyAsync(() -> {
                            User user = userFuture.join();
                            for (Node node : user.getNodes()) {
                                if (node.getKey().equals(permission)) return true;
                            }
                            return false;
                        });
                    } catch (Exception e) {
                        return CompletableFuture.completedFuture(isOp(uuid));
                    }
                });
    }
}
