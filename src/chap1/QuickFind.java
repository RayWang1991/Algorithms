package chap1;

/**
 * Created by raywang on 2017/11/22.
 */

/**
 * Quick Find takes one array access for find
 * O(N) for Union
 */
public class QuickFind implements UF {
    private int n;
    private int[] s;

    public QuickFind(int N) {
        this.setN(N);
    }

    public QuickFind() {
    }

    public void setN(int n) {
        this.n = n;
        this.s = new int[n];
        for (int i = 0; i < n; i++) {
            this.s[i] = i;
        }
    }

    public void union(int p, int q) {
        // replace all component(i) in which s[i] == s[p]
        if (s[p] == s[q]) {
            return;
        }
        int r = s[p], t = s[q];
        for (int i = 0; i < this.s.length; i++) {
            if (r == s[i]) {
                s[i] = t;
            }
        }
        this.n--;
    }

    public int find(int p) {
        return s[p];
    }


    public boolean connected(int p, int q) {
        return s[p] == s[q];
    }


    public int count() {
        return this.n;
    }
}
