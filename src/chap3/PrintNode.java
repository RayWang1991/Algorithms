package chap3;

import edu.princeton.cs.algs4.StdOut;

import java.util.List;

/**
 * Created by raywang on 2018/1/9.
 */
public class PrintNode<Key, Value> {
    public int x, y, r, lk, rk, wid; // x is the leftmost point of node
    public Node<Key, Value> node;
    public PrintNode<Key, Value> left, right, parent;

    public int rightMost() {
        return x + wid - 1;
    }

    public int leftMost() {
        return x;
    }

    public int centerX() {
        return (x + x + wid - 1) / 2;
    }

    PrintNode(Node<Key, Value> node) {
        String s = node.toString();
        this.wid = s.length();
        this.node = node;
    }

    PrintNode(Node<Key, Value> node, int x, int y) {
        this(node);
        this.x = x;
        this.y = y;
    }

    PrintNode(Node<Key, Value> node, int x, int y, int l, int r) {
        this(node, x, y);
        this.lk = l;
        this.rk = r;
    }

    public static void printNodesInline(List<PrintNode> list) {
        int i = 0;
        // print nodes
        for (PrintNode node : list) {
            int lm = node.leftMost();
            while (i < lm) {
                StdOut.print(' ');
                i++;
            }
            StdOut.printf("%s", node.node);
            i += node.wid;
        }
        StdOut.println();

        // print left and right links
        i = 0;
        for (PrintNode node : list) {
            if (node.lk > 0 && node.rk > 0) {
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

            } else if (node.lk > 0) {
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

    public void updateX() {
        this.x = this.parent.x + this.r;
        if (this.left != null) {
            this.left.updateX();
        }
        if (this.right != null) {
            this.right.updateX();
        }
    }

    public static PrintNode LCA(PrintNode a, PrintNode b) {
        if (a == null || b == null) {
            return null;
        }
        if (a == b) {
            return a;
        }
        PrintNode t;
        if (a.y < b.y) {
            t = a;
            a = b;
            b = t;
        }

        t = a;
        while (t.y > b.y) {
            t = t.parent;
        }
        while (t != b) {
            t = t.parent;
            b = b.parent;
        }
        return t;
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
