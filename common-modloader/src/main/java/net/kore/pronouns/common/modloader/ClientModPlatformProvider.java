package net.kore.pronouns.common.modloader;

import net.kore.pronouns.common.AbstractPlatformProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class ClientModPlatformProvider extends AbstractPlatformProvider {
    @Override
    public String getDisplayName(UUID uuid) {
        if (uuid.equals(Minecraft.getInstance().getGameProfile().getId())) return Minecraft.getInstance().getGameProfile().getName();
        if (Minecraft.getInstance().level == null) return uuid.toString();
        Player player = Minecraft.getInstance().level.getPlayerByUUID(uuid);
        if (player == null) return uuid.toString();
        Component display = player.getDisplayName();
        if (display == null) display = player.getName();

        return display.getString();
    }

    @Override
    public void printThrowable(Throwable throwable) {
        Pronouns.logger.error("Pronouns has thrown an error!", throwable);
    }

    @Override
    public void info(String string) {
        Pronouns.logger.info(string);
    }

    @Override
    public void warn(String string) {
        Pronouns.logger.warn(string);
    }

    @Override
    public void error(String string) {
        Pronouns.logger.error(string);
    }

    @Override
    public boolean isOp(UUID uuid) {
        return true; // We don't really ask questions on the client version, since it's your own, do what you want player
    }

    @Override
    public CompletableFuture<Boolean> hasPermission(UUID uuid, String permission) {
        return CompletableFuture.completedFuture(true); // See the above method comment
    }
}
