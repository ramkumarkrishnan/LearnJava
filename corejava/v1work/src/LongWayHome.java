/**
 * Demo worker threads for Long Running Tasks in UI Programming
 * One of the reasons to use threads is to make your programs more responsive. This is particularly important in an application with a user interface. When your program needs to do something time consuming, you cannot do the work in the user-interface thread, or the user interface will be frozen. Instead, fire up another worker thread.
 *
 * if you want to read a file when the user clicks a button, do the work in a separate thread.
 * open.addActionListener(event ->
 *    { // GOOD--long-running action in separate thread
 *       Runnable task = () ->
 *          {
 *             var in = new Scanner(file);
 *             while (in.hasNextLine())
 *             {
 *                String line = in.nextLine();
 *                . . .
 *             }
 *          };
 *       executor.execute(task);
 *    });
 *  However, you cannot directly update the user interface from the worker thread that executes the long-running task. User interfaces such as Swing, JavaFX, or Android are not thread-safe. You cannot manipulate user interface elements from multiple threads, or they risk becoming corrupted. In fact, JavaFX and Android check for this, and throw an exception if you try to access the user interface from a thread other than the UI thread.
 *  Therefore, you need to schedule any UI updates to happen on the UI thread. Each user interface library provides some mechanism to schedule a Runnable for execution on the UI thread. For example, in Swing, you call
 *  EventQueue.invokeLater(() -> label.setText(percentage + "% complete"));
 *  It is tedious to implement user feedback in a worker thread, so each user interface library provides some kind of helper class for managing the details, such as SwingWorker in Swing, Task in JavaFX, and AsyncTask in Android. You specify actions for the long-running task (which is run on a separate thread), as well as progress updates and the final disposition (which are run on the UI thread).
 *  This program has commands for loading a text file and for canceling the file loading process. You should try the program with a long file, such as the full text of The Count of Monte Cristo, supplied in the gutenberg directory of the book’s companion code. The file is loaded in a separate thread. While the file is being read, the Open menu item is disabled and the Cancel item is enabled (see Figure 12.6). After each line is read, a line counter in the status bar is updated. After the reading process is complete, the Open menu item is reenabled, the Cancel item is disabled, and the status line text is set to Done.
 *  This example shows the typical UI activities of a background task:
 *
 * After each work unit, update the UI to show progress.
 *
 * After the work is finished, make a final change to the UI.
 *
 * The SwingWorker class makes it easy to implement such a task. Override the doInBackground method to do the time-consuming work and occasionally call publish to communicate work progress. This method is executed in a worker thread. The publish method causes a process method to execute in the event dispatch thread to deal with the progress data. When the work is complete, the done method is called in the event dispatch thread so that you can finish updating the UI.
 *
 * Whenever you want to do some work in the worker thread, construct a new worker. (Each worker object is meant to be used only once.) Then call the execute method. You will typically call execute on the event dispatch thread, but that is not a requirement.
 * It is assumed that a worker produces a result of some kind; therefore, SwingWorker<T, V> implements Future<T>. This result can be obtained by the get method of the Future interface. Since the get method blocks until the result is available, you don’t want to call it immediately after calling execute. It is a good idea to call it only when you know that the work has been completed. Typically, you call get from the done method. (There is no requirement to call get. Sometimes, processing the progress data is all you need.)
 *
 * Both the intermediate progress data and the final result can have arbitrary types. The SwingWorker class has these types as type parameters. A SwingWorker<T, V> produces a result of type T and progress data of type V.
 *
 * To cancel the work in progress, use the cancel method of the Future interface. When the work is canceled, the get method throws a CancellationException.
 *
 * As already mentioned, the worker thread’s call to publish will cause calls to process on the event dispatch thread. For efficiency, the results of several calls to publish may be batched up in a single call to process. The process method receives a List<V> containing all intermediate results.
 * Let us put this mechanism to work for reading in a text file. As it turns out, a JTextArea is quite slow. Appending lines from a long text file (such as all lines in The Count of Monte Cristo) takes considerable time.
 *
 * To show the user that progress is being made, we want to display the number of lines read in a status line. Thus, the progress data consist of the current line number and the current line of text. We package these into a trivial inner class:
 * private class ProgressData
 * {
 *    public int number;
 *    public String line;
 * }
 * The final result is the text that has been read into a StringBuilder. Thus, we need a SwingWorker<StringBuilder, ProgressData>.
 *
 * In the doInBackground method, we read a file, a line at a time. After each line, we call publish to publish the line number and the text of the current line.
 * @Override public StringBuilder doInBackground() throws IOException, InterruptedException
 * {
 *    int lineNumber = 0;
 *    var in = new Scanner(new FileInputStream(file), StandardCharsets.UTF_8);
 *    while (in.hasNextLine())
 *    {
 *       String line = in.nextLine();
 *       lineNumber++;
 *       text.append(line).append("\n");
 *       var data = new ProgressData();
 *       data.number = lineNumber;
 *       data.line = line;
 *       publish(data);
 *       Thread.sleep(1); // to test cancellation; no need to do this in your programs
 *    }
 *    return text;
 * }
 * We also sleep for a millisecond after every line so that you can test cancellation without getting stressed out, but you wouldn’t want to slow down your own programs by sleeping. If you comment out this line, you will find that The Count of Monte Cristo loads quite quickly, with only a few batched user interface updates.
 *
 * In the process method, we ignore all line numbers but the last one, and we concatenate all lines for a single update of the text area.
 * @Override public void process(List<ProgressData> data)
 * {
 *    if (isCancelled()) return;
 *    var b = new StringBuilder();
 *    statusLine.setText("" + data.get(data.size() - 1).number);
 *    for (ProgressData d : data) b.append(d.line).append("\n");
 *    textArea.append(b.toString());
 * }
 * In the done method, the text area is updated with the complete text, and the Cancel menu item is disabled.
 *
 * Note how the worker is started in the event listener for the Open menu item.
 *
 * This simple technique allows you to execute time-consuming tasks while keeping the user interface responsive.
 */
