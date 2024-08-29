package org.dynapi.common.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * utility methods for {@link Set}'s
 */
public class SetUtils {
    /**
     * merge multiple sets
     * @param sets the sets to merge
     * @return new merged {@link Set} containing all elements of the other
     */
    @SafeVarargs
    public static <T> Set<T> merged(Set<T>... sets) {
        final Set<T> merged = new HashSet<T>();
        for (Set<T> set : sets)
            merged.addAll(set);
        return merged;
    }

    /**
     * returns a new {@link Set} containing all elements from {@code set1} that are not in {@code set1}
     * @param set base elements
     * @param others elements that should be removed from {@code set1}
     * @return {@link Set} containing the difference
     */
    @SafeVarargs
    public static <T> Set<T> difference(Set<T> set, Set<T>... others) {
        final Set<T> diff = new HashSet<>(set);
        for (Set<T> other : others)
            diff.removeAll(other);
        return diff;
    }
}
