/**
 * Demo for Completable Futures - wait free asynchronous computations.
 * complete program that reads a web page, scans it for images, loads the images and saves them locally.
 * approach to concurrent computation has been to break up a task, and then wait until all pieces have completed. But waiting is not always a good idea. In the following sections, you will see how to implement wait-free, or asynchronous, computations.
 * When you have a Future object, you need to call get to obtain the value, blocking until the value is available. The CompletableFuture class implements the Future interface, and it provides a second mechanism for obtaining the result. You register a callback that will be invoked (in some thread) with the result once it is available.
 * CompletableFuture<String> f = . . .;
 * f.thenAccept(s -> Process the result string s);
 * In this way, you can process the result without blocking once it is available.
 *
 * There are a few API methods that return CompletableFuture objects. For example, you can fetch a web page asynchronously with the HttpClient class that you will encounter in Chapter 4 of Volume II:
 * HttpClient client = HttpClient.newHttpClient();
 * HttpRequest request = HttpRequest.newBuilder(URI.create(urlString)).GET().build();
 * CompletableFuture<HttpResponse<String>> f = client.sendAsync(
 *    request, BodyHandlers.ofString());
 * It is nice if there is a method that produces a ready-made CompletableFuture, but most of the time, you need to make your own. To run a task asynchronously and obtain a CompletableFuture, you don’t submit it directly to an executor service. Instead, you call the static method CompletableFuture.supplyAsync. Here is how to read the web page without the benefit of the HttpClient class:
 * public CompletableFuture<String> readPage(URL url)
 * {
 *    return CompletableFuture.supplyAsync(() ->
 *       {
 *          try
 *          {
 *             return new String(url.openStream().readAllBytes(), "UTF-8");
 *          }
 *          catch (IOException e)
 *          {
 *             throw new UncheckedIOException(e);
 *          }
 *       }, executor);
 * }
 * If you omit the executor, the task is run on a default executor (namely the executor returned by ForkJoinPool.commonPool()). You usually don’t want to do that.
 * Note that the first argument of the supplyAsync method is a Supplier<T>, not a Callable<T>. Both interfaces describe functions with no arguments and a return value of type T, but a Supplier function cannot throw a checked exception. As you can see from the code above, that was not an inspired choice.
 * A CompletableFuture can complete in two ways: either with a result, or with an uncaught exception. In order to handle both cases, use the whenComplete method. The supplied function is called with the result (or null if none) and the exception (or null if none).
 * f.whenComplete((s, t) ->
 *    {
 *       if (t == null)
 *       {
 *          Process the result s;
 *       }
 *       else
 *       {
 *          Process the Throwable t;
 *       }
 *    });
 * The CompletableFuture is called completable because you can manually set a completion value. (In other concurrency libraries, such an object is called a promise.) Of course, when you create a CompletableFuture with supplyAsync, the completion value is implicitly set when the task has finished. But setting the result explicitly gives you additional flexibility. For example, two tasks can work simultaneously on computing an answer:
 * var f = new CompletableFuture<Integer>();
 * executor.execute(() ->
 *    {
 *       int n = workHard(arg);
 *       f.complete(n);
 *    });
 * executor.execute(() ->
 *    {
 *       int n = workSmart(arg);
 *       f.complete(n);
 *    });
 * To instead complete a future with an exception, call
 * Throwable t = . . .;
 * f.completeExceptionally(t);
 * It is safe to call complete or completeExceptionally on the same future in multiple threads. If the future is already completed, these calls have no effect.
 * The isDone method tells you whether a Future object has been completed (normally or with an exception). In the preceding example, the workHard and workSmart methods can use that information to stop working when the result has been determined by the other method.
 * Unlike a plain Future, the computation of a CompletableFuture is not interrupted when you invoke its cancel method. Canceling simply sets the Future object to be completed exceptionally, with a CancellationException. In general, this makes sense since a CompletableFuture may not have a single thread that is responsible for its completion. However, this restriction also applies to CompletableFuture instances returned by methods such as supplyAsync, which could in principle be interrupted.
 * Implementing Completable Futures:
 * Nonblocking calls are implemented through callbacks. The programmer registers a callback for the action that should occur after a task completes. Of course, if the next action is also asynchronous, the next action after that is in a different callback. Even though the programmer thinks in terms of “first do step 1, then step 2, then step 3,” the program logic can become dispersed in “callback hell.” It gets even worse when one has to add error handling. Suppose step 2 is “the user logs in.” You may need to repeat that step since the user can mistype the credentials. Trying to implement such a control flow in a set of callbacks, or to understand it once it has been implemented, can be quite challenging.
 * The CompletableFuture class solves this problem by providing a mechanism for composing asynchronous tasks into a processing pipeline.
 * For example, suppose we want to extract all images from a web page. Let’s say we have a method:
 * public CompletableFuture<String> readPage(URL url
 * that yields the text of a web page when it becomes available. If the method
 * public List<URL> getImageURLs(String page)
 * yields the URLs of images in an HTML page, you can schedule it to be called when the page is available:
 * CompletableFuture<String> contents = readPage(url);
 * CompletableFuture<List<URL>> imageURLs = contents.thenApply(this::getLinks);
 * The thenApply method doesn’t block either. It returns another future. When the first future has completed, its result is fed to the getImageURLs method, and the return value of that method becomes the final result.
 *
 * With completable futures, you just specify what you want to have done and in which order. It won’t all happen right away, of course, but what is important is that all the code is in one place.
 *
 * Conceptually, CompletableFuture is a simple API, but there are many variants of methods for composing completable futures. Let us first look at those that deal with a single future (see Table 12.3). In the table, I use a shorthand notation for the ponderous functional interfaces, writing T -> U instead of Function<? super T, U>. These aren’t actual Java types, of course.
 *
 * For each method shown, there are also two Async variants that I don’t show. One of them uses the common ForkJoinPool, and the other has an Executor parameter.
 *
 * You have already seen the thenApply method. Suppose f is a function that receives values of type T and returns values of type U. The calls
 * CompletableFuture<U> future.thenApply(f);
 * CompletableFuture<U> future.thenApplyAsync(f, executor);
 * return a future that applies the function f to the result of future when it is available. The second call runs f with yet another executor.
 *
 * The thenCompose method, instead of taking a function mapping the type T to the type U, receives a function mapping T to CompletableFuture<U>. That sounds rather abstract, but it can be quite natural. Consider the action of reading a web page from a given URL. Instead of supplying a method
 * public String blockingReadPage(URL url)
 * it is more elegant to have that method return a future:
 * public CompletableFuture<String> readPage(URL url)
 * Now, suppose we have another method that gets the URL from user input, perhaps from a dialog that won’t reveal the answer until the user has clicked the OK button. That, too, is an event in the future:
 * public CompletableFuture<URL> getURLInput(String prompt)
 * Here we have two functions T -> CompletableFuture<U> and U -> CompletableFuture<V>. Clearly, they compose to a function T -> CompletableFuture<V> if the second function is called when the first one has completed. That is exactly what thenCompose does.
 *
 * In the preceding section, you saw the whenComplete method for handling exceptions. There is also a handle method that requires a function processing the result or exception and computing a new result. In many cases, it is simpler to call the exceptionally method instead. That method computes a dummy value when an exception occurs:
 * CompletableFuture<List<URL>> imageURLs = readPage(url)
 *    .exceptionally(ex -> "<html></html>")
 *    .thenApply(this::getImageURLs)
 *  You can handle a timeout in the same way:
 * CompletableFuture<List<URL>> imageURLs = readPage(url)
 *    .completeOnTimeout("<html></html>", 30, TimeUnit.SECONDS)
 *    .thenApply(this::getImageURLs)
 * Alternatively, you can throw an exception on timeout:
 * CompletableFuture<String> = readPage(url).orTimeout(30, TimeUnit.SECONDS)
 * The first three methods run a CompletableFuture<T> and a CompletableFuture<U> action concurrently and combine the results.
 *
 * The next three methods run two CompletableFuture<T> actions concurrently. As soon as one of them finishes, its result is passed on, and the other result is ignored.
 *
 * Finally, the static allOf and anyOf methods take a variable number of completable futures and yield a CompletableFuture<Void> that completes when all of them, or any one of them, completes. The allOf method does not yield a result. The anyOf method does not terminate the remaining tasks.
 * Technically speaking, the methods in this section accept parameters of type CompletionStage, not CompletableFuture. The CompletionStage interface describes how to compose asynchronous computations, whereas the Future interface focuses on the result of a computation. A CompletableFuture is both a CompletionStage and a Future.
 * Note how all time-consuming methods return a CompletableFuture. To kick off the asynchronous computation, we use a little trick. Rather than calling the readPage method directly, we make a completed future with the URL argument, and then compose that future with this::readPage. That way, the pipeline has a very uniform appearance:
 * CompletableFuture.completedFuture(url)
 *    .thenComposeAsync(this::readPage, executor)
 *    .thenApply(this::getImageURLs)
 *    .thenCompose(this::getImages)
 *    .thenAccept(this::saveImages);
 */
