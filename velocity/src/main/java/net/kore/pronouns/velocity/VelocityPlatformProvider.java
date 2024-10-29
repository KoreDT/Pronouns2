package net.kore.pronouns.velocity;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kore.pronouns.common.AbstractPlatformProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class VelocityPlatformProvider extends AbstractPlatformProvider {
    @Override
    public String getDisplayName(UUID uuid) {
        Optional<Player> playerOptional = VelocityPronouns.INSTANCE.server.getPlayer(uuid);
        return playerOptional.map(Player::getUsername).orElseGet(uuid::toString);
    }

    @Override
    public void printThrowable(Throwable throwable) {
        VelocityPronouns.INSTANCE.logger.error("Pronouns has thrown an error!", throwable);
    }

    @Override
    public void info(String string) {
        VelocityPronouns.INSTANCE.logger.info(string);
    }

    @Override
    public void warn(String string) {
        VelocityPronouns.INSTANCE.logger.warn(string);
    }

    @Override
    public void error(String string) {
        VelocityPronouns.INSTANCE.logger.error(string);
    }

    @Override
    public Path getDataPath() {
        return VelocityPronouns.INSTANCE.dataDirectory;
    }

    @Override
    public boolean isOp(UUID uuid) {
        return false; // Velocity has no OP concept
    }

    @Override
    public CompletableFuture<Boolean> hasPermission(UUID uuid, String permission) {
        Optional<Player> playerOptional = VelocityPronouns.INSTANCE.server.getPlayer(uuid);
        return playerOptional.map(player -> CompletableFuture.completedFuture(player.hasPermission(permission)))
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
