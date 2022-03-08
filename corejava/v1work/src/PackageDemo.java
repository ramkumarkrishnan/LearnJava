import com.ramkrish.corejava.*;
// the PackageEmployee class is defined in the above package

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
   }
}
