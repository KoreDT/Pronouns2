package net.kore.pronouns.neoforge;

import net.kore.pronouns.common.modloader.ModPlatformProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.fml.loading.FMLLoader;

import java.nio.file.Path;

public class NeoForgePlatformProvider extends ModPlatformProvider {
    private final PronounsNeoForge pronounsNeoForge;

    public NeoForgePlatformProvider(PronounsNeoForge pronounsNeoForge) {
        this.pronounsNeoForge = pronounsNeoForge;
    }

    @Override
    public Path getDataPath() {
        return FMLLoader.getGamePath().resolve("config");
    }

    @Override
    public void sendMessage(CommandSourceStack commandSourceStack, String message) {
        pronounsNeoForge.serverAdventure().audience(commandSourceStack).sendMessage(MiniMessage.miniMessage().deserialize(message));
    }
}
