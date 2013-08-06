package org.soldomi.commons;

public interface Reducer<T, R> {
    public R foldIn(T next, R value);
}
