package net.kore.pronouns.allay;

import net.kore.pronouns.api.InstanceHolder;
import org.allaymc.api.plugin.Plugin;

public class AllayPronouns extends Plugin {
    @Override
    public void onLoad() {
        InstanceHolder.setPlatformProvider(new AllayPlatformProvider(this));
    }
}
