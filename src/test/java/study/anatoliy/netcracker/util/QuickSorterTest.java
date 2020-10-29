package study.anatoliy.netcracker.util;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class QuickSorterTest {
    ISorter sorter = new QuickSorter();

    @Test
    public void sort_emptyArray_nothingHappened() {
        Integer[] a = new Integer[]{};

        assertDoesNotThrow(() -> sorter.sort(a));
    }

    @Test
    public void sort_sortedArray_arrayWontChange() {
        Integer[] a = new Integer[]{1, 2, 3, 4, 5};

        sorter.sort(a);

        for (int i = 0; i < a.length - 1; i++) {
            assertTrue(a[i] <= a[i+1], Arrays.toString(a));
        }
    }

    @Test
    public void sort_randomArray_arrayWillSort() {
        Random random = new Random();
        Integer[] a = new Integer[10];
        for (int i = 0; i < a.length; i++) {
            a[i] = random.nextInt(a.length);
        }

        sorter.sort(a);

        for (int i = 0; i < a.length - 1; i++) {
            assertTrue(a[i] <= a[i+1], Arrays.toString(a));
        }
    }

    @Test
    public void sort_reverseArray_arrayWillSort() {
        Integer[] a = new Integer[10];
        for (int i = 0; i < a.length; i++) {
            a[i] = a.length - i;
        }

        sorter.sort(a);

        for (int i = 0; i < a.length - 1; i++) {
            assertTrue(a[i] <= a[i+1], Arrays.toString(a));
        }
    }

    @Test
    public void sort_emptyList_nothingHappened() {
        List<Integer> a = Collections.emptyList();

        assertDoesNotThrow(() -> sorter.sort(a));
    }

    @Test
    public void sort_sortedList_arrayWontChange() {
        List<Integer> a = Arrays.asList(1, 2, 3, 4, 5);

        sorter.sort(a);

        for (int i = 0; i < a.size() - 1; i++) {
            assertTrue(a.get(i) <= a.get(i+1), a.toString());
        }
    }

    @Test
    public void sort_randomList_arrayWillSort() {
        Random random = new Random();
        List<Integer> a = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            a.add(random.nextInt(10));
        }

        sorter.sort(a);

        for (int i = 0; i < a.size() - 1; i++) {
            assertTrue(a.get(i) <= a.get(i+1), a.toString());
        }
    }

    @Test
    public void sort_reverseList_arrayWillSort() {
        List<Integer> a = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            a.add(10 - a.size());
        }

        sorter.sort(a);

        for (int i = 0; i < a.size() - 1; i++) {
            assertTrue(a.get(i) <= a.get(i+1), a.toString());
        }
    }
}