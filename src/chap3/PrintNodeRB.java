package chap3;

import edu.princeton.cs.algs4.StdOut;

import java.util.List;

/**
 * Created by raywang on 2018/1/12.
 */
public class PrintNodeRB {
    public int x, y, r, lk, rk, wid; // x is the leftmost point of node

    public RBNode node;

    public PrintNodeRB left, right, parent;

    public int rightMost() {
        return x + wid - 1;
    }

    public int leftMost() {
        return x;
    }

    public int centerX() {
        return (x + x + wid - 1) / 2;
    }

    PrintNodeRB(RBNode node) {
        this.node = node;
        String s = node.toString();
        this.wid = s.length();
    }

    public static void printNodesInline(List<PrintNodeRB> list) {
        int i = 0;
        // print nodes
        for (PrintNodeRB node : list) {
            int lm = node.leftMost();
            if (node.node.isBlack()) {
                while (i < lm) {
                    StdOut.print(' ');
                    i++;
                }
            } else {
                StdOut.print('<');
                i++;
                while (i < lm) {
                    StdOut.print('~');
                    i++;
                }
            }
            StdOut.printf("%s", node.node);
            i += node.wid;
        }
        StdOut.println();

        // print left and right links
        i = 0;
        for (PrintNodeRB node : list) {
            if (node.lk > 0 && node.rk > 0 && node.node.isBlack()) {
                // blank
                while (i < node.lk) {
                    StdOut.print(' ');
                    i++;
                }
                StdOut.print('/');
                i++;
                // '-'
                int lm = node.leftMost() - 1;
                while (i < lm) {
                    StdOut.print('-');
                    i++;
                }
                // blank
                int rm = node.rightMost();
                while (i <= rm) {
                    StdOut.print(' ');
                    i++;
                }
                // '-'
                while (i < node.rk) {
                    StdOut.print('-');
                    i++;
                }
                // '\'
                StdOut.print('\\');

            } else if (node.lk > 0 && node.node.isBlack()) {
                // blank
                while (i < node.lk) {
                    StdOut.print(' ');
                    i++;
                }
                StdOut.print('/');
                i++;
                // '-'
                int lm = node.leftMost() - 1;
                while (i < lm) {
                    StdOut.print('-');
                    i++;
                }
            } else if (node.rk > 0) {
                // blank
                int rm = node.rightMost();
                while (i <= rm) {
                    StdOut.print(' ');
                    i++;
                }
                // '-'
                while (i < node.rk) {
                    StdOut.print('-');
                    i++;
                }
                // '\'
                StdOut.print('\\');
            }
        }
        StdOut.println();
    }


    public static PrintNodeRB LCA(PrintNodeRB a, PrintNodeRB b) {
        if (a == null || b == null) {
            return null;
        }
        if (a == b) {
            return a;
        }
        PrintNodeRB t;
        if (a.y < b.y) {
            t = a;
            a = b;
            b = t;
        }

        t = a;
        while (t.y > b.y) {
            t = t.parent;
        }

        while (t != b) { // same y for red node and its left child
            if (t.left == b && t.node.isRed()) {
                break;
            } else if (b.left == t && b.node.isRed()) {
                t = b;
                break;
            } else {
                t = t.parent;
                b = b.parent;
                // find the one with bigger y(lower level)
                while (t.y > b.y) {
                    t = t.parent;
                }
                while (t.y < b.y) {
                    b = b.parent;
                }
            }
        }
        return t;
    }

    public void updateX() {
        this.x = this.parent.x + this.r;
        if (this.left != null) {
            this.left.updateX();
        }
        if (this.right != null) {
            this.right.updateX();
        }
    }

    public void trim() {
        if (this.left != null) {
            this.left.x = this.left.r + this.x;
            this.lk = this.left.x + this.left.wid - 1;
            this.left.trim();
        }
        if (this.right != null) {
            this.right.x = this.right.r + this.x;
            this.rk = this.right.x;
            this.right.trim();
        }
    }
}
