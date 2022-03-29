/**
 * This program demonstrates inheritance.
 * The PackageManager class inherits from ("Extends") the PackageEmployee class
 * and this driver shows the use of both these classes.
 */

import com.ramkrish.corejava.*;
// the PackageEmployee class is defined in the above package

import static java.lang.System.out;
import static java.lang.System.setOut;

public class ManagerTest {
    public static void main(String[] args) {
        // construct a Manager object
        var mgr = new PackageManager("Ball Breaker", 80000, 1987, 12, 15, 1960, 12, 11);
        mgr.setBonus(5000);

        // add a few staff including manager
        var staff = new PackageEmployee[5];
        staff[0] = mgr;
        staff[1] = new PackageEmployee("Stanley Blacker", 50000, 1989, 10, 1, 1966, 1, 2);
        staff[2] = new PackageEmployee("Steve Slacker", 40000, 1990, 3, 15, 1960, 3, 4);

        staff[0].raiseSalary(50); // invoke the method defined in the superclass from an object in the subclass

        // Try to initialize using Object class
        Object obj = new PackageEmployee("Neal Nutter", 30000, 1991, 3, 15, 1959, 3, 4);

        staff[3] = (PackageEmployee)obj;
        out.println(staff[3].getName() + " cast to PackageEmployee has a salary of " + staff[3].getSalary());

        // The following causes a runtime error - proving that for use of obj, you can only cast it to the class
        // with which it was created, and not to its subclasses.
        // staff[3] = (PackageManager)obj; // you have to cast it to whatever you created the object with
        // out.println(staff[3].getName() + " cast to PackageManager has a salary of " + staff[3].getSalary());

        // NOTE: Both the above assignments are only references and NOT deep copies
        // Also note that you cannot do this from the first assignment; you have to define the assigned
        // variable to of type PackageManager.
        // staff[3].setBonus(10000);

        // staff[1].setBonus() - invoking a method in the subclass by a superclass object will not work

        // PackageManager mgr2 = staff[2]; - this won't work either. staff is of super class.
        // java: incompatible types: com.ramkrish.corejava.PackageEmployee cannot be converted
        // to com.ramkrish.corejava.PackageManager

        // demo equality of two object instances - equals method defined in PackageEmployee
        staff[4] = new PackageEmployee("Bob Butter", 30000, 1991, 3, 15, 1959, 3, 4);
        if (staff[4].equals(staff[3])) {
            out.println(staff[4].getName() + " is equal to " + staff[3].getName());
        }
        else  {
            out.println(staff[4].getName() + " is not equal to " + staff[3].getName());
        }

        var mgr2 = new PackageManager("Nutt Cracker", 80000, 1987, 12, 15, 1960, 12, 11);
        if (mgr2.equals(staff[0])) {
            out.println(staff[4].getName() + " is equal to " + staff[3].getName());
        }
        else {
            out.println(staff[4].getName() + " is equal to " + staff[3].getName());
        }

        // print out information about all PackageEmployee objects
        for (PackageEmployee e : staff) {
            out.println("name=" + e.getName() + ",salary=" + e.getSalary());
            if (e instanceof PackageManager m && m.getBonus() > 2000)
                out.println(m.getName() + " was boss man, and he made " + m.getBonus() + " in bonus");
            out.println(e.toString());;
        }
        // get an employee
        var e = staff[0].getPackageEmployee(staff, "Stanley Blacker");
        out.println("The name in the retrieved employee object is " + e.getName() + " with earnings " + e.getSalary());

        // you can get the manager using the same method on the base class
        var f = staff[0].getPackageEmployee(staff, "Ball Breaker");
        out.println("The name in the retrieved manager object is " + f.getName() + " with earnings " + f.getSalary());

        // see what happens if there is no employee
        var g = staff[0].getPackageEmployee(staff, "No Name");
        out.println("The name in the retrieved employee object is " + g.getName());
    }
}
