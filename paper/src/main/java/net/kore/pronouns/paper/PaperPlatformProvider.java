package net.kore.pronouns.paper;

import net.kore.pronouns.api.PronounUtils;
import net.kore.pronouns.common.AbstractPlatformProvider;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PaperPlatformProvider extends AbstractPlatformProvider {
    @Override
    public String getDisplayName(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return uuid.toString();
        return PlainTextComponentSerializer.plainText().serialize(player.displayName());
    }

    @Override
    public void printThrowable(Throwable throwable) {
        PaperPronouns.getPlugin(PaperPronouns.class).getLogger().severe(PronounUtils.throwableString(throwable));
    }
}
