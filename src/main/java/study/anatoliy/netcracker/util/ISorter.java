package study.anatoliy.netcracker.util;

import java.util.*;

/**
 * Interface that determines the ability of an object to sort an array or collection
 *
 * @see BubbleSorter
 * @see QuickSorter
 *
 * @author Udarczev Anatoliy
 */
public interface ISorter {

    /**
     * Swaps elements in an array
     *
     * @param a source array
     * @param index1 index of the first element
     * @param index2 index of the second element
     * @param <T> type of source array
     *
     * @throws ArrayIndexOutOfBoundsException if index1 or index2 less that 0 or more that array.length
     */
    static <T> void swap(T[] a, int index1, int index2) {
        T temp = a[index1];
        a[index1] = a[index2];
        a[index2] = temp;
    }

    /**
     * Swaps elements in a list
     *
     * @param a source list
     * @param index1 index of the first element
     * @param index2 index of the second element
     * @param <T> the type of the items in the source list
     *
     * @throws ArrayIndexOutOfBoundsException if index1 or index2 less that 0 or more that list.size()
     */
    static <T> void swap(List<T> a, int index1, int index2) {
        T temp = a.get(index1);
        a.set(index1, a.get(index2));
        a.set(index2, temp);
    }

    /**
     * @param a source array
     * @param c comparator that compares the elements of source array
     * @param <T> type of source array
     *
     * @see ISorter#sort(Object[], int, int, Comparator) where start is 0 and end is a.length
     */
    default <T> void sort(T[] a, Comparator<T> c) {
        sort(a, 0, a.length, c);
    }

    /**
     * @param a source array
     * @param from the start of the sorting range, inclusive
     * @param to the end of the sorting range, exclusive
     * @param <T> type of source array that extends of Comparable
     *
     * @see ISorter#sort(Object[], int, int, Comparator) where comparator gets from the extends Comparable
     */
    default <T extends Comparable<T>> void sort(T[] a, int from, int to) {
        Comparator<T> c = Comparable::compareTo;
        sort(a, from, to, c);
    }

    /**
     * @param a source array
     * @param <T> type of source array that extends of Comparable
     *
     * @see ISorter#sort(Object[], Comparator) where comparator gets from the extends Comparable
     */
    default <T extends Comparable<T>> void sort(T[] a) {
        Comparator<T> c = Comparable::compareTo;
        sort(a, c);
    }

    /**
     * @param a source array
     * @param c comparator that compares the elements of source list
     * @param <T> the type of the items in the source list
     *
     * @see ISorter#sort(List, int, int, Comparator)  where start is 0 and end is a.size()
     */
    default <T> void sort(List<T> a, Comparator<T> c) {
        sort(a, 0, a.size(), c);
    }

    /**
     * @param a source list
     * @param from the start of the sorting range, inclusive
     * @param to the end of the sorting range, exclusive
     * @param <T> the type of the items in the source list that extends of Comparable
     *
     * @see ISorter#sort(Object[], int, int, Comparator) where comparator gets from the extends Comparable
     */
    default <T extends Comparable<T>> void sort(List<T> a, int from, int to) {
        Comparator<T> c = Comparable::compareTo;
        sort(a, from, to, c);
    }

    /**
     * @param a source list
     * @param <T> the type of the items in the source list that extends of Comparable
     *
     * @see ISorter#sort(List, Comparator) where comparator gets from the extends Comparable
     */
    default <T extends Comparable<T>> void sort(List<T> a) {
        Comparator<T> c = Comparable::compareTo;
        sort(a, c);
    }

    /**
     * Sorts an array range by a comparator
     *
     * @param a source array
     * @param from the start of the sorting range, inclusive
     * @param to the end of the sorting range, exclusive
     * @param c comparator that compares the elements of source array
     * @param <T> type of source array
     *
     * @throws IllegalArgumentException if start > end
     * @throws NullPointerException is comparator or source array is null
     * @throws ArrayIndexOutOfBoundsException if start < 0 or end > array.length
     */
    <T> void sort(T[] a, int from, int to, Comparator<T> c);

    /**
     * Sorts an array range by a comparator
     *
     * @param a source list
     * @param from the start of the sorting range, inclusive
     * @param to the end of the sorting range, exclusive
     * @param c comparator that compares the elements of source list
     * @param <T> the type of the items in the source list
     *
     * @throws IllegalArgumentException if start > end
     * @throws NullPointerException is comparator or source array is null
     * @throws ArrayIndexOutOfBoundsException if start < 0 or end > list.size()
     */
    <T> void sort(List<T> a, int from, int to, Comparator<T> c);


    /**
     * @return type of sorter
     *
     * @see TypeSorter
     */
    TypeSorter getType();
}
