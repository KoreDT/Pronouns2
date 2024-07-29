package net.kore.pronouns.api.provider;

import net.kore.pronouns.api.data.AccountData;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PlatformProvider {
    CompletableFuture<AccountData> getPronounDBAccount(UUID uuid);
    CompletableFuture<List<AccountData>> getPronounDBAccounts(UUID... uuids);
    String getDisplayName(UUID uuid);

    void printThrowable(Throwable throwable);
}
