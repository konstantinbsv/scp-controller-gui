import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.chart.XYChart;

public class GUIModel {

    public static final int MAX_CHART_DATA_POINTS = 250;
    private static GUIModel model_instance = null;

    /* gui properties */
    // SCP1
    private final StringProperty voltageSCP1Property = new SimpleStringProperty("***");
    private final StringProperty currentSCP1Property = new SimpleStringProperty("***");
    private final StringProperty powerSCP1Property = new SimpleStringProperty("***");
    private final StringProperty tempSCP1Property = new SimpleStringProperty("***");
    private final StringProperty dutyCycleSCP1Property = new SimpleStringProperty("***");

    // SCP2
    private final StringProperty voltageSCP2Property = new SimpleStringProperty("***");
    private final StringProperty currentSCP2Property = new SimpleStringProperty("***");
    private final StringProperty powerSCP2Property = new SimpleStringProperty("***");
    private final StringProperty tempSCP2Property = new SimpleStringProperty("***");
    private final StringProperty dutyCycleSCP2Property = new SimpleStringProperty("***");
    // SCP3
    private final StringProperty voltageSCP3Property = new SimpleStringProperty("***");
    private final StringProperty currentSCP3Property = new SimpleStringProperty("***");
    private final StringProperty powerSCP3Property = new SimpleStringProperty("***");
    private final StringProperty tempSCP3Property = new SimpleStringProperty("***");
    private final StringProperty dutyCycleSCP3Property = new SimpleStringProperty("***");

    /* Area Chart variables and arrays */
    int dataPointSCP = 0;

    // SCP1
    private XYChart.Series<Number, Number> current_series_scp1 = new XYChart.Series<>();
    private XYChart.Series<Number, Number> temp_series_scp1 = new XYChart.Series<>();

    // SCP2
    private XYChart.Series<Number, Number> current_series_scp2 = new XYChart.Series<>();
    private XYChart.Series<Number, Number> temp_series_scp2 = new XYChart.Series<>();

    // SCP3
    private XYChart.Series<Number, Number> current_series_scp3 = new XYChart.Series<>();
    private XYChart.Series<Number, Number> temp_series_scp3 = new XYChart.Series<>();

    private GUIModel() {

    }

    public static GUIModel getInstance() {
        if (model_instance == null) {
                model_instance = new GUIModel();
        }

        return model_instance;
    }

    /* SETTERS */
    public void setVoltageSCP1(String voltageSCP1) {
        this.voltageSCP1Property.set(voltageSCP1);
    }

    public final void setCurrentSCP1(String currentSCP1) {
        this.currentSCP1Property.set(currentSCP1);
        updateCurrentChartSeriesSCP1(Float.parseFloat(currentSCP1));
    }

    public void setPowerSCP1(String powerSCP1) {
        this.powerSCP1Property.set(powerSCP1);
    }

    public void setTempSCP1(String tempSCP1) {
        this.tempSCP1Property.set(tempSCP1);
        updateTempChartSeriesSCP1(Float.parseFloat(tempSCP1));
    }

    public void setDutyCycleSCP1(String dutyCycleSCP1) {
        this.dutyCycleSCP1Property.set(dutyCycleSCP1);
    }

    public void setVoltageSCP2(String voltageSCP2) {
        this.voltageSCP2Property.set(voltageSCP2);
    }

    public void setCurrentSCP2(String currentSCP2) {
        this.currentSCP2Property.set(currentSCP2);
        updateCurrentChartSeriesSCP2(Float.parseFloat(currentSCP2));
    }

    public void setPowerSCP2(String powerSCP2) {
        this.powerSCP2Property.set(powerSCP2);
    }

    public void setTempSCP2(String tempSCP2) {
        this.tempSCP2Property.set(tempSCP2);
        updateTempChartSeriesSCP2(Float.parseFloat(tempSCP2));

    }

    public void setDutyCycleSCP2(String dutyCycleSCP2) {
        this.dutyCycleSCP2Property.set(dutyCycleSCP2);
    }

    public void setVoltageSCP3(String voltageSCP3) {
        this.voltageSCP3Property.set(voltageSCP3);
    }

