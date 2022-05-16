package v2work.ramkrish.mystream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/***
 * Compared to collections, streams provide a view of data that lets you specify
 * computations at a higher conceptual level. With a stream, you specify what
 * you want to have done, not how to do it. You leave the scheduling of
 * operations to the implementation. For example, suppose you want to compute
 * the average of a certain property. You specify the source of data and the
 * property, and the stream library can then optimize the computation, for
 * example by using multiple threads for computing sums and counts and
 * combining the results.
 *
 * Count long words in text file using Stream - rather than procedural count
 */

public class StreamCount {
    public static void main(String[] args) throws IOException {
        var contents = Files.readString(Path.of("../gutenberg/alice30.txt"));
        List<String> words = List.of(contents.split("\\PL+"));

        // procedural way - you should be aware of efficiency
        long count = 0;
        for (String w : words)  {
            if (w.length() > 12) count++;
        }
        System.out.println("Count via Procedural way: "  + count);

        // streams functional way
        count = words.stream().filter(w -> w.length() > 12).count();
        System.out.println("Count via Streams function: " + count);

        // streams parallel streams
        count = words.parallelStream().filter(w -> w.length() > 12).count();
        System.out.println("Count via Parallel Streams: " + count);
    }
}