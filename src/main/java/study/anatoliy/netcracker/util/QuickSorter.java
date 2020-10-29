package study.anatoliy.netcracker.util;

import java.util.Comparator;

public class QuickSorter implements ISorter {

    @Override
    public <T> void sort(T[] a, int start, int end, Comparator<T> c) {
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
            sort(a, start, j + 1, c);
        }

        if (end > i) {
            sort(a, i, end + 1, c);
        }
    }
}
