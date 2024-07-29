package net.kore.pronouns.sponge;

import net.kore.pronouns.common.AbstractPlatformProvider;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

import java.util.Optional;
import java.util.UUID;

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
        Sponge.pluginManager().plugin("pronouns").get().logger().error("Pronouns has thrown an error!", throwable);
    }
}
