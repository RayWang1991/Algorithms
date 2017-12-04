package chap2;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by raywang on 2017/12/1.
 */

/**
 * Max priority queue
 * The heap data always structure holds the invariant that every father is no
 * less than its children
 */
public class PriorityQueue {
    private int n;

    private Comparable[] a;

    // TODO,flexible array length
    private void setN(int N) {
        this.n = N;
        int len = this.a.length;
        if (N >= len) {
            Comparable[] na = new Comparable[N * 2];
            for (int i = 0; i < this.n; i++) {
                na[i] = this.a[i];
            }
            this.a = na;
        } else if (N > 0 && N < len / 4) {
            Comparable[] na = new Comparable[Math.max(len / 2, 4)]; // no
            // less than 4
            for (int i = 0; i < this.n; i++) {
                na[i] = this.a[i];
            }
            this.a = na;
        }
    }

    public PriorityQueue() {
        this.a = new Comparable[4];
        this.n = 0;
    }

    public PriorityQueue(int N) {
        this.a = new Comparable[2 * N];
        this.n = 0;
    }

    public PriorityQueue(Comparable[] array) {
        this.a = new Comparable[2 * array.length];
        this.n = array.length;
        for (Comparable e : array) {
            this.insert(e);
        }
    }

    /**
     * insert an element, logarithmic
     *
     * @param e the element to insert
     */
    public void insert(Comparable e) {
        setN(this.n + 1);
        a[n - 1] = e;
        swim(n - 1);
    }

    /**
     * delete and returns the max element, logarithmic
     *
     * @return
     */
    public Comparable deleteMax() {
        Comparable t = a[0];
        Utils.exch(a, 0, this.n - 1);
        setN(this.n - 1);
        sink(0);
        return t;
    }

    private int father(int k) {
        return (k - 1) / 2;
    }

    private int leftChild(int k) {
        return k * 2 + 1;
    }

    private int rightChild(int k) {
        return k * 2 + 2;
    }

    // TODO test
    private void swim(int k) {
        while (k > 0 && Utils.less(a, father(k), k)) {
            Utils.exch(a, father(k), k);
            k = father(k);
        }
    }

    private void sink0(int k) {
        while (leftChild(k) < this.n) {
            int t = leftChild(k);
            if (t + 1 < this.n && Utils.less(a, t, t + 1)) {
                t++;
            }
            if (Utils.less(a, k, t)) {
                Utils.exch(a, k, t);
                k = t;
            } else {
                break;
            }
        }
    }

    // TODO test

    private void sink(int k) {
        while (leftChild(k) < this.n) {
            if (rightChild(k) < this.n) { // having left, and right
                if (Utils.less(a, k, leftChild(k))) {
                    int t = leftChild(k);
                    // find the bigger one
                    if (Utils.less(a, t, rightChild(k))) {
                        t = rightChild(k);
                        Utils.exch(a, k, t);
                    } else {
                        Utils.exch(a, k, t);
                    }
                    k = t;
                } else if (Utils.less(a, k, rightChild(k))) {
                    Utils.exch(a, k, rightChild(k));
                    k = rightChild(k);
                } else {
                    break;
                }
            } else { // only left
                if (Utils.less(a, k, leftChild(k))) {
                    Utils.exch(a, k, leftChild(k));
                    k = leftChild(k);
                } else {
                    break;
                }
            }
        }
    }

    // out put the nin 10 elements
    public static void main(String[] args) {
        String[] a = In.readStrings
                ("/Users/raywang/IdeaProjects/Algorithms/testRaw/chap1" +
                        "/tinyUF.txt");
        int Vol = 10, i;
        PriorityQueue pq = new PriorityQueue(Vol + 1);
        long t1 = System.currentTimeMillis();
        for (i = 0; i < Vol; i++) {
            pq.insert(a[i]);
        }
        for (; i < a.length; i++) {
            pq.insert(a[i]);
            StdOut.printf("%s\n", pq.deleteMax());
        }
        long t2 = System.currentTimeMillis();
//        Utils.show(a);
        StdOut.printf("%d ms passed\n", t2 - t1);
    }
}
