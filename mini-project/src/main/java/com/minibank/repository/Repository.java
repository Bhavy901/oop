package com.minibank.repository;

import com.minibank.model.annotation.Id;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Practical 13 (Part B)
 * Generic type-safe Repository storing domain entities using reflection to discover @Id primary keys.
 */
public class Repository<T> {

    // Thread-safe internal storage
    private final Map<String, T> storage = new ConcurrentHashMap<>();

    /**
     * Extracts the @Id annotated field value via reflection.
     */
    private String extractId(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        Class<?> current = entity.getClass();
        while (current != null && current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    try {
                        Object val = field.get(entity);
                        if (val != null) {
                            return val.toString();
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Cannot access @Id field " + field.getName(), e);
                    }
                }
            }
            current = current.getSuperclass();
        }
        throw new IllegalArgumentException("Entity of class " + entity.getClass().getSimpleName() + " has no @Id annotated field!");
    }

    public T save(T entity) {
        String id = extractId(entity);
        storage.put(id, entity);
        return entity;
    }

    public T findById(String id) {
        if (id == null) return null;
        return storage.get(id);
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public boolean delete(String id) {
        if (id == null) return false;
        return storage.remove(id) != null;
    }

    @SafeVarargs
    public final void saveAll(T... items) {
        if (items != null) {
            for (T item : items) {
                if (item != null) {
                    save(item);
                }
            }
        }
    }

    public int count() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }

    /**
     * Practical 13: Generic bounded method calculating statistical average.
     */
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) return 0.0;
        double sum = 0.0;
        for (N val : values) {
            if (val != null) sum += val.doubleValue();
        }
        return sum / values.length;
    }
}
