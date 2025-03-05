package org.alexeybelakhvostsik.arraylist;

public class MyArrayListTest {
    public static void main(String[] args) {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list);

        list.add(1, 99);
        System.out.println(list);

        list.remove(2);
        System.out.println(list);
    }
}
