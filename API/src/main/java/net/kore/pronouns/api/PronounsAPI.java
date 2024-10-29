package net.kore.pronouns.api;

import net.kore.pronouns.api.data.AccountData;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PronounsAPI {
    public static CompletableFuture<AccountData> getPronounDBAccount(UUID uuid) {
        return InstanceHolder.getPlatformProvider().getPronounAccount(uuid);
    }

    public static CompletableFuture<List<AccountData>> getPronounDBAccounts(UUID... uuids) {
        return InstanceHolder.getPlatformProvider().getPronounAccounts(uuids);
    }
}
