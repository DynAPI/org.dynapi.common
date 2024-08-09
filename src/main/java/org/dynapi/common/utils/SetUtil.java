package org.dynapi.common.utils;

import java.util.HashSet;
import java.util.Set;

public class SetUtil {
    @SafeVarargs
    public static <T> Set<T> merged(Set<T>... sets) {
        Set<T> merged = new HashSet<T>();
        for (Set<T> set : sets)
            merged.addAll(set);
        return merged;
    }
}
