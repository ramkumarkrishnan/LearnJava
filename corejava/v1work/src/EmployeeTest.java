/**
 * This program tests the Employee class and has different access levels
 */

public class EmployeeTest
{
    public static void main(String[] args)
    {
        // fill the staff array with three Employee objects
        Employee[] staff = new Employee[4];
        staff[0] = new Employee("Carl Cracker", 75000, 1987, 12, 15, 1960, 3, 22);
        staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1, 1965, 4, 15);
        staff[2] = new Employee("Tony Tester", 40000, 1990, 3, 15, 1972, 5, 20);
        staff[3] = new Employee("Bob Baker", 40000, 1993, 6, 25, 1975, 12, 11);

        // raise everyone's salary by 5%
        for (Employee e : staff) e.raiseSalary(5);

        // add reviews
        staff[0].addEvaluation("Excellent Cracker");
        staff[1].addEvaluation("Dangerous Hacker");
        staff[2].addEvaluation("Electric Tester");
        staff[3].addEvaluation("Tasty Baker");

        // print out information about all Employee objects
        for (Employee e : staff)
            System.out.println("name = " + e.getName()
            + ", salary = " + e.getSalary()
            + ", hireDay = " + e.getHireDay()
            + ", birthDay = " + e.getBirthDay()
            + ", remarks = " + e.getEvaluations()
            + ", years to retire = " + e.getYearsToRetirement()
            + ", ID = " + e.getID());
        System.out.println();

        // method of one object can access a private instance variable of another object of the same class
        if (staff[2].equalSalary(staff[3])) {
            System.out.println(staff[2].getName() + " and " + staff[3].getName() + " have the same salaries");
        }

        // QUESTION - WHAT IF YOU WANT TO PREVENT ONE OBJECT FROM SEEING ANOTHER OBJECT?
    }
}