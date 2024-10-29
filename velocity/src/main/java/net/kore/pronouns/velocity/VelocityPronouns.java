package net.kore.pronouns.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kore.pronouns.api.InstanceHolder;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "${id}",
        name = "${name}",
        version = "${version}",
        description = "${description}",
        url = "${website}",
        authors = {"Kore Team"}
)
public class VelocityPronouns {
    protected static VelocityPronouns INSTANCE;

    public final Logger logger;
    public final Path dataDirectory;
    public final ProxyServer server;

    @Inject
    public VelocityPronouns(Logger logger, @DataDirectory Path dataDirectory, ProxyServer server) {
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        this.server = server;
        INSTANCE = this;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        logger.info("Starting Pronouns...");

        InstanceHolder.setPlatformProvider(new VelocityPlatformProvider());
    }
}
