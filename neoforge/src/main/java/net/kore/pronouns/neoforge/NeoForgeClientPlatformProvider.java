package net.kore.pronouns.neoforge;

import net.kore.pronouns.common.modloader.ClientModPlatformProvider;
import net.kyori.adventure.platform.modcommon.MinecraftAudiences;
import net.kyori.adventure.platform.modcommon.MinecraftClientAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.fml.loading.FMLLoader;

import java.nio.file.Path;
import java.util.UUID;

public class NeoForgeClientPlatformProvider extends ClientModPlatformProvider {
    private final MinecraftAudiences audience = MinecraftClientAudiences.builder().build();

    public void sendMessage(UUID uuid, String minimessage) {
        if (uuid.equals(Minecraft.getInstance().getGameProfile().getId())) {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if (localPlayer != null) localPlayer.sendSystemMessage(audience.asNative(MiniMessage.miniMessage().deserialize(minimessage)));
        }
    }

    @Override
    public Path getDataPath() {
        return FMLLoader.getGamePath().resolve("config");
    }
}