import java.awt.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

import javax.swing.*;

public class LongWayHome {
    public static void main(String[] args) throws Exception
    {
        EventQueue.invokeLater(() ->
        {
            var frame = new SwingWorkerFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * This frame has a text area to show the contents of a text file, a menu to open a file and
 * cancel the opening process, and a status line to show the file loading progress.
 */
class SwingWorkerFrame extends JFrame
{
    private JFileChooser chooser;
    private JTextArea textArea;
    private JLabel statusLine;
    private JMenuItem openItem;
    private JMenuItem cancelItem;
    private SwingWorker<StringBuilder, ProgressData> textReader;
    public static final int TEXT_ROWS = 20;
    public static final int TEXT_COLUMNS = 60;

    public SwingWorkerFrame()
    {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));

        textArea = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        add(new JScrollPane(textArea));

        statusLine = new JLabel(" ");
        add(statusLine, BorderLayout.SOUTH);

        var menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        var menu = new JMenu("File");
        menuBar.add(menu);

        openItem = new JMenuItem("Open");
        menu.add(openItem);
        openItem.addActionListener(event ->
        {
            // show file chooser dialog
            int result = chooser.showOpenDialog(null);

            // if file selected, set it as icon of the label
            if (result == JFileChooser.APPROVE_OPTION)
            {
                textArea.setText("");
                openItem.setEnabled(false);
                textReader = new TextReader(chooser.getSelectedFile());
                textReader.execute();
                cancelItem.setEnabled(true);
            }
        });

        cancelItem = new JMenuItem("Cancel");
        menu.add(cancelItem);
        cancelItem.setEnabled(false);
        cancelItem.addActionListener(event -> textReader.cancel(true));
        pack();
    }

    private class ProgressData
    {
        public int number;
        public String line;
    }

    private class TextReader extends SwingWorker<StringBuilder, ProgressData>
    {
        private File file;
        private StringBuilder text = new StringBuilder();

        public TextReader(File file)
        {
            this.file = file;
        }

        // the following method executes in the worker thread; it doesn't touch Swing components

        public StringBuilder doInBackground() throws IOException, InterruptedException
        {
            int lineNumber = 0;
            try (var in = new Scanner(new FileInputStream(file), StandardCharsets.UTF_8))
            {
                while (in.hasNextLine())
                {
                    String line = in.nextLine();
                    lineNumber++;
                    text.append(line).append("\n");
                    var data = new ProgressData();
                    data.number = lineNumber;
                    data.line = line;
                    publish(data);
                    Thread.sleep(1); // to test cancellation; no need to do this in your programs
                }
            }
            return text;
        }

        // the following methods execute in the event dispatch thread

        public void process(List<ProgressData> data)
        {
            if (isCancelled()) return;
            var builder = new StringBuilder();
            statusLine.setText("" + data.get(data.size() - 1).number);
            for (ProgressData d : data) builder.append(d.line).append("\n");
            textArea.append(builder.toString());
        }

        public void done()
        {
            try
            {
                StringBuilder result = get();
                textArea.setText(result.toString());
                statusLine.setText("Done");
            }
            catch (InterruptedException ex)
            {
            }
            catch (CancellationException ex)
            {
                textArea.setText("");
                statusLine.setText("Cancelled");
            }
            catch (ExecutionException ex)
            {
                statusLine.setText("" + ex.getCause());
            }

            cancelItem.setEnabled(false);
            openItem.setEnabled(true);
        }
    };
}



