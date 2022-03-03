import java.util.Scanner;

import static java.lang.System.*;

public class Lottery {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // get inputs
        out.println("How many white balls will you draw?");
        int whiteBalls = in.nextInt();

        out.println("What is the highest white ball number?");
        int whiteMax = in.nextInt();

        out.println("How many red balls will you draw?");
        int redBalls = in.nextInt();

        out.println("What is the highest red ball number?");
        int redMax = in.nextInt();

        // Powerball Odds formula = 69C5 x 26C1
        // nCr = n!/r! x (n - r)! =
        // (69!/(5! x 64!)) x (26!/(1! x 25!)) = ((69 x 68 x 67 x 66 x 65)/(5 x 4 x 3 x 2 x 1)) x 26
        // https://www.durangobill.com/
        // (69 * 68 * 67 * 66 * 65) / (5 * 4 * 3 * 2 * 1) * 26
        double jackpotOdds =
                (Lottery.fact(whiteMax) / (Lottery.fact(whiteBalls) * Lottery.fact(whiteMax - whiteBalls)))
                        * (Lottery.fact(redMax) / (Lottery.fact(redBalls) * Lottery.fact(redMax - redBalls)));

        out.printf("Your odds of winning the jackpot are 1 in %d or %e %%",
                (int) jackpotOdds, (double) ((1 * 100) / jackpotOdds));
    }

    public static double fact(int num) {
        double j = 1;
        for (int i = num; i > 0; i--) j *= i;
        return j;
    }
}
