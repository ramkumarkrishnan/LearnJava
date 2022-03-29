/**
 * Display calendar for the month and year
 */

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class LocalTimeCheck {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter \"now\" or date in \"mm/dd/yyyy\" format:");
        String inDate = in.next();

        LocalDate date;
        if (inDate.equalsIgnoreCase("now")) {
            date = LocalDate.now();
        } else {
            DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            date = LocalDate.parse(inDate, dtFormat);
        }

        Boolean success = LocalTimeCheck.printLocalCalendar(date);
    }

    public static boolean printLocalCalendar(LocalDate date) {
        // get month and day of the month
        int month = date.getMonthValue();
        int today = date.getDayOfMonth();

        // set date to start of month and print header
        date = date.minusDays(today - 1);
        System.out.println(date.getMonth().name() + " " + date.getYear());
        System.out.println("Mon Tue Wed Thu Fri Sat Sun");

        // show blank for leading days of the week not in this month
        DayOfWeek weekday = date.getDayOfWeek();
        int value = weekday.getValue(); // 1 = Monday, . . . , 7 = Sunday
        for (int i = 1; i < value; i++)
            System.out.print("    ");

        // print the calendar for the rest of the month
        while (date.getMonthValue() == month) {
            System.out.printf("%3d", date.getDayOfMonth());

            // add * to indicate today
            if (date.getDayOfMonth() == today)
                System.out.print("*");
            else
                System.out.print(" ");

            // increment date to next day
            date = date.plusDays(1);

            // if the date is a Mon, print that week's worth of dates
            if (date.getDayOfWeek().getValue() == 1) System.out.println();
        }

        // once past this month, print blanks for rest of the days of the week
        if (date.getDayOfWeek().getValue() != 1)
            System.out.println();

        Boolean bool = true;
        return bool;
    }
}