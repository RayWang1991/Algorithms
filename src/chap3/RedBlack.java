package chap3;

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

    private boolean isRed(RBNode node) {
        return node != null && node.isRed();
    }

    /**
     * bottom up re balance the red black tree
     *
     * @param node root of the sub tree
     * @return
     */
    public RBNode reBalance(RBNode node) {
        if (isRed(node) && isRed(node.left)) {
            RBNode m = node.left;
            node.color = RBNode.BLACK;
            node.left = m.right;
            m.color = RBNode.BLACK;
            m.right = node;
            node = m;
        }
        return node;
    }

    //todo
    public void deleteMin() {
        if (this.root == null) {
            return;
        }
    }

    /**
     * delete min red black node in subtree
     *
     * @param node, the root of the subtree
     * @return the root of the subtree after deletion
     */
    public RBNode deleteMin(RBNode node) {
        if (node == null) {
            return null;
        }
        if (node.left == null) {
            return node.right;
        }
        // must have right node
        if (node.isBlack()) {
            // node.left is a 2-node
            if (node.right.isBlack()) { // p442, 1, 4
                RBNode r = node.right, l = node.left;
                node.color = RBNode.RED;
                node.right = r.left;
                r.color = RBNode.RED;
                r.left = node;
                node = r;
            } else { // p442, 2 , 3
                RBNode r = node.right, l = node.left, t = r.left;
                node.color = RBNode.RED;
                node.right = t.left;
                r.color = RBNode.BLACK;
                r.left = t.right;
                t.left = node;
                t.right = r;
                node = t; // for return
            }
        }
        node.left = deleteMin(node.left);
        if (node.left == null) {
            node.color = RBNode.BLACK;
        }
        node = reBalance(node);
        return node;
    }

    /**
     * print tree
     */
    public void printTree() {
        if (this.root == null) {
            StdOut.println(null);
            return;
        }

        // put all nodes in print nodes, bsf level order, set left, right,
        // parent, x, y, r
        LinkedList<ArrayList<PrintNodeRB>> listArr = new LinkedList<>();
        Queue<PrintNodeRB> workList = new LinkedList<>();
        ArrayList<PrintNodeRB> rl = new ArrayList<>();

        PrintNodeRB rootPN = new PrintNodeRB(root);
        int current = 1;
        if (root.isRed()) {
            PrintNodeRB rootL = new PrintNodeRB(root.left);
            //todo, dispose rootL
            rootPN.left = rootL;
            rootL.parent = rootPN;
            rootL.r = -2 - rootL.wid;
            rootL.x = rootPN.x + rootL.r;
            rl.add(rootL);
            workList.add(rootL);
            current++;
        }
        rl.add(rootPN);
        workList.add(rootPN);
        listArr.add(rl);

        int next = 0, y = 1;
        while (!workList.isEmpty()) {
            next = 0;
            ArrayList<PrintNodeRB> list = new ArrayList<>();
            for (; current > 0; current--) {
                PrintNodeRB parent = workList.remove();
                RBNode node = parent.node;
                if (node.left != null && node.isBlack()) {
                    if (node.left.isBlack()) {
                        PrintNodeRB t = new PrintNodeRB(node.left);
                        list.add(t);
                        t.parent = parent;
                        parent.left = t;
                        t.y = y;
                        t.r = -t.wid;
                        t.x = parent.x + t.r;
                        workList.add(parent.left);
                        next++;
                    } else {
                        PrintNodeRB t1 = new PrintNodeRB(node.left.left);
                        PrintNodeRB t = new PrintNodeRB(node.left);
                        list.add(t1);
                        list.add(t);
                        t1.parent = t;
                        t.parent = parent;
                        t.left = t1;
                        parent.left = t;
                        t.y = y;
                        t1.y = y;
                        t.r = -t.wid;
                        t1.r = -2 - t1.wid;
                        t.x = parent.x + t.r;
                        t1.x = t.x + t1.r;
                        workList.add(t1);
                        workList.add(t);
                        next += 2;
                    }
                }
                if (node.right != null && node.isBlack()) {
                    if (node.right.isBlack()) {
                        PrintNodeRB t = new PrintNodeRB(node.right);
                        list.add(t);
                        t.parent = parent;
                        parent.right = t;
                        t.y = y;
                        t.r = parent.wid;
                        t.x = parent.x + t.r;

                        workList.add(parent.right);
                        next++;
                    } else {
                        PrintNodeRB t1 = new PrintNodeRB(node.right.left);
                        PrintNodeRB t = new PrintNodeRB(node.right);
                        list.add(t1);
                        list.add(t);
                        t1.parent = t;
                        t.parent = parent;
                        t.left = t1;
                        parent.right = t;
                        t.y = y;
                        t1.y = y;
                        t.r = parent.wid;
                        t1.r = -2 - t1.wid;
                        t.x = parent.x + t.r;
                        t1.x = t.x + t1.r;

                        workList.add(t1);
                        workList.add(t);
                        next += 2;
                    }
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
        for (ArrayList<PrintNodeRB> list : listArr) {
            PrintNodeRB pre = null;
            for (PrintNodeRB node : list) {
                if (pre == null) {
                    if (lm > node.x) {
                        lm = node.x;
                    }
                } else {
                    int rm = pre.rightMost();
                    if (node.x < rm + 2) {
                        PrintNodeRB lca = PrintNodeRB.LCA(pre, node);
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
            for (ArrayList<PrintNodeRB> list : listArr) {
                PrintNodeRB pre = null;
                for (PrintNodeRB node : list) {
                    if (pre != null) {
                        int rm = pre.rightMost();
                        if (node.x < rm + 2) {
                            PrintNodeRB lca = PrintNodeRB.LCA(pre, node);
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
        for (List<PrintNodeRB> list : listArr) {
            PrintNodeRB.printNodesInline(list);
        }

    }

    public static void testPut() {
        String[] str = Test.smallRaw();
        String[] vs = Test.smallRaw(); // key and value should be the same
        int l = str.length;
        RedBlack<String, String> rb = new RedBlack<>();

        // put all keys with the same value
        for (int i = 0; i < l; i++) {
            StdOut.printf("Put %s,%s:\n", str[i], vs[i]);
            rb.put(str[i], vs[i]);
            rb.printTree();
            StdOut.println();
        }

        str = Test.searchExampleRaw();
        l = str.length;
        rb = new RedBlack<>();
        for (int i = 0; i < l; i++) {
            StdOut.printf("Put %s,%s:\n", str[i], str[i]);
            rb.put(str[i], str[i]);
            rb.printTree();
        }
    }

    public static void testDeleteMin() {
        String[] str = Test.smallRaw();
        String[] vs = Test.smallRaw(); // key and value should be the same
        int l = str.length;
        RedBlack<String, String> rb = new RedBlack<>();

        // put all keys with the same value
        for (int i = 0; i < l; i++) {
            StdOut.printf("Put %s,%s:\n", str[i], vs[i]);
            rb.put(str[i], vs[i]);
            rb.printTree();
        }
        StdOut.println();
        for (int i = 0; i < l; i++) {
            rb.root = rb.deleteMin(rb.root);
            StdOut.printf("DeleteMIN \n");
            rb.printTree();
        }
    }

    public static void main(String[] args) {
//        RedBlack.testPut();
        RedBlack.testDeleteMin();
    }
}
