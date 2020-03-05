import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUIController implements Initializable {

    static final boolean DEBUG_MODE = true;

    static final int refreshDelay = 50; // interface refresh delay in milliseconds
    Logger logger = Logger.getLogger(getClass().getName());

    private GUIModel model;

    public ToggleButton activate_scp1;
    public Slider slider_scp1;
    public Label setpoint_label_scp1;
    public Label voltage_scp1;
    public Label current_scp1;
    public Label power_scp1;
    public Label temp_scp1;
    public Label duty_cycle_scp1;
    public AreaChart<Number, Number> current_chart_scp1;
    public AreaChart<Number, Number> temp_chart_scp1;

    public ToggleButton activate_scp2;
    public Slider slider_scp2;
    public Label setpoint_label_scp2;
    public Label voltage_scp2;
    public Label current_scp2;
    public Label power_scp2;
    public Label temp_scp2;
    public Label duty_cycle_scp2;
    public AreaChart<Number, Number> current_chart_scp2;
    public AreaChart<Number, Number> temp_chart_scp2;

    public ToggleButton activate_scp3;
    public Slider slider_scp3;
    public Label setpoint_label_scp3;
    public Label voltage_scp3;
    public Label current_scp3;
    public Label power_scp3;
    public Label temp_scp3;
    public Label duty_cycle_scp3;
    public AreaChart<Number, Number> current_chart_scp3;
    public AreaChart<Number, Number> temp_chart_scp3;

    // menu items
    public MenuItem change_pids_menu_item;
    public MenuItem about_menu;

    private int setpointSCP1Store;
    private int setpointSCP2Store;
    private int setpointSCP3Store;

    private boolean firstUIUpdate = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = GUIModel.getInstance(); // get GUI model instance (singleton)

        /* bind area chart series in model to chart views */
        // SCP1
        current_chart_scp1.getData().add(model.getCurrentSeriesSCP1());
        temp_chart_scp1.getData().add(model.getTempSeriesSCP1());
        // SCP2
        current_chart_scp2.getData().add(model.getCurrentSeriesSCP2());
        temp_chart_scp2.getData().add(model.getTempSeriesSCP2());
        // SCP3
        current_chart_scp3.getData().add(model.getCurrentSeriesSCP3());
        temp_chart_scp3.getData().add(model.getTempSeriesSCP3());

        // start update daemon
        startUpdateDaemonTask();

        // Configure sliders and slider labels
        initializeActivationButtons();
        initializeSliders();

        // Menu items
        initializeMenuItems();
    }

    /**
     * Initializes setpoint labels with temperature values from model
     */
    private void initializeWithSTM32Setpoints() {
        // get setpoints from current temperature of fiber.
        int setpointSCP1 = (int) Double.parseDouble(model.getTempSCP1Property().get());
        int setpointSCP2 = (int) Double.parseDouble(model.getTempSCP2Property().get());
        int setpointSCP3 = (int) Double.parseDouble(model.getTempSCP3Property().get());

        setpoint_label_scp1.setText(String.valueOf(setpointSCP1));
        setpoint_label_scp2.setText(String.valueOf(setpointSCP2));
        setpoint_label_scp3.setText(String.valueOf(setpointSCP3));

        model.setSetpointSCP1(setpointSCP1);
        model.setSetpointSCP2(setpointSCP2);
        model.setSetpointSCP3(setpointSCP3);
    }

    /**
     * Sets actions listeners on activations toggle buttons
     * links buttons to model
     */
    private void initializeActivationButtons() {
        // Activation button SCP1
        activate_scp1.setOnAction(event -> {
            if(activate_scp1.isSelected()){
                setpointSCP1Store = model.getSetpointSCP1();        // save current setpoint
                model.setSetpointSCP1(0);                           // set model to zero
                slider_scp1.setDisable(true);                       // disable slider
                activate_scp1.setText(GUIModel.inactiveToggleText); // set text to inactive
            } else {
                model.setSetpointSCP1(setpointSCP1Store);           // restore old setpoint
                slider_scp1.setDisable(false);                      // enable slider
                activate_scp1.setText(GUIModel.activeToggleText); // set text to active
            }
            STM32Serial.sendSetpoints(model.getSetpoints()); // update STM32 with 0 as setpoint (i.e., disable this channel)
        });

        // Activation button SCP2
        activate_scp2.setOnAction(event -> {
            if(activate_scp2.isSelected()){
                setpointSCP2Store = model.getSetpointSCP2();        // save current setpoint
                model.setSetpointSCP2(0);                           // set model to zero
                slider_scp2.setDisable(true);                       // disable slider
                activate_scp2.setText(GUIModel.inactiveToggleText); // set text to inactive
            } else {
                model.setSetpointSCP2(setpointSCP2Store);           // restore old setpoint
                slider_scp2.setDisable(false);                      // enable slider
                activate_scp2.setText(GUIModel.activeToggleText);   // set text to active
            }
            STM32Serial.sendSetpoints(model.getSetpoints()); // update STM32 with 0 as setpoint (i.e., disable this channel)
        });

        // Activation button SCP3
        activate_scp3.setOnAction(event -> {
            if(activate_scp3.isSelected()){
                setpointSCP3Store = model.getSetpointSCP3();        // save current setpoint
                model.setSetpointSCP3(0);                           // set model to zero
                slider_scp3.setDisable(true);                       // disable slider
                activate_scp3.setText(GUIModel.inactiveToggleText); // set text to inactive
            } else {
                model.setSetpointSCP3(setpointSCP3Store);           // restore old setpoint
                slider_scp3.setDisable(false);                      // enable slider
                activate_scp3.setText(GUIModel.activeToggleText);   // set text to active
            }
            STM32Serial.sendSetpoints(model.getSetpoints()); // update STM32 with 0 as setpoint (i.e., disable this channel)
        });
    }

    /**
     * Initializes setpoint sliders and labels
     * Creates EventHandler for each slider
     * Add listeners and links them to labels and model
     */
    private void initializeSliders() {
        // Slider 1
        EventHandler<Event> scp1CommEvent = event -> {  // serial communication event
            int newValue = (int) slider_scp1.getValue();
            model.setSetpointSCP1(newValue);
            STM32Serial.sendSetpoints(model.getSetpoints()); // send new slider setpoints to STM32
        };
        slider_scp1.setOnMouseReleased(scp1CommEvent);  // only when mouse is released prevent sending of two many values
        slider_scp1.setOnKeyReleased(scp1CommEvent);    // can use keyboard arrows to change setpoints
        slider_scp1.valueProperty().addListener(new ChangeListener<Number>() {  // update UI label value continuously
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setpoint_label_scp1.textProperty().setValue(String.valueOf(newValue.intValue())); // make int first to remove fractional digits
            }
        });

        // Slider 2
        EventHandler<Event> scp2CommEvent = event -> {
            int newValue = (int) slider_scp2.getValue();
            model.setSetpointSCP2(newValue);
            STM32Serial.sendSetpoints(model.getSetpoints()); // send new slider setpoints to STM32
        };
        slider_scp2.setOnMouseReleased(scp2CommEvent);
        slider_scp2.setOnKeyReleased(scp2CommEvent);
        slider_scp2.valueProperty().addListener(new ChangeListener<Number>() {  // update UI label value continuously
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setpoint_label_scp2.textProperty().setValue(String.valueOf(newValue.intValue())); // make int first to remove fractional digits
            }
        });

        // Slider 3
        EventHandler<Event> scp3CommEvent = event -> {
            int newValue = (int) slider_scp3.getValue();
            model.setSetpointSCP3(newValue);
            STM32Serial.sendSetpoints(model.getSetpoints()); // send new slider setpoints to STM32
        };
        slider_scp3.setOnMouseReleased(scp3CommEvent);
        slider_scp3.setOnKeyReleased(scp3CommEvent);
        slider_scp3.valueProperty().addListener(new ChangeListener<Number>() {  // update UI label value continuously
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setpoint_label_scp3.textProperty().setValue(String.valueOf(newValue.intValue())); // make int first to remove fractional digits
            }
        });
    }

    /**
     * Creates and starts and daemon task which calls functions necessary to update UI
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

    /**
     * Updates UI with data from model
     */
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

        model.updateAreaCharts();

        // if this is the first UI update, set the setpoint labels with data from STM32
        if (firstUIUpdate) {
            initializeWithSTM32Setpoints();
            firstUIUpdate = false;
        }
    }

    /**
     * Updates interface with data from STM32
     * This function is called in an infinite loop by the startUpdateDaemonTask
     */
    private void updateModelData() {
        long startTime = System.nanoTime();

        // wait for start of packet
        String line  = STM32Serial.getNextLine();
        if (!PacketPatterns.isPacketStart(line)) {
            System.out.println(line);
            return;
        }
       // System.out.println("Waiting time: " + (System.nanoTime() - startTime));

        // SCP1
        model.setVoltageSCP1(PacketPatterns.getStringValue(STM32Serial.getNextLine()));
        model.setCurrentSCP1(PacketPatterns.getStringValue(STM32Serial.getNextLine()));
        model.setPowerSCP1(PacketPatterns.getStringValue(STM32Serial.getNextLine()));
        model.setTempSCP1(PacketPatterns.getStringValue(STM32Serial.getNextLine()));
        model.setDutyCycleSCP1(PacketPatterns.getStringValue(STM32Serial.getNextLine(), false));

        // SCP2
        model.setVoltageSCP2(PacketPatterns.getStringValue(STM32Serial.getNextLine()));
        model.setCurrentSCP2(PacketPatterns.getStringValue(STM32Serial.getNextLine()));
        model.setPowerSCP2(PacketPatterns.getStringValue(STM32Serial.getNextLine()));
        model.setTempSCP2(PacketPatterns.getStringValue(STM32Serial.getNextLine()));
        model.setDutyCycleSCP2(PacketPatterns.getStringValue(STM32Serial.getNextLine(), false));

        // SCP3
        model.setVoltageSCP3(PacketPatterns.getStringValue(STM32Serial.getNextLine()));
        model.setCurrentSCP3(PacketPatterns.getStringValue(STM32Serial.getNextLine()));
        model.setPowerSCP3(PacketPatterns.getStringValue(STM32Serial.getNextLine()));
        model.setTempSCP3(PacketPatterns.getStringValue(STM32Serial.getNextLine()));
        model.setDutyCycleSCP3(PacketPatterns.getStringValue(STM32Serial.getNextLine(), false));

        STM32Serial.getNextLine(); // removes END marker from packet buffer

        String traceString = "Update properties time: " + (System.nanoTime() - startTime) / 1000;
        logger.log(Level.FINE, traceString);
    }


    /**
     * Sets listeners on menu items
     */
    void initializeMenuItems() {
        change_pids_menu_item.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("PIDView.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                scene.getStylesheets().add(GUI.cssRes);     // use same as main stage
                stage.setTitle("Change PID Coefficients");
                stage.setScene(scene);
                stage.getIcons().add(
                        new Image(
                                getClass().getResourceAsStream(GUI.iconRes),
                                100, 100, true, true)); // add icon
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



        about_menu.setOnAction(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("AboutView.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage(StageStyle.UTILITY);

                scene.getStylesheets().add(GUI.cssRes);
                stage.setTitle("About SCP Controller");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.getIcons().add(
                        new Image(getClass().getResourceAsStream(GUI.iconRes),
                                100, 100, true, true)); // add icon
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}


