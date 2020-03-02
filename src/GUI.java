import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;



public class GUI extends Application implements Runnable {
    private static final String stageTitle = "SCP Controller v1.0";
    public static final String cssRes = "flat_dark_theme.css";
    public static final String iconRes = "/res/images/icon3.png";

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GUIView.fxml"));
        Scene scene = new Scene(root);
        // scene.getStylesheets().clear();
        scene.getStylesheets().add(cssRes);
        stage.getIcons().add(
                new Image(
                        getClass().getResourceAsStream(iconRes),
                        100, 100, true, true)); // add icon
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
