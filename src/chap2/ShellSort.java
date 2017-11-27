package chap2;

/**
 * Created by raywang on 2017/11/27.
 */
public class ShellSort {
    public static void sort(Comparable[] a, int low, int high) {

        int N = 1;

        while (N < a.length) {
            N = 3 * N + 1; // TODO, finally n >= a.length
        }
        while (N >= 1) {
            for (int i = low + N; i <= high; i++) {
                int j;
                Comparable t = a[i];
                for (j = i; j >= low + N && Utils.less(t, a[j - N]); j -= N) {
                    a[j] = a[j - N];
                }
                a[j] = t;
            }
            N /= 3;
        }
    }

    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }
}
