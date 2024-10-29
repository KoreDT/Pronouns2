package net.kore.pronouns.sponge;

import com.google.inject.Inject;
import net.kore.pronouns.api.InstanceHolder;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Server;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.api.event.lifecycle.StartingEngineEvent;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.nio.file.Path;

@Plugin("pronouns")
public class SpongePronouns {
    protected static SpongePronouns INSTANCE;

    public final Logger logger;
    public final Path dataDirectory;

    @Inject
    SpongePronouns(Logger logger, @ConfigDir(sharedRoot = true) Path dataDirectory) {
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        INSTANCE = this;
    }

    @Listener
    public void onConstructPlugin(final ConstructPluginEvent event) {
        logger.info("Constructing Pronouns...");
    }

    @Listener
    public void onServerStarting(final StartingEngineEvent<Server> event) {
        logger.info("Starting Pronouns...");
        InstanceHolder.setPlatformProvider(new SpongePlatformProvider(event.engine()));
    }
}
