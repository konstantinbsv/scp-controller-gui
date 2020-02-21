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
    AnchorPane rootLayout;
    VBox dataPanesVBox;
    HBox logoHBox;
    GridPane dataPane;
    Label fiberLabel;
    FileInputStream ubcLogoFile;
    Image ubcLogo;
    ImageView ubcLogoView;
    Scene mainScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("SCP Controller");
        primaryStage.initStyle(StageStyle.DECORATED);

        // initialize main layout
        rootLayout = new AnchorPane();
        rootLayout.setStyle("-fx-background-color: rgb(200, 200, 200)");               // set background color CSS
        rootLayout.setPadding(new Insets(10, 10, 10, 10));  // set padding

        // initialize VBox layout for data panes
        dataPanesVBox = new VBox();
        dataPanesVBox.setAlignment(Pos.TOP_LEFT);   // align everything in this layout top left
        rootLayout.getChildren().add(dataPanesVBox);

        /* Create graph and information layout for fiber */
        dataPane = new GridPane();     // create GridPane layout
        AnchorPane.setTopAnchor(dataPane, 0.0);
        AnchorPane.setLeftAnchor(dataPane, 0.0);
        dataPane.setHgap(5);    // gap between columns
        dataPane.setVgap(5);    // gap between rows
        dataPanesVBox.getChildren().add(dataPane);

        // initialize, create, and add label
        fiberLabel = new Label();
        fiberLabel.setText("SCP 1");                // set label text
        fiberLabel.setFont(defaultFont);            // set font
        GridPane.setConstraints(fiberLabel, 0, 0, 2, 1); // set location of label on grid
        dataPane.getChildren().add(fiberLabel);     // add label to layout

        // add separator to bottom of data pane
        Separator dataPaneSeparator = new Separator(Orientation.HORIZONTAL);        // create horizontal separator
        GridPane.setRowIndex(dataPaneSeparator, dataPane.getRowCount());            // set on last row
        GridPane.setColumnSpan(dataPaneSeparator, GridPane.REMAINING);              // span all columns
        dataPane.getChildren().add(dataPaneSeparator);                              // add to data pane

        /* Create HBox footer */
        logoHBox = new HBox();
        logoHBox.setAlignment(Pos.CENTER);
        rootLayout.getChildren().add(logoHBox);           // add to root anchor layout
        AnchorPane.setBottomAnchor(logoHBox, 0.0);  // place on bottom
        AnchorPane.setRightAnchor(logoHBox, 0.0);   // right of screen

        // add logos to footer
        ubcLogoFile = new FileInputStream("resources/images/ubclogo.png");
        ubcLogo = new Image(ubcLogoFile);           // create image instance
        ubcLogoView = new ImageView(ubcLogo);       // initialize ImageView object
        ubcLogoView.setPreserveRatio(true);         // preserve ration
        ubcLogoView.setFitHeight(50);               // set height to 50 pixels
        ubcLogoView.setOnMouseClicked(e -> {
            primaryStage.close();
        });
        logoHBox.getChildren().add(ubcLogoView);      // add logo to logo hbox


        // create main scene and set it on primaryStage
        mainScene = new Scene(rootLayout);
        primaryStage.setScene(mainScene);

        // show primary stage
        primaryStage.setMaximized(true);
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
