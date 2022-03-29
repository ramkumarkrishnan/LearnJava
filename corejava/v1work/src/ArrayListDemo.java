/*
 * Demo ArrayList class
 */
import java.util.*;
import com.ramkrish.corejava.*;

public class ArrayListDemo {
    public static void main(String[] args) {
        var arr = new ArrayList<PackageEmployee>();
        arr.ensureCapacity(10);
        arr.add(new PackageEmployee("Stanley Blacker", 50000, 1989, 10, 1, 1966, 1, 2));
        arr.add(new PackageEmployee("Steve Slacker", 40000, 1990, 3, 15, 1960, 3, 4));
        System.out.println("ArrayList contains " + arr.size() + " elements");
        // the next line will runtime fail because you have not filled up the array up to 2 elements, index starts with 0, and
        // set only sets an existing array element - does not add a new one.
        // arr.set(3, new PackageEmployee("Mark Marker", 60000, 1990, 3, 15, 1964, 3, 14));
        arr.set(1, new PackageEmployee("Mark Marker", 60000, 1990, 3, 15, 1964, 3, 14));
        System.out.println("Array size " + arr.size() + " array: " + arr.toString());
        arr.trimToSize(); // trimToSize is an internal op - the above call shows array size 2
        System.out.println("Array size after trim " + arr.size() + " array: " + arr.toString());
        // PackageEmployee e = arr.get(4);

        var a = new PackageEmployee[arr.size()];
        arr.toArray(a);

        int n = arr.size() / 2;
        arr.add(n, new PackageEmployee("New Yorker", 60000, 1990, 3, 15, 1964, 3, 14));
        System.out.println(arr.toString());
        PackageEmployee r = arr.remove(2);
        System.out.println(arr.toString());

        // AUTOBOX EXAMPLE
        // ArrayList needs Objects in type specification - not scalars
        var arroyo = new ArrayList<Integer>();
        arroyo.add(Integer.valueOf(3));

        System.out.println(arroyo.toString());
    }
}
