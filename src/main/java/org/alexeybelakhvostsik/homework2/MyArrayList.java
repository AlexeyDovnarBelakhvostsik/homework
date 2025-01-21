package org.alexeybelakhvostsik.homework2;

import java.util.Arrays;
import java.util.Comparator;

public class MyArrayList<T> {

    // Начальная вместимость по умолчанию
    private static final int DEFAULT_CAPACITY = 10;

    //Массив для хранения элементов
    private Object[] elements;

    //текущее колличество элементов в списке
    private int size;

    //Конструктор который создает список с заданной вместимостью
    public MyArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    //Конструктор который создает список с заданной вместимостью
    public MyArrayList(int initialCapacity) {
        if ((initialCapacity < 0)) {
            throw new IllegalArgumentException("Недопустимое значение вместимости");
        }
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    //Метод который проверяет нужно ли увеличить размер списка
    private void ensureCapacity() {
        if (size == elements.length) {
            int newCapacity = elements.length * 2;  //Увеличиваем вместимость в 2 раза
            Object[] newElements = new Object[newCapacity]; //Копируем элементы из старого списка в новый
            System.arraycopy(elements, 0, newElements, 0, elements.length);
            elements = newElements;  //Заменяем старый массив на новый
        }
    }

    //Метод для добавления элемента в конец списка
    public void add(T element) {
        ensureCapacity();  //Проверяем нужно ли увеличить размер списка
        elements[size++] = element; //Добавляем элемент и увеличиваем размер
    }

    //Метод для добавленияэлемента по заданному индексу
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Недопустимый индекс");
        }
        ensureCapacity(); //Проверяем нужно ли увеличить размер списка
        System.arraycopy(elements, index, elements, index + 1, size - index);  //Сдвигаем вправа, чтобы освободить место для нового элемента
        elements[index] = element;  //Вставляем элемент на заданную позицию
        size++;  //Инкрементируем размер
    }

    //Метод для получения элемента по индексу
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Недопустимый индекс");
        }
        return (T) elements[index]; //Возвращаем элемент приведенный к типу T
    }

    //Удаление элемента по индексу
    @SuppressWarnings("unchecked")
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Недопустимый индекс");
        }
        T removedElement = get(index); //Получаем удаленный элемент
        System.arraycopy(elements, index + 1, elements, index, size - index - 1); //Сдвигаем элементы влево
        elements[--size] = null; //Устанавливаем значение null для освобождения памяти
    }

    //Метод для очистки списка
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;  //Пробегаемся по всем элементам и устанавливаем значение null для освобождения памяти
        }
        size = 0;  //Делаем список пустым
    }

    //Метод для сортировки списка
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super Object> comparator) {
        Arrays.sort((T[]) elements, 0, size, comparator);  //Сортируем элементы
    }

    //Переопределенный метод toString(),переписанный для корректного отображения списка
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