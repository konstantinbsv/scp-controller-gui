import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;



public class GUI extends Application implements Runnable {
    private static final String stageTitle = "SCP Controller v1.0";

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GUIView.fxml"));
        Scene scene = new Scene(root);
        // scene.getStylesheets().clear();
        scene.getStylesheets().add("modena_dark.css");
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    @Override
    public void run() {
        Application.launch(GUI.class, (String) null);
    }
}
