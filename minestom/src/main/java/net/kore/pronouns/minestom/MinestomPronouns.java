package net.kore.pronouns.minestom;

import net.kore.pronouns.api.InstanceHolder;
import net.minestom.server.instance.Instance;

public class MinestomPronouns {
    public static void init(Instance instance) {
        InstanceHolder.setPlatformProvider(new MinestomPlatformProvider(instance));
    }

    public static String getVersion() {
        return "${version}";
    }
}
