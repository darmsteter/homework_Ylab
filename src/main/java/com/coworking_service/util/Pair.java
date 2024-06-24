package com.coworking_service.util;

/**
 * Класс, представляющий пару значений ключ-значение.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public record Pair<K, V>(K key, V value) {
}

