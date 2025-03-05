package org.alexeybelakhvostsik.arraylist;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Реализация упрощённого аналога ArrayList на Java.
 *
 * @param <T> тип элементов, хранящихся в списке
 */
public class MyArrayList<T> {

    /**
     * Начальная вместимость по умолчанию.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Массив для хранения элементов списка.
     */
    private Object[] elements;

    /**
     * Текущее количество элементов в списке.
     */
    private int size;

    /**
     * Конструктор по умолчанию. Создаёт список с начальной вместимостью по умолчанию.
     */
    public MyArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Конструктор с указанием начальной вместимости.
     *
     * @param initialCapacity начальная вместимость списка
     * @throws IllegalArgumentException если начальная вместимость отрицательна
     */
    public MyArrayList(int initialCapacity) {
        if ((initialCapacity < 0)) {
            throw new IllegalArgumentException("Недопустимое значение вместимости");
        }
        this.elements = new Object[initialCapacity];
        this.size = 0;
    }

    /**
     * Увеличивает вместимость списка, если это необходимо.
     */
    private void ensureCapacity() {
        if (size == elements.length) {
            int newCapacity = elements.length * 2;  //Увеличиваем вместимость в 2 раза
            Object[] newElements = new Object[newCapacity]; //Копируем элементы из старого списка в новый
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;  //Заменяем старый массив на новый
        }
    }

    /**
     * Добавляет элемент в конец списка.
     *
     * @param element элемент для добавления
     */
    public void add(T element) {
        ensureCapacity();  //Проверяем нужно ли увеличить размер списка
        elements[size++] = element; //Добавляем элемент и увеличиваем размер
    }

    /**
     * Добавляет элемент на указанную позицию в списке.
     *
     * @param index   индекс, на который будет добавлен элемент
     * @param element элемент для добавления
     * @throws IndexOutOfBoundsException если индекс выходит за пределы списка
     */
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Недопустимый индекс");
        }
        ensureCapacity(); //Проверяем нужно ли увеличить размер списка
        System.arraycopy(elements, index, elements, index + 1, size - index);  //Сдвигаем вправа, чтобы освободить место для нового элемента
        elements[index] = element;  //Вставляем элемент на заданную позицию
        size++;  //Инкрементируем размер
    }

    /**
     * Возвращает элемент по указанному индексу.
     *
     * @param index индекс элемента
     * @return элемент на указанной позиции
     * @throws IndexOutOfBoundsException если индекс выходит за пределы списка
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Недопустимый индекс");
        }
        return (T) elements[index]; //Возвращаем элемент приведенный к типу T
    }

    /**
     * Удаляет элемент по указанному индексу.
     *
     * @param index индекс элемента для удаления
     * @throws IndexOutOfBoundsException если индекс выходит за пределы списка
     */
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Недопустимый индекс");
        }
        System.arraycopy(elements, index + 1, elements, index, size - index - 1); //Сдвигаем элементы влево
        elements[--size] = null; //Устанавливаем значение null для освобождения памяти
    }

    /**
     * Очищает список, удаляя все элементы.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;  //Пробегаемся по всем элементам и устанавливаем значение null для освобождения памяти
        }
        size = 0;  //Делаем список пустым
    }

    /**
     * Сортирует список с использованием компаратора.
     *
     * @param comparator компаратор для определения порядка сортировки
     */
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super Object> comparator) {
        Arrays.sort((T[]) elements, 0, size, comparator);  //Сортируем элементы
    }

    /**
     * Возвращает строковое представление списка.
     *
     * @return строковое представление списка
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}