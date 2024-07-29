package net.kore.pronouns.common.modloader;

import net.kore.pronouns.common.AbstractPlatformProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class ModPlatformProvider extends AbstractPlatformProvider {
    @Override
    public String getDisplayName(UUID uuid) {
        ServerPlayer serverPlayer = Pronouns.SERVER.getPlayerList().getPlayer(uuid);
        if (serverPlayer == null) return uuid.toString();
        Component display = serverPlayer.getDisplayName();
        if (display == null) display = serverPlayer.getName();

        return display.getString();
    }

    @Override
    public void printThrowable(Throwable throwable) {
        Pronouns.logger.error("An error has been provided by Pronouns!", throwable);
    }
}
