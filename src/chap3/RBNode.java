package chap3;

/**
 * Created by raywang on 2018/1/11.
 */
public class RBNode<Key, Value> {
    public static boolean RED = true;
    public static boolean BLACK = false;
    public Key key;
    public Value val;
    public boolean color;
    public RBNode<Key, Value> left, right;
    public int N; //num of subtree todo, for balance use

    RBNode(Key k, Value v) {
        this.key = k;
        this.val = v;
    }

    RBNode(Key k, Value v, RBNode<Key, Value> left, RBNode<Key, Value> right) {
        this.key = k;
        this.val = v;
        this.left = left;
        this.right = right;
    }

    public String toString() {
        return "[" + this.key.toString() + ":" + this.val.toString() + "]";
    }

    public boolean isRed() {
        return this.color == RED;
    }

    public boolean isBlack() {
        return this.color == BLACK;
    }

}
