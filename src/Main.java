import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.application.Platform;

import java.util.Scanner;

/**
 *
 */

public class Main {

    public static void main(String[] args) {

        // get GUIModel instance

        Application.launch(GUI.class, args);
//        Runnable gui = new GUI();
//        Thread guiThread = new Thread(gui);
//        guiThread.run();

        // open COM port
        // SerialComm stm32Comm = new SerialComm(SerialComm.comPort);
    }

}
