package sample;

import animatefx.animation.Bounce;
import animatefx.animation.Flip;
import animatefx.animation.FlipInX;
import animatefx.animation.FlipOutX;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

enum Mode {
    NORMAL,
    EDIT
}

public class Controller implements Initializable {
    @FXML
    private SplitPane rootPane;
    @FXML
    private AnchorPane detailPane;
    @FXML
    private JFXButton editButton;

    private Mode mode;

    @FXML
    void close() {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mode = Mode.NORMAL;
    }

    @FXML
    void toggleMode() {
        if (mode.equals(Mode.NORMAL)) {
            mode = Mode.EDIT;
            new FlipInX(editButton).play();
            editButton.setText("done");
        } else {
            mode = Mode.NORMAL;
            new FlipInX(editButton).play();
            editButton.setText("edit");
        }
    }
}
