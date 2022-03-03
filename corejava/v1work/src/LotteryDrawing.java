import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.*;

public class LotteryDrawing {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("How many white balls will you draw?");
        int whiteBalls = in.nextInt();

        System.out.println("What is the highest white ball number you will draw?");
        int whiteMax = in.nextInt();

        // build a number bin from 1 to whiteMax, indexed 0 to WhiteMax-1
        int[] bin = new int[whiteMax];
        for (int i = 0; i < bin.length; i++) {
            bin[i] = i + 1;
        }

        // set up the rack for white balls and fill it with zeros
        int[] rack = new int[whiteBalls];
        for (int z : rack) rack[z] = 0;

        // now fill the rack with white ball numbers
        for (int pos = 0; (pos < rack.length) && (whiteMax > 0); whiteMax--, pos++) {
            // get a random index between 0 and whiteMax-1, and place its corresponding whiteBall in the rack
            // random returns 0 incl to 1 excl, (int) casting strips post decimal, does NOT round up/down
            // so the idx can be lowest 0 and highest whiteMax-1
            int idx = (int) (random() * whiteMax);
            rack[pos] = bin[idx];
            System.out.print("whiteMax: " + whiteMax + " Idx: " + idx + " bin[idx]: " + bin[idx] + " pos: " + pos + " rack[pos]: " + rack[pos]);

            // remove the pick from the bin to avoid a repeat as follows:
            // overwrite the picked value in the bin with the last value, and reduce the bin range by one so that
            // the idx for the last value is not returned by random, and thereby the last value is not picked.
            bin[idx] = bin[whiteMax - 1];
            System.out.println(" Updated bin[idx]: " + bin[idx]);
        }
        Arrays.sort(rack);

        // we could have a rack for red ball also and adopt the same approach as above.
        // For simplicity, we will go with PowerBall concept and pick one red ball between 1 and 26
        int redMax = 26;
        int redBall = (int) (random() * redMax + 1);

        // extend the rack to include the red ball
        rack = Arrays.copyOf(rack, rack.length + 1);
        System.out.print("New rack length: " + rack.length);
        rack[rack.length - 1] = redBall;
        System.out.println(" and red ball: " + rack[rack.length - 1]);

        // print out the drawing rack
        System.out.print("Your lottery drawing: ");
        for (int i = 0; i < rack.length; i++) System.out.print(rack[i] + " ");
        System.out.println();

        System.out.print("Your lottery drawing with Arrays.toString: ");
        System.out.println(Arrays.toString(rack));

        // The following is useful to realize a key misunderstanding - you should refer to the
        // *element* in the foreach array, and not the array itself. THIS USAGE IS CORRECT:
        System.out.print("Your lottery drawing using for each: ");
        for (int i : rack) {
            System.out.print(i + " ");
        }
        System.out.println();

        // THE ENTIRE SECTION BELOW DEMONSTRATES THE WRONG USAGE OF FOREACH
        //
        // Let us try foreach where the values are ordered and indices are within the array size
        int[] forEachArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // regular for loop will work for any values placed in any order
        for (int i = 0; i < forEachArray.length; i++) System.out.print(forEachArray[i] + " ");
        System.out.println();

        // whereas, foreach loop uses the *values* as index, finds 10 out of bounds and fails
        /*
        for (int i : forEachArray) System.out.print(forEachArray[i] + " ");
        System.out.println();
        *
        Output:
        2 3 4 5 6 7 8 9 10 Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException:
        Index 10 out of bounds for length 10 at LotteryDrawing.main(LotteryDrawing.java:73)
        */

        // if the values exactly match the indices, or are within the range
        // foreach processes them in ascending order of values
        int[] forEachArray2 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i : forEachArray2) System.out.print(forEachArray2[i] + " ");
        System.out.println();

        // even if the values are in exact reverse order
        int[] forEachArray3 = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        for (int i : forEachArray3) System.out.print(forEachArray3[i] + " ");
        System.out.println();

        // or in an arbitrary order - but values within the index range
        // in this case, it prints it out in some weird order
        int[] forEachArray4 = {1, 0, 2, 9, 3, 8, 4, 7, 5, 6};
        for (int i : forEachArray4) System.out.print(forEachArray4[i] + " ");
        System.out.println();

        /*
        Output:
        0 1 2 3 4 5 6 7 8 9
        0 1 2 3 4 5 6 7 8 9
        0 1 2 6 9 5 3 7 8 4
        */
    }
}

/*
How many white balls will you draw?
5
What is the highest white ball number you will draw?
69
whiteMax: 69 Idx: 62 bin[idx]: 63 pos: 0 rack[pos]: 63 Updated bin[idx]: 69
whiteMax: 68 Idx: 51 bin[idx]: 52 pos: 1 rack[pos]: 52 Updated bin[idx]: 68
whiteMax: 67 Idx: 10 bin[idx]: 11 pos: 2 rack[pos]: 11 Updated bin[idx]: 67
whiteMax: 66 Idx: 14 bin[idx]: 15 pos: 3 rack[pos]: 15 Updated bin[idx]: 66
whiteMax: 65 Idx: 15 bin[idx]: 16 pos: 4 rack[pos]: 16 Updated bin[idx]: 65
New rack length: 6 and red ball: 7
Your lottery drawing: 11 15 16 52 63 7

Process finished with exit code 1
 */