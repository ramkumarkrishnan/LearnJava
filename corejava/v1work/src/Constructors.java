import java.util.Random;

/**
 * Program to demonstrate
 * An object initialization block
 * A static initialization block
 * An instance field initialization
 * Overloaded constructors
 * A call to another constructor with this(. . .)
 * A no-argument constructor
 */
public class Constructors {
    public static void main(String[] args) {
        // fill three persons
        var person = new People[4];

        person[0] = new People("Peter", 50);
        person[1] = new People("Paul", 60, 40000);
        person[2] = new People("Mary", 70);
        person[3] = new People();

        // Example of formatted string output using a String method
        for (People e : person)
            System.out.println("Person[%d : %s : %d : %,.2f]".formatted(e.getId(), e.getName(), e.getAge(), e.getSalary()));
    }
}

class People {
    // private static - class variables
    private static int nextId;
    private static Random gen = new Random();

    // private scalar instance field - does not mandate initialization
    private int id;

    // private instance field initialization (required)
    private String name = "No Name  ";

    // private scalar instance field - does not mandate initialization
    private int age;

    // private scalar instance field - does not mandate initialization
    private double salary;

    // static initialization block
    static {
        nextId = gen.nextInt(10000);
    }

    // object initialization block
    {
        id = nextId;
        nextId++;
    }

    // the default constructor
    public People()
    {
        // id initialized in initialization block
        // name initialized to "" -- see above
        // age not set -- initialized to 0
        // salary not explicitly set -- initialized to 0
    }

    // first overload constructor
    public People(String n, int a) {
        name = n;
        age = a;
    }

    // second overload constructor
    public People(String n, int a, double s) {
        // calls the Person(String, int) constructor
        this("ID:" + nextId + ":" + n, a);

        // sets the salary
        this.salary = s;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public double getSalary()
    {
        return salary;
    }
}