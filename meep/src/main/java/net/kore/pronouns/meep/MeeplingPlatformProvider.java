package net.kore.pronouns.meep;

import net.kore.meep.api.Meep;
import net.kore.meep.api.entity.Player;
import net.kore.meep.api.meepling.MeeplingManager;
import net.kore.pronouns.api.PronounUtils;
import net.kore.pronouns.common.AbstractPlatformProvider;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.UUID;

public class MeeplingPlatformProvider extends AbstractPlatformProvider {
    @Override
    public String getDisplayName(UUID uuid) {
        Player player = Meep.get().getPlayer(uuid);
        if (player == null) return uuid.toString();
        return PlainTextComponentSerializer.plainText().serialize(player.displayName());
    }

    @Override
    public void printThrowable(Throwable throwable) {
        MeeplingManager.get().getMeepling(MeeplingPronouns.class).getLogger().error(PronounUtils.throwableString(throwable));
    }
}
