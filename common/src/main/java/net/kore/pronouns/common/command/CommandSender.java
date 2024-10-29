package net.kore.pronouns.common.command;

import javax.annotation.Nullable;
import java.util.UUID;

public interface CommandSender {
    void sendMessage(String message);

    @Nullable
    UUID getUUID();
}
