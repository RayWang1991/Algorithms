package chap2;

/**
 * Created by raywang on 2017/11/26.
 */
public class MergeSort {
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

    public static void sortTD(Comparable[] a, Comparable[] aux, int low,
                              int high) {
        if (low >= high) {
            return;
        }
        if (high - low <= 16) { //2.2.2.1 for short array use insertion sortTD
            InsertionSort.sort1(a, low, high);
        }
        int mid = low + (high - low) / 2;
        sortTD(a, aux, low, mid);
        sortTD(a, aux, mid + 1, high);
        merge(a, aux, low, mid, high);
    }

    // Top down
    public static void sortTD(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sortTD(a, aux, 0, a.length - 1);
    }

    public static void sortBU(Comparable[] a, Comparable[] aux, int low, int
            high) {
        for (int size = 1; size < a.length; size += size) {
            for (int i = low; i + size <= high; i += 2 * size) {
                merge(a, aux, i, i + size - 1, Math.min(i + 2 * size -
                        1, high));
            }
        }
    }

    // Bottom up
    public static void sortBU(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sortBU(a, aux, 0, a.length - 1);
    }
}
