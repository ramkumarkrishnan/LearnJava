/**
 * Demo Map collection HashMap
 */

import com.ramkrish.corejava.PackageEmployee;
import java.util.*;

public class Maputharu {
    public static void main(String[] args)  {
        var staff = new HashMap<String, PackageEmployee>();
        staff.put("123-45-6789", new PackageEmployee("Carl Cracker", 75000, 1987, 12, 15, 1960, 3, 22));
        staff.put("567-89-0123", new PackageEmployee("Harry Hacker", 50000, 1989, 10, 1, 1965, 4, 15));
        staff.put("901-23-4567", new PackageEmployee("Tony Tester", 40000, 1990, 3, 15, 1972, 5, 20));
        staff.put("345-67-8901", new PackageEmployee("Bob Baker", 40000, 1993, 6, 25, 1975, 12, 11));

        // print all entries
        System.out.println(staff);

        // remove an entry
        staff.remove("567-24-2546");

        // replace an entry
        staff.put("789-01-2345", new PackageEmployee("Sad Sacker", 40000, 1993, 6, 25, 1975, 12, 11));

        // look up a value
        System.out.println(staff.get("123-45-6789"));

        // iterate through all entries
        staff.forEach((k, v) ->
                System.out.println("key=" + k + ", value=" + v));
    }
}
