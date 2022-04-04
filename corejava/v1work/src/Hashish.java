/**
 * Demo for Hash Set
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;

import static java.lang.System.in;

public class Hashish {
    public static void main(String[] args) throws IOException {
        var words = new HashSet<String>();
        long totalTime = 0;
        int wordCount = 0;

        try {
            try (var in = new Scanner(Path.of("/Users/ramkrish/github/LearnJava/corejava/v1work/src/hashish.txt"), StandardCharsets.UTF_8))  {
                while (in.hasNext())
                {
                    String word = in.next();
                    System.out.println("Added word: " + word);
                    long callTime = System.currentTimeMillis();
                    words.add(word);
                    callTime = System.currentTimeMillis() - callTime;
                    totalTime += callTime;
                    wordCount++;
                }
            }
            catch (FileNotFoundException | UnknownHostException e)  {
                System.out.println(e.getMessage());
                throw e;
            }
            finally {
                in.close();
            }
        }
        catch (IOException e)  {
            System.out.println(e.getMessage());
            throw e;
        }

        System.out.println("Total Word Count: " + wordCount);

        Iterator<String> iter = words.iterator();
        for (int i = 1; i <= wordCount && iter.hasNext(); i++)
            System.out.println(iter.next());
        System.out.println(". . .");
        System.out.println(words.size() + " distinct words. " + totalTime + " milliseconds.");
    }
}
