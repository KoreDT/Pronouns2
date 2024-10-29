package net.kore.pronouns.api.language;

public class Languages {
    private static final English ENGLISH = new English();
    private static final German GERMAN = new German();

    public static English english() {
        return ENGLISH;
    }
    public static German german() {
        return GERMAN;
    }
}
