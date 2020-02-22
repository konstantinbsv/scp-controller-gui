import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControllerGUI extends Application {
    private static final String stageTitle = "SCP Controller v1.0";
    private Label scp1_current;

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ControllerGUI.fxml"));
        Scene scene = new Scene(root); // may wish to set default height and width?

        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.show();

    }

}