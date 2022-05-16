/**
 * Demo Fork Join framework
 * Some applications use a large number of threads that are mostly idle.
 * An example would be a web server that uses one thread per connection. Other
 * applications use one thread per processor core, in order to carry out
 * computationally intensive tasks, such as image or video processing.
 *
 * The fork-join framework, which appeared in Java 7, is designed to support
 * the latter.
 * if (problemSize < threshold)
 *    solve problem directly
 * else
 * {
 *    break problem into subproblems
 *    recursively solve each subproblem
 *    combine the results
 * }
 *
 * One example is image processing. To enhance an image, you can transform the
 * top half and the bottom half. If you have enough idle processors, those
 * operations can run in parallel. (You will need to do a bit of extra work
 * along the strip that separates the two halves, but that’s a technical detail.)
 *
 * Here, we discuss a simpler example. Suppose we want to count how many
 * elements of an array fulfill a particular property. We cut the array in
 * half, compute the counts of each half, and add them up.
 *
 * To put the recursive computation in a form that is usable by the framework,
 * supply a class that extends RecursiveTask<T> (if the computation produces a
 * result of type T) or RecursiveAction (if it does not produce a result).
 * Override the compute method to generate and invoke subtasks, and to combine
 * their results.
 *
 * Here, the invokeAll method receives a number of tasks and blocks until all
 * of them have completed. The join method yields the result. Here, we apply
 * join to each subtask and return the sum.
 * There is also a get method for getting the current result, but it is less
 * attractive since it can throw checked exceptions that we are not allowed
 * to throw in the compute method.
 *
 * Behind the scenes, the fork-join framework uses an effective heuristic,
 * called work stealing, for balancing the workload among available threads.
 * Each worker thread has a deque (double-ended queue) for tasks. A worker
 * thread pushes subtasks onto the head of its own deque. (Only one thread
 * accesses the head, so no locking is required.) When a worker thread is
 * idle, it “steals” a task from the tail of another deque. Since large
 * subtasks are at the tail, such stealing is rare.
 *
 */
import java.util.concurrent.*;
import java.util.function.*;
import java.util.logging.Filter;

import static java.lang.System.*;

public class JoinUntilDeathDoUsApart {
    public static void main(String[] args)  {
        final int SIZE = 10000000;
        var workloadArray = new double[SIZE];

        for (int i = 0; i < SIZE; i++)
            workloadArray[i] = Math.random();

        var counter = new FilterFulfilledCounter(workloadArray, 0, workloadArray.length, x -> x > 0.5);

        var pool = new ForkJoinPool();
        pool.invoke(counter);
        out.println(counter.join());
    }
}

class FilterFulfilledCounter extends RecursiveTask<Integer>  {
    public static final int THRESHOLD = 1000;
    private double[] numbers;
    private int from;
    private int to;
    private DoublePredicate filter; // DoublePredicate functional interface

    public FilterFulfilledCounter(double[] numbers, int from, int to, DoublePredicate filter)  {
        this.numbers = numbers;
        this.from = from;
        this.to = to;
        this.filter = filter;
    }

    // Implement compute() interface in RecursiveTask<> class
    protected Integer compute()  {
        if ((to - from) < THRESHOLD)  {
            int count = 0;
            for (int i = from; i < to; i++)  {
                if (filter.test(numbers[i])) count++;
            }
            return count;
        }
        else  {
            int mid = from + (to - from) / 2;

            var first = new FilterFulfilledCounter(numbers, from, mid, filter);
            var second = new FilterFulfilledCounter(numbers, mid, to, filter);

            // fork two pools to split the workloads
            invokeAll(first, second);

            // join the results of the two workloads
            return first.join() + second.join();
        }
    }
}