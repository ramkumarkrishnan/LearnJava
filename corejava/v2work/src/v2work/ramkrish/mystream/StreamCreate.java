package v2work.ramkrish.mystream;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.DoubleStream.generate;

/**
 * Demo Stream Creation, empty stream, etc
 * Don't modify the collection backing a stream while carrying out a stream
 * operation. Streams do not collect their data — the data is always in a
 * separate collection. If you modify that collection, the outcome of the
 * stream operations becomes undefined.
 *
 * When you use map, a function is applied to each element, and the result
 * is a new stream with the results. Occasionally, you are faced with a
 * mapping function that generates an optional result, or more than one
 * result. As an example, consider a method codePoints that yields all
 * code points of a string. For example, codePoints("Hello ") would consist
 * of "H", "e", "l", "l", "o", " ", "". Note that the globe symbol (U+1F310)
 * consists of two char values, so the codePoints method has to do some
 * heavy lifting to make this happen.
 *
 * How should codePoints collect its multiple results? For the stream API,
 * it is most natural if the method returns a Stream<String>.
 * The String class has a codePoints method that yields a stream of integer
 * code points:
 * Next, we need to transform each of the integers to a string containing
 * the Unicode character with the given code point. Unfortunately, there
 * is a technical difficulty. The codePoints method produces an IntStream,
 * which is a little different from a Stream<Integer>. Instead of map,
 * you use the mapToObj method to transform the elements. Perhaps surprisingly,
 * there is no convenient method for turning an integer codepoint into a string.
 */
