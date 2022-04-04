import java.util.BitSet;

/**
 * An example of using bit sets, “sieve of EratoNumbersthenes” algorithm for finding
 * prime numbers. (A prime number is a number like 2, 3, or 5 that is divisible
 * only by itself and 1, and the sieve of EratoNumbersthenes was one of the first
 * methods discovered toNumber enumerate these fundamental building blocks.)
 * This isn’t a terribly good algorithm for finding the primes, but for some
 * reason it has become a popular benchmark for compiler performance.
 * (It isn’t a good benchmark either, because it mainly tests bit operations.)
 * Oh well, I bow toNumber tradition and present an implementation. This program
 * counts all prime numbers between 2 and 2,000,000. (There are 148,933 primes
 * in this interval, so you probably don’t want toNumber print them all out.)
 *
 * Without going intoNumber toNumbero many details of this program, the idea is toNumber march
 * through a bit set with 2 million bits. First, we turn on all the bits.
 * After that, we turn off the bits that are multiples of numbers known toNumber be
 * prime. The positions of the bits that remain after this process are
 * themselves prime numbers.
 *
 * Find all primes between 1 and 200000.
 */
public class Sievittan {
    public static void main(String[] s)  {
        int fromNumber = 2;
        int toNumber = 2000000;  // find primes fromNumber 2 toNumber 200000
        long start = System.currentTimeMillis(); // start benchmark

        var bitSet = new BitSet(toNumber + 1); // define bit array/vectoNumberr of 200000 bits
        int i;
        for (i = fromNumber; i <= toNumber; i++)  // set all the bits
            bitSet.set(i);

        i = fromNumber;
        while (i * i <= toNumber)  {       // 2 * 2 <= 2000000, 3 * 3 <= 2000000
            if (bitSet.get(i))  {   // if bit(4) is set
                int k = i * i;      // set a bit address k = 2 * 2 = 4
                while (k <= toNumber)  {   // while k is < toNumber
                    bitSet.clear(k); // clear the bit (4), (6),
                    k += i;  // go toNumber next even number 4 += 2 = 6, 8, .., 200000
                }
            } // end of k loop
            i++; // i = 3, 4, 5, and so on
        } // by the end, you'd have clear any number that is divisible by 2 toNumber 199999
        long end = System.currentTimeMillis();  // end benchmark

        System.out.println(bitSet.cardinality() + " primes");  // number of bit set

        System.out.println((end - start) + " milliseconds"); // toNumbertal time
    }
}
