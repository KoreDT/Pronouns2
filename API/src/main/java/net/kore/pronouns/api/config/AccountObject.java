package net.kore.pronouns.api.config;

import net.kore.pronouns.api.data.AccountData;
import net.kore.pronouns.api.data.DecorationData;
import net.kore.pronouns.api.data.PronounData;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.UUID;

@ConfigSerializable
public class AccountObject {
    public PronounData p1;
    public PronounData p2;
    public PronounData p3;

    public DecorationData d;

    public AccountData getAccountData(UUID uuid) {
        return new AccountData(uuid, p1, p2, p3, d);
    }

    public static AccountObject fromAccountData(AccountData accountData) {
        AccountObject accountObject = new AccountObject();
        accountObject.p1 = accountData.pronoun1();
        accountObject.p2 = accountData.pronoun2();
        accountObject.p3 = accountData.pronoun3();
        accountObject.d = accountData.decorationData();
        return accountObject;
    }
}
