package net.kore.pronouns.api.data;

// Sorted by alphabetical order except NONE
public enum DecorationData {
    NONE("<gradient:white:white>"),

    CATGIRL_CHIEF("<gradient:#F49898:#F49898>"),
    COGS("<gradient:#C3591D:#C3591D>"),
    COOKIE("<gradient:#DA9F83:#8A4B38:#DA9F83>"),
    DAYTIME("<gradient:#FFAC33:#FFAC33>"),
    DF_KANIN("<gradient:#E08C73:#E08C73>"),
    DF_PLUME("<gradient:#BAD9B5:#BAD9B5>"),
    DONATOR_AURORA("<gradient:#18F89A:#C243EE>"),
    DONATOR_BLOSSOM("<gradient:#F4ABBA:#F4ABBA>"),
    DONATOR_RIBBON("<gradient:#DD2E44:#DD2E44>"),
    DONATOR_STAR("<gradient:#FDD264:#FDD264>"),
    DONATOR_STRAWBERRY("<gradient:#77B255:#BE1931:#F4ABBA:#BE1931>"),
    DONATOR_WARMTH("<gradient:#FDD264:#EB5353>"),
    NIGHTTIME("<gradient:#66757F:#66757F>"),
    PRIDE("<gradient:#F47C7C:#FFC268:#F7F48B:#A1DE93:#70A1D7:#957DAD>"),
    PRIDE_BI("<gradient:#D872AC:#957DAD:#6AA9ED>"),
    PRIDE_LESBIAN("<gradient:#EB765A:#FBAB74:#FFFFFF:#F295CC:#A36088>"),
    PRIDE_PAN("<gradient:#FF82B1:#F7F48B:#8BD1F9>"),
    PRIDE_TRANS("<gradient:#67D4EA:#FCB6B3:#FFFFFF:#FCB6B3:#67D4EA>");

    private final String minimessageGradient;
    public String getMiniMessageGradientTag() {
        return minimessageGradient;
    }

    DecorationData(String minimessageGradient) {
        this.minimessageGradient = minimessageGradient;
    }
}
