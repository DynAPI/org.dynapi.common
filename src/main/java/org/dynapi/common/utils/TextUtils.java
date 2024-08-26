package org.dynapi.common.utils;

import java.util.function.Predicate;

/**
 * utility class containing text-related utility functions
 */
public abstract class TextUtils {

    // -------------------------------------------------------------------------

    /**
     * Remove any common leading whitespace from every line in text.
     * <br>
     * Note that tabs and spaces are both treated as whitespace, but they are not equal:
     * the lines {@code "  hello"} and {@code "\thello"} are considered to have no common leading whitespace.
     * <br>
     * Lines containing only whitespace are ignored in the input and normalized to a single newline character in the output.
     *
     * @param text input text
     * @return dedent text
     */
    public static String dedent(String text) {
        if (text == null) return null;

        final String[] lines = splitLines(text, true);

        String commonPrefix = extractLeadingSpace(lines[0]);
        if (commonPrefix.isEmpty()) return text;

        for (int i = 1; i < lines.length; i++) {
            if (lines[i].isBlank()) continue;
            while (!lines[i].startsWith(commonPrefix)) {
                commonPrefix = commonPrefix.substring(0, commonPrefix.length() - 1);
                if (commonPrefix.isEmpty()) return text;
            }
        }

        final StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            if (line.isBlank()) sb.append(line);
            else sb.append(line.substring(commonPrefix.length() + 1));
        }
        return sb.toString();
    }

    /**
     * Add {@code prefix} to the beginning of selected lines in {@code text}.
     * <br>
     * By default, {@code prefix} is added to all lines.
     *
     * @param text input text
     * @param prefix prefix to add
     * @return with {@code prefix} indented text
     *
     * @see TextUtils#indent(String, String, Predicate)
     */
    public static String indent(String text, String prefix) {
        return indent(text, prefix, null);
    }

    /**
     * Add {@code prefix} to the beginning of selected lines in {@code text}.
     *
     * @param text input text
     * @param prefix prefix to add
     * @param predicate controls which lines are indented
     * @return with {@code prefix} indent text
     */
    public static String indent(String text, String prefix, Predicate<String> predicate) {
        if (text == null) return null;
        if (prefix == null) return text;

        final StringBuilder sb = new StringBuilder();
        for (String line : splitLines(text, true)) {
            if (predicate == null || predicate.test(line)) sb.append(prefix);
            sb.append(line);
        }
        return sb.toString();
    }

    // -------------------------------------------------------------------------

    /**
     * Return an array of the lines in the string, breaking at line boundaries.
     * @param text text to split
     * @return array of lines
     */
    public static String[] splitlines(String text) {
        return splitLines(text, false);
    }

    /**
     * Return an array of the lines in the string, breaking at line boundaries.
     * @param text text to split
     * @param keepends whether to keep the linebreak (\n | \r\n) at the end of the lines
     * @return array of lines
     */
    public static String[] splitLines(String text, boolean keepends) {
        // this split uses a lookbehind-trick to keep the linebreak
        return text.split(keepends ? "(?<=\\r?\\n)" : "\\r?\\n", -1);
    }

    // -------------------------------------------------------------------------

    private static String extractLeadingSpace(String string) {
        if (string.isEmpty()) return "";

        final char whitespace = string.charAt(0);
        if (whitespace != ' ' && whitespace != '\t') return "";

        int depth = 1;
        final int maxDepth = string.length() - 1;
        while (depth < maxDepth && string.charAt(depth + 1) == whitespace) depth++;

        return String.valueOf(whitespace).repeat(depth);
    }
}
