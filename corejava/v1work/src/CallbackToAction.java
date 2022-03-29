/**
 * Demos how to implement callbacks in Java
 * Rather than a callback function, you pass an object instance here
 */
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import javax.swing.*;

import static java.lang.System.out;

public class CallbackToAction {
    public static void main(String[] args) {
        var listener = new TimerPrint();

        // construct a timer that calls the listener every second
        var timer = new Timer(1000, listener);
        timer.start();

        // keep running till user inputs OK
        JOptionPane.showMessageDialog(null, "Quit?");
        System.exit(0);
    }
}

class TimerPrint implements ActionListener {
    public void actionPerformed(ActionEvent event) {
        out.println("At the tone, the time is " + Instant.ofEpochMilli(event.getWhen()));
        Toolkit.getDefaultToolkit().beep();
    }
}
