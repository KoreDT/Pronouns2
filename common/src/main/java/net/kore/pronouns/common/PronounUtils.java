package net.kore.pronouns.common;

import java.io.PrintWriter;
import java.io.StringWriter;

public class PronounUtils {
    public static String throwableString(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        t.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
