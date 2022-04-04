import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class ExceptionTest {
    public static void main(String[] args)  {
        int thousand = 1000;
        double[] a = { 1000, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        performAction("Integer divide by zero", () -> 1 / (a.length - a.length));
        performAction("Floating point divide by zero", () -> a[2] / (a[3] - a[3]));
        performAction("Integer overflow", () -> thousand * thousand * thousand * thousand);
        performAction("Square root of negative number", () -> Math.sqrt(-1));
        performAction("Array index out of bounds", () -> a[1] - a[100]);
        performAction("Bad cast", () -> (int[]) (Object) a);
        performAction("Null pointer", () -> System.getProperty("woozle").toString());
        performAction("No such file", () -> new Scanner(Path.of("woozle.txt"), StandardCharsets.UTF_8).next());

        // another exception test
        int i = 0;
        int ntry = 10000000;
        var stack = new Stack<String>();

        // test a stack for emptiness ntry times
        System.out.println("Testing for empty stack");

        long start = System.currentTimeMillis();
        for (i = 0; i <= ntry; i++)
            if (!stack.empty()) stack.pop();

        long end = System.currentTimeMillis();
        System.out.println((end - start) + " milliseconds");

        // pop an empty stack ntry times and catch the resulting exception
        System.out.println("Catching EmptyStackException");
        start = System.currentTimeMillis();
        for (i = 0; i <= ntry; i++)
        {
            try
            {
                stack.pop();
            }
            catch (EmptyStackException e)
            {
            }
        }
        end = System.currentTimeMillis();
        System.out.println((end - start) + " milliseconds");
    }

    private static void performAction(String description, Callable<Object> action)  {
        System.out.println(description);
        try
        {
            System.out.println(action.call());
        }
        catch (Throwable t)
        {
            System.out.println(t.getClass().getName() + ": " + t.getMessage());
        }
    }
}
