package chap2;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by raywang on 2017/12/5.
 */
public class IndexMinPQ<Key extends Comparable<Key>> {
    private Key[] keys; // raw array holds keys
    private int[] pq; // priority to key index
    private int[] qp; // reverse index, key ind to priority, qp[pq[i]] = i,
    private int n; // current size
    // vice versa

    public IndexMinPQ(int N) {
        pq = new int[N];
        qp = new int[N];
        keys = (Key[]) new Comparable[N];
        for (int i = 0; i < N; i++) {
            qp[i] = -1; // for has i use
        }
    }

    public int size() {
        return n;
    }

    private void exch(int i, int j) {
        // exchange pq index
        int t = pq[i];
        int k = pq[j];
        pq[i] = pq[j];
        pq[j] = t;

        // exchange qp index
        i = qp[t];
        qp[t] = qp[k];
        qp[k] = i;
    }

    private boolean less(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) < 0;
    }

    private static int father(int k) {
        return (k - 1) / 2;
    }

    private static int left(int k) {
        return k * 2 + 1;
    }

    /**
     * swim at key index k
     *
     * @param k
     */
    private void swim(int k) {
        while (k >= 0 && less(k, father(k))) {
            exch(k, father(k));
            k = father(k);
        }
    }

    /**
     * sink at key index k
     *
     * @param k
     */
    private void sink(int k) {
        while (left(k) < n) {
            int j = left(k);
            if (j + 1 < n && less(j + 1, j)) {
                j++;
            }
            if (less(j, k)) {
                exch(j, k);
                k = j;
            } else {
                break;
            }
        }
    }

    /**
     * insert a key to index k
     *
     * @param k
     * @param key
     */
    public void insert(int k, Key key) {
        if (n >= keys.length) {
            // TODO
            System.out.println("Out of size");
        }
        keys[k] = key;
        pq[n] = k;
        qp[k] = n;
        n++;
        swim(n - 1);
    }

    public void change(int k, Key key) {
        keys[k] = key;
        exch(qp[k], n - 1);
        swim(n - 1);
    }

    public boolean contains(int k) {
        return qp[k] >= 0;
    }

    public Key min() {
        return keys[minIndex()];
    }

    public int minIndex() {
        return pq[0];
    }

    public int delMin() {
        int t = minIndex();
        exch(0, --n);
        sink(0);
        return t;
    }

    public boolean isEmpty() {
        return this.n == 0;
    }

    public static void main(String[] args) {
        String[] a = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
        IndexMinPQ<String> pq = new IndexMinPQ<>(10);
        for (int i = 0; i < a.length; i++) {
            pq.insert(a.length - 1 - i, a[i]);
        }
        while (!pq.isEmpty()) {
            String t = pq.min();
            pq.delMin();
            StdOut.println(t);
        }
    }
}
