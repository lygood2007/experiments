/**
 * % echo A B C D E F | java Subset 3
 * % E
 * % A
 * % F
 * % echo A B C D E F | java Subset 3
 * % A
 * % D
 * % B
 *  
 * @author irpagnossin
 * @email ivan.pagnossin@gmail.com
 * @version: 2013.02.14
 */
public class Subset {

    /**
     * Reads N strings from standard input and prints k of them, in random order.
     * @param args k Number of items to print (k <= N)
     */
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }

        for (int i = 1; i <= k; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}
