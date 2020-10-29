package study.anatoliy.netcracker.util;

import java.util.Comparator;

public class BubbleSorter implements ISorter {

    @Override
    public <T> void sort(T[] a, int start, int end, Comparator<T> c) {
        int i = start;
        while (i < end) {
            int last = i + 1;
            for (int j = end - 1; j > i; j--) {
                if (c.compare(a[j], a[j-1]) < 0) {
                    ISorter.swap(a, j, j-1);
                    last = j;
                }
            }
            i = last;
        }
    }
}
