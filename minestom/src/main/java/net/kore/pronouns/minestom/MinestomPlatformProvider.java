package net.kore.pronouns.minestom;

import net.kore.pronouns.common.AbstractPlatformProvider;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;

import java.util.UUID;

public class MinestomPlatformProvider extends AbstractPlatformProvider {
    private final Instance instance;

    public MinestomPlatformProvider(Instance instance) {
        this.instance = instance;
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
        throwable.printStackTrace();
    }
}
