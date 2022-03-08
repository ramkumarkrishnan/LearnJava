import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class LocalTimeCheckObj {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter \"now\" or date in \"mm/dd/yyyy\" format:");
        String inDate = in.next();
        LocalDate date;

        if (inDate.equalsIgnoreCase("now")) {
            date = LocalDate.now();
        }
        else {
            DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            date = LocalDate.parse(inDate, dtFormat);
        }

        LocalCalendar cal = new LocalCalendar(date);
        cal.printCalendar();
    }
}
