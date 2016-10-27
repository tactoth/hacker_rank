import java.util.Objects;

/**
 * Created by liuwei on 10/26/16.
 */
public class TestUtil {
    static long start;

    public static void markStart() {
        start = System.currentTimeMillis();
    }

    public static void reportEnd(String label) {
        System.out.println(label + " => " + (System.currentTimeMillis() - start) + "ms");
    }

    static void test(Object actual, Object expected) {
        if (Objects.equals(actual, expected)) {
            System.out.println("Pass");
        } else {
            System.err.println("Actual: " + actual + ", expected: " + expected);
        }
    }

}
