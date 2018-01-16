package chap3;

import edu.princeton.cs.algs4.StdOut;

import java.util.*;

/**
 * Created by raywang on 2018/1/3.
 */
public class BST<Key extends Comparable<Key>, Value> {
    private Node<Key, Value> root;

    public Value get(Key k) {
        return getRec(root, k);
    }

    // recursive get
    public Value getRec(Node<Key, Value> root, Key k) {
        if (root == null) {
            return null;
        }
        int res = k.compareTo(root.key);
        if (res == 0) {
            return root.val;
        } else if (res > 0) {
            return getRec(root.right, k);
        } else {
            return getRec(root.left, k);
        }
    }

    private Node<Key, Value> getNodeItr(Key k) {
        Node<Key, Value> r = this.root;
        while (r != null) {
            int res = k.compareTo(root.key);
            if (res == 0) {
                break;
            } else if (res > 0) {
                r = r.right;
            } else {
                r = r.left;
            }
        }
        return r;
    }

    // iterative get
    public Value getItr(Key k) {
        Node<Key, Value> r = getNodeItr(k);
        return r == null ? null : r.val;
    }

    public void put(Key k, Value v) {
        Node<Key, Value> r = this.root, l; // rk is current, lk is last
        if (this.root == null) {
            this.root = new Node<>(k, v);
            return;
        }
        int res;
        while (true) {
            res = k.compareTo(r.key);
            if (res == 0) {
                r.val = v;
                return;
            } else if (res > 0) {
                if (r.right != null) {
                    r = r.right;
                } else {
                    r.right = new Node<>(k, v);
                    return;
                }
            } else {
                if (r.left != null) {
                    r = r.left;
                } else {
                    r.left = new Node<>(k, v);
                    return;
                }
            }
        }
    }

    // find the max node in root's subtree
    private Node<Key, Value> max(Node<Key, Value> root) {
        if (root == null) {
            return null;
        }

        while (root.right != null) {
            root = root.right;
        }
        return root;
    }

