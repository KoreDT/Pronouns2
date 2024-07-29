package net.kore.pronouns.paper;

import net.kore.pronouns.api.InstanceHolder;
import org.bukkit.plugin.java.JavaPlugin;

public class PaperPronouns extends JavaPlugin {
    @Override
    public void onLoad() {
        InstanceHolder.setPlatformProvider(new PaperPlatformProvider());
    }
}