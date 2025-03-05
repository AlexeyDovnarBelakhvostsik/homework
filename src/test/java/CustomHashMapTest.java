import static org.junit.jupiter.api.Assertions.*;

import org.alexeybelakhvostsik.hashmap.CustomHashMap;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CustomHashMapTest {
    @Test
    void testPutAndGet() {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();
        map.put("a", 1);
        assertEquals(1, map.get("a"));
    }

    @Test
    void testCollisions() {
        CustomHashMap<Integer, String> map = new CustomHashMap<>(2);
        map.put(1, "a");
        map.put(3, "b"); // Коллизия при емкости 2
        assertEquals("a", map.get(1));
        assertEquals("b", map.get(3));
    }

    @Test
    void testLoadFactorResize() {
        CustomHashMap<Integer, Integer> map = new CustomHashMap<>(16, 0.5f);
        for (int i = 0; i < 9; i++) map.put(i, i); // Порог 8
        assertEquals(32, map.capacity);
    }

    @Test
    void testMassiveOperations() {
        CustomHashMap<Integer, Integer> map = new CustomHashMap<>();
        for (int i = 0; i < 1000; i++) map.put(i, i);
        assertEquals(1000, map.size());

        for (int i = 0; i < 1000; i++) assertEquals(i, map.get(i));

        for (int i = 0; i < 1000; i++) map.remove(i);
        assertTrue(map.isEmpty());
    }

    @Test
    void testIsEmptyAfterMassiveOperations() {
        CustomHashMap<Integer, String> map = new CustomHashMap<>();
        assertTrue(map.isEmpty());
        // Добавление 1000 элементов
        for (int i = 0; i < 1000; i++) {
            map.put(i, "value_" + i);
            assertFalse(map.isEmpty());
        }
        // Удаление всех элементов
        for (int i = 0; i < 1000; i++) {
            map.remove(i);
        }
        assertTrue(map.isEmpty());
    }

    @Test
    void testKeySet() {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Set<String> keys = map.keySet();
        assertTrue(keys.contains("a"));
        assertTrue(keys.contains("b"));
        assertEquals(2, keys.size());
    }

    @Test
    void testValues() {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Collection<Integer> values = map.values();
        assertTrue(values.contains(1));
        assertTrue(values.contains(2));
        assertEquals(2, values.size());
    }

    @Test
    void testEntrySet() {
        CustomHashMap<String, Integer> map = new CustomHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        assertEquals(2, entries.size());
        for (Map.Entry<String, Integer> entry : entries) {
            assertTrue(entry.getKey().equals("a") || entry.getKey().equals("b"));
            assertTrue(entry.getValue() == 1 || entry.getValue() == 2);
        }
    }
}
