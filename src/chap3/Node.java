package chap3;

/**
 * Created by raywang on 2018/1/3.
 */
public class Node<Key, Value> {
    public Key key;
    public Value val;
    public Node<Key, Value> left, right;
    public int N; //num of subtree todo, for balance use

    Node(Key k, Value v) {
        this.key = k;
        this.val = v;
    }

    Node(Key k, Value v, Node<Key, Value> left, Node<Key, Value> right) {
        this.key = k;
        this.val = v;
        this.left = left;
        this.right = right;
    }

    public String toString() {
        return "[" + this.key.toString() + ":" + this.val.toString() + "]";
    }
}
