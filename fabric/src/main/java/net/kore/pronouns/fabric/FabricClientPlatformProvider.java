package net.kore.pronouns.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.kore.pronouns.common.modloader.ClientModPlatformProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

import java.nio.file.Path;
import java.util.UUID;

public class FabricClientPlatformProvider extends ClientModPlatformProvider {
    public void sendMessage(UUID uuid, String minimessage) {
        if (uuid.equals(Minecraft.getInstance().getGameProfile().getId())) {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if (localPlayer != null) localPlayer.sendMessage(MiniMessage.miniMessage().deserialize(minimessage));
        }
    }

    @Override
    public Path getDataPath() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
