package chap3;

import edu.princeton.cs.algs4.StdOut;

/**
 * Created by raywang on 2018/1/11.
 */
public class RedBlack<Key extends Comparable<Key>, Value> {
    private RBNode<Key, Value> root;

    public Value get(Key k) {
        return getRec(root, k);
    }

    // recursive get
    public Value getRec(RBNode<Key, Value> root, Key k) {
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

    private RBNode<Key, Value> getNodeItr(Key k) {
        RBNode<Key, Value> r = this.root;
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

    public void put(Key k, Value v) {
        RBNode<Key, Value> r = putRec(this.root, k, v);
        if (r != null) {
            this.root = r;
        }
    }

    /**
     * @param node must not be null
     * @param k
     * @param v
     * @return if null, no node adjust, if is red, need parent change link,
     * otherwise need parent insert this node
     */

    private RBNode<Key, Value> putRec(RBNode<Key, Value> node, Key k, Value v) {
        if (node == null) {
            return new RBNode<Key, Value>(k, v); // black, for parent to insert
        }
        int cmp = k.compareTo(node.key);
        if (cmp == 0) {
            node.val = v;
            return null;
        }
        RBNode<Key, Value> r = null;
        if (node.isBlack()) {
            if (cmp < 0) {
                r = putRec(node.left, k, v);
                if (r == null) {
                    return null;
                } else if (r.isRed()) { //need update link
                    node.left = r;
                    return null; //do not need update
                } else { //need insert, r's key must < node.key, node is right
                    node.color = RBNode.RED;
                    node.left = r;
                    return node; //update
                }
            } else {
                r = putRec(node.right, k, v);
                if (r == null) {
                    return null;
                } else if (r.isRed()) {
                    node.right = r;
                    return null;//do not need update
                } else {
                    r.color = RBNode.RED;
                    node.right = r.left;
                    r.left = node;
                    return r; //need update link
                }
            }
        } else {
            if (cmp > 0) {
                r = putRec(node.right, k, v);
                if (r == null) {
                    return null;
                } else if (r.isRed()) {
                    node.right = r;
                    return null;
                } else {
                    node.right = r;
                    node.color = RBNode.BLACK;
                    return node;
                }
            } else {
                cmp = k.compareTo(node.left.key);
                if (cmp == 0) {
                    node.left.val = v;
                    return null;
                } else if (cmp < 0) {
                    r = putRec(node.left.left, k, v);
                    if (r == null) {
                        return null;
                    } else if (r.isRed()) {
                        node.left.left = r;
                        return null;
                    } else {
                        node.color = RBNode.BLACK;
                        node.left.left = r;
                        r = node.left;
                        node.left = r.right;
                        r.right = node;
                        return r;
                    }
                } else {
                    r = putRec(node.left.right, k, v);
                    if (r == null) {
                        return null;
                    } else if (r.isRed()) {
                        node.left.right = r;
                        return null;
                    } else {
                        node.color = RBNode.BLACK;
                        RBNode<Key, Value> t = node.left;
                        t.right = r.left;
                        node.left = r.right;
                        r.left = t;
                        r.right = node;
                        return r;
                    }
                }
            }
        }
    }

    public static void testPut() {
        String[] str = Test.smallRaw();
        String[] vs = Test.smallRaw(); // key and value should be the same
        int l = str.length;
        RedBlack<String, String> rb = new RedBlack<>();

        // put all keys with the same value
        for (int i = 0; i < l; i++) {
            rb.put(str[i], vs[i]);
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        RedBlack.testPut();
    }
}
