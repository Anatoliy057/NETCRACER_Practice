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

    QuickSorter() {}

    @Override
    public <T> void sort(List<T> a, int start, int end, Comparator<T> c) {
        if (start - end > 0) {
            throw new IllegalArgumentException(start + " > " + end);
        }
        recursiveSort(a, start, end, c);
    }

    @Override
    public <T> void sort(T[] a, int start, int end, Comparator<T> c) {
        if (start - end > 0) {
            throw new IllegalArgumentException(start + " > " + end);
        }
        recursiveSort(a, start, end, c);
    }

    private <T> void recursiveSort(List<T> a, int start, int end, Comparator<T> c) {
        end -= 1;
        if (a.size() == 0)
            return;

        if (start >= end)
            return;

        int middle = start + (end - start) / 2;
        T supElem = a.get(middle);

        int i = start, j = end;
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

        if (start < j) {
            recursiveSort(a, start, j + 1, c);
        }

        if (end > i) {
            recursiveSort(a, i, end + 1, c);
        }
    }

    private <T> void recursiveSort(T[] a, int start, int end, Comparator<T> c) {
        end -= 1;
        if (a.length == 0)
            return;

        if (start >= end)
            return;

        int middle = start + (end - start) / 2;
        T supElem = a[middle];

        int i = start, j = end;
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

        if (start < j) {
            recursiveSort(a, start, j + 1, c);
        }

        if (end > i) {
            recursiveSort(a, i, end + 1, c);
        }
    }

    @Override
    public TypeSorter getType() {
        return TypeSorter.QUICK;
    }
}
