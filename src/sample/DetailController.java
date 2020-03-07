package sample;

import data.model.Person;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import util.DateUtil;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DetailController implements Initializable {
    @FXML
    private Label nameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label birthdayLabel;
    private Person currentPerson;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setCurrentPerson(Person currentPerson) {
        this.currentPerson = currentPerson;
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
        nameLabel.setText(currentPerson.getName());
        phoneLabel.setText(currentPerson.getPhone());
        addressLabel.setText(currentPerson.getAddress());
        String birthday = currentPerson.getBirthday().equals(LocalDate.MIN)
                ? ""
                : DateUtil.format(currentPerson.getBirthday());
        birthdayLabel.setText(birthday);
    }
}
