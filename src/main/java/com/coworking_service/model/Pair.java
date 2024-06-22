package com.coworking_service.model;

/**
 * Класс, представляющий пару значений ключ-значение.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public record Pair<K, V>(K key, V value) {
}

