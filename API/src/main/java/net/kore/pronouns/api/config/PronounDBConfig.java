package net.kore.pronouns.api.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
// Not needed, these values can change, the compiler just doesn't know.
@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public class PronounDBConfig {
    private String displayName = "PronounDB";
    private String homePageLink = "https://pronoundb.org/";
    private String apiLink = "https://pronoundb.org/api/v2";

    public String displayName() {
        return displayName;
    }

    public String homePageLink() {
        return homePageLink;
    }

    public String apiLink() {
        return apiLink;
    }
}
