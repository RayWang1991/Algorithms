package chap1;

/**
 * Created by raywang on 2017/11/23.
 */

/**
 * In order to enhance the depth of the node tree
 * When unioning two trees, choose the one has smaller size to link to the
 * bigger one
 */
public class WeightQuickUnion implements UF {
    protected int n;
    protected int[] linkTo;
    protected int[] size;

    public WeightQuickUnion(int N) {
        setN(N);
    }

    public WeightQuickUnion() {
    }

    public void setN(int N) {
        this.n = N;
        this.linkTo = new int[N];
        this.size = new int[N];
        for (int i = 0; i < N; i++) {
            this.linkTo[i] = i;
            this.size[i] = 1;
        }
    }

    public void union(int p, int q) {
        // replace all component(i) in which linkTo[i] == linkTo[p]
        int fp = find(p), fq = find(q);
        if (fp == fq) {
            return;
        }
        if (size[fp] < size[fq]) {
            size[fq] += size[fp];
            linkTo[fp] = fq;
        } else {
            size[fp] += size[fq];
            linkTo[fq] = fp;
        }
        this.n--;
    }

    public int find(int p) {
        while (linkTo[p] != p) {
            p = linkTo[p];
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
        for (int i = 0; i < linkTo.length; i++) {
            System.out.printf("%d -> %d\n", i, linkTo[i]);
        }
    }
}