package net.kore.pronouns.meep;

import net.kore.meep.api.event.EventListener;
import net.kore.meep.api.event.EventManager;
import net.kore.meep.api.event.lifecycle.EnableEvent;
import net.kore.meep.api.meepling.Meepling;
import net.kore.pronouns.api.InstanceHolder;

public class MeeplingPronouns extends Meepling {
    @Override
    public void init() {
        EventManager.get().registerListener(this);
    }

    @EventListener(event = EnableEvent.class)
    public void onEnable() {
        InstanceHolder.setPlatformProvider(new MeeplingPlatformProvider());
    }
}
