package net.kore.pronouns.api;

import net.kore.pronouns.api.provider.PlatformProvider;

public class InstanceHolder {
    private static PlatformProvider platformProvider;
    public static PlatformProvider getPlatformProvider() {
        if (platformProvider == null) throw new IllegalStateException("No PlatformProvider given.");
        return platformProvider;
    }

    public static void setPlatformProvider(PlatformProvider provider) {
        if (platformProvider != null) throw new IllegalStateException("PlatformProvider already given!");
        platformProvider = provider;
    }
}
