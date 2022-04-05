/**
 * Concurrent processes
 * how to execute Java code in separate threads within the same program. Sometimes, you need to execute another program. For this, use the ProcessBuilder and Process classes. The Process class executes a command in a separate operating system process and lets you interact with its standard input, output, and error streams. The ProcessBuilder class lets you configure a Process object.
 * Start by specifying the command that you want to execute. You can supply a List<String> or simply the strings that make up the command.
 * var builder = new ProcessBuilder("gcc", "myapp.c");
 * The first string must be an executable command, not a shell builtin. For example, to run the dir command in Windows, you need to build a process with strings "cmd.exe", "/C", and "dir".
 * Each process has a working directory, which is used to resolve relative directory names. By default, a process has the same working directory as the virtual machine, which is typically the directory from which you launched the java program. You can change it with the directory method:
 * builder = builder.directory(path.toFile());
 * Each of the methods for configuring a ProcessBuilder returns itself, so that you can chain commands. Ultimately, you will call
 * Process p = new ProcessBuilder(command).directory(file).…start();
 * Next, you will want to specify what should happen to the standard input, output, and error streams of the process. By default, each of them is a pipe that you can access with
 * OutputStream processIn = p.getOutputStream();
 * InputStream processOut = p.getInputStream();
 * InputStream processErr = p.getErrorStream();
 * Note that the input stream of the process is an output stream in the JVM! You write to that stream, and whatever you write becomes the input of the process. Conversely, you read what the process writes to the output and error streams. For you, they are input streams.
 *
 * You can specify that the input, output, and error streams of the new process should be the same as the JVM. If the user runs the JVM in a console, any user input is forwarded to the process, and the process output shows up in the console. Call
 * builder.inheritIO()
 * to make this setting for all three streams. If you only want to inherit some of the streams, pass the value
 * ProcessBuilder.Redirect.INHERIT
 * to the redirectInput, redirectOutput, or redirectError methods. For example,
 * builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
 * You can redirect the process streams to files by supplying File objects:
 * builder.redirectInput(inputFile)
 *    .redirectOutput(outputFile)
 *    .redirectError(errorFile)
 * The files for output and error are created or truncated when the process starts. To append to existing files, use
 * builder.redirectOutput(ProcessBuilder.Redirect.appendTo(outputFile));
 * It is often useful to merge the output and error streams, so you see the outputs and error messages in the sequence in which the process generates them. Call
 * builder.redirectErrorStream(true)
 * to activate the merging. If you do that, you can no longer call redirectError on the ProcessBuilder or getErrorStream on the Process.
 *
 * You may also want to modify the environment variables of the process. Here, the builder chain syntax breaks down. You need to get the builder’s environment (which is initialized by the environment variables of the process running the JVM), then put or remove entries.
 * Map<String, String> env = builder.environment();
 * env.put("LANG", "fr_FR");
 * env.remove("JAVA_HOME");
 * Process p = builder.start();
 * If you want to pipe the output of one process into the input of another (as with the | operator in a shell), Java 9 offers a startPipeline method. Pass a list of process builders and read the result from the last process. Here is an example, enumerating the unique extensions in a directory tree:
 * List<Process> processes = ProcessBuilder.startPipeline(List.of(
 *    new ProcessBuilder("find", "/opt/jdk-17"),
 *    new ProcessBuilder("grep", "-o", "\\.[^./]*$"),
 *    new ProcessBuilder("sort"),
 *    new ProcessBuilder("uniq")
 * ));
 * Process last = processes.get(processes.size() - 1);
 * var result = new String(last.getInputStream().readAllBytes());
 * Of course, this particular task would be more efficiently solved by making the directory walk in Java instead of running four processes. Chapter 2 of Volume II will show you how to do that.
 * After you have configured the builder, invoke its start method to start the process. If you configured the input, output, and error streams as pipes, you can now write to the input stream and read the output and error streams. For example,
 * Process process = new ProcessBuilder("/bin/ls", "-l")
 *    .directory(Path.of("/tmp").toFile())
 *    .start();
 * try (var in = new Scanner(process.getInputStream()))
 * {
 *    while (in.hasNextLine())
 *       System.out.println(in.nextLine());
 * }
 * There is limited buffer space for the process streams. You should not flood the input, and you should read the output promptly. If there is a lot of input and output, you may need to produce and consume it in separate threads.
 * To wait for the process to finish, call
 * int result = process.waitFor();
 * or, if you don’t want to wait indefinitely,
 * long delay = . . .;
 * if (process.waitFor(delay, TimeUnit.SECONDS))
 * {
 *    int result = process.exitValue();
 *    . . .
 * }
 * else
 * {
 *    process.destroyForcibly();
 * }
 * The first call to waitFor returns the exit value of the process (by convention, 0 for success or a nonzero error code). The second call returns true if the process didn’t time out. Then you need to retrieve the exit value by calling the exitValue method.
 *
 * Instead of waiting for the process to finish, you can just leave it running and occasionally call isAlive to see whether it is still alive. To kill the process, call destroy or destroyForcibly. The difference between these calls is platform-dependent. On UNIX, the former terminates the process with SIGTERM, the latter with SIGKILL. (The supportsNormalTermination method returns true if the destroy method can terminate the process normally.)
 *
 * Finally, you can receive an asynchronous notification when the process has completed. The call process.onExit() yields a CompletableFuture<Process> that you can use to schedule any action.
 * process.onExit().thenAccept(
 *    p -> System.out.println("Exit value: " + p.exitValue()));
 * To get more information about a process that your program started, or any other process that is currently running on your machine, use the ProcessHandle interface. You can obtain a ProcessHandle in four ways:
 *
 * Given a Process object p, p.toHandle() yields its ProcessHandle.
 *
 * Given a long operating system process ID, ProcessHandle.of(id) yields the handle of that process.
 *
 * ProcessHandle.current() is the handle of the process that runs this Java virtual machine.
 *
 * ProcessHandle.allProcesses() yields a Stream<ProcessHandle> of all operating system processes that are visible to the current process.
 *
 * Given a process handle, you can get its process ID, its parent process, its children, and descendants:
 * long pid = handle.pid();
 * Optional<ProcessHandle> parent = handle.parent();
 * Stream<ProcessHandle> children = handle.children();
 * Stream<ProcessHandle> descendants = handle.descendants()
 * The Stream<ProcessHandle> instances that are returned by the allProcesses, children, and descendants methods are just snapshots in time. Any of the processes in the stream might be terminated by the time you get around to seeing them, and other processes may have started that are not in the stream.
 * The info method yields a ProcessHandle.Info object with methods for obtaining information about the process.
 * Optional<String[]> arguments()
 * Optional<String> command()
 * Optional<String> commandLine()
 * Optional<String> startInstant()
 * Optional<String> totalCpuDuration()
 * Optional<String> user()
 * For monitoring or forcing process termination, the ProcessHandle interface has the same isAlive, supportsNormalTermination, destroy, destroyForcibly, and onExit methods as the Process class. However, there is no equivalent to the waitFor method.
 *
 */
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ConcurProcesses {
    public static void main(String[] args) throws IOException, InterruptedException
    {
        Process p = new ProcessBuilder("/bin/ls", "-l")
                .directory(Path.of("/tmp").toFile())
                .start();
        try (var in = new Scanner(p.getInputStream()))
        {
            while (in.hasNextLine())
                System.out.println(in.nextLine());
        }
        int result = p.waitFor();
        System.out.println("Exit value: " + result);
    }
}
