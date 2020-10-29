package study.anatoliy.netcracker.util;

import java.util.Comparator;
import java.util.List;

/**
 * Sorter of type BUBBLE that implements bubble sort
 *
 * @see ISorter
 * @see <a href="https://en.wikipedia.org/wiki/Bubble_sort">Bubblesort</a>
 *
 * @author Udarczev Anatoliy
 */
public class BubbleSorter implements ISorter {

    /**
     * @see Sorters#get(TypeSorter)
     */
    BubbleSorter() {}

    @Override
    public <T> void sort(List<T> a, int from, int to, Comparator<T> c) {
        if (from - to > 0) {
            throw new IllegalArgumentException(from + " > " + to);
        }

        int i = from;
        while (i < to) {
            int last = i + 1;
            for (int j = to - 1; j > i; j--) {
                if (c.compare(a.get(j), a.get(j-1)) < 0) {
                    ISorter.swap(a, j, j-1);
                    last = j;
                }
            }
            i = last;
        }
    }

    @Override
    public <T> void sort(T[] a, int from, int to, Comparator<T> c) {
        if (from - to > 0) {
            throw new IllegalArgumentException(from + " > " + to);
        }

        int i = from;
        while (i < to) {
            int last = i + 1;
            for (int j = to - 1; j > i; j--) {
                if (c.compare(a[j], a[j-1]) < 0) {
                    ISorter.swap(a, j, j-1);
                    last = j;
                }
            }
            i = last;
        }
    }

    @Override
    public TypeSorter getType() {
        return TypeSorter.BUBBLE;
    }
}
