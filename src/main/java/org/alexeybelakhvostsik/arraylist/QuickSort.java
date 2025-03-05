package org.alexeybelakhvostsik.arraylist;
import java.util.Comparator;

public class QuickSort {

    // Основной метод для сортировки с использованием Comparable
    public static <T extends Comparable<T>> void sort(T[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        quickSort(array, 0, array.length - 1, null);
    }

    // Основной метод для сортировки с использованием Comparator
    public static <T> void sort(T[] array, Comparator<? super T> comparator) {
        if (array == null || array.length == 0) {
            return;
        }
        quickSort(array, 0, array.length - 1, comparator);
    }

    // Рекурсивный метод быстрой сортировки
    private static <T> void quickSort(T[] array, int low, int high, Comparator<? super T> comparator) {
        if (low < high) {
            int pivotIndex = partition(array, low, high, comparator); // Разделение массива
            quickSort(array, low, pivotIndex - 1, comparator); // Сортировка левой части
            quickSort(array, pivotIndex + 1, high, comparator); // Сортировка правой части
        }
    }

    // Метод для разделения массива
    private static <T> int partition(T[] array, int low, int high, Comparator<? super T> comparator) {
        T pivot = array[high]; // Опорный элемент
        int i = low - 1; // Индекс для элементов меньше опорного

        for (int j = low; j < high; j++) {
            if (compare(array[j], pivot, comparator) <= 0) { // Сравнение элементов
                i++;
                swap(array, i, j); // Меняем местами элементы
            }
        }
        swap(array, i + 1, high); // Ставим опорный элемент на правильное место
        return i + 1;
    }

    // Метод для сравнения элементов
    @SuppressWarnings("unchecked")
    private static <T> int compare(T a, T b, Comparator<? super T> comparator) {
        if (comparator != null) {
            return comparator.compare(a, b); // Используем Comparator, если он предоставлен
        } else {
            return ((Comparable<T>) a).compareTo(b); // Используем Comparable по умолчанию
        }
    }

    // Метод для обмена элементов массива
    private static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
