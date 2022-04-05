/**
 * Demo concurrent hashmap
 * uses a concurrent hash map to count all words in the Java files of
 * a directory tree.
 * java provides methods that make atomic updates more convenient.
 * The compute method is called with a key and a function to compute
 * the new value. That function receives the key and the associated value,
 * or null if there is none, and it computes the new value.
 * You cannot have null values in a ConcurrentHashMap. There are many
 * methods that use a null value as an indication that a given key is
 * not present in the map.
 * There are also variants computeIfPresent and computeIfAbsent that only
 * compute a new value when there is already an old one, or when there
 * isn’t yet one.
 * You often need to do something special when a key is added for the
 * first time. The merge method makes this particularly convenient. It
 * has a parameter for the initial value that is used when the key is not yet
 * present. Otherwise, the function that you supplied is called, combining
 * the existing value and the initial value. (Unlike compute, the function
 * does not process the key.) If the function that is passed to compute or
 * merge returns null, the existing entry is removed from the map.
 *
 * The compute method is called with a key and a function to compute the new
 * value. That function receives the key and the associated value, or null
 * if there is none, and it computes the new value. For example, here is how
 * we can update a map of integer counters:
 * map.compute(word, (k, v) -> v == null ? 1 : v + 1);
 */
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class ConcurrentMaputharu {
    public static ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();

    /**
     * Adds all words in the given file to the concurrent hash map.
     * @param file a file
     */
    public static void AddWordsToMap(Path file)  {
        try (var in = new Scanner(file))  {
            while (in.hasNext())  {
                String word = in.next();
                map.merge(word, 1L, Long::sum);
            }
        }
        catch (IOException e)  {
            e.printStackTrace();
        }
    }

    /**
     * Returns all descendants of a given directory--see Chapters 1 and 2 of Volume II
     * @param rootDir the root directory
     * @return a set of all descendants of the root directory
     */
    public static Set<Path> descendants(Path rootDir) throws IOException  {
        try (Stream<Path> entries = Files.walk(rootDir))  {
            return entries.collect(Collectors.toSet());
        }
    }

    public static void main(String[] args)
            throws InterruptedException, ExecutionException, IOException  {
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(processors);
        Path pathToRoot = Path.of(".");

        for (Path p : descendants(pathToRoot))  {
            if (p.getFileName().toString().endsWith(".java"))
                executor.execute(() -> AddWordsToMap(p));
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);

        // print each key,value pair in the ConcurrentHashMap
        map.forEach((k, v) ->  {
            if (v >= 10)
                System.out.println(k + " occurs " + v + " times");
        });
    }
}