package chapter1;

public class Fib4 {

    public static int fib3(int n) {
       int last = 0;
       int next = 1;

       for (int i = 0; i < n; i++) {
           int oldLast = last;
           last = next;
           next = oldLast + next;
       }

       return last;
    }

    public static void main(String[] args) {
        System.out.println(fib3(5));
        //System.out.println(fib3(40));
    }
}
