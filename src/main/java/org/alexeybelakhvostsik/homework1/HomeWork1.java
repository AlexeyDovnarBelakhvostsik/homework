package org.alexeybelakhvostsik.homework1;

import java.util.Arrays;

public class HomeWork1 {

    public static void main(String[] args) {
        turnString("I love Java");

        int[] ints = {1, 2, 2, 3, 4, 5, 5, 6, 7, 8, 8, 9};
        getDistinctNumbers(ints);

        int[] arr = {10, 15, 23, 11, 44, 13, 66, 1, 6, 47};
        int i = findSecondMaxElement(arr);
        System.out.println(i);

        int j = lengthOfLastWord("    fly me    to the moon    ");
        System.out.println(j);

        boolean palindrome = isPalindrome("aba");
        System.out.println(palindrome);
    }

    // Перевернуть строку и вывести на консоль
    //  String string = "I love Java";
    public static void turnString(String string) {
        StringBuffer stringBuffer = new StringBuffer(string);
        stringBuffer.reverse();
        System.out.println(stringBuffer);
    }

    // int[] ints = {1, 2, 2, 3, 4, 5, 5, 6, 7, 8, 8, 9};
    // Удалить дубликаты из массива и вывести в консоль
    public static void getDistinctNumbers(int[] ints) {
        Arrays.stream(ints).distinct().forEach(System.out::println);
    }

    // Дан массив, заполненный уникальными значениями типа int.
    // int[] arr = {10, 15, 23, 11, 44, 13, 66, 1, 6, 47};
    // Необходимо найти элемент, который меньше максимума, но больше всех остальных.
    public static Integer findSecondMaxElement(int[] arr) {
        Arrays.sort(arr);
        return arr[arr.length - 2];
    }

    // Найти длину последнего слова в строке. В строке только буквы и пробелы.
    // "Hello world" - 5
    // "    fly me    to the moon    " - 4
    public static Integer lengthOfLastWord(String string) {

        int right = string.length() - 1;
        while (right >= 0 && string.charAt(right) == ' ') {
            right--;
        }
        int left = right;
        while (left >= 0 && string.charAt(left) != ' ') {
            left--;
        }

        return right - left;
    }

    // Определить, что строка является палиндромом
    // Сложность по памяти O(1), не создавать новые String, StringBuilder
    // Примеры:
    // abc - false
    // 112233 - false
    // aba - true
    // 112211 - true
    public static boolean isPalindrome(String string) {

        int length = string.length();
        for (int i = 0; i < length / 2; i++) {
            if (string.charAt(i) != string.charAt(length - 1 - i)) {
                return false;
            }
        }
        return true;
    }
}
