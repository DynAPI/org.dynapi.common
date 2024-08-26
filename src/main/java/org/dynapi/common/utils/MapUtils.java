package org.dynapi.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * utility methods for {@link Map}'s
 */
public class MapUtils {
    /**
     * merge multiple lists
     * @param maps the maps to merge
     * @return new merged {@link Map} containing all elements of the other
     */
    @SafeVarargs
    public static <K, V> Map<K, V> merged(Map<K, V>... maps) {
        final Map<K, V> result = new HashMap<>();
        for (Map<K, V> map : maps)
            result.putAll(map);
        return result;
    }
}
