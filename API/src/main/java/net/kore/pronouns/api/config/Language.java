package net.kore.pronouns.api.config;

import net.kore.pronouns.api.language.LanguageSet;
import net.kore.pronouns.api.language.Languages;

public enum Language {
    ENGLISH(Languages.english()),
    GERMAN(Languages.german());

    private final LanguageSet languageSet;

    Language(LanguageSet languageSet) {
        this.languageSet = languageSet;
    }

    public LanguageSet getLanguageSet() {
        return languageSet;
    }
}
