package net.kore.pronouns.fabric;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.loader.api.FabricLoader;
import net.kore.pronouns.api.config.PronounConfig;
import net.kore.pronouns.common.CommonMethods;
import net.kore.pronouns.common.modloader.ModPlatformProvider;
import net.kore.pronouns.common.modloader.Pronouns;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class FabricPlatformProvider extends ModPlatformProvider {
    @Override
    public Path getDataPath() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public void sendMessage(CommandSourceStack commandSourceStack, String message) {
        commandSourceStack.sendMessage(MiniMessage.miniMessage().deserialize(message));
    }

    @Override
    public CompletableFuture<Boolean> hasPermission(UUID uuid, String permission) {
        return Permissions.check(uuid, permission);
    }
}
