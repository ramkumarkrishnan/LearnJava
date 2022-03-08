import java.util.Date;

/**
 * This program demonstrates records, and the following types of constructors
 * canonical
 * compact
 * custom
 */
public class RecordClasses {
    public static void main(String[] args) {
        // instantiating a canonical record
        var m = new Point();
        System.out.println("Coordinates of m: " + m.x() + " " + m.y());
        // instantiating a custom record
        var p = new Point(3, 4);
        System.out.println("Coordinates of p: " + p.x() + " " + p.y());
        System.out.println("Distance from origin: " + p.distanceFromOrigin());

        // Same computation with static field and method
        System.out.println("Distance from origin: " + Point.distance(Point.ORIGIN, p));

        // instantiating a mutable record
        var q = new PointInTime(3, 4, new Date());
        System.out.println("Before: " + q);
        q.when().setTime(0);
        System.out.println("After: " + q);

        // instantiating a compact constructor
        var r = new Range(4, 3);
        System.out.println("r: " + r);
    }

    // record Point(double x, double y) {
        // two instance variables that are automatically final
        // private double x;
        // private double y;
        // and two get methods
        // x()
        // y()
        // accessed as var.x and var.y
    // }

    // canonical constructor for a record
    // public Point() {
    //    this(x:0, y:0);
    // }

    record Point(double x, double y) {
        // A custom constructor
        public Point() {
            this(0, 0);
        }

        // We can add a method to the record like any other class
        public double distanceFromOrigin() {
            return Math.hypot(x, y);
        }

        // We can add static field to the record
        public static Point ORIGIN = new Point();

        // We can add a static method to the record
        public static double distance(Point p, Point q) {
            return Math.hypot(p.x - q.x, p.y - q.y);
        }
    }

    record PointInTime(double x, double y, Date when) {

    }

    record Range(int from, int to) {
        // A compact constructor
        public Range {
            if (from > to) // Swap the bounds
            {
                int temp = from;
                from = to;
                to = temp;
            }
        }
    }

    // Following proves that instance variables that are automatically final
    // We cannot set the this.to value
    // /Users/ramkrish/github/LearnJava/corejava/v1work/src/RecordClasses.java:87:21
    // java: cannot assign a value to final variable from
    /*
    record Range2(int from, int to) {
        public Range2 {
            if (from <= to) {
                this.from = from;
                this.to = to;
            } else {
                this.from = to;
                this.to = from;
            }
        }
    }
     */

}

