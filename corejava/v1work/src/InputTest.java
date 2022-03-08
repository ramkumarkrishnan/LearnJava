import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;
import static java.lang.System.out;

/**
 * InputTest.java
 * Demonstrate Console input
 * ramkrish 022822 Creation
 */
public class InputTest {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in); // System.in here possibly to disambiguate from variable in

        // get first input
        /* no System class reference here, thanks to import */
        out.println("Your name please: ");
        String name = in.nextLine();

        // second input
        out.println("Your age please: ");
        int age = in.nextInt();

        // file input
        var input_file = new Scanner(Path.of("/Users/ramkrish/github/LearnJava/corejava/v1work/src/Welcome.java"), StandardCharsets.UTF_8);

        // console output
        out.println(new StringBuilder().append("Welcome to the US ").append(name).append(" aged ").append(age).append(" years").toString());
        out.printf("Welcome again to the US %s aged %d years", name, age);

        // file output
        // var output_file = new PrintWriter("Users/ramkrish/github/LearnJava/corejava/v1work/InputTest.out", StandardCharsets.UTF_8);
    }
}
