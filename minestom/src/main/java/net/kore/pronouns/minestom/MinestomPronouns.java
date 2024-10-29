package net.kore.pronouns.minestom;

import net.kore.pronouns.api.InstanceHolder;
import net.minestom.server.instance.Instance;

import java.nio.file.Path;

public class MinestomPronouns {
    public static void init(Instance instance, Path configDir, LoggerAdapter loggerAdapter) {
        InstanceHolder.setPlatformProvider(new MinestomPlatformProvider(instance, configDir, loggerAdapter));
    }

    public interface LoggerAdapter {
        void info(String string);

        void warn(String string);

        void error(String string);
    }
}
