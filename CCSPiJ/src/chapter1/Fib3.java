package chapter1;

import java.util.HashMap;
import java.util.Map;

public class Fib3 {

    // for storing the results of previous methods call (memoization)
    static Map<Integer, Integer> map = new HashMap<>(Map.of(0, 0, 1, 1));

    public static int fib3(int n) {
        if (!map.containsKey(n)) {
            map.put(n, fib3(n - 1) + fib3(n - 2));
        }

        return map.get(n);
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 20; i++) {
            System.out.print(fib3(i) + " ");
        }
        System.out.println();

        System.out.println(fib3(5));
        System.out.println(fib3(30));
    }
}
