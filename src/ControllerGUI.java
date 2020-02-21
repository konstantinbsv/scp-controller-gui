import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;

public class ControllerGUI extends Application {

    /* Constants  */
    private static final int width = 1000;
    private static final int height = 500;
    private static final Font defaultFont = new Font("Century Gothic", 24);

    /* Components  */
    Label fiberLabel;
    VBox rootLayout;
    FileInputStream ubcLogoFile;
    Image ubcLogo;
    ImageView ubcLogoView;
    Scene mainScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("SCP Controller");
        primaryStage.initStyle(StageStyle.DECORATED);

        // initialize main layout
        rootLayout = new VBox();
        rootLayout.setAlignment(Pos.CENTER);

        /* Create graph and information layout for fiber */
        GridPane dataPane = new GridPane();     // create GridPane layout
        dataPane.setPadding(new Insets(10, 10, 10, 10));    // set padding
        dataPane.setHgap(5);    // gap between columns
        dataPane.setVgap(5);    // gap between rows

        // initialize, create, and add label
        fiberLabel = new Label();
        fiberLabel.setText("Fiber 1");              // set label text
        fiberLabel.setFont(defaultFont);            // set font
        GridPane.setConstraints(fiberLabel, 0, 0, 2, 1); // set location of label on grid
        dataPane.getChildren().add(fiberLabel);     // add label to layout

        // show logos
        ubcLogoFile = new FileInputStream("resources/images/ubclogo.png");
        ubcLogo = new Image(ubcLogoFile);           // create image instance
        ubcLogoView = new ImageView(ubcLogo);       // initialize ImageView object
        ubcLogoView.setPreserveRatio(true);         // preserve ration
        ubcLogoView.setFitHeight(50);               // set height to 50 pixels
        ubcLogoView.setOnMouseClicked(e -> {
            primaryStage.close();
        });
        rootLayout.getChildren().add(ubcLogoView);      // add logo to VBox

        // set scene
        mainScene = new Scene(rootLayout, width, height);
        primaryStage.setScene(mainScene);

        // show primary stage
        primaryStage.show();
    }
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
//    }
}
