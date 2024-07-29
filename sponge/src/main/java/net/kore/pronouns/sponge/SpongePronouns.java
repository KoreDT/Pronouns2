package net.kore.pronouns.sponge;

import com.google.inject.Inject;
import net.kore.pronouns.api.InstanceHolder;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.api.event.lifecycle.StartingEngineEvent;
import org.spongepowered.plugin.builtin.jvm.Plugin;

/**
 * The main class of your Sponge plugin.
 *
 * <p>All methods are optional -- some common event registrations are included as a jumping-off point.</p>
 */
@Plugin("pronouns")
public class SpongePronouns {
    private final Logger logger;

    @Inject
    SpongePronouns(Logger logger) {
        this.logger = logger;
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