public class StreamCreate {
    public static void main(String[] args) throws IOException {
        Path path = Path.of("../gutenberg/alice30.txt");

        // split words by space from contents
        var contents = Files.readString(path);
        Stream<String> words = Stream.of(contents.split("\\PL+"));
        showStream("WORDS", words);

        var contents2 = Files.readString(Path.of("../gutenberg/alice30.txt"));
        List<String> words2 = List.of(contents.split("\\PL+"));

        // map function to translate stream components
        Stream<String> uppercaseWords = words2.stream().map(String::toUpperCase);
        // Stream<String> lowercaseWords = words.stream().map(String::toLowerCase);
        showStream("UPPER-CASE", uppercaseWords);

        // Predicate<T> filter
        Stream<String> longWords = words2.stream().filter(w -> w.length() > 12);
        showStream("FILTERED", longWords);

        // Stream<String> firstLetters = words.stream().map(s -> s.substring(0, 1));
        Stream<Stream<String>> result = words2.stream().map(w -> codePoints(w));
        showStream("MAP-CODEPOINTS", result);

        // Calls codePoints on each word and flattens the results
        // When using flatMap, you provide a method that produces a new stream
        // for every stream element. As you have seen, that can be tedious.
        // It can also be a little inefficient.
        Stream<String> flatResult = words2.stream().flatMap(w -> codePoints(w));
        showStream("FLATMAP-CODEPOINTS", flatResult);

        // When using flatMap, you provide a method that produces a new stream
        // for every stream element, which is tedious and inefficient. The
        // mapMulti method offers an alternative. Instead of producing a
        // stream of results, you generate the results and pass them to a
        // collector—an object of a class implementing the functional interface
        // Consumer. For each result, invoke the collector’s accept method.
        // The following loop produces Codepoints of a string s.
        /*
         *
        int i = 0;
        while (i < s.length())
        {
            int cp = s.codePointAt(i);
            // Do something with cp
            if (Character.isSupplementaryCodePoint(cp)) i += 2;
            else i++;
        }
         */

        // When calling mapMulti, you provide a function that is invoked with
        // the stream element and the collector. In your function, pass your
        // results to the collector.
        Stream<String> multiResult = words2.stream().mapMulti((s, collector) ->
        {
            int i = 0;
            while (i < s.length())
            {
                int cp = s.codePointAt(i); // unicode code point at the ith
                collector.accept(String.valueOf(cp));
                /* int cp = s.codePointAt(i);
                 * collector.accept(Character.toString(i)); String.valueOf(cp));
                 */
                i += (Character.isSupplementaryCodePoint(cp)) ? 2 : 1;
            }
        });
        showStream("MAP-MULTI", multiResult);

        // The Scanner.tokens method yields a stream of tokens of a scanner.
        Stream<String> scanWords = new Scanner(contents).tokens();
        showStream("SCAN-WORDS", scanWords);

        // The of method has a varargs parameter, so you can construct a
        // stream from any number of arguments
        Stream<String> song = Stream.of("gently", "down", "the", "stream");
        showStream("SONGS", song);

        // array as a stream
        String StrArray[] = {"alpha", "beta", "gamma", "delta", "epsilon", "phi", "theta"};
        Stream<String> stringStream = Arrays.stream(StrArray, 2, 5);
        showStream("ARRAY", stringStream);

        // silent stream
        Stream<String> silence = Stream.empty();
        showStream("SILENCIO", silence);

        Stream<String> echoes = Stream.generate(() -> "Echo");
        showStream("ECHOES", echoes);

        // stream of random numbers
        Stream<Double> randoms = Stream.generate(Math::random);
        showStream("RANDOMS", randoms);

        // To view the contents of one of the streams introduced in this section,
        // use the toList method, which collects the stream’s elements in a list.
        // Like count, toList is a terminal operation. If the stream is infinite,
        // first truncate it with the limit method
        // The call stream.limit(n) returns a new stream that ends after n
        // elements (or when the original stream ends if it is shorter).
        // This method is particularly useful for cutting infinite streams
        // down to size.
        System.out.println("LIMIT: " + Stream.generate(Math::random).limit(10).toList());

        // The call stream.skip(n) does the exact opposite. It discards the
        // first n elements. This is handy in our book reading example where,
        // due to the way the split method works, the first element is an
        // unwanted empty string. We can make it go away by calling skip
        Stream<String> words4 = Stream.of(contents.split("\\PL+")).skip(1);
        showStream("SPLIT-SKIP", words4);

        // use the codePoints method of the preceding section to split a string
        // into characters, and we want to collect all initial digits.
        Stream<String> initialWords4 = codePoints(String.valueOf(scanWords)).takeWhile(s -> "This".contains(s));
        showStream("TAKE-WHILE", initialWords4);

        Stream<String> noInitialWords4 = codePoints(String.valueOf(scanWords)).dropWhile(s -> s.strip().length() == 0);
        showStream("DROP-WHILE", noInitialWords4);

        // Concat streams
        // Yields the stream ["H", "e", "l", "l", "o", "W", "o", "r", "l", "d"]
        Stream<String> combined = Stream.concat(codePoints("Hello"), codePoints("World"));
        showStream("CONCAT", combined);

        // Distinct
        Stream<String> distinctWords = Stream.of("merrily", "merrily", "merrily", "gently").distinct();
        showStream("DISTINCT", distinctWords);

        // Sort
        // variations of the sorted method. One works for streams of Comparable elements, and another accepts a Comparator
        Stream<String> longestFirst = words2.stream().sorted(Comparator.comparing(String::length).reversed());
        showStream("SORTED", longestFirst);

        // Peek - handy for debugging
        // the peek method yields another stream with the same elements as the
        // original, but a function is invoked every time an element is retrieved.
        // When you use a debugger to debug a stream computation, you can set a
        // breakpoint in a method that is called from one of the transformations.
        // With most IDEs, you can also set breakpoints in lambda expressions.
        // If you just want to know what happens at a particular point in the
        // stream pipeline, add
        // .peek(x ->
        //   {
        //      return;
        //   })
        Object[] powers = Stream.iterate(1.0, p -> p * 2)
                .peek(e -> System.out.println("Fetching " + e))
                .limit(20).toArray();

        // To produce sequences such as 0 1 2 3 . . ., use the iterate method
        // instead. It takes a “seed” value and a function (technically, a
        // UnaryOperator<T>) and repeatedly applies the function to the previous
        // result. The first element in the sequence is the seed BigInteger.ZERO.
        // The second element is f(seed) which yields 1 (as a big integer).
        // The next element is f(f(seed)) which yields 2, and so on.
        Stream<BigInteger> integers = Stream.iterate(BigInteger.ONE, n -> n.add(BigInteger.ONE));
        showStream("ITERATE", integers);

        Stream<String> greetings = "Hello\nGuten Tag\nBonjour".lines();
        showStream("GREETINGS", greetings);

        // The Pattern class has a method splitAsStream that splits a CharSequence
        // by a regular expression. You can use the following statement to split
        // a string into words:
        Stream<String> wordsAnotherWay = Pattern.compile("\\PL+").splitAsStream(contents);
        showStream("WORDSANOTHERWAY", wordsAnotherWay);

        // The static Files.lines method returns a Stream of all lines in a file
        try (Stream<String> lines = Files.lines(path)) {
            showStream("LINES", lines);
        }

        // You can turn an Iterable that is not a collection into a stream by calling
        Iterable<Path> iterable = FileSystems.getDefault().getRootDirectories();
        // turns an iterator into a splittable iterator of unknown size with the
        // given characteristics (a bit pattern containing constants such as
        // Spliterator.ORDERED).
        Stream<Path> rootDirectories = StreamSupport.stream(iterable.spliterator(), false);
        showStream("ROOTDIRS", rootDirectories);

        Iterator<Path> iterator = Path.of("/usr/share/dict/words").iterator();
        Stream<Path> pathComponents = StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED),
                false);
        showStream("PATH-COMPONENTS", pathComponents);
    }

    // Example of polymorphic function - output type same as input type
    public static <T> void showStream(String title, Stream<T> stream)  {
        final int SIZE = 20;
        List<T> firstElements = stream.limit(SIZE + 1).toList();

        System.out.print(title + ": ");
        for (int i = 0; i < firstElements.size(); i++)  {
            if (i > 0)
                System.out.print(", ");

            // print only the first SIZE and then a ... for the rest
            if (i < SIZE)
                System.out.print(firstElements.get(i));
            else
                System.out.print("...");
        }
        System.out.println();
    }

    public static Stream<String> codePoints(String s)
    {
        return s.codePoints().mapToObj(cp -> new String(new int [] { cp }, 0, 1));
    }
}