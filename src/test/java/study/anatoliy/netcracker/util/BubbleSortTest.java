package study.anatoliy.netcracker.util;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortTest {

    ISorter sorter = new BubbleSort();

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
            assertTrue(a[i] <= a[i+1]);
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
            assertTrue(a[i] <= a[i+1]);
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
            assertTrue(a[i] <= a[i+1]);
        }
    }

}