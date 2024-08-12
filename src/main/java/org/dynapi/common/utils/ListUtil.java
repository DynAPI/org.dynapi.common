package org.dynapi.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * utility methods for {@link List}'s
 */
public class ListUtil {
    /**
     * merge multiple lists
     * @param lists the lists to merge
     * @return new merged {@link List} containing all elements of the other
     */
    @SafeVarargs
    public static <T> List<T> merged(List<T>... lists) {
        List<T> merged = new ArrayList<>();
        for (List<T> list : lists)
            merged.addAll(list);
        return merged;
    }
}
