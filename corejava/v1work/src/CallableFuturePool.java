/**
 * Demo thread pools and Callable (complement of Runnable) and Futures
 * A Runnable encapsulates a task that runs asynchronously; an asynchronous
 * method with no parameters and no return value. A Callable is similar to
 * a Runnable, but it returns a value. The Callable interface is a
 * parameterized type, with a single method call. The type parameter is the
 * type of the returned value.
 * A Future holds the result of an asynchronous computation. You start a
 * computation, give someone the Future object, and forget about it. The owner
 * of the Future object can obtain the result when it is ready.
 *
 * The Future<V> interface has the following methods:
 * V get()
 * V get(long timeout, TimeUnit unit)
 * void cancel(boolean mayInterrupt)
 * boolean isCancelled()
 * boolean isDone()
 * A call to the first get method blocks until the computation is finished.
 * The second get method also blocks, but it throws a TimeoutException if
 * the call timed out before the computation finished. If the thread running
 * the computation is interrupted, both methods throw an InterruptedException.
 * If the computation has already finished, get returns immediately.
 *
 * The isDone method returns false if the computation is still in progress,
 * true if it is finished.
 *
 * You can cancel the computation with the cancel method. If the computation
 * has not yet started, it is canceled and will never start. If the computation
 * is currently in progress, it is interrupted if the mayInterrupt parameter
 * is true.
 *
 * Canceling a task involves two steps. The underlying thread must be located
 * and interrupted. And the task implementation (in the call method) must sense
 * the interruption and abandon its work. If a Future object does not know
 * on which thread the task is executed, or if the task does not monitor the
 * interrupted status of the thread on which it executes, cancellation will
 * have no effect.
 *
 * The newCachedThreadPool method constructs a thread pool that executes
 * each task immediately, using an existing idle thread when available and
 * creating a new thread otherwise. The newFixedThreadPool method constructs
 * a thread pool with a fixed size. If more tasks are submitted than there are
 * idle threads, the un-served tasks are placed on a queue. They are run when
 * other tasks have completed. The newSingleThreadExecutor is a degenerate
 * pool of size 1 where a single thread executes the submitted tasks, one
 * after another. These three methods return an object of the ThreadPoolExecutor
 * class that implements the ExecutorService interface.
 * Use a cached thread pool when you have threads that are short-lived or spend
 * a lot of time blocking. However, if you have threads that are working hard
 * without blocking, you don’t want to run a large number of them together.
 * For optimum speed, the number of concurrent threads is the number of processor
 * cores. In such a situation, you should use a fixed thread pool that bounds
 * the total number of concurrent threads.
 * The single-thread executor is useful for performance analysis. If you
 * temporarily replace a cached or fixed thread pool with a single-thread pool,
 * you can measure how much slower your application runs without the benefit of
 * concurrency.
 * Java EE provides a ManagedExecutorService subclass that is suitable for
 * concurrent tasks in a Java EE environment. Similarly, web frameworks
 * such as Play provide executor services that are intended for tasks within
 * the framework.
 *
 * You can submit a Runnable or Callable to an ExecutorService with one of the
 * following methods:
 * Future<T> submit(Callable<T> task)
 * Future<?> submit(Runnable task)
 * Future<T> submit(Runnable task, T result)
 * The pool will run the submitted task at its earliest convenience.
 * When you call submit, you get back a Future object that you can use to get
 * the result or cancel the task.
 *
 * The second submit method returns an odd-looking Future<?>. You can use such
 * an object to call isDone, cancel, or isCancelled, but the get method simply
 * returns null upon completion.
 *
 * The third version of submit yields a Future whose get method returns the
 * given result object upon completion. When you are done with a thread pool,
 * call shutdown. This method initiates the shutdown sequence for the pool.
 * An executor that is shut down accepts no new tasks. When all tasks are
 * finished, the threads in the pool die. Alternatively, you can call
 * shutdownNow. The pool then cancels all tasks that have not yet begun.
 *
 * Here, in summary, is what you do to use a thread pool:
 *
 * Call the static newCachedThreadPool or newFixedThreadPool method of the Executors class.
 * Call submit to submit Callable or Runnable objects.
 * Hang on to the returned Future objects so that you can get the results or cancel the tasks.
 * Call shutdown when you no longer want to submit any tasks.
 *
 * The ScheduledExecutorService interface has methods for scheduled or repeated
 * execution of tasks. It is a generalization of java.util.Timer that allows for
 * thread pooling. The newScheduledThreadPool and newSingleThreadScheduledExecutor
 * methods of the Executors class return objects that implement the
 * ScheduledExecutorService interface.
 *
 * You can schedule a Runnable or Callable to run once, after an initial delay.
 * You can also schedule a Runnable to run periodically.
 */
import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class CallableFuturePool {
    /**
     * Counts occurrences of a given word in a file.
     * @return the number of times the word occurs in the given word
     */
    public static long occurrences(String word, Path path)  {
        try (var in = new Scanner(path))  {
            int count = 0;
            while (in.hasNext())
                if (in.next().equals(word)) count++;
            return count;
        }
        catch (IOException ex)  {
            return 0;
        }
    }

    /**
     * Returns all descendants of a given directory--see Chapters 1 and 2 of Volume II.
     * @param rootDir the root directory
     * @return a set of all descendants of the root directory
     */
    public static Set<Path> descendants(Path rootDir) throws IOException  {
        try (Stream<Path> entries = Files.walk(rootDir))  {
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
    public static Callable<Path> searchForTask(String word, Path path)  {
        return () ->  {
            try (var in = new Scanner(path))  {
                while (in.hasNext())  {
                    if (in.next().equals(word))
                        return path;
                    if (Thread.currentThread().isInterrupted())  {
                        System.out.println("Search in " + path + " canceled.");
                        return null;
                    }
                }
                throw new NoSuchElementException();
            }
        };
    }

    public static void main(String[] args)
            throws InterruptedException, ExecutionException, IOException  {
        try (var in = new Scanner(System.in))  {
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
