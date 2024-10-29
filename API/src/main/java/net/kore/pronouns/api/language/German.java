package net.kore.pronouns.api.language;

import net.kore.pronouns.api.InstanceHolder;

import java.util.UUID;

public class German implements LanguageSet {
    protected German() {}

    @Override
    public PronounSet none() {
        return new PronounSet.Delegated(they()) {
            @Override
            public String usualDisplay() {
                return "Nein/Pronomen";
            }
        };
    }

    @Override
    public PronounSet he() {
        return new PronounSet() {
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
    }

    @Override
    public PronounSet it() {
        return new PronounSet() {
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
    }

    @Override
    public PronounSet she() {
        return new PronounSet() {
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
    }

    @Override
    public PronounSet they() {
        return new PronounSet() {
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
                return "Dey/Demm";
            }
        };
    }

    @Override
    public PronounSet any() {
        return new PronounSet.Delegated(they()) {
            @Override
            public String usualDisplay() {
                return "Alle/Pronomen";
            }
        };
    }

    @Override
    public PronounSet ask() {
        return new PronounSet() {
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
                return "Bitten/Pronomen";
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

    @Override
    public PronounSet other() {
        return new PronounSet.Delegated(they()) {
            @Override
            public String usualDisplay() {
                return "Andere/Pronomen";
            }
        };
    }
}
