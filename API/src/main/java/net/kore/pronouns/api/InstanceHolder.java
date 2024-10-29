package net.kore.pronouns.api;

import net.kore.pronouns.api.config.PronounConfig;
import net.kore.pronouns.api.provider.IPlatformProvider;

public class InstanceHolder {
    private static IPlatformProvider platformProvider;
    public static IPlatformProvider getPlatformProvider() {
        if (platformProvider == null) throw new IllegalStateException("No PlatformProvider given.");
        return platformProvider;
    }

    public static void setPlatformProvider(IPlatformProvider provider) {
        if (platformProvider != null) throw new IllegalStateException("PlatformProvider already given!");
        platformProvider = provider;
    }

    private static PronounConfig pronounConfig;
    public static PronounConfig getPronounConfig() {
        if (pronounConfig == null) {
            try {
                pronounConfig = platformProvider.getConfig();
            } catch (Exception e) {
                pronounConfig = new PronounConfig();
                platformProvider.error("Unable to load config file, loading in a default. Error provided below.");
                platformProvider.printThrowable(e);
            }
        }
        return pronounConfig;
    }

    public static void setPronounConfig(PronounConfig pronounConfig) {
        InstanceHolder.pronounConfig = pronounConfig;
    }
}
