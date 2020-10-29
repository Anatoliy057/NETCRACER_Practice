package study.anatoliy.netcracker.util;

import java.util.Comparator;
import java.util.List;

/**
 * Sorter of type QUICK that implements quick sort
 *
 * @see ISorter
 * @see <a href="https://en.wikipedia.org/wiki/Quicksort">Quicksort</a>
 *
 * @author Udarczev Anatoliy
 */
public class QuickSorter implements ISorter {


    /**
     * @see Sorters#get(TypeSorter)
     */
    QuickSorter() {}

    @Override
    public <T> void sort(List<T> a, int from, int to, Comparator<T> c) {
        if (from - to > 0) {
            throw new IllegalArgumentException(from + " > " + to);
        }
        recursiveSort(a, from, to - 1, c);
    }

    @Override
    public <T> void sort(T[] a, int from, int to, Comparator<T> c) {
        if (from - to > 0) {
            throw new IllegalArgumentException(from + " > " + to);
        }
        recursiveSort(a, from, to - 1, c);
    }

    private <T> void recursiveSort(List<T> a, int from, int to, Comparator<T> c) {
        if (a.size() == 0)
            return;

        if (from >= to)
            return;

        int middle = from + (to - from) / 2;
        T supElem = a.get(middle);

        int i = from, j = to;
        while (i <= j) {
            while (c.compare(a.get(i), supElem) < 0) {
                i++;
            }

            while (c.compare(a.get(j), supElem) > 0) {
                j--;
            }

            if (i <= j) {
                ISorter.swap(a, i, j);
                i++;
                j--;
            }
        }

        if (from < j) {
            recursiveSort(a, from, j, c);
        }

        if (to > i) {
            recursiveSort(a, i, to, c);
        }
    }

    private <T> void recursiveSort(T[] a, int from, int to, Comparator<T> c) {
        if (a.length == 0)
            return;

        if (from >= to)
            return;

        int middle = from + (to - from) / 2;
        T supElem = a[middle];

        int i = from, j = to;
        while (i <= j) {
            while (c.compare(a[i], supElem) < 0) {
                i++;
            }

            while (c.compare(a[j], supElem) > 0) {
                j--;
            }

            if (i <= j) {
                ISorter.swap(a, i, j);
                i++;
                j--;
            }
        }

        if (from < j) {
            recursiveSort(a, from, j, c);
        }

        if (to > i) {
            recursiveSort(a, i, to, c);
        }
    }

    @Override
    public TypeSorter getType() {
        return TypeSorter.QUICK;
    }
}
