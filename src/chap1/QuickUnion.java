package chap1;

/**
 * Created by raywang on 2017/11/22.
 */

/**
 * Use parent linked tree to represent a component
 * The Find() takes the depth of the tree's array access
 * The Union() and Connected() takes 2 * Find()
 */
public class QuickUnion implements UF {
    private int n;
    private int[] s;

    public QuickUnion(int N) {
        setN(N);
    }

    public QuickUnion() {
    }

    public void setN(int N) {
        this.n = N;
        this.s = new int[N];
        for (int i = 0; i < N; i++) {
            this.s[i] = i;
        }
    }

    public void union(int p, int q) {
        // replace all component(i) in which s[i] == s[p]
        int fp = find(p), fq = find(q);
        if (fp == fq) {
            return;
        }
        s[fq] = fp;
        this.n--;
    }

    public int find(int p) {
        while (s[p] != p) {
            p = s[p];
        }
        return p;
    }


    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }


    public int count() {
        return this.n;
    }

    public void print() {
        for (int i = 0; i < s.length; i++) {
            System.out.printf("%d -> %d\n", i, s[i]);
        }
    }
}
