package net.kore.pronouns.api.provider;

import net.kore.pronouns.api.config.PronounConfig;
import net.kore.pronouns.api.data.AccountData;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IPlatformProvider {
    CompletableFuture<AccountData> getPronounAccount(UUID uuid);

    CompletableFuture<List<AccountData>> getPronounAccounts(UUID... uuids);

    String getDisplayName(UUID uuid);

    void printThrowable(Throwable throwable);
    void info(String string);
    void warn(String string);
    void error(String string);

    PronounConfig getConfig() throws Exception;

    Path getDataPath();
}
