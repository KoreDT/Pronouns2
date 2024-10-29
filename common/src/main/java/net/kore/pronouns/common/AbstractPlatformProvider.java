package net.kore.pronouns.common;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.kore.pronouns.api.InstanceHolder;
import net.kore.pronouns.api.config.AccountObject;
import net.kore.pronouns.api.config.PronounConfig;
import net.kore.pronouns.api.data.AccountData;
import net.kore.pronouns.api.data.DecorationData;
import net.kore.pronouns.api.data.PronounData;
import net.kore.pronouns.api.provider.IPlatformProvider;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public abstract class AbstractPlatformProvider implements IPlatformProvider {
    private AccountData NULL_ACCOUNT = new AccountData(new UUID(0L, 0L), PronounData.NONE, PronounData.NONE, PronounData.NONE, DecorationData.NONE);

    private final Cache<UUID, AccountData> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES).build();

    public Cache<UUID, AccountData> getCache() {
        return cache;
    }

    @Override
    public CompletableFuture<AccountData> getPronounAccount(UUID uuid) {
        return switch (PronounConfig.get().provider()) {
            case PRONOUNDB -> getPronounDBAccount(uuid);
            case HOCON -> getPronounHOCONAccount(uuid);
            case SQLITE -> null;
        };
    }

    @Override
    public CompletableFuture<List<AccountData>> getPronounAccounts(UUID... uuids) {
        return switch (PronounConfig.get().provider()) {
            case PRONOUNDB -> getPronounDBAccounts(uuids);
            case HOCON -> getPronounHOCONAccounts(uuids);
            case SQLITE -> null;
        };
    }

    public CompletableFuture<AccountData> getPronounDBAccount(UUID uuid) {
        String baseUrl = PronounConfig.get().pronoundb().apiLink();
        AccountData accountData = cache.getIfPresent(uuid);
        if (accountData == null) {
            String url = baseUrl + "/lookup?platform=minecraft&ids=" + uuid;
            return CompletableFuture.supplyAsync(() -> {
                try (InputStream is = new URI(url).toURL().openStream()) {
                    JsonObject jo = JsonParser.parseReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                            .getAsJsonObject();
                    jo = jo.get(uuid.toString()).getAsJsonObject();
                    DecorationData decorationData = DecorationData.NONE;
                    if (jo.has("DECORATION")) {
                        String decorationKey = jo.get("DECORATION").getAsString();
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
                            pronounData1 = getPronounFromPDBKey(ja.get(0).getAsString());
                            pronounData2 = getPronounFromPDBKey(ja.get(1).getAsString());
                            pronounData3 = getPronounFromPDBKey(ja.get(2).getAsString());
                        } catch (IndexOutOfBoundsException ignored) {}
                    }
                    AccountData accountDat = new AccountData(uuid, pronounData1, pronounData2, pronounData3, decorationData);
                    cache.put(uuid, accountDat);
                    return accountDat;
                } catch (Exception e) {
                    InstanceHolder.getPlatformProvider().printThrowable(e);
                    return null;
                }
            });
        } else {
            return CompletableFuture.completedFuture(accountData);
        }
    }
    public CompletableFuture<List<AccountData>> getPronounDBAccounts(UUID... uuids) {
        String baseUrl = PronounConfig.get().pronoundb().apiLink();
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
            String url = baseUrl + "/lookup?platform=minecraft&ids=" + String.join(",", toFetch.stream().map(UUID::toString).toList());
            return CompletableFuture.supplyAsync(() -> {
                try (InputStream is = new URI(url).toURL().openStream()) {
                    JsonObject jo = JsonParser.parseReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                            .getAsJsonObject();
                    for (UUID uuid : toFetch) {
                        jo = jo.get(uuid.toString()).getAsJsonObject();
                        DecorationData decorationData = DecorationData.NONE;
                        if (jo.has("DECORATION")) {
                            String decorationKey = jo.get("DECORATION").getAsString();
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
                                pronounData1 = getPronounFromPDBKey(ja.get(0).getAsString());
                                pronounData2 = getPronounFromPDBKey(ja.get(1).getAsString());
                                pronounData3 = getPronounFromPDBKey(ja.get(2).getAsString());
                            } catch (IndexOutOfBoundsException ignored) {
                            }
                        }
                        AccountData accountDat = new AccountData(uuid, pronounData1, pronounData2, pronounData3, decorationData);
                        cache.put(uuid, accountDat);
                        accounts.add(accountDat);
                    }
                    return accounts;
                } catch (Exception e) {
                    InstanceHolder.getPlatformProvider().printThrowable(e);
                    return null;
                }
            });
        }
    }

    public CompletableFuture<AccountData> getPronounHOCONAccount(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            Path path = InstanceHolder.getPlatformProvider().getDataPath().resolve("pronouns-player-data.conf");

            try {
                HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
                        .path(path)
                        .prettyPrinting(true)
                        .build();

                CommentedConfigurationNode root = loader.load();

                AccountObject accountObject = root.node(uuid.toString()).get(AccountObject.class);

                if (accountObject == null) {
                    root.node(uuid.toString()).set(NULL_ACCOUNT.clone());
                    loader.save(root);
                    return NULL_ACCOUNT.clone();
                } else {
                    return accountObject.getAccountData(uuid);
                }
            } catch (Exception e) {
                InstanceHolder.getPlatformProvider().printThrowable(e);
                return null;
            }
        });
    }
    public CompletableFuture<List<AccountData>> getPronounHOCONAccounts(UUID... uuids) {
        return CompletableFuture.supplyAsync(() -> {
            Path path = InstanceHolder.getPlatformProvider().getDataPath().resolve("pronouns-player-data.conf");

            try {
                HoconConfigurationLoader loader = HoconConfigurationLoader.builder().path(path).build();

                CommentedConfigurationNode root = loader.load();

                List<AccountData> accounts = new ArrayList<>();

                for (UUID uuid : uuids) {
                    AccountObject accountObject = root.node(uuid.toString()).get(AccountObject.class);

                    if (accountObject == null) {
                        root.node(uuid.toString()).set(NULL_ACCOUNT.clone());
                        loader.save(root);
                        accounts.add(NULL_ACCOUNT.clone());
                    } else {
                        accounts.add(accountObject.getAccountData(uuid));
                    }
                }

                return accounts;
            } catch (Exception e) {
                InstanceHolder.getPlatformProvider().printThrowable(e);
                return null;
            }
        });
    }
    
    private PronounData getPronounFromPDBKey(String key) {
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

    @Override
    public PronounConfig getConfig() throws Exception {
        return CommonMethods.getConfig(getDataPath().resolve("pronouns-config.conf"));
    }

    /**
     * Checks if a UUID has a permission, has blocking operations
     * @param uuid The UUID of the user
     * @param permission The permission to check
     * @return If they have the permission
     */
    public CompletableFuture<Boolean> hasPermission(UUID uuid, String permission) {
        try {
            LuckPerms luckPerms = LuckPermsProvider.get();
            UserManager userManager = luckPerms.getUserManager();
            CompletableFuture<User> userFuture = userManager.loadUser(uuid);
            return CompletableFuture.supplyAsync(() -> {
               User user = userFuture.join();
               for (Node node : user.getNodes()) {
                   if (node.getKey().equals(permission)) return true;
               }
               return false;
            });
        } catch (Throwable t) { //Anything could happen here, we don't really care as we have a *some what* solid fallback
            return CompletableFuture.completedFuture(isOp(uuid));
        }
    }

    public abstract boolean isOp(UUID uuid);
}