    // find the min node in root's subtree
    private Node<Key, Value> min(Node<Key, Value> root) {
        if (root == null) {
            return null;
        }
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    /**
     * @param l,      parent node
     * @param isLeft, isLeftChild?
     * @param n,      new node to update
     */
    private void update(Node<Key, Value> l, boolean isLeft, Node<Key, Value> n) {
        if (l == null) {
            this.root = n;
        } else if (isLeft) {
            l.left = n;
        } else {
            l.right = n;
        }
    }

    // delete max node for node rk, recursively
    private Node<Key, Value> deleteMax(Node<Key, Value> r) {
        if (r == null) {
            return null;
        }
        if (r.right == null) {
            return r.left;
        }
        r.right = deleteMax(r.right);
        return r;
    }

    // delete node with given key k
    public void delete(Key k) {
        // find the node with key k
        Node<Key, Value> r = this.root, l = null; // l is the parent of r
        boolean isLeft = false;
        while (r != null) {
            int res = k.compareTo(r.key);
            if (res == 0) {
                break;
            } else if (res > 0) {
                l = r;
                isLeft = false;
                r = r.right;
            } else {
                l = r;
                isLeft = true;
                r = r.left;
            }
        }

        if (r == null) {
            return;
        }

        if (r.left == null) {
            update(l, isLeft, r.right);
        } else if (r.right == null) {
            update(l, isLeft, r.left);
        } else { // rk has two children
            Node<Key, Value> t = max(r.left); // find the max of the left sub
            t.left = deleteMax(r.left);
            t.right = r.right;
            update(l, isLeft, t);
        }
    }


    public Key min() {
        Node<Key, Value> r = this.root;
        while (r != null) {
            if (r.left != null) {
                r = r.left;
            } else {
                break;
            }
        }
        return r == null ? null : r.key;
    }

    public Key max() {
        Node<Key, Value> r = this.root;
        while (r != null) {
            if (r.right != null) {
                r = r.right;
            } else {
                break;
            }
        }
        return r == null ? null : r.key;
    }

    public int size(Node<Key, Value> node) {
        if (node == null) {
            return 0;
        }
        return node.N;
    }

    /**
     * return number of nodes less than Key k
     *
     * @param k
     * @return
     */
    public int rank(Key k) {
        return rank(this.root, k);
    }

    // helper method, rank with given root
    private int rank(Node<Key, Value> node, Key k) {
        if (node == null) {
            return 0;
        }
        int cmp = k.compareTo(node.key);
        if (cmp == 0) {
            return size(node.left);
        } else if (cmp < 0) {
            return rank(node.left, k);
        } else {
            return 1 + size(node.left) + rank(node.right, k);
        }
    }

    /**
     * return the key in rank rk(0 base)
     *
     * @param r
     * @return
     */
    public Key select(int r) {
        if (r < 0 || r > this.root.N) {
            return null;
        }
        return select(this.root, r);
    }

    private Key select(Node<Key, Value> node, int r) {
        if (node == null) {
            return null;
        }
        int n = size(node.left);
        if (r == n) {
            return node.key;
        } else if (r < n) {
            return select(node.left, r);
        } else {
            return select(node.right, r - n - 1);
        }
    }

    /**
     * the max key less than or equal to k
     *
     * @param k
     * @return
     */
    public Key floor(Key k) {
        return select(rank(k)); //todo, more efficient
    }

    /**
     * the min key greater than or equal to k
     *
     * @param k
     * @return
     */
    public Key ceiling(Key k) {
        if (this.root == null) {
            return null;
        }
        Node<Key, Value> t = this.root, last = null;
        while (t != null) {
            int cmp = k.compareTo(t.key);
            if (cmp == 0) {
                return t.key;
            } else if (cmp < 0) {
                last = t;
                t = t.left;
            } else {
                break;
            }
        }
        return last.key;
    }

    public Iterable<Key> Keys() {
        if (this.root == null) {
            return null;
        }
        ArrayList<Key> list = new ArrayList<Key>(this.root.N);
        iKeys(this.root, list);
        return list;
    }

    //helper method for add keys to list
    private void iKeys(Node<Key, Value> node, ArrayList<Key> list) {
        if (node == null) {
            return;
        }
        iKeys(node.left, list);
        list.add(node.key);
        iKeys(node.right, list);
    }

    public Iterable<Key> keys(Key io, Key hi) {
        if (io.compareTo(hi) > 0 || this.root == null) {
            return null;
        }
        ArrayList list = new ArrayList<Key>(this.root.N);
        iKeys(this.root, io, hi, list);
        return list;
    }

    private void iKeys(Node<Key, Value> node, Key io, Key hi,
                       ArrayList<Key> list) {
        if (node == null) {
            return;
        }
        if (node.key.compareTo(hi) > 0) {
            return;
        }
        iKeys(node.left, io, hi, list);
        if (node.key.compareTo(io) >= 0) {
            list.add(node.key);
        }
        iKeys(node.right, io, hi, list);
    }

    public void printTree() {
        if (this.root == null) {
            StdOut.println(null);
            return;
        }

        // put all nodes in print nodes, bsf level order, set left, right,
        // parent, x, y, r
        LinkedList<ArrayList<PrintNode>> listArr = new LinkedList<>();
        Queue<PrintNode> workList = new LinkedList<>();

        PrintNode rootPN = new PrintNode(root);
        workList.add(rootPN);
        ArrayList<PrintNode> rl = new ArrayList<>();
        rl.add(rootPN);
        listArr.add(rl);

        int current = 1, next = 0, y = 1;
        while (!workList.isEmpty()) {
            next = 0;
            ArrayList<PrintNode> list = new ArrayList<>();
            for (; current > 0; current--) {
                PrintNode<Key, Value> parent = workList.remove();
                Node<Key, Value> node = parent.node;
                if (node.left != null) {
                    PrintNode<Key, Value> t = new PrintNode<>(node.left);
                    list.add(t);
                    t.parent = parent;
                    parent.left = t;
//                    parent.lk = parent.x - 1;
                    t.y = y;
                    t.r = -t.wid;
                    t.x = parent.x + t.r;

                    workList.add(parent.left);
                    next++;
                }
                if (node.right != null) {
                    PrintNode<Key, Value> t = new PrintNode<>(node.right);
                    list.add(t);
                    t.parent = parent;
                    parent.right = t;
//                    parent.rk = parent.wid + parent.x;
                    t.y = y;
                    t.r = parent.wid;
                    t.x = parent.x + t.r;

                    workList.add(parent.right);
                    next++;
                }
            }
            if (!list.isEmpty()) {
                listArr.add(list);
            }
            current = next;
            y++;
        }

        //find the left most x, adjustD pos
        int lm = 0;
        boolean conflict = false;
        for (ArrayList<PrintNode> list : listArr) {
            PrintNode pre = null;
            for (PrintNode node : list) {
                if (pre == null) {
                    if (lm > node.x) {
                        lm = node.x;
                    }
                } else {
                    int rm = pre.rightMost();
                    if (node.x < rm + 2) {
                        PrintNode lca = PrintNode.LCA(pre, node);
                        lca.right.r += rm + 2 - node.x;
                        lca.right.updateX();
                        conflict = true;
                    }
                }
                pre = node;
            }
        }

        // check
        while (conflict) {
            conflict = false;
            for (ArrayList<PrintNode> list : listArr) {
                PrintNode pre = null;
                for (PrintNode node : list) {
                    if (pre != null) {
                        int rm = pre.rightMost();
                        if (node.x < rm + 2) {
                            PrintNode lca = PrintNode.LCA(pre, node);
                            lca.right.r += rm + 2 - node.x;
                            lca.right.updateX();
                            conflict = true;
                        }
                    }
                    pre = node;
                }
            }
        }

        //finally, turn all relatives to absolutes
        rootPN.x = -lm;
        rootPN.trim();

        // print nodes
        for (List<PrintNode> list : listArr) {
            PrintNode.printNodesInline(list);
        }
    }


    public void printInOrder() {
        printInOrder(this.root);
        StdOut.println();
    }

    private void printInOrder(Node<Key, Value> node) {
        if (node == null) {
            return;
        }
        printInOrder(node.left);
        StdOut.printf("%s ", node);
        printInOrder(node.right);
    }

    // unit tests
    public static String[] sortedRaw(String[] raw) {
        String[] sRaw = Arrays.copyOf(raw, raw.length);
        Arrays.sort(sRaw);
        return sRaw;
    }

    /**
     * input a sorted array, return the dedup array
     *
     * @param raw
     * @return
     */
    public static String[] dedup(String[] raw) {
        String[] dedup = Arrays.copyOf(raw, raw.length);
        int j = 0;
        for (int i = 0; i < dedup.length; i++) {
            if (dedup[i] == dedup[j]) {
                continue;
            }
            dedup[j] = dedup[i];
        }
        for (; j < dedup.length; j++) {
            dedup[j] = null;
        }
        return dedup;
    }


    public static void testPutGetDelete() {
        String[] str = Test.mediumRaw();
        String[] vs = Test.mediumRaw(); // key and value should be the same
        int l = str.length;
        BST<String, String> bst = new BST<>();

        // put all keys with the same value
        for (int i = 0; i < l; i++) {
            bst.put(str[i], vs[i]);
        }
        bst.printInOrder();
        for (int i = 0; i < l; i++) {
            String k = str[i];
            String want = vs[i];
            String v = bst.get(k);
            assert (v.equals(want));
        }

        StdOut.println();

        bst.printTree();

        // put all keys to 'A'
        String a = "A";
        for (int i = 0; i < l; i++) {
            bst.put(str[i], a);
        }
        bst.printInOrder();
        for (int i = 0; i < l; i++) {
            String k = str[i];
            String v = bst.get(k);
            assert (v.equals(a));
        }

        // delete certain keys
        Random rand = new Random(str.length * 10086);
        for (int i = 0; i < 5; i++) {
            int rd = rand.nextInt() % str.length;
            if (rd < 0) {
                rd = -rd;
            }
            String k = str[rd];
            bst.delete(k);
            assert bst.get(k) == null;
            StdOut.printf("delete %s\n", k);
            bst.printInOrder();
            bst.printTree();
        }
    }

    public static void testRankSelect() {
        String[] str = Test.smallRaw();

    }

    public static void main(String[] args) {
        // test get, put, delete
        testPutGetDelete();
        testRankSelect();
    }
}
