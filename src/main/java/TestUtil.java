import java.util.Arrays;
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

    static <T> void test(T actual, T expected) {
        if (Objects.equals(actual, expected)) {
            System.out.println("Pass");
        } else {
            System.out.println("Actual: " + actual + ", expected: " + expected);
        }
    }

    static <T> void test(T[] actual, T[] expected) {
        if (Arrays.equals(actual, expected)) {
            System.out.println("Pass");
        } else {
            System.out.println("Actual: " + actual + ", expected: " + expected);
        }
    }

}
