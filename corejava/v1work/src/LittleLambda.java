/**
 * Demo of Lambda function for String compare
 */
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import static java.lang.System.*;

public class LittleLambda  {
    public static void main(String[] args) {
        var planets = new String[] { "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune" };
        out.println(Arrays.toString(planets));
        out.println("Sorted in dictionary order:");
        Arrays.sort(planets);
        out.println(Arrays.toString(planets));

        // sorted using lambda function
        out.println("Sorted by length:");
        Arrays.sort(planets, (first, second) -> first.length() - second.length());
        out.println(Arrays.toString(planets));

        var timer = new Timer(1000, event ->
        out.println("The time is " + new Date()));
        timer.start();

        // keep program running until user selects "OK"
        JOptionPane.showMessageDialog(null, "Quit program?");
        exit(0);
    }
}
