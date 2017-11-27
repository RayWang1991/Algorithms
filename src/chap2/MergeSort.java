package chap2;

/**
 * Created by raywang on 2017/11/26.
 */
public class MergeSortTD {
    public static void merge(Comparable[] a, Comparable[] aux, int low, int mid,
                             int high) {
        if (mid + 1 > high || Utils.less(a[mid], a[mid + 1])) { //2.2.2.2 if is
            // already sorted, do not merge again
            return;
        }
        for (int i = low; i <= high; i++) {
            aux[i] = a[i];
        }
        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > high) {
                a[k] = aux[i++];
            } else if (Utils.less(aux[i], aux[j])) {
                a[k] = aux[i++];
            } else {
                a[k] = aux[j++];
            }
        }
    }

    public static void sort(Comparable[] a, Comparable[] aux, int low,
                            int high) {
        if (low >= high) {
            return;
        }
        if (high - low <= 16) { //2.2.2.1 for short array use insertion sort
            InsertionSort.sort1(a, low, high);
        }
        int mid = low + (high - low) / 2;
        sort(a, aux, low, mid);
        sort(a, aux, mid + 1, high);
        merge(a, aux, low, mid, high);
    }

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }
}
