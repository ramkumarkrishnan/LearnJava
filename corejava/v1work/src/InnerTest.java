/**
 * Demo  inner class
 * Inner class is a class defined inside another class
 * Inner classes can be hidden from other classes in the same package.
 * Inner class methods can access the data from the scope in which they are
 * defined—including the data that would otherwise be private.
 * Inner classes used to be very important for concisely implementing
 * callbacks, but nowadays lambda expressions do a much better job. Still,
 * inner classes can be very useful for structuring your code
 */
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import javax.swing.*;

public class InnerTest {
    public static void main(String[] args) {
        var clock = new TalkingClock(1000, true);
        clock.start();

        // keep program running until the user selects "OK"
        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

/**
 * A clock that prints the time in regular intervals.
 */
class TalkingClock {
    private int interval;
    private boolean beep;

    /**
     * Constructs a talking clock
     * @param interval the interval between messages (in milliseconds)
     * @param beep true if the clock should beep
     */
    public TalkingClock(int interval, boolean beep)  {
        this.interval = interval;
        this.beep = beep;
    }

    /**
     * Starts the clock.
     */
    public void start()  {
        // Method of the outer class TalkingClock creates an instance of the
        // inner class TimerPrinter
        var listener = new TimePrinter();

        // .. and provides that instance as a parameter to the Java Timer class
        var timer = new Timer(interval, listener);
        timer.start();
    }

    public class TimePrinter implements ActionListener  {
        public void actionPerformed(ActionEvent event)   {
            System.out.println("At the tone, the time is "
                    + Instant.ofEpochMilli(event.getWhen()));

            /*
             * Had the TimePrinter class been a regular class, it would have
             * needed to access the beep flag through a public method of the
             * TalkingClock class. Using an inner class is an improvement.
             * There is no need to provide accessors that are of interest
             * only to one other class
             */
            if (beep) Toolkit.getDefaultToolkit().beep();
        }
    }
}

/* If TimerPrinter was being used only within the start() mmethod, we could
 * have defined the class inside the start() method as follows:
public void start()  {
   class TimePrinter implements ActionListener  {
      public void actionPerformed(ActionEvent event)  {
         System.out.println("At the tone, the time is "
            + Instant.ofEpochMilli(event.getWhen()));
         if (beep) Toolkit.getDefaultToolkit().beep();
      }
   }

   var listener = new TimePrinter();
   var timer = new Timer(interval, listener);
   timer.start();
}
 */

/* Further, if only one instance was going to be created, we could have
 * defined an ANONYMOUS INNER CLASS - WITHOUT THE TIMERPRINTER CLASS NAME
 * as follows:
public void start(int interval, boolean beep)  {
   var listener = new ActionListener()  {
      public void actionPerformed(ActionEvent event)  {
            System.out.println("At the tone, the time is "
               + Instant.ofEpochMilli(event.getWhen()));
            if (beep) Toolkit.getDefaultToolkit().beep();
      }
   };
   var timer = new Timer(interval, listener);
   timer.start();
}
 */