import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;

public class ControllerGUI extends Application {

    /* Constants  */
    private static final int width = 1000;
    private static final int height = 500;
    private static final Font defaultFont = new Font("Helvetica", 24);

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
        rootLayout.setAlignment(Pos.CENTER_LEFT);   // align everything in this layout in the along the center
        rootLayout.setStyle("-fx-background-color: rgb(200, 200, 200)");               // set background color CSS
        rootLayout.setPadding(new Insets(10, 10, 10, 10));  // set padding

        /* Create graph and information layout for fiber */
        GridPane dataPane = new GridPane();     // create GridPane layout
        dataPane.setHgap(5);    // gap between columns
        dataPane.setVgap(5);    // gap between rows

        // initialize, create, and add label
        fiberLabel = new Label();
        fiberLabel.setText("Fiber 1");              // set label text
        fiberLabel.setFont(defaultFont);            // set font
        GridPane.setConstraints(fiberLabel, 0, 0, 2, 1); // set location of label on grid
        dataPane.getChildren().add(fiberLabel);     // add label to layout

        // add separator to bottom of data pane
        Separator dataPaneSeparator = new Separator(Orientation.HORIZONTAL);        // create horizontal separator
        GridPane.setRowIndex(dataPaneSeparator, dataPane.getRowCount());            // set on last row
        GridPane.setColumnSpan(dataPaneSeparator, GridPane.REMAINING);              // span all columns
        dataPane.getChildren().add(dataPaneSeparator);                              // add to data pane

        /* Create HBox footer */
        HBox footerBox = new HBox();
        footerBox.setAlignment(Pos.BOTTOM_RIGHT);

        // add logos to footer
        ubcLogoFile = new FileInputStream("resources/images/ubclogo.png");
        ubcLogo = new Image(ubcLogoFile);           // create image instance
        ubcLogoView = new ImageView(ubcLogo);       // initialize ImageView object
        ubcLogoView.setPreserveRatio(true);         // preserve ration
        ubcLogoView.setFitHeight(50);               // set height to 50 pixels
        ubcLogoView.setOnMouseClicked(e -> {
            primaryStage.close();
        });
        footerBox.getChildren().add(ubcLogoView);      // add logo to VBox

        // add dataPanes and footer to main VBox layout
        rootLayout.getChildren().add(dataPane);
        rootLayout.getChildren().add(footerBox);

        // create main scene and set it on primaryStage
        mainScene = new Scene(rootLayout);
        primaryStage.setScene(mainScene);
        primaryStage.sizeToScene();

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
