package sample;

import data.model.Person;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import util.DateUtil;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DetailController extends ContentController implements Initializable {
    @FXML
    private Label nameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label birthdayLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void setCurrentPerson(Person currentPerson) {
        super.setCurrentPerson(currentPerson);
        if (currentPerson == null) {
            clearLabels();
        } else {
            setUpLabels();
        }
    }

    private void clearLabels() {
        nameLabel.setText("");
        phoneLabel.setText("");
        addressLabel.setText("");
        birthdayLabel.setText("");
    }

    private void setUpLabels() {
        nameLabel.setText(getCurrentPerson().getName());
        phoneLabel.setText(getCurrentPerson().getPhone());
        addressLabel.setText(getCurrentPerson().getAddress());
        String birthday;
        if (getCurrentPerson().getBirthday().equals(LocalDate.MIN)) {
            birthday = "";
        } else {
            birthday = DateUtil.format(getCurrentPerson().getBirthday());
        }
        birthdayLabel.setText(birthday);
    }
}
