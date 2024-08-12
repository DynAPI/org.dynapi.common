package org.dynapi.common.builder;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * fluent {@link List} builder
 */
@EqualsAndHashCode
public class ListBuilder<T> {
    protected final List<T> list = new ArrayList<>();

    public ListBuilder<T> add(T element) {
        list.add(element);
        return this;
    }

    public ListBuilder<T> addAll(Collection<T> elements) {
        list.addAll(elements);
        return this;
    }

    public ListBuilder<T> remove(T element) {
        list.remove(element);
        return this;
    }

    public ListBuilder<T> removeAll(Collection<T> elements) {
        list.removeAll(elements);
        return this;
    }

    public List<T> getList() {
        return List.copyOf(list);
    }
}
