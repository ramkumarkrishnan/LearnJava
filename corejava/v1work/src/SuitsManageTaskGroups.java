/**
 * Demo use of Executors() to manage task groups
 * use an executor service as a thread pool to increase the efficiency of task execution. Sometimes, an executor is used for a more tactical reason—simply to control a group of related tasks. For example, you can cancel all tasks in an executor with the shutdownNow method.
 *
 * The invokeAny method submits all objects in a collection of Callable objects and returns the result of a completed task. You don’t know which task that is—presumably, it is the one that finished most quickly. Use this method for a search problem in which you are willing to accept any solution. For example, suppose that you need to factor a large integer—a computation that is required for breaking the RSA cipher. You could submit a number of tasks, each attempting a factorization with numbers in a different range. As soon as one of these tasks has an answer, your computation can stop.
 *
 * The invokeAll method submits all objects in a collection of Callable objects, blocks until all of them complete, and returns a list of Future objects that represent the solutions to all tasks. You can process the results of the computation, when they are available, like this:
 * List<Callable<T>> tasks = . . .;
 * List<Future<T>> results = executor.invokeAll(tasks);
 * for (Future<T> result : results)
 *    processFurther(result.get());
 * In the for loop, the first call result.get() blocks until the first result is available. That is not a problem if all tasks finish in about the same time. However, it may be worth obtaining the results in the order in which they are available. This can be arranged with the ExecutorCompletionService.
 * Start with an executor, obtained in the usual way. Then construct an ExecutorCompletionService. Submit tasks to the completion service. The service manages a blocking queue of Future objects, containing the results of the submitted tasks as they become available. Thus, a more efficient organization for the preceding computation is the following:
 * var service = new ExecutorCompletionService<T>(executor);
 * for (Callable<T> task : tasks) service.submit(task);
 * for (int i = 0; i < tasks.size(); i++)
 *    processFurther(service.take().get());
 *
 * In the first computation, we count how many files in a directory tree contain a given word. We make a separate task for each file:
 * Set<Path> files = descendants(Path.of(start));
 * var tasks = new ArrayList<Callable<Long>>();
 * for (Path file : files)
 * {
 *    Callable<Long> task = () -> occurrences(word, file);
 *    tasks.add(task);
 * }
 * Then we pass the tasks to an executor service:
 * ExecutorService executor = Executors.newCachedThreadPool();
 * List<Future<Long>> results = executor.invokeAll(tasks);
 *
 * To get the combined count, we add all results, blocking until they are available:
 * long total = 0;
 * for (Future<Long> result : results)
 *    total += result.get();
 * The program also displays the time spent during the search. Unzip the source code for the JDK somewhere and run the search. Then replace the executor service with a single-thread executor and try again to see whether the concurrent computation was faster.
 *
 * In the second part of the program, we search for the first file that contains the given word. We use invokeAny to parallelize the search. Here, we have to be more careful about formulating the tasks. The invokeAny method terminates as soon as any task returns. So we cannot have the search tasks return a boolean to indicate success or failure. We don’t want to stop searching when a task failed. Instead, a failing task throws a NoSuchElementException. Also, when one task has succeeded, the others are canceled. Therefore, we monitor the interrupted status. If the underlying thread is interrupted, the search task prints a message before terminating, so that you can see that the cancellation is effective.
 * For informational purposes, this program prints out the largest pool size during execution. This information is not available through the ExecutorService interface. For that reason, we had to cast the pool object to the ThreadPoolExecutor class.
 * As you read through this program, you can appreciate how useful executor services are. In your own programs, you should use executor services to manage threads instead of launching threads individually.
 *
 */
import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class SuitsManageTaskGroups {
    /**
     * Counts occurrences of a given word in a file.
     * @return the number of times the word occurs in the given word
     */
    public static long occurrences(String word, Path path)
    {
        try (var in = new Scanner(path))
        {
            int count = 0;
            while (in.hasNext())
                if (in.next().equals(word)) count++;
            return count;
        }
        catch (IOException ex)
        {
            return 0;
        }
    }

    /**
     * Returns all descendants of a given directory--see Chapters 1 and 2 of Volume II.
     * @param rootDir the root directory
     * @return a set of all descendants of the root directory
     */
    public static Set<Path> descendants(Path rootDir) throws IOException
    {
        try (Stream<Path> entries = Files.walk(rootDir))
        {
            return entries.filter(Files::isRegularFile)
                    .collect(Collectors.toSet());
        }
    }

    /**
     * Yields a task that searches for a word in a file.
     * @param word the word to search
     * @param path the file in which to search
     * @return the search task that yields the path upon success
     */
    public static Callable<Path> searchForTask(String word, Path path)
    {
        return () ->
        {
            try (var in = new Scanner(path))
            {
                while (in.hasNext())
                {
                    if (in.next().equals(word)) return path;
                    if (Thread.currentThread().isInterrupted())
                    {
                        System.out.println("Search in " + path + " canceled.");
                        return null;
                    }
                }
                throw new NoSuchElementException();
            }
        };
    }

    public static void main(String[] args)
            throws InterruptedException, ExecutionException, IOException
    {
        try (var in = new Scanner(System.in))
        {
            System.out.print("Enter base directory (e.g. /opt/jdk-9-src): ");
            String start = in.nextLine();
            System.out.print("Enter keyword (e.g. volatile): ");
            String word = in.nextLine();

            Set<Path> files = descendants(Path.of(start));
            var tasks = new ArrayList<Callable<Long>>();
            for (Path file : files)
            {
                Callable<Long> task = () -> occurrences(word, file);
                tasks.add(task);
            }
            ExecutorService executor = Executors.newCachedThreadPool();
            // use a single thread executor instead to see if multiple threads
            // speed up the search
            // ExecutorService executor = Executors.newSingleThreadExecutor();

            Instant startTime = Instant.now();
            List<Future<Long>> results = executor.invokeAll(tasks);
            long total = 0;
            for (Future<Long> result : results)
                total += result.get();
            Instant endTime = Instant.now();
            System.out.println("Occurrences of " + word + ": " + total);
            System.out.println("Time elapsed: "
                    + Duration.between(startTime, endTime).toMillis() + " ms");

            var searchTasks = new ArrayList<Callable<Path>>();
            for (Path file : files)
                searchTasks.add(searchForTask(word, file));
            Path found = executor.invokeAny(searchTasks);
            System.out.println(word + " occurs in: " + found);

            if (executor instanceof ThreadPoolExecutor tpExecutor)
                // the single thread executor isn't
                System.out.println("Largest pool size: "
                        + tpExecutor.getLargestPoolSize());
            executor.shutdown();
        }
    }
}
