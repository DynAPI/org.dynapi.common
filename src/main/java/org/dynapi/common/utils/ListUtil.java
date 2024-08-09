package org.dynapi.common.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
    @SafeVarargs
    public static <T> List<T> merged(List<T>... lists) {
        List<T> merged = new ArrayList<>();
        for (List<T> list : lists)
            merged.addAll(list);
        return merged;
    }
}
