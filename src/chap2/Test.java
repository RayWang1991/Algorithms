package chap2;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by raywang on 2017/11/26.
 */
public class Test {
    /**
     * return true if array is sorted (less key first)
     *
     * @param a int array to be examed
     * @return
     */
    public static boolean isSorted(Comparable[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i].compareTo(a[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String[] a = In.readStrings
                ("/Users/raywang/IdeaProjects/Algorithms/testRaw/chap1" +
                        "/largeUF.txt");
        long t1 = System.currentTimeMillis();
//        Selection.sortTD(a);
//        InsertionSort.sortTD(a);
//        InsertionSort.sort(a);
//        ShellSort.sort(a);
//        MergeSort.sortTD(a);
//        MergeSort.sortBU(a);
//        QuickSort.sort(a);
        QuickSort.sort3way(a);
        assert isSorted(a);
        long t2 = System.currentTimeMillis();
        Utils.show(a);
        StdOut.printf("%d ms passed\n", t2 - t1);
    }
}
