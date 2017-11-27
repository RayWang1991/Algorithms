package chap2;

/**
 * Created by raywang on 2017/11/26.
 */
public class SelectionSort {
    public static void sort(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            int m = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j].compareTo(a[m]) < 0) {
                    m = j;
                }
            }
            Utils.exch(a, i, m);
        }
    }
}
