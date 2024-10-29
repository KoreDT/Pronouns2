package net.kore.pronouns.api.config;

import net.kore.pronouns.api.InstanceHolder;
import net.kore.pronouns.api.language.LanguageSet;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.nio.file.Path;
import java.util.UUID;

@ConfigSerializable
public class PronounConfig {
    //
    // Static helpers
    //
    public static PronounConfig get() {
        return InstanceHolder.getPronounConfig();
    }

    public static LanguageSet currentLanguage() {
        return get().language().getLanguageSet();
    }

    @Comment(
            """
                    Only changes on a server restart
                    PRONOUNDB - Uses PronounDB for all Pronoun fetches, disables the set pronoun command
                    HOCON - Store user data in a local HOCON file
                    SQLITE - Store user data in a local DB file
                    """
    )
    private PronounStorageType provider = PronounStorageType.PRONOUNDB;

    private Language language = Language.ENGLISH;

    private PronounDBConfig pronoundb = new PronounDBConfig();
    private HOCONConfig hocon = new HOCONConfig();
    private SQLiteConfig sqlite = new SQLiteConfig();

    public PronounStorageType provider() {
        return provider;
    }

    public Language language() {
        return language;
    }

    public PronounDBConfig pronoundb() {
        return pronoundb;
    }
    public HOCONConfig hocon() {
        return hocon;
    }
    public SQLiteConfig sqlite() {
        return sqlite;
    }

    //
    // Helper methods
    //

    // Returns NULL if setting pronouns is allowed
    public String disallowSetMessageMM() {
        String link = pronoundb.homePageLink();

        return switch (provider) {
            case PRONOUNDB -> "<hover:show_text:'<green>Click to open</green>'><click:OPEN_URL:" + link + ">Set your pronouns at " + pronoundb().displayName() + "</click></hover>";
            case HOCON, SQLITE -> null;
        };
    }

    // Returns NULL if nothing went wrong
    public String saveAccountObject(UUID uuid, AccountObject accountObject) {
        switch (provider) {
            case PRONOUNDB -> throw new IllegalStateException("Cannot save an account while using PronounDB.");
            case HOCON -> {
                Path path = InstanceHolder.getPlatformProvider().getDataPath().resolve("pronouns-player-data.conf");

                try {
                    HoconConfigurationLoader loader = HoconConfigurationLoader.builder().path(path).build();

                    CommentedConfigurationNode root = loader.load();

                    root.node(uuid.toString()).set(accountObject);

                    loader.save(root);
                } catch (Exception e) {
                    InstanceHolder.getPlatformProvider().printThrowable(e);
                    return "<red>Failed to save data. See console for more detail.";
                }
            }
            case SQLITE -> {
                return "<red>SQLite is not finished.";
            }
        }
        return null;
    }
}
