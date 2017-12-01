package chap2;

import edu.princeton.cs.algs4.Insertion;

/**
 * Created by raywang on 2017/11/29.
 */
public class QuickSort {
    // text book
    public static int partition0(Comparable[] a, int low, int high) {
        int i = low + 1, j = high;
        Comparable v = a[low];
        loop:
        while (i <= j) {
            while (Utils.less(a[i], v)) {
                i++;
                if (i > j) break loop;
            }
            while (!Utils.less(a[j], v)) {
                j--;
                if (i > j) break loop;
            }
            Utils.exch(a, i, j);
        }
        i--;
        Utils.exch(a, low, i);
        return i;
    }

    // my own
    public static int partition(Comparable[] a, int low, int high) {
        Comparable v = a[low];
        int i = low + 1;
        while (i <= high) { // use high as j
            if (Utils.less(a[i], v)) {
                Utils.exch(a, i, i - 1);
                i++;
            } else {
                Utils.exch(a, i, high);
                high--;
            }
        }
        return i - 1;
    }

    public static int partition3way(Comparable[] a, int low, int high) {
        Comparable v = a[low];
        int i = low + 1, j = high + 1, k = low;
        while (i < j) {
            if (Utils.less(v, a[i])) Utils.exch(a, k++, i++);
            else if (Utils.less(a[i], v)) Utils.exch(a, i, --j);
            else i++;
        }
        return i - 1;
    }

    public static void sort(Comparable[] a, int low, int high) {
        if (low + 10 >= high) { // use insertion sort in small array
            InsertionSort.sort(a, low, high);
            return;
        }
        if (low >= high) return;
        int mid = partition(a, low, high);
        sort(a, low, mid - 1);
        sort(a, mid + 1, high);
    }

    public static void sort3way(Comparable[] a, int low, int high) {
//        if (low + 10 >= high) {
//            InsertionSort.sort(a, low, high);
//            return;
//        }

        if (low >= high) {
            return;
        }
        Comparable v = a[low];
        int i = low + 1, j = high + 1, k = low;
        while (i < j) {
            if (Utils.less(a[i], v)) Utils.exch(a, k++, i++);
            else if (Utils.less(v, a[i])) Utils.exch(a, i, --j);
            else i++;
        }

        sort3way(a, low, k - 1);
        sort3way(a, j, high);
    }

    public static void sort3way(Comparable[] a) {
        sort3way(a, 0, a.length - 1);
    }

    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }
}
