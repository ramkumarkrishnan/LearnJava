import javax.swing.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import static java.lang.System.exit;
import static java.lang.System.out;

/**
 * Demo lambda functions with Enums
 */
public class MarysLambda {
    public static void main(String[] args) {
        out.println("Enter the planet of interest:");
        var in = new Scanner(System.in);
        String inPlanet = in.next();

        PlanetaryDistance p = Enum.valueOf(PlanetaryDistance.class, inPlanet);
        out.println("Distance of " + p.name() + " from the Sun is " + p.getDistance());

        out.println("Planets loaded from Enum into an array: ");
        PlanetaryDistance[] pa = PlanetaryDistance.values();
        out.println(Arrays.toString(pa));
        // for (PlanetaryDistance e : pa) out.println("Planet " + e.name() + ":" + e.getDistance());

        out.println("Planets sorted by their Distances from Sun using lambda: ");
        Arrays.sort(pa, (first, second) -> (int)first.getDistance() - (int)second.getDistance());
        out.println(Arrays.toString(pa));

        out.println("Planets sorted by Name lengths using lambda: ");
        Arrays.sort(pa, (first, second) -> (int)first.name().length() - (int)second.name().length());
        out.println(Arrays.toString(pa));

        out.println("Planets in an array: ");
        var pb = new String[] { "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune" };
        out.println(Arrays.toString(pb));

        out.println("Planets in alphabetical order:");
        Arrays.sort(pb);
        out.println(Arrays.toString(pb));

        // sorted using lambda function
        out.println("Planets sorted by Name lengths using lambda:");
        Arrays.sort(pb, (first, second) -> first.length() - second.length());
        out.println(Arrays.toString(pb));
    }

    public enum PlanetaryDistance {
        Venus(67.2), Saturn(886.5),
        Mercury(36.8), Earth(93),
        Jupiter(483.6), Neptune(2795.2),
        Pluto(3670.1), Uranus(1783.7),
        Mars(141.6);

        private double distanceInMillionMiles;
        private static final double OneMillion = 1000000;

        PlanetaryDistance(double distanceInMillionMiles) {
            this.distanceInMillionMiles = distanceInMillionMiles;
        }

        public double getDistance() {
            return (distanceInMillionMiles * OneMillion);
        }
    }
}

