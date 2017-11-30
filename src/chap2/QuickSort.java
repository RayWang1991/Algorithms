package chap2;

/**
 * Created by raywang on 2017/11/29.
 */
public class QuickSort {
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

    public static void sort(Comparable[] a, int low, int high) {
        if (low >= high) {
            return;
        }
        int mid = partition0(a, low, high);
        sort(a, low, mid - 1);
        sort(a, mid + 1, high);
    }

    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }
}
