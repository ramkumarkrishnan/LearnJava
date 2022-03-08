import java.util.Scanner;
import static java.lang.System.out;

public class RetirementSavings {
    public static void main(String[] args) {
        // read inputs
        Scanner in = new Scanner(System.in);
        out.println("What is your current asset $s?");
        double assets = in.nextDouble();
        out.println("What is your goal $s when you enter retirement?");
        double goal = in.nextDouble();
        out.println("What is your estimated $ contribution per year?");
        double contribution = in.nextDouble();
        out.println("What is your estimated growth in % per year discounting for inflation?");
        double growthPct = in.nextDouble();

        // compute number of years to retire
        int years = 0;
        while (assets < goal)  {
            assets += contribution;
            assets += assets * growthPct/100;
            years++;
        }
        out.println("You must contribute $" + contribution + " for another " + years + " years to reach your goal of $" + goal + " at retirement");
    }
}
