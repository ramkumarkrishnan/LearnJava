/**
 * Demo anonymous inner class
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

public class AnonInnerTest {
    public static void main(String[] args) {
        var clock = new AnonTalkingClock();
        clock.start(1000, true);

        // keep program running until the user selects "OK"
        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

class AnonTalkingClock
{
    /* NOTE: The absence of Constructor - compared to Inner Class */

    /**
     * Starts the clock.
     * @param interval the interval between messages (in milliseconds)
     * @param beep true if the clock should beep
     */
    public void start(int interval, boolean beep)
    {
        var listener = new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                System.out.println("At the tone, the time is "
                        + Instant.ofEpochMilli(event.getWhen()));
                if (beep) Toolkit.getDefaultToolkit().beep();
            }
        };
        var timer = new Timer(interval, listener);
        timer.start();
    }
}
