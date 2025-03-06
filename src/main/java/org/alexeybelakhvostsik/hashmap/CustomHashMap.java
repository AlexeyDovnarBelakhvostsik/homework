package org.alexeybelakhvostsik.hashmap;

import java.util.*;

/**
 * Пользовательская реализация HashMap с поддержкой основных операций.
 * Не потокобезопасна. Использует связные списки для разрешения коллизий.
 * При достижении порога нагрузки (capacity * loadFactor) увеличивает емкость вдвое.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class CustomHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Node<K, V>[] buckets;
    private int size;
    public int capacity;
    private final float loadFactor;
    private int threshold;
    private int modCount;

    /**
     * Конструктор с параметрами по умолчанию.
     */
    public CustomHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Конструктор с указанной начальной емкостью.
     *
     * @param initialCapacity начальная емкость
     * @throws IllegalArgumentException если емкость <= 0
     */
    public CustomHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Основной конструктор.
     *
     * @param initialCapacity начальная емкость
     * @param loadFactor      коэффициент загрузки
     * @throws IllegalArgumentException если параметры некорректны
     */
    @SuppressWarnings("unchecked")
    public CustomHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0 || loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Некорректные параметры");

        this.capacity = nextPowerOfTwo(initialCapacity);
        this.loadFactor = loadFactor;
        this.threshold = (int) (capacity * loadFactor);
        this.buckets = (Node<K, V>[]) new Node[capacity];
    }

    private int nextPowerOfTwo(int n) {
        return n <= 0 ? 1 : 1 << (32 - Integer.numberOfLeadingZeros(n - 1));
    }

    private int hash(K key) {
        return key == null ? 0 : Objects.hashCode(key) & (capacity - 1);
    }

    /**
     * Добавляет пару ключ-значение.
     *
     * @param key   ключ
     * @param value значение
     * @return предыдущее значение или null
     */
    public V put(K key, V value) {
        int index = hash(key);
        Node<K, V> node = buckets[index];
        Node<K, V> prev = null;

        while (node != null) {
            if (Objects.equals(node.key, key)) {
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
            prev = node;
            node = node.next;
        }

        if (prev == null) {
            buckets[index] = new Node<>(key, value);
        } else {
            prev.next = new Node<>(key, value);
        }

        if (++size > threshold) resize();
        modCount++;
        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        capacity *= 2;
        threshold = (int) (capacity * loadFactor);
        Node<K, V>[] newBuckets = (Node<K, V>[]) new Node[capacity];

        for (Node<K, V> node : buckets) {
            while (node != null) {
                int newIndex = hash(node.key);
                Node<K, V> next = node.next;
                node.next = newBuckets[newIndex];
                newBuckets[newIndex] = node;
                node = next;
            }
        }
        buckets = newBuckets;
    }

    /**
     * Возвращает значение по ключу.
     *
     * @param key ключ
     * @return значение или null
     */
    public V get(Object key) {
        int index = hash((K) key);
        Node<K, V> node = buckets[index];

        while (node != null) {
            if (Objects.equals(node.key, key)) return node.value;
            node = node.next;
        }
        return null;
    }

    /**
     * Удаляет элемент по ключу.
     *
     * @param key ключ
     * @return удаленное значение или null
     */
    public V remove(Object key) {
        int index = hash((K) key);
        Node<K, V> node = buckets[index];
        Node<K, V> prev = null;

        while (node != null) {
            if (Objects.equals(node.key, key)) {
                if (prev == null) {
                    buckets[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                modCount++;
                return node.value;
            }
            prev = node;
            node = node.next;
        }
        return null;
    }

    /**
     * Возвращает количество сопоставлений ключ-значение в этой карте.
     *
     * @return количество сопоставлений ключ-значение
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет, пуста ли карта.
     *
     * @return {@code true} если карта не содержит элементов, {@code false} в противном случае
     */
    public boolean isEmpty() {

        return size == 0;
    }

    /**
     * Возвращает Set представление ключей, содержащихся в этой карте.
     *
     * @return Set ключей
     */
    public Set<K> keySet() {
        return new KeySet();
    }

    private class KeySet extends AbstractSet<K> {
        @Override
        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        @Override
        public int size() {
            return size;
        }
    }

    /**
     * Возвращает Collection представление значений, содержащихся в этой карте.
     *
     * @return Collection значений
     */
    public Collection<V> values() {
        return new Values();
    }

    private class Values extends AbstractCollection<V> {
        @Override
        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        @Override
        public int size() {
            return size;
        }
    }

    /**
     * Возвращает Set представление пар ключ-значение, содержащихся в этой карте.
     *
     * @return Set пар ключ-значение
     */
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    private class EntrySet extends AbstractSet<Map.Entry<K, V>> {
        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public int size() {
            return size;
        }
    }

    private abstract class HashIterator {
        int currentBucket;
        Node<K, V> currentNode;
        Node<K, V> nextNode;
        int expectedModCount;

        HashIterator() {
            expectedModCount = modCount;
            currentBucket = 0;
            currentNode = null;
            nextNode = null;

            // Находим первый непустой бакет
            while (currentBucket < capacity && buckets[currentBucket] == null) {
                currentBucket++;
            }
            if (currentBucket < capacity) {
                nextNode = buckets[currentBucket];
            }
        }

        public boolean hasNext() {
            return nextNode != null;
        }

        final void advance() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            if (nextNode == null)
                throw new NoSuchElementException();

            currentNode = nextNode;
            nextNode = nextNode.next;

            // Если в текущем бакете больше нет элементов, переходим к следующему
            if (nextNode == null) {
                do {
                    currentBucket++;
                } while (currentBucket < capacity && buckets[currentBucket] == null);
                if (currentBucket < capacity) {
                    nextNode = buckets[currentBucket];
                }
            }
        }

        public void remove() {
            if (currentNode == null)
                throw new IllegalStateException();
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();

            CustomHashMap.this.remove(currentNode.key);
            currentNode = null;
            expectedModCount = modCount;
        }
    }

    private class KeyIterator extends HashIterator implements Iterator<K> {
        @Override
        public K next() {
            advance();
            return currentNode.key;
        }
    }

    private class ValueIterator extends HashIterator implements Iterator<V> {
        @Override
        public V next() {
            advance();
            return currentNode.value;
        }
    }

    private class EntryIterator extends HashIterator implements Iterator<Map.Entry<K, V>> {
        @Override
        public Map.Entry<K, V> next() {
            advance();
            return currentNode;
        }
    }
}
