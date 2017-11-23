package chap1;

/**
 * Created by raywang on 2017/11/22.
 */

interface UF {
    /**
     * add connection between p and q
     * assuming that p, q in [0,n)
     *
     * @param p
     * @param q
     */
    void union(int p, int q);

    void setN(int n);

    /**
     * component id for site p
     *
     * @return
     */
    int find(int p);

    /**
     * true if p and q are in the same components
     *
     * @param p
     * @param q
     * @return
     */
    boolean connected(int p, int q);

    /**
     * count of components
     *
     * @return
     */
    int count();
}

