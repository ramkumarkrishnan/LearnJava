/**
 * This program demonstrates that Java method parameters are passed by
 * two means:
 * - scalars - call by value only
 * - objects - call by object reference (NOT the same as call by reference)
 */
import static java.lang.System.*;
import static java.lang.System.out;

public class JavaCallsByValueOnly {
    public static void main(String[] args)  {
        // Test 1: Methods cannot modify scalar parameters
        double x = 10;
        out.println("x before triple(): " + x);
        triple(x);
        out.println("x after triple(): " + x);

        // Test 2: Methods can change the state of object instance fields
        var harry = new Employee("Harry Stiles", 50000, 1997, 5, 6, 1965, 8, 12);
        out.println(harry.getName() + " before tripleSalary: " + harry.getSalary());
        tripleSalary(harry);
        out.println(harry.getName() + " after tripleSalary: " + harry.getSalary());

        // Test 3: But methods CANNOT change objects themselves.  When objects
        // are passed in as parameters, OBJECT REFERENCES ARE PASSED BY VALUE.
        // Specifically, if you expect the swap() routine below to take the
        // "contents of" a and put it in b, and vice versa - that won't happen
        var sally = new Employee("Sally Field", 30000, 1995, 12, 11, 1955, 4, 01);

        out.println("Before swap, harry and sally are: ");
        out.println(harry.getName() + " salary: " + harry.getSalary());
        out.println(sally.getName() + " salary: " + sally.getSalary());
        swap(harry,sally);  // swap expecting sally to get harry's attributes and vice versa
        out.println("After swap, harry and sally remain: ");
        out.println(harry.getName() + " salary: " + harry.getSalary());
        out.println(sally.getName() + " salary: " + sally.getSalary());
    }

    // if triple() was intended to change x to 3 * x, then the following code
    // will not work in Java - because all scalars are passed by value
    public static void triple(double x)
    {
        x = 3 * x;
    }

    // methods can change the state of objects passed as parameters
    public static void tripleSalary(Employee x) // works
    {
        x.raiseSalary(200);
    }

    public static void swap(Employee x, Employee y) {
        out.println("x param inside before swap: " + x.getName() + " salary: " + x.getSalary());
        out.println("y param inside before swap: " + y.getName() + " salary: " + y.getSalary());

        Employee temp = x;
        x = y;
        y = temp;
        out.println("x param inside after swap: " + x.getName() + " salary: " + x.getSalary());
        out.println("y param inside after swap: " + y.getName() + " salary: " + y.getSalary());
    }
}
