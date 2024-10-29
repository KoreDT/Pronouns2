package net.kore.pronouns.nukkit;

import cn.nukkit.plugin.PluginBase;
import net.kore.pronouns.api.InstanceHolder;

public class NukkitPronouns extends PluginBase {
    @Override
    public void onLoad() {
        InstanceHolder.setPlatformProvider(new NukkitPlatformProvider(this));
    }
}
