package org.dynapi.common.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    @SafeVarargs
    public static <K, V> Map<K, V> merged(Map<K, V>... maps) {
        Map<K, V> result = new HashMap<>();
        for (Map<K, V> map : maps)
            result.putAll(map);
        return result;
    }
}
