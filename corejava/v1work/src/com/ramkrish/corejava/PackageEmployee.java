package com.ramkrish.corejava;
// above declaration says that classes in this file are part of the
// above package. Any java program that "imports" the package can
// make use of these classes.

import java.time.LocalDate;

/**
 * Employee class used by EmployeeTest
 * Test various access levels of methods and instance fields
 * Demo Interfaces - Comparable and Cloneable
 */
public class PackageEmployee implements Comparable<PackageEmployee>, Cloneable {
    private String name;
    private double salary;
    private LocalDate hireDay;
    private LocalDate birthDay;

    private static int nextId = 1;
    private int id;

    public static final double Matching401kPercentage = 3;

    private final StringBuilder evaluations;

    public PackageEmployee(String n, double s,
      int year, int month, int day, int byear, int bmonth, int bday) {
        name = n;

        salary = s;
        hireDay = LocalDate.of(year, month, day);
        birthDay = LocalDate.of(byear, bmonth, bday);

        evaluations = new StringBuilder();

        id = PackageEmployee.advanceNextId();
    }

    public static int advanceNextId() {
        int return_id = PackageEmployee.nextId;
        PackageEmployee.nextId++;
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

    public final void raiseSalary(double byPercent) {
        this.salary += this.salary * (byPercent / 100);
    }

    public PackageEmployee getPackageEmployee(PackageEmployee[] emp, String nm) {
        boolean found = Boolean.FALSE;
        PackageEmployee ret = new PackageEmployee("",0,9999, 9, 9, 9999, 9, 9);
        for (PackageEmployee e : emp) {
            if (e.getName().compareTo(nm) == 0) {
                ret = e;
                break;
            }
        }
        return ret;
    }

    /*
    If you don't have the class in the package, you get the following error;
    Change Employee to PackageEmployee
    /Users/ramkrish/github/LearnJava/corejava/v1work/src/com/ramkrish/corejava/PackageEmployee.java:76:32
    java: cannot find symbol
    symbol:   class Employee
    location: class com.ramkrish.corejava.PackageEmployee
    */
    public boolean equalSalary(PackageEmployee another) {
        return (this.salary == another.salary);
    }

    public void addEvaluation(String evaluation)  {
        evaluations.append(LocalDate.now() + ": " + evaluation);
    }

    @Override public String toString()  {
        return getClass().getName()
                + ":name=" + name
                + ",salary=" + salary
                + ",hireDay=" + hireDay
                + ",birthDay=" + birthDay
                + ",eval=" + evaluations;
    }

    /**
     * Compares employees by salary
     * @param other another Employee object
     * @return a negative value if this employee has a lower salary than
     * otherObject, 0 if the salaries are the same, a positive value otherwise
     */
    @Override
    public int compareTo(PackageEmployee other) {
        return Double.compare(salary, other.salary);
    }

    /**
     * Demo Cloneable interface
     * protected - the member can be accessed by any class in the same package
     * and by subclasses even if they are in another packages.
     * override clone method by simply calling Object class clone method
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}