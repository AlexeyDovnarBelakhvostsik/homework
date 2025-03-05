package org.alexeybelakhvostsik.arraylist;

import java.util.Arrays;

public class QuickSortTest {
    public static void main(String[] args) {
        // Пример с использованием Comparable (естественный порядок)
        Integer[] numbers = {34, 7, 23, 32, 5, 62};
        System.out.println(Arrays.toString(numbers));
        QuickSort.sort(numbers);
        System.out.println(Arrays.toString(numbers));

        // Пример с использованием Comparator (пользовательский порядок)
        String[] words = {"banana", "apple", "cherry", "date"};
        System.out.println("Before sorting (Comparator): " + Arrays.toString(words));
        QuickSort.sort(words, String.CASE_INSENSITIVE_ORDER); // Сортировка без учёта регистра
        System.out.println("After sorting (Comparator): " + Arrays.toString(words));
    }
}
