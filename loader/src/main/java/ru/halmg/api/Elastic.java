package ru.halmg.api;

import java.util.Map;

public interface Elastic<T> {
    Map<String, Object> toMap(T object);
}
