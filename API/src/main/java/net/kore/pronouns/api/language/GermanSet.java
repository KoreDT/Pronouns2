package net.kore.pronouns.api.language;

import net.kore.pronouns.api.InstanceHolder;
import net.kore.pronouns.api.data.PronounData;

import java.util.UUID;

public class GermanSet {
    public static LanguageSet fromPronounData(UUID uuid, PronounData pronounData) {
        return switch (pronounData) {
            case HE -> new LanguageSet() {
                @Override
                public String possessive() {
                    return "sein";
                }

                @Override
                public String[] personals() {
                    return new String[]{"er", "ihn"};
                }

                @Override
                public String reflexive() {
                    return "selbst";
                }

                @Override
                public String intensive() {
                    return "selbst";
                }

                @Override
                public String usualDisplay() {
                    return "Er/Ihn";
                }
            };
            case IT -> new LanguageSet() {
                @Override
                public String possessive() {
                    return "seine";
                }

                @Override
                public String[] personals() {
                    return new String[]{"es", "seine"};
                }

                @Override
                public String reflexive() {
                    return "selbst";
                }

                @Override
                public String intensive() {
                    return "selbst";
                }

                @Override
                public String usualDisplay() {
                    return "Es/Seine";
                }
            };
            case SHE -> new LanguageSet() {
                @Override
                public String possessive() {
                    return "ihres";
                }

                @Override
                public String[] personals() {
                    return new String[]{"sie", "ihr"};
                }

                @Override
                public String reflexive() {
                    return "selbst";
                }

                @Override
                public String intensive() {
                    return "selbst";
                }

                @Override
                public String usualDisplay() {
                    return "Sie/Ihr";
                }
            };
            case THEY, ANY, ASK, OTHER, NONE -> new LanguageSet() {
                @Override
                public String possessive() {
                    return "ihre";
                }

                @Override
                public String[] personals() {
                    return new String[]{"dey", "demm"};
                }

                @Override
                public String reflexive() {
                    return "selbst";
                }

                @Override
                public String intensive() {
                    return "selbst";
                }

                @Override
                public String usualDisplay() {
                    return switch (pronounData) {
                        case NONE -> "Nein/Pronomen";
                        case THEY -> "Dey/Demm";
                        case ANY -> "Alle/Pronomen";
                        case ASK -> "Bitten/Pronomen";
                        case OTHER -> "Andere/Pronomen";
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
                        return "selbst";
                    }

                    @Override
                    public String intensive() {
                        return "selbst";
                    }

                    @Override
                    public String usualDisplay() {
                        return "Vermeiden/Pronomen";
                    }
                };
            }
        };
    }
}
