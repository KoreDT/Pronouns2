package net.kore.pronouns.api.language;

import net.kore.pronouns.api.InstanceHolder;
import net.kore.pronouns.api.data.PronounData;

import java.util.UUID;

public class EnglishSet {
    public static LanguageSet fromPronounData(UUID uuid, PronounData pronounData) {
        return switch (pronounData) {
            case HE -> new LanguageSet() {
                @Override
                public String possessive() {
                    return "his";
                }

                @Override
                public String[] personals() {
                    return new String[]{"he", "him"};
                }

                @Override
                public String reflexive() {
                    return "himself";
                }

                @Override
                public String intensive() {
                    return "himself";
                }

                @Override
                public String usualDisplay() {
                    return "He/Him";
                }
            };
            case IT -> new LanguageSet() {
                @Override
                public String possessive() {
                    return "it's";
                }

                @Override
                public String[] personals() {
                    return new String[]{"it", "its"};
                }

                @Override
                public String reflexive() {
                    return "itself";
                }

                @Override
                public String intensive() {
                    return "itself";
                }

                @Override
                public String usualDisplay() {
                    return "It/Its";
                }
            };
            case SHE -> new LanguageSet() {
                @Override
                public String possessive() {
                    return "her's";
                }

                @Override
                public String[] personals() {
                    return new String[]{"she", "her"};
                }

                @Override
                public String reflexive() {
                    return "herself";
                }

                @Override
                public String intensive() {
                    return "herself";
                }

                @Override
                public String usualDisplay() {
                    return "She/Her";
                }
            };
            case THEY, ANY, ASK, OTHER, NONE -> new LanguageSet() {
                @Override
                public String possessive() {
                    return "their";
                }

                @Override
                public String[] personals() {
                    return new String[]{"they", "them"};
                }

                @Override
                public String reflexive() {
                    return "themself";
                }

                @Override
                public String intensive() {
                    return "themself";
                }

                @Override
                public String usualDisplay() {
                    return switch (pronounData) {
                        case NONE -> "No/Pronouns";
                        case THEY -> "They/Them";
                        case ANY -> "Any/Pronouns";
                        case ASK -> "Ask/Pronouns";
                        case OTHER -> "Other/Pronouns";
                        default -> throw new IllegalStateException("Unexpected value: " + pronounData);
                    };
                }
            };
            case AVOID -> {
                String name = InstanceHolder.getPlatformProvider().getDisplayName(uuid);
                yield new LanguageSet() {
                    @Override
                    public String possessive() {
                        return name+"'s";
                    }

                    @Override
                    public String[] personals() {
                        return new String[]{name, name};
                    }

                    @Override
                    public String reflexive() {
                        return "itself";
                    }

                    @Override
                    public String intensive() {
                        return "itself";
                    }

                    @Override
                    public String usualDisplay() {
                        return "Avoid/Pronouns";
                    }
                };
            }
        };
    }

    public static String getFormattedPronouns(PronounData pronounData1) {
        return fromPronounData(null, pronounData1).usualDisplay();
    }

    public static String getFormattedPronouns(PronounData pronounData1, PronounData pronounData2) {
        if (pronounData2.equals(PronounData.NONE)) {
            return getFormattedPronouns(pronounData1);
        }
        String display1 = fromPronounData(null, pronounData1).usualDisplay().split("/")[0];
        String display2 = fromPronounData(null, pronounData2).usualDisplay().split("/")[0];
        return display1 + "/" + display2;
    }

    public static String getFormattedPronouns(PronounData pronounData1, PronounData pronounData2, PronounData pronounData3) {
        if (pronounData2.equals(PronounData.NONE) && pronounData3.equals(PronounData.NONE)) {
            return getFormattedPronouns(pronounData1);
        } else if (pronounData3.equals(PronounData.NONE)) {
            return getFormattedPronouns(pronounData1, pronounData2);
        } else if (pronounData2.equals(PronounData.NONE)) {
            return getFormattedPronouns(pronounData1, pronounData3);
        }

        String display1 = fromPronounData(null, pronounData1).usualDisplay().split("/")[0];
        String display2 = fromPronounData(null, pronounData2).usualDisplay().split("/")[0];
        String display3 = fromPronounData(null, pronounData3).usualDisplay().split("/")[0];
        return display1 + "/" + display2 + "/" + display3;
    }
}
