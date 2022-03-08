import java.time.LocalDate;

/**
 * Employee class used by EmployeeTest
 * Test various access levels of methods and instance fields
 */
public class Employee {
    private String name;
    private double salary;
    private LocalDate hireDay;
    private LocalDate birthDay;

    /*
    A field defined STATIC is not present in the objects of the class.
    There is only a single copy of each static field. You can think of
    static fields as belonging to the class, not to the individual objects.
    */
    private static int nextId = 1;
    private int id;

    /*
    An instance field defined FINAL must be initialized when the object is
    constructed, and not modified thereafter. You must guarantee that the
    field value has been set after the end of every constructor.
    "static final" is equivalent to const in C. public constants are an
    allowed in Java.
    */
    public static final double Matching401kPercentage = 3;

    /*
    It is a BAD PRACTICE to define variables of MUTABLE classes (i.e. classes
    with set methods) as final. However, you can define an instance in the
    constructor as long as you initialize it in the constructor.
    */
    private final StringBuilder evaluations;

    public Employee(String n, double s, int year, int month, int day, int byear, int bmonth, int bday) {
        name = n;
        // Benign way to handle potential NULL inputs is shown below.
        // name = Objects.requireNonNullElse(n, "unknown");
        // BUT DON'T DO THIS
        // Let NullPointerException point out where the error is.
        // THIS APPLIES MAINLY FOR INSTANCE FIELDS THAT ARE OBJECTS;
        // SCALARS HAVE A DEFAULT VALUE IF NOT SPECIFIED.

        salary = s;
        hireDay = LocalDate.of(year, month, day);
        birthDay = LocalDate.of(byear, bmonth, bday);

        evaluations = new StringBuilder();

        // the following shows how to initialize a static field (which
        // belongs to the class) from the object (instance) constructor
        id = Employee.advanceNextId();

        /*
         The following shows a final variable can be initialized once in the
         constructor and not changed thereafter.
        */

        /*
        The following shows you cannot do that with a static final instance
        variable. It has to be initialized at the point of definition.
         /Users/ramkrish/github/LearnJava/corejava/v1work/src/Employee.java:47:9
        java: cannot assign a value to final variable Matching401KPercentage
         */
        // Matching401kPercentage = 3;

        /*
         ACHTUNG!! NEVER DO THE FOLLOWING - DECLARING LOCAL VARIABLES IN THE
         CONSTRUCTOR WITH SAME NAMES AS THE INSTANCE FIELDS/VARIABLES OF THE CLASS
         JAVA ALLOWS THIS AND COMPILERS DO NOT CATCH IT - BUT SUCH CODE CAN
         CAUSE PROBLEMS AND IS HARD TO DEBUG
         String name = "nom de guerre";
         double salary = 0.0;
        */
    }

    public static int advanceNextId() {
        int return_id = Employee.nextId; // explicitly refer to the static field
        Employee.nextId++;
        return return_id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getHireDay() {
        return hireDay;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public int getYearsToRetirement() {
        return (int) 0;
    }

    public double getSalary() {
        return salary;
    }

    public String getEvaluations() {
        return evaluations.toString();
    }

    public int getID() {
        return id;
    }

    public void raiseSalary(double byPercent) {
        // USE this TO INDICATE OPERATION ON INSTANCE FIELD FOR THE CLASS
        this.salary += this.salary * (byPercent / 100);
    }

    public boolean equalSalary(Employee another) {
        // BELOW IS LEGAL BECAUSE another IS AN INSTANCE OF SAME CLASS Employee
        return (this.salary == another.salary);
    }

    public void addEvaluation(String evaluation)  {
        evaluations.append(LocalDate.now() + ": " + evaluation);
    }
}