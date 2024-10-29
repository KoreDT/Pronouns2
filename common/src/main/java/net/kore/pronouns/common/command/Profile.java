package net.kore.pronouns.common.command;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

public record Profile(@NonNull UUID uuid, @Nullable String name) {
    public String getName() {
        return name == null ? uuid.toString() : name;
    }
}
