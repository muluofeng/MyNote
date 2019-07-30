package com.example.xing.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author xiexingxing
 * @Created by 2019-05-26 5:48 PM.
 */
public class Sort {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList();
        list.add(9);
        list.add(3);
        list.add(5);

        Integer[] listArr = list.toArray(new Integer[list.size()]);
        sort(listArr);
        System.out.println(new ArrayList<>(Arrays.asList(listArr)).toString());
    }

    /**
     * 实现冒泡排序
     *
     * @param list
     * @param <T>
     */
    public static <T extends Comparable<T>> void sort(T[] list) {
        //判断一次循环比较是否有 数据的转换 ，没有就代表所有的数据都是顺序的
        boolean isSwap = true;
        // 3 5 7 8
        for (int i = 1, len = list.length; i < len && isSwap; i++) {
            isSwap = false;
            for (int j = 0; j < len - i; j++) {
                if (list[j].compareTo(list[j + 1]) > 0) {
                    T temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                    isSwap = true;
                }
            }

        }
    }

    /**
     * 实现冒泡排序
     *
     * @param list
     * @param <T>
     */
    public static <T extends Comparable<T>> void sort(T[] list, Comparator<T> com) {

        boolean isSwap = true;
        // 3 5 7 8
        for (int i = 1, len = list.length; i < len && isSwap; i++) {
            isSwap = false;
            for (int j = 0; j < len - i; j++) {
                if (com.compare(list[j], list[j + 1]) > 0) {
                    T temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                    isSwap = true;
                }
            }

        }
    }
}
