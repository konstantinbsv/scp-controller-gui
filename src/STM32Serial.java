import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class STM32Serial {

    static SerialPort stmPort;
    static InputStream stmIn;
    static final String comPort = "COM8";
    static final int baudRate = 115200;
    static final int dataBits = 8;
    static final int stopBits = 1;
    static final int parity = 0;

    static Logger logger = Logger.getLogger("STM32Serial");

    /**
     * Retrieves next line from serial (stops when it reaches '\r' or '\n')
     *
     * @return String
     */
    static String getNextLine() {

        char currentChar;
        StringBuilder bufferString = new StringBuilder();

        try {
            do {
                currentChar = (char) stmIn.read();
                bufferString.append(currentChar);
            } while (currentChar != '\r');
        } catch (IOException io) {
            System.out.println("IO exception: " + io.toString());
        }

        return bufferString.toString();
    }

    /**
     * Sends the new PID set points over serial according to the tx/rx protocol
     *
     */
    static void sendSetpoints(int[] setpoints) {
        assert (setpoints.length == 3); // array length must equal number of output channels to trigger interrupt in STM32

        StringBuilder pidString = new StringBuilder();
        for (int i = 0; i < 3; i++){

            if (setpoints[i] < 10) {
                pidString.append("00"); // STM32 expect 3 digits per set point
            }
            else if (setpoints[i] < 100) {
                pidString.append("0"); // STM32 expect 3 digits per set point
            }
            pidString.append(setpoints[i]);
            pidString.append("\r\n");
        }

        sendStringUART(pidString.toString());
    }

    /**
     * Send string over serial
     * @param inputString String to be sent
     */
    static void sendStringUART(String inputString) {
        logger.log(Level.INFO, inputString);        // send inputString to debugger
        byte[] buffer = inputString.getBytes();

        // send bytes in new thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                stmPort.writeBytes(buffer, buffer.length);
            }
        }).start();

    }

    /**
     * Initializes serial communication with STM32
     *
     * @return true if initialization successful
     */
    static boolean initializeSerial() {

        stmPort = SerialPort.getCommPort(comPort);
        stmPort.setComPortParameters(baudRate, dataBits, stopBits, parity);
        stmPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0 , 0);

        /* Initialize COM port */
        if (stmPort.openPort()) {
            System.out.println("Port " + comPort + " opened successfully!");
            // stmIn = new BufferedReader(new InputStreamReader(stmPort.getInputStream()));
            stmIn = stmPort.getInputStream();
            return true;
        } else {
            System.out.println("Port " + comPort + " unreachable");
            // TODO: request new port from user
            return false;
        }
    }

}
