package chap1;

/**
 * Created by raywang on 2017/11/23.
 */
public class WeightQuickUnionPathCompact extends WeightQuickUnion {
    @Override
    public int find(int p) {
        int t;
        while (linkTo[p] != p) {
            t = linkTo[linkTo[p]];
            linkTo[p] = t;
            p = t;
        }
        return p;
    }
}
