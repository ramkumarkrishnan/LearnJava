package com.ramkrish.corejava;

public class PackageManager extends PackageEmployee {

    private double bonus;

    /**
     * @param n the employee's name
     * @param s the salary
     * @param year the hire year
     * @param month the hire month
     * @param day the hire day
     * @param byear the birth year
     * @param bmonth the birth month
     * @param bday the birth day
     */
    public PackageManager(String n, double s, int year, int month, int day, int byear, int bmonth, int bday) {
        super(n, s, year, month, day, byear, bmonth, bday);
        bonus = 0;
    }

    // new method in subclass
    public void setBonus (double b) {
        bonus = b;
    }

    // new method in subclass
    public double getBonus () {
        double b = bonus;
        return b;
    }
    // override the method defined in super class
    public double getSalary() {
        return super.getSalary() + bonus;
    }

    // attempt to override the method defined in the super class, adds
    // 5% more to the prescribed percentage (this is a greedy management -
    // so we will not allow it by defining the base class method final)
    // Compiler warns you like this:
    // java: raiseSalary(double) in com.ramkrish.corejava.PackageManager
    // cannot override raiseSalary(double) in com.ramkrish.corejava.PackageEmployee
    // overridden method is final
    // public void raiseSalary(double byPercent) {
    //    this.salary += this.salary * ((5 + byPercent) / 100);
    // }

    // define method to check equality of objects (Employee is not the best
    // example - it will be more useful for geometric objects and complex numbers
    @Override public boolean equals(Object otherObject)
    {
        if (!super.equals(otherObject)) return false;

        PackageManager m = (PackageManager) otherObject;

        // super.equals checked that this and otherObject belong to the same class
        return ((bonus == m.getBonus()) && (super.getSalary() == m.getSalary()) && (super.getBirthDay() == m.getBirthDay()));
    }

    @Override public String toString()  {
        return super.toString()
                    + ",bonus=" + bonus;
    }
}
