package org.dynapi.common.utils;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

/**
 * better formatting for (debug) printing
 */
public class PrintUtils {
    /**
     * @param object object to format
     * @return formatted object
     */
    public static String repr(Object object) {
        if (object == null) return null;
        if (object instanceof Object[] array) return repr(array);
        if (object instanceof CharSequence string) return repr(string);
        if (object instanceof Number number) return repr(number);
        if (object instanceof Boolean bool) return repr(bool);
        if (object instanceof Collection<?> collection) return repr(collection);
        if (object instanceof Map<?, ?> map) return repr(map);
        if (object instanceof Class<?> clazz) return repr(clazz);
        return reprObject(object);
    }

    /**
     * @param object object to format
     * @return formatted object
     */
    public static String reprObject(Object object) {
        if (object == null) return null;
        Class<?> clazz = object.getClass();
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        for (Field field : clazz.getFields()) {
            try {
                joiner.add(field.getName() + "=" + repr(field.get(object)));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return clazz.getCanonicalName() + joiner;
    }

    // -------------------------------------------------------------------------

    /**
     * @param array array to format
     * @return formatted object
     */
    public static String repr(Object[] array) {
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        for (Object object : array)
            joiner.add(repr(object));
        Class<?> arrayType = array.getClass().getComponentType();
        return arrayType.getCanonicalName() + "[]" + joiner;
    }

    // -------------------------------------------------------------------------

    /**
     * @param number number to format
     * @return formatted object
     */
    public static String repr(Number number) {
        if (number == null) return null;
        return new DecimalFormat("#,###.#").format(number).replace(",", "_");
    }

    // -------------------------------------------------------------------------

    /**
     * @param bool boolean value to format
     * @return formatted object
     */
    public static String repr(Boolean bool) {
        return bool == null ? null : bool.toString();
    }

    // -------------------------------------------------------------------------

    private static final char CONTROL_LIMIT = ' ';
    private static final char PRINTABLE_LIMIT = '~';
    private static final char[] HEX_DIGITS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * converts a string to a better human-readable format
     *
     * @param charSequence input string
     * @return escaped string
     */
    public static String repr(CharSequence charSequence) {
        if (charSequence == null) return null;

        final StringBuilder sb = new StringBuilder();
        final int limit = charSequence.length();

        char[] hexbuf = null;

        int pointer = 0;

        sb.append('"');

        while (pointer < limit) {
            int ch = charSequence.charAt(pointer++);

            switch (ch) {
                case '\0' -> sb.append("\\0");
                case '\t' -> sb.append("\\t");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\"' -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                default -> {
                    if(CONTROL_LIMIT <= ch && ch <= PRINTABLE_LIMIT) sb.append((char)ch);
                    else {
                        sb.append("\\u");
                        if(hexbuf == null)
                            hexbuf = new char[4];

                        for(int offs = 4; offs > 0;) {
                            hexbuf[--offs] = HEX_DIGITS[ch & 0xf];
                            ch >>>= 4;
                        }
                        sb.append(hexbuf, 0, 4);
                    }
                }
            }
        }

        return sb.append('"').toString();
    }

    // -------------------------------------------------------------------------

    /**
     * @param collection collection to format
     * @return formatted object
     */
    public static String repr(Collection<?> collection) {
        if (collection == null) return null;

        StringJoiner joiner = new StringJoiner(", ", "[", "]");

        for (Object object : collection)
            joiner.add(repr(object));

        return collection.getClass().getCanonicalName() + joiner;
    }

    // -------------------------------------------------------------------------

    /**
     * @param map mapping to format
     * @return formatted object
     */
    public static String repr(Map<?, ?> map) {
        if (map == null) return null;

        StringJoiner joiner = new StringJoiner(", ", "{", "}");

        for (Map.Entry<?, ?> entry : map.entrySet())
            joiner.add(repr(entry.getKey()) + "=" + repr(entry.getValue()));

        return map.getClass().getCanonicalName() + joiner;
    }

    // -------------------------------------------------------------------------

    /**
     * @param clazz class to format
     * @return formatted object
     */
    public static String repr(Class<?> clazz) {
        if (clazz == null) return null;
        return "Class<" + clazz.getCanonicalName() + ">";
    }

    // -------------------------------------------------------------------------
}
