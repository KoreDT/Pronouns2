package net.kore.pronouns.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kore.pronouns.api.InstanceHolder;
import org.slf4j.Logger;

@Plugin(
        id = "${id}",
        name = "${name}",
        version = "${version}",
        description = "${description}",
        url = "${website}",
        authors = {"Kore Team"}
)
public class VelocityPronouns {
    @Inject
    private Logger logger;

    @Inject
    private ProxyServer server;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        logger.info("Starting Pronouns...");

        InstanceHolder.setPlatformProvider(new VelocityPlatformProvider(server));
    }
}
