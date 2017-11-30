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

    // 2.2.10 quick merge
    public static void merge1(Comparable[] a, Comparable[] aux, int low, int
            mid, int high) {
        for (int i = low; i <= mid; i++) {
            aux[i] = a[i];
        }
        for (int i = mid + 1; i <= high; i++) {
            aux[i] = a[mid + 1 + high - i];
        }
        int i = low, j = high, k = low;
        while (i <= j) {
            if (Utils.less(aux[i], aux[j])) {
                a[k++] = aux[i++];
            } else {
                a[k++] = aux[j--];
            }
        }
        return;
    }

    // 2.2.11 swap destination and source alternatively, just merge without
    // copy data
    public static void merge2(Comparable[] des, Comparable[] src, int low, int
            mid, int high) {
//        if (mid + 1 > high || Utils.less(des[mid], des[mid + 1])) { //2.2.2.2 if is
//            // already sorted, do not merge again
//            return;
//        }
        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) {
                des[k] = src[j++];
            } else if (j > high) {
                des[k] = src[i++];
            } else if (Utils.less(src[i], src[j])) {
                des[k] = src[i++];
            } else {
                des[k] = src[j++];
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
        merge1(a, aux, low, mid, high);
    }

    // Top down
    public static void sortTD(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sortTD(a, aux, 0, a.length - 1);
    }

    public static void sortBU(Comparable[] a, Comparable[] aux, int low, int
            high) {
        boolean flag = false;
        for (int size = 1; size < a.length; size += size) {
            for (int i = low; i + size <= high; i += 2 * size) {
                merge2(aux, a, i, i + size - 1, Math.min(i + 2 * size -
                        1, high));
            }
            flag = !flag;
            Comparable[] t = a;
            a = aux;
            aux = t;
        }
        if (flag) {
            for (int i = low; i <= high; i++) {
                a[i] = aux[i];
            }
        }
    }

    // Bottom up
    public static void sortBU(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sortBU(a, aux, 0, a.length - 1);
    }
}
