package net.kore.pronouns.common;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.kore.pronouns.api.data.AccountData;
import net.kore.pronouns.api.data.DecorationData;
import net.kore.pronouns.api.data.PronounData;
import net.kore.pronouns.api.provider.PlatformProvider;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public abstract class AbstractPlatformProvider implements PlatformProvider {
    private final Cache<UUID, AccountData> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES).build();

    @Override
    public CompletableFuture<AccountData> getPronounDBAccount(UUID uuid) {
        AccountData accountData = cache.getIfPresent(uuid);
        if (accountData == null) {
            String url = "https://pronoundb.org/api/v2/lookup?platform=minecraft&ids=" + uuid;
            return CompletableFuture.supplyAsync(() -> {
                try (InputStream is = new URI(url).toURL().openStream()) {
                    JsonObject jo = JsonParser.parseReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                            .getAsJsonObject();
                    jo = jo.get(uuid.toString()).getAsJsonObject();
                    DecorationData decorationData = DecorationData.NONE;
                    if (jo.has("decoration")) {
                        String decorationKey = jo.get("decoration").getAsString();
                        try {
                            decorationData = DecorationData.valueOf(decorationKey.toUpperCase(Locale.ROOT));
                        } catch (IllegalArgumentException ignored) {}
                    }

                    JsonObject sets = jo.getAsJsonObject("sets");
                    PronounData pronounData1 = PronounData.NONE;
                    PronounData pronounData2 = PronounData.NONE;
                    PronounData pronounData3 = PronounData.NONE;
                    if (sets.has("en")) {
                        JsonArray ja = sets.getAsJsonArray("en");
                        try {
                            pronounData1 = getPronounFromKey(ja.get(0).getAsString());
                            pronounData2 = getPronounFromKey(ja.get(1).getAsString());
                            pronounData3 = getPronounFromKey(ja.get(2).getAsString());
                        } catch (IndexOutOfBoundsException ignored) {}
                    }
                    AccountData accountDat = new AccountData(pronounData1, pronounData2, pronounData3, decorationData);
                    cache.put(uuid, accountDat);
                    return accountDat;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });
        } else {
            return CompletableFuture.completedFuture(accountData);
        }
    }

    @Override
    public CompletableFuture<List<AccountData>> getPronounDBAccounts(UUID... uuids) {
        List<AccountData> accounts = new ArrayList<>();
        List<UUID> toFetch = new ArrayList<>();
        for (UUID uuid : uuids) {
            AccountData accountData = cache.getIfPresent(uuid);
            if (accountData != null) accounts.add(accountData);
            else toFetch.add(uuid);
        }
        if (toFetch.isEmpty()) {
            return CompletableFuture.completedFuture(accounts);
        } else {
            String url = "https://pronoundb.org/api/v2/lookup?platform=minecraft&ids=" + String.join(",", toFetch.stream().map(UUID::toString).toList());
            return CompletableFuture.supplyAsync(() -> {
                try (InputStream is = new URI(url).toURL().openStream()) {
                    JsonObject jo = JsonParser.parseReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                            .getAsJsonObject();
                    for (UUID uuid : toFetch) {
                        jo = jo.get(uuid.toString()).getAsJsonObject();
                        DecorationData decorationData = DecorationData.NONE;
                        if (jo.has("decoration")) {
                            String decorationKey = jo.get("decoration").getAsString();
                            try {
                                decorationData = DecorationData.valueOf(decorationKey.toUpperCase(Locale.ROOT));
                            } catch (IllegalArgumentException ignored) {
                            }
                        }

                        JsonObject sets = jo.getAsJsonObject("sets");
                        PronounData pronounData1 = null;
                        PronounData pronounData2 = null;
                        PronounData pronounData3 = null;
                        if (sets.has("en")) {
                            JsonArray ja = sets.getAsJsonArray("en");
                            try {
                                pronounData1 = getPronounFromKey(ja.get(0).getAsString());
                                pronounData2 = getPronounFromKey(ja.get(1).getAsString());
                                pronounData3 = getPronounFromKey(ja.get(2).getAsString());
                            } catch (IndexOutOfBoundsException ignored) {
                            }
                        }
                        AccountData accountDat = new AccountData(pronounData1, pronounData2, pronounData3, decorationData);
                        cache.put(uuid, accountDat);
                        accounts.add(accountDat);
                    }
                    return accounts;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });
        }
    }
    
    private PronounData getPronounFromKey(String key) {
        return switch (key) {
            case "he" -> PronounData.HE;
            case "it" -> PronounData.IT;
            case "she" -> PronounData.SHE;
            case "they" -> PronounData.THEY;
            case "any" -> PronounData.ANY;
            case "ask" -> PronounData.ASK;
            case "avoid" -> PronounData.AVOID;
            case "other" -> PronounData.OTHER;
            default -> throw new IllegalStateException("Unexpected value: " + key);
        };
    }
}
