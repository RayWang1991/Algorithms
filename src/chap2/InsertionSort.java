package chap2;

/**
 * Created by raywang on 2017/11/26.
 */
public class InsertionSort {
    // basic insertion
    public static void sort(Comparable[] a) {
        sort(a,0,a.length-1);
//        for (int i = 1; i < a.length; i++) {
//            for (int j = i; j > 0 && Utils.less(a, j, j - 1); j--) {
//                Utils.exch(a, j, j - 1); // may use later
//            }
//        }
    }

    public static void sort(Comparable[] a, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            for (int j = i; j > low && Utils.less(a, j, j - 1); j--) {
                Utils.exch(a, j, j - 1); // may use later
            }
        }
    }


    public static void sort1(Comparable[] a, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            Comparable t = a[i];
            int j;
            for (j = i; j > low && Utils.less(t, a[j - 1]); j--) {
                a[j] = a[j - 1];
            }
            a[j] = t;
        }
    }

    public static void sort1(Comparable[] a) {
        sort1(a, 0, a.length - 1);
    }
}
