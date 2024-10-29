package net.kore.pronouns.api.data;

import java.util.UUID;

public record AccountData(UUID uuid, PronounData pronoun1, PronounData pronoun2, PronounData pronoun3, DecorationData decorationData) {
    public AccountData clone() {
        return new AccountData(uuid, pronoun1, pronoun2, pronoun3, decorationData);
    }
}
