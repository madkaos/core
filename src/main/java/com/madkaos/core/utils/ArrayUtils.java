package com.madkaos.core.utils;

public class ArrayUtils {
    public static String[] removeFirstElement(String[] arr) {
        String newArr[] = new String[arr.length - 1];
        for (int i = 1; i < arr.length; i++) {
            newArr[i-1] = arr[i];
        }
        return newArr;
    }

    public static boolean contains(String[] arr, String value) {
        for (String item : arr) {
            if (item.equals(value)) {
                return true;
            }
        }

        return false;
    }

    public static String[] addElement(String[] arr, String item) {
        String newArr[] = new String[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i];
        }
        newArr[newArr.length - 1] = item;
        return newArr;
    }
}
