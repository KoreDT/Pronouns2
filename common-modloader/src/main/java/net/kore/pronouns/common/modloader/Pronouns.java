package net.kore.pronouns.common.modloader;

import net.kore.pronouns.api.InstanceHolder;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Pronouns {
    public static final String MOD_ID = "pronouns";
    public static MinecraftServer SERVER;
    public static final Logger logger = LoggerFactory.getLogger("Pronouns");

    public static void init() {
        InstanceHolder.setPlatformProvider(new ModPlatformProvider());
    }
}
