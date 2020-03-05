import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PIDMenuController implements Initializable {
    GUIModel model;

    public TextField scp1_p_field;
    public TextField scp1_i_field;
    public TextField scp1_d_field;

    public TextField scp2_p_field;
    public TextField scp2_i_field;
    public TextField scp2_d_field;

    public TextField scp3_p_field;
    public TextField scp3_i_field;
    public TextField scp3_d_field;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = GUIModel.getInstance();

        // TODO: getPIDCoefficientsSTM32();

    }
}
