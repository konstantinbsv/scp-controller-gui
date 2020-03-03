import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class PIDMenuController implements Initializable {
    private GUIModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = GUIModel.getInstance();
    }
}
