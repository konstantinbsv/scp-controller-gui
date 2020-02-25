import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Label;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    static final int refreshDelay = 50; // interface refresh delay in milliseconds

    SerialPort stmPort;
    InputStream stmIn;
    static final String comPort = "COM8";
    static final int baudRate = 115200;
    static final int dataBits = 8;
    static final int stopBits = 1;
    static final int parity = 0;

    GUIModel model;

    public Label voltage_scp1;
    public Label current_scp1;
    public Label power_scp1;
    public Label temp_scp1;
    public Label duty_cycle_scp1;
    public AreaChart<Number, Number> current_chart_scp1;
    public AreaChart<Number, Number> temp_chart_scp1;

    public Label voltage_scp2;
    public Label current_scp2;
    public Label power_scp2;
    public Label temp_scp2;
    public Label duty_cycle_scp2;
    public AreaChart<Number, Number> current_chart_scp2;
    public AreaChart<Number, Number> temp_chart_scp2;

    public Label voltage_scp3;
    public Label current_scp3;
    public Label power_scp3;
    public Label temp_scp3;
    public Label duty_cycle_scp3;
    public AreaChart<Number, Number> current_chart_scp3;
    public AreaChart<Number, Number> temp_chart_scp3;

    int i = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = GUIModel.getInstance(); // get GUI model instance (singleton)

        /* bind text properties to model */
        // SCP1
        current_chart_scp1.getData().add(model.getCurrentSeriesSCP1());
        temp_chart_scp1.getData().add(model.getTempSeriesSCP1());

        // SCP2
        current_chart_scp2.getData().add(model.getCurrentSeriesSCP2());
        temp_chart_scp2.getData().add(model.getTempSeriesSCP2());

        // SCP3
        current_chart_scp3.getData().add(model.getCurrentSeriesSCP3());
        temp_chart_scp3.getData().add(model.getTempSeriesSCP3());

        if (!initializeSerial()) {   // initialize serial communication with STM32
            System.exit(5);
        }
        startUpdateDaemonTask();    // start update daemon
    }

    /**
     * creates and starts and daemon task which calls functions necessary to update UI
     */
    private void startUpdateDaemonTask() {
        Task task = new Task<Void>() {
          @Override
          protected Void call() throws Exception {
              while (true) {
                  updateModelData();
                  Platform.runLater(() -> updateUI());
                  Thread.sleep(refreshDelay);
              }
          }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    private void updateUI(){
        // SCP1
        voltage_scp1.setText(model.getVoltageSCP1Property().getValue());
        current_scp1.setText(model.getCurrentSCP1Property().getValue());
        power_scp1.setText(model.getPowerSCP1Property().getValue());
        temp_scp1.setText(model.getTempSCP1Property().getValue());
        duty_cycle_scp1.setText(model.getDutyCycleSCP1Property().getValue());
        
        // SCP2
        voltage_scp2.setText(model.getVoltageSCP2Property().getValue());
        current_scp2.setText(model.getCurrentSCP2Property().getValue());
        power_scp2.setText(model.getPowerSCP2Property().getValue());
        temp_scp2.setText(model.getTempSCP2Property().getValue());
        duty_cycle_scp2.setText(model.getDutyCycleSCP2Property().getValue());

        // SCP3
        voltage_scp3.setText(model.getVoltageSCP3Property().getValue());
        current_scp3.setText(model.getCurrentSCP3Property().getValue());
        power_scp3.setText(model.getPowerSCP3Property().getValue());
        temp_scp3.setText(model.getTempSCP3Property().getValue());
        duty_cycle_scp3.setText(model.getDutyCycleSCP3Property().getValue());
        
    }

    /**
     * Updates interface with data from STM32
     * This function is called in an infinite loop by the startUpdateDaemonTask
     */
    private void updateModelData() {
        long startTime = System.nanoTime();

        // wait for start of packet
        String line  = getNextLine();
        if (!PacketPatterns.isPacketStart(line)) {
            System.out.println(line);
            return;
        }
       // System.out.println("Waiting time: " + (System.nanoTime() - startTime));

        // SCP1
        model.setVoltageSCP1(PacketPatterns.getStringValue(getNextLine()));
        model.setCurrentSCP1(PacketPatterns.getStringValue(getNextLine()));
        model.setPowerSCP1(PacketPatterns.getStringValue(getNextLine()));
        model.setTempSCP1(PacketPatterns.getStringValue(getNextLine()));

        // SCP2
        model.setVoltageSCP2(PacketPatterns.getStringValue(getNextLine()));
        model.setCurrentSCP2(PacketPatterns.getStringValue(getNextLine()));
        model.setPowerSCP2(PacketPatterns.getStringValue(getNextLine()));
        model.setTempSCP2(PacketPatterns.getStringValue(getNextLine()));

        // SCP3
        model.setVoltageSCP3(PacketPatterns.getStringValue(getNextLine()));
        model.setCurrentSCP3(PacketPatterns.getStringValue(getNextLine()));
        model.setPowerSCP3(PacketPatterns.getStringValue(getNextLine()));
        model.setTempSCP3(PacketPatterns.getStringValue(getNextLine()));

        getNextLine(); // removes END marker from packet buffer
        System.out.println("Update properties time: " + (System.nanoTime() - startTime) / 1000);

    }

    private String getNextLine() {
       // long startTime = System.nanoTime();

        char currentChar;
        StringBuilder bufferString = new StringBuilder();

        try {
            do {
                currentChar = (char) stmIn.read();
                bufferString.append(currentChar);
            } while (currentChar != '\r');
            // System.out.print(bufferString.toString());
        } catch (IOException io) {
            System.out.println("IO exception: " + io.toString());
        }

    //    long msElapsed = (System.nanoTime() - startTime) / 1000;
    //    System.out.println("Get next line time: " + msElapsed);
    //        if (msElapsed > 200_000) {
    //            System.out.println(bufferString.toString());
    //        }

        return bufferString.toString();
    }

    /**
     * Initializes serial communication with STM32
     *
     * @return true if initialization successful
     */
    boolean initializeSerial() {

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