import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

import javax.imageio.*;

public class WaitFreeOrDie {
    private static final Pattern IMG_PATTERN = Pattern.compile(
            "[<]\\s*[iI][mM][gG]\\s*[^>]*[sS][rR][cC]\\s*[=]\\s*['\"]([^'\"]*)['\"][^>]*[>]");
    private ExecutorService executor = Executors.newCachedThreadPool();
    private URL urlToProcess;

    public CompletableFuture<String> readPage(URL url)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            try
            {
                var contents = new String(url.openStream().readAllBytes(),
                        StandardCharsets.UTF_8);
                System.out.println("Read page from " + url);
                return contents;
            }
            catch (IOException e)
            {
                throw new UncheckedIOException(e);
            }
        }, executor);
    }

    public List<URL> getImageURLs(String webpage) // not time consuming
    {
        try
        {
            var result = new ArrayList<URL>();
            Matcher matcher = IMG_PATTERN.matcher(webpage);
            while (matcher.find())
            {
                var url = new URL(urlToProcess, matcher.group(1));
                result.add(url);
            }
            System.out.println("Found URLs: " + result);
            return result;
        }
        catch (IOException e)
        {
            throw new UncheckedIOException(e);
        }
    }

    public CompletableFuture<List<BufferedImage>> getImages(List<URL> urls)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            try
            {
                var result = new ArrayList<BufferedImage>();
                for (URL url : urls)
                {
                    result.add(ImageIO.read(url));
                    System.out.println("Loaded " + url);
                }
                return result;
            }
            catch (IOException e)
            {
                throw new UncheckedIOException(e);
            }
        }, executor);
    }

    public void saveImages(List<BufferedImage> images)
    {
        System.out.println("Saving " + images.size() + " images");
        try
        {
            for (int i = 0; i < images.size(); i++)
            {
                String filename = "/tmp/image" + (i + 1) + ".png";
                ImageIO.write(images.get(i), "PNG", new File(filename));
            }
        }
        catch (IOException e)
        {
            throw new UncheckedIOException(e);
        }
        executor.shutdown();
    }

    public void run(URL url)
            throws IOException, InterruptedException
    {
        urlToProcess = url;
        CompletableFuture.completedFuture(url)
                .thenComposeAsync(this::readPage, executor)
                .thenApply(this::getImageURLs)
                .thenCompose(this::getImages)
                .thenAccept(this::saveImages);

      /*
      // or use the HTTP client:

      HttpClient client = HttpClient.newBuilder().build();
      HttpRequest request = HttpRequest.newBuilder(urlToProcess.toURI()).GET()
         .build();
      client.sendAsync(request, BodyProcessors.ofString())
         .thenApply(HttpResponse::body)
         .thenApply(this::getImageURLs)
         .thenCompose(this::getImages)
         .thenAccept(this::saveImages);
      */
    }

    public static void main(String[] args)
            throws IOException, InterruptedException
    {
        new WaitFreeOrDie().run(new URL("http://horstmann.com/index.html"));
    }
}
