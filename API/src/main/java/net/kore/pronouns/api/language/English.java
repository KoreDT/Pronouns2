package net.kore.pronouns.api.language;

import net.kore.pronouns.api.InstanceHolder;

import java.util.UUID;

public class English implements LanguageSet {
    protected English() {}

    @Override
    public PronounSet none() {
        return new PronounSet.Delegated(they()) {
            @Override
            public String usualDisplay() {
                return "No/Pronouns";
            }
        };
    }

    @Override
    public PronounSet he() {
        return new PronounSet() {
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
    }

    @Override
    public PronounSet it() {
        return new PronounSet() {
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
    }

    @Override
    public PronounSet she() {
        return new PronounSet() {
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
    }

    @Override
    public PronounSet they() {
        return new PronounSet() {
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
                return "They/Them";
            }
        };
    }

    @Override
    public PronounSet any() {
        return new PronounSet.Delegated(they()) {
            @Override
            public String usualDisplay() {
                return "Any/Pronouns";
            }
        };
    }

    @Override
    public PronounSet ask() {
        return new PronounSet.Delegated(they()) {
            @Override
            public String usualDisplay() {
                return "Ask/Pronouns";
            }
        };
    }

    @Override
    public PronounSet avoid(UUID uuid) {
        String name = InstanceHolder.getPlatformProvider().getDisplayName(uuid);
        return new PronounSet() {
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

    @Override
    public PronounSet other() {
        return new PronounSet.Delegated(they()) {
            @Override
            public String usualDisplay() {
                return "Other/Pronouns";
            }
        };
    }
}
