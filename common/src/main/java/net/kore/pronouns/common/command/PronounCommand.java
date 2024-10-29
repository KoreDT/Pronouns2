package net.kore.pronouns.common.command;

import net.kore.pronouns.api.InstanceHolder;
import net.kore.pronouns.api.PronounsAPI;
import net.kore.pronouns.api.PronounsMeta;
import net.kore.pronouns.api.config.AccountObject;
import net.kore.pronouns.api.config.PronounConfig;
import net.kore.pronouns.api.data.DecorationData;
import net.kore.pronouns.api.data.PronounData;
import net.kore.pronouns.api.language.LanguageSet;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

public class PronounCommand {
    public static void info(CommandSender commandSender) {
        commandSender.sendMessage(
                "<green>Pronouns<white>: <gray><br>"
                        + "- <green>Version</green><white>: " + PronounsMeta.VERSION + "</white><br>"
                        + "- <green>Website</green><white>: <blue><u><click:open_url:"+ PronounsMeta.WEBSITE +">" + PronounsMeta.WEBSITE + "</click></u></blue></white>"
        );
    }

    public static void get(CommandSender commandSender) {
        commandSender.sendMessage("<yellow>Fetching pronouns...");

        PronounsAPI.getPronounDBAccount(commandSender.getUUID()).thenAcceptAsync(accountData -> {
            commandSender.sendMessage(
                    "<green>You " +
                            "<gray>have the pronouns <gold>" +
                            LanguageSet.getDisplayedPronouns(PronounConfig.currentLanguage(), accountData).replace("/", "<gray>/</gray>")
            );
        });
    }

    public static void get(CommandSender commandSender, Profile profile) {
        commandSender.sendMessage("<yellow>Fetching pronouns...");

        PronounsAPI.getPronounDBAccount(profile.uuid()).thenAcceptAsync(accountData -> {
            commandSender.sendMessage(
                    "<green>" +
                            profile.name() +
                            " <gray>has the pronouns <gold>" +
                            LanguageSet.getDisplayedPronouns(PronounConfig.currentLanguage(), accountData).replace("/", "<gray>/</gray>")
            );
        });
    }

    public static void set(CommandSender commandSender, DataType dataType, @Nullable PronounData pronounData, @Nullable DecorationData decorationData) {
        if (commandSender.getUUID() == null) {
            commandSender.sendMessage("<red>This command can only be executed from a player.");
        }

        set(commandSender, dataType, pronounData, decorationData, commandSender.getUUID());
    }

    public static void set(CommandSender commandSender, DataType dataType, @Nullable PronounData pronounData, @Nullable DecorationData decorationData, UUID uuid) {
        String denyMessage = PronounConfig.get().disallowSetMessageMM();

        if (denyMessage != null) {
            commandSender.sendMessage(denyMessage);
            return;
        }

        commandSender.sendMessage("<yellow>Making changes...");

        PronounsAPI.getPronounDBAccount(uuid).thenAcceptAsync(accountData -> {
            AccountObject accountObject = AccountObject.fromAccountData(accountData);

            switch (dataType) {
                case PRONOUN1 -> accountObject.p1 = pronounData;
                case PRONOUN2 -> accountObject.p2 = pronounData;
                case PRONOUN3 -> accountObject.p3 = pronounData;
                case DECORATION -> accountObject.d = decorationData;
            }

            String message = PronounConfig.get().saveAccountObject(uuid, accountObject);
            if (message == null) message = "<green>Saved account successfully!";

            commandSender.sendMessage(message);
        });
    }

    public static void reload(CommandSender commandSender) {
        commandSender.sendMessage("<yellow>Reloading config...");
        try {
            InstanceHolder.setPronounConfig(InstanceHolder.getPlatformProvider().getConfig());
        } catch (Exception e) {
            commandSender.sendMessage("<red>Failed to reload config. See the console for more information.");
            InstanceHolder.getPlatformProvider().printThrowable(e);
            return;
        }
        commandSender.sendMessage("<green>Reloaded config!");
    }
}
