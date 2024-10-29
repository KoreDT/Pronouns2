package net.kore.pronouns.api.language;

public interface PronounSet {
    String possessive();
    String[] personals();
    String reflexive();
    String intensive();
    String usualDisplay();

    class Delegated implements PronounSet {
        private final PronounSet parent;

        public Delegated(PronounSet parent) {
            this.parent = parent;
        }

        @Override
        public String possessive() {
            return parent.possessive();
        }

        @Override
        public String[] personals() {
            return parent.personals();
        }

        @Override
        public String reflexive() {
            return parent.reflexive();
        }

        @Override
        public String intensive() {
            return parent.intensive();
        }

        @Override
        public String usualDisplay() {
            return parent.usualDisplay();
        }
    }
}
