package study.anatoliy.netcracker.util;

import java.util.Comparator;

public interface ISorter {

    default <T> void sort(T[] a, Comparator<T> c) {
        sort(a, 0, a.length, c);
    }

    default <T extends Comparable<T>> void sort(T[] a) {
        Comparator<T> c = Comparable::compareTo;
        sort(a, c);
    }

    static <T> void swap(T[] a, int index1, int index2) {
        T temp = a[index1];
        a[index1] = a[index2];
        a[index2] = temp;
    }

    <T> void sort(T[] a, int start, int end, Comparator<T> c);
}
