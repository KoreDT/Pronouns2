package net.kore.pronouns.common.modloader;

import net.kore.pronouns.common.AbstractPlatformProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;
import java.util.function.Predicate;

public abstract class ModPlatformProvider extends AbstractPlatformProvider {
    @Override
    public String getDisplayName(UUID uuid) {
        ServerPlayer serverPlayer = Pronouns.SERVER.getPlayerList().getPlayer(uuid);
        if (serverPlayer == null) return uuid.toString();
        Component display = serverPlayer.getDisplayName();
        if (display == null) display = serverPlayer.getName();

        return display.getString();
    }

    public abstract void sendMessage(CommandSourceStack commandSourceStack, String message);

    public Predicate<CommandSourceStack> hasPermission(String permission) {
        return commandSourceStack -> {
            if (commandSourceStack.isPlayer())
                return hasPermission(commandSourceStack.getPlayer().getUUID(), permission).join();
            else return true;
        };
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
        ServerPlayer serverPlayer = Pronouns.SERVER.getPlayerList().getPlayer(uuid);
        if (serverPlayer == null) return false;
        return serverPlayer.hasPermissions(4);
    }
}
