package org.alexeybelakhvostsik.hashmap;

import java.util.Map;

/**
 * Узел, хранящий пару ключ-значение в CustomHashMap.
 * Поддерживает связь с следующим узлом для обработки коллизий.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class Node<K, V> implements Map.Entry<K, V>{
    final K key;
    V value;
    Node<K, V> next;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }

}