    public void setCurrentSCP3(String currentSCP3) {
        this.currentSCP3Property.set(currentSCP3);
        updateCurrentChartSeriesSCP3(Float.parseFloat(currentSCP3));

    }

    public void setPowerSCP3(String powerSCP3) {
        this.powerSCP3Property.set(powerSCP3);
    }

    public void setTempSCP3(String tempSCP3) {
        this.tempSCP3Property.set(tempSCP3);
        updateTempChartSeriesSCP3(Float.parseFloat(tempSCP3));
    }

    public void setDutyCycleSCP3(String dutyCycleSCP3) {
        this.dutyCycleSCP3Property.set(dutyCycleSCP3);
    }

    /* GETTERS */

    public StringProperty getVoltageSCP1Property() {
        return voltageSCP1Property;
    }

    public StringProperty getCurrentSCP1Property() {
        return currentSCP1Property;
    }

    public StringProperty getPowerSCP1Property() {
        return powerSCP1Property;
    }

    public StringProperty getTempSCP1Property() {
        return tempSCP1Property;
    }

    public StringProperty getDutyCycleSCP1Property() {
        return dutyCycleSCP1Property;
    }

    public StringProperty getVoltageSCP2Property() {
        return voltageSCP2Property;
    }

    public StringProperty getCurrentSCP2Property() {
        return currentSCP2Property;
    }

    public StringProperty getPowerSCP2Property() {
        return powerSCP2Property;
    }

    public StringProperty getTempSCP2Property() {
        return tempSCP2Property;
    }

    public StringProperty getDutyCycleSCP2Property() {
        return dutyCycleSCP2Property;
    }

    public StringProperty getVoltageSCP3Property() {
        return voltageSCP3Property;
    }

    public StringProperty getCurrentSCP3Property() {
        return currentSCP3Property;
    }

    public StringProperty getPowerSCP3Property() {
        return powerSCP3Property;
    }

    public StringProperty getTempSCP3Property() {
        return tempSCP3Property;
    }

    public StringProperty getDutyCycleSCP3Property() {
        return dutyCycleSCP3Property;
    }

    public XYChart.Series getCurrentSeriesSCP1() {
        return current_series_scp1;
    }

    public XYChart.Series getTempSeriesSCP1() {
        return temp_series_scp1;
    }

    public XYChart.Series getCurrentSeriesSCP2() {
        return current_series_scp2;
    }

    public XYChart.Series getTempSeriesSCP2() {
        return temp_series_scp2;
    }

    public XYChart.Series getCurrentSeriesSCP3() {
        return current_series_scp3;
    }

    public XYChart.Series getTempSeriesSCP3() {
        return temp_series_scp3;
    }

    /* Chart Data Updaters */
    private void updateCurrentChartSeriesSCP1(float currentSCP1) {
        current_series_scp1.getData().add(new XYChart.Data<>(dataPointSCP, currentSCP1));

        dataPointSCP++;
        if (dataPointSCP > MAX_CHART_DATA_POINTS) {
            dataPointSCP = 0;
            current_series_scp1.getData().clear();
            temp_series_scp1.getData().clear();
            current_series_scp2.getData().clear();
            temp_series_scp2.getData().clear();
            current_series_scp3.getData().clear();
            temp_series_scp3.getData().clear();
        }
    }

    private void updateTempChartSeriesSCP1(float tempSCP1) {
        temp_series_scp1.getData().add(new XYChart.Data<>(dataPointSCP, tempSCP1));
    }

    private void updateCurrentChartSeriesSCP2(float currentSCP2) {
        current_series_scp2.getData().add(new XYChart.Data<>(dataPointSCP, currentSCP2));
    }

    private void updateTempChartSeriesSCP2(float tempSCP2) {
        temp_series_scp2.getData().add(new XYChart.Data<>(dataPointSCP, tempSCP2));

    }

    private void updateCurrentChartSeriesSCP3(float currentSCP3) {
        current_series_scp3.getData().add(new XYChart.Data<>(dataPointSCP, currentSCP3));
    }

    private void updateTempChartSeriesSCP3(float tempSCP3) {
        temp_series_scp3.getData().add(new XYChart.Data<>(dataPointSCP, tempSCP3));

    }
}
