package chap3;

/**
 * Created by raywang on 2018/1/3.
 */
public class Test {
    public static String[] _toOneRaw(String str) {
        String[] res = new String[str.length()];
        for (int i = 0; i < res.length; i++) {
            res[i] = str.substring(i, i + 1);
        }
        return res;
    }

    public static String[] smallRaw() {
        return _toOneRaw("EXAMPLETEST");
    }

    public static String[] searchExampleRaw() {
        return _toOneRaw("SEARCHEXAMPLE");
    }

    public static String[] mediumRaw() {
        return new String[]{"this", "is", "a", "medium", "text", "just", "to",
                "test", "the", "correctness", "of", "the", "BST", "algorithm",
                "in", "book", "however", "this", "test", "is", "not", "enough",
                "yet"};
    }

    public void testOrderSample() {
    }

    public void testOrderPerformance() {
    }

    public void main(String[] args) {
    }
}
