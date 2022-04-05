/**
 * Demos Blocking Queue - a great synchronization mechanism for threads
 * Many threading problems can be formulated elegantly and safely by using one
 * or more queues. Producer threads insert items into the queue, and consumer
 * threads retrieve them. The queue lets you safely hand over data from one
 * thread to another.
 * A blocking queue causes a thread to block when you try to add an element
 * when the queue is currently full or to remove an element when the queue is
 * empty. Blocking queues are a useful tool for coordinating the work of
 * multiple threads. Worker threads can periodically deposit intermediate
 * results into a blocking queue. Other worker threads remove the intermediate
 * results and modify them further. The queue automatically balances the workload.
 * If the first set of threads runs slower than the second, the second set
 * blocks while waiting for the results. If the first set of threads runs
 * faster, the queue fills up until the second set catches up.
 * No synchronization is necessary. (Of course, the implementors of the
 * thread-safe queue classes had to worry about locks and conditions, but
 * that is their problem, not ours.)
 *
 * ArrayBlockingQueue(int capacity)
 * ArrayBlockingQueue(int capacity, boolean fair)
 * construct a blocking queue with the given capacity and fairness settings.
 * The queue is implemented as a circular array.
 *
 * LinkedBlockingQueue()
 * LinkedBlockingDeque()
 * construct an unbounded blocking queue or deque, implemented as a linked list.
 * LinkedBlockingQueue(int capacity)
 * LinkedBlockingDeque(int capacity)
 * construct a bounded blocking queue or deque with the given capacity,
 * implemented as a linked list.
 *
 * DelayQueue()
 * constructs an unbounded blocking queue of Delayed elements. Only elements
 * whose delay has expired can be removed from the queue.
 *
 * PriorityBlockingQueue()
 * PriorityBlockingQueue(int initialCapacity)
 * PriorityBlockingQueue(int initialCapacity, Comparator<? super E> comparator)
 * construct an unbounded blocking priority queue implemented as a heap.
 * The default for the initial capacity is 11. If the comparator is not
 * specified, the elements must implement the Comparable interface.
 *
 * The program searches through all files in a directory and its subdirectories,
 * printing lines that contain a given keyword.
 *
 * A producer thread enumerates all files in all subdirectories and places
 * them in a blocking queue. This operation is fast, and the queue would
 * quickly fill up with all files in the file system if it was not bounded.
 *
 * Each search thread takes a file from the queue, opens it, prints all
 * lines containing the keyword, and then takes the next file. We use a
 * trick to terminate the application when no further work is required.
 * In order to signal completion, the enumeration thread places a dummy
 * object into the queue. (This is similar to a dummy suitcase with a
 * label “last bag” in a baggage claim belt.) When a search thread takes
 * the dummy, it puts it back and terminates.
 *
 * Note that no explicit thread synchronization is required. In this
 * application, we use the queue data structure as a synchronization mechanism.
 */
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class BlockdeQueleil {
    private static final int FILE_QUEUE_SIZE = 10;
    private static final int SEARCH_THREADS = 100;
    private static final Path DUMMY = Path.of("");
    private static BlockingQueue<Path> queue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

    public static void main(String[] args)  {
        try (var in = new Scanner(System.in))  {
            System.out.print("Enter base directory (e.g. /opt/jdk-11-src): ");
            String directory = in.nextLine();
            System.out.print("Enter keyword (e.g. start): ");
            String keyword = in.nextLine();

            Runnable enumerator = () ->  {
                try  {
                    enumerate(Path.of(directory));
                    queue.put(DUMMY);
                }
                catch (IOException e)  {
                    e.printStackTrace();
                }
                catch (InterruptedException e)  {
                }
            };

            new Thread(enumerator).start();
            for (int i = 1; i <= SEARCH_THREADS; i++)  {
                Runnable searcher = () ->  {
                    try  {
                        boolean done = false;
                        while (!done)  {
                            Path file = queue.take();
                            if (file == DUMMY)  {
                                queue.put(file);
                                done = true;
                            }
                            else search(file, keyword);
                        }
                    }
                    catch (IOException e)  {
                        e.printStackTrace();
                    }
                    catch (InterruptedException e)  {
                    }
                };
                new Thread(searcher).start();
            }
        }
    }

    /**
     * Recursively enumerates all files in a given directory and its subdirectories.
     * See Chapters 1 and 2 of Volume II for the stream and file operations.
     * @param directory the directory in which to start
     */
    public static void enumerate(Path directory) throws IOException, InterruptedException
    {
        try (Stream<Path> children = Files.list(directory))
        {
            for (Path child : children.toList())
            {
                if (Files.isDirectory(child))
                    enumerate(child);
                else
                    queue.put(child);
            }
        }
    }

    /**
     * Searches a file for a given keyword and prints all matching lines.
     * @param file the file to search
     * @param keyword the keyword to search for
     */
    public static void search(Path file, String keyword) throws IOException
    {
        try (var in = new Scanner(file, StandardCharsets.UTF_8))
        {
            int lineNumber = 0;
            while (in.hasNextLine())
            {
                lineNumber++;
                String line = in.nextLine();
                if (line.contains(keyword))
                    System.out.printf("%s:%d:%s%n", file, lineNumber, line);
            }
        }
    }
}
