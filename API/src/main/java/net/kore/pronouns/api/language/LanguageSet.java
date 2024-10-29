package net.kore.pronouns.api.language;

import net.kore.pronouns.api.data.AccountData;
import net.kore.pronouns.api.data.PronounData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface LanguageSet {
    PronounSet none();
    PronounSet he();
    PronounSet it();
    PronounSet she();
    PronounSet they();
    PronounSet any();
    PronounSet ask();
    PronounSet avoid(UUID uuid);
    PronounSet other();

    static PronounSet getFromPronoun(LanguageSet language, PronounData pronounData, UUID uuid) {
        return switch (pronounData) {
            case NONE -> language.none();
            case HE -> language.he();
            case IT -> language.it();
            case SHE -> language.she();
            case THEY -> language.they();
            case ANY -> language.any();
            case ASK -> language.ask();
            case AVOID -> language.avoid(uuid);
            case OTHER -> language.other();
        };
    }

    static String getDisplayedPronouns(LanguageSet language, AccountData accountData) {
        List<PronounData> orderedData = new ArrayList<>();

        if (!accountData.pronoun1().equals(PronounData.NONE)) orderedData.add(accountData.pronoun1());
        if (!accountData.pronoun2().equals(PronounData.NONE)) orderedData.add(accountData.pronoun2());
        if (!accountData.pronoun3().equals(PronounData.NONE)) orderedData.add(accountData.pronoun3());

        if (orderedData.isEmpty()) return language.none().usualDisplay();
        else if (orderedData.size() == 1) return getFromPronoun(language, orderedData.getFirst(), accountData.uuid()).usualDisplay();
        else if (orderedData.size() == 2) {
            String first = getFromPronoun(language, orderedData.getFirst(), accountData.uuid()).usualDisplay();
            String second = getFromPronoun(language, orderedData.get(1), accountData.uuid()).usualDisplay();

            String[] firsts = first.split("/");
            String[] seconds = second.split("/");
            return firsts[0] + "/" + seconds[0];
        }
        else {
            String first = getFromPronoun(language, orderedData.getFirst(), accountData.uuid()).usualDisplay();
            String second = getFromPronoun(language, orderedData.get(1), accountData.uuid()).usualDisplay();
            String third = getFromPronoun(language, orderedData.get(2), accountData.uuid()).usualDisplay();

            String[] firsts = first.split("/");
            String[] seconds = second.split("/");
            String[] thirds = third.split("/");
            return firsts[0] + "/" + seconds[0] + "/" + thirds[0];
        }
    }
}
