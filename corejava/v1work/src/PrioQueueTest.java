/**
 * Demo priority queue - without sorting it will provide the smallest element
 * during queue removal.  Built using self organizing binary tree.
 * A priority queue retrieves elements in sorted order after they were inserted
 * in arbitrary order. That is, whenever you call the remove method, you get the
 * smallest element currently in the priority queue. However, the priority queue
 * does not sort all its elements. If you iterate over the elements, they are not
 * necessarily sorted. The priority queue makes use of an elegant and efficient
 * data structure called a heap. A heap is a self-organizing binary tree in which
 * the add and remove operations cause the smallest element to gravitate to the
 * root, without wasting time on sorting all elements.
 * A typical use for a priority queue is job scheduling. Each job has a priority.
 * Jobs are added in random order. Whenever a new job can be started, the highest
 * priority job is removed from the queue. (Since it is traditional for priority
 * 1 to be the “highest” priority, the remove operation yields the minimum element.
 */
import java.time.LocalDate;
import java.util.*;

public class PrioQueueTest {
    public static void main(String[] args)
    {
        var pq = new PriorityQueue<LocalDate>();

        pq.add(LocalDate.of(1906, 12, 9)); // G. Hopper
        pq.add(LocalDate.of(1815, 12, 10)); // A. Lovelace
        pq.add(LocalDate.of(1903, 12, 3)); // J. von Neumann
        pq.add(LocalDate.of(1912, 6, 23)); // A. Turing

        System.out.println("Iterating over elements . . .");
        for (LocalDate date : pq)
            System.out.println(date);

        System.out.println("Removing elements . . .");
        while (!pq.isEmpty())
            System.out.println(pq.remove());
    }
}
