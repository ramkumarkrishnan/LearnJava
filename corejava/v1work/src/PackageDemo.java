import com.ramkrish.corejava.*;
// the PackageEmployee class is defined in the above package

import java.sql.Clob;
import java.util.Arrays;
import static java.lang.System.out;

/**
 * This program demonstrates the use of packages.
 * @author Ram Krishnan
 * @since v1.0
 */
public class PackageDemo
{
   public static void main(String[] args)
   {
      // because of the import statement, we don't have to use
      // com.ramkrish.corejava.PackageEmployee here
      var harry = new PackageEmployee("Harry Hacker", 50000, 1989, 10, 1, 1960, 12,1);

      harry.raiseSalary(50);

      // because of the static import statement, we don't have to
      // use System.out here
      out.println(harry.getName() + ":" + harry.getSalary() + ":" + harry.getBirthDay());

      // fill the staff array with three Employee objects
      PackageEmployee[] staff = new PackageEmployee[4];
      staff[0] = new PackageEmployee("Carl Cracker", 75000, 1987, 12, 15, 1960, 3, 22);
      staff[1] = new PackageEmployee("Harry Hacker", 50000, 1989, 10, 1, 1965, 4, 15);
      staff[2] = new PackageEmployee("Tony Tester", 40000, 1990, 3, 15, 1972, 5, 20);
      staff[3] = new PackageEmployee("Bob Baker", 40000, 1993, 6, 25, 1975, 12, 11);

      // Given implementation of Comparable interface, the array elements can be compared
      if (staff[0] instanceof Comparable) {
         out.println(staff[1].getName()
                 + " makes "
                 + ((staff[0].compareTo(staff[1]) < 0) ? "less" : "equal or more")
                 + " money than " + staff[2].getName());

         // An array of PackageEmployee is sortable given elements can be compared
         Arrays.sort(staff);
         for (PackageEmployee e : staff) out.println(e.toString());
      }
      else
         out.println("Instance does not implement Comparable interface");

      // clone a PackageEmployee
      // Runtime error if defined as protected - 'cause PackageDemo is not in the
      // same package com.ramkrish.corejava as the class PackageEmployee
      // java: clone() has protected access in com.ramkrish.corejava.PackageEmployee
      out.println("staff[0] is " + staff[0].toString());
      if (staff[0] instanceof Cloneable) {
         try {
            PackageEmployee emp = (PackageEmployee) staff[0].clone();
            out.println("staff[0] clone is " + emp.toString());
         } catch (CloneNotSupportedException e) {
            out.println("Cloning not supported exception " + e.getMessage());
         }
      }
      else
         out.println("Instance does not implement Cloneable interface");
   }
}
