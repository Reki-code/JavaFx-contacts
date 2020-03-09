package main.java.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import main.java.data.model.Person;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.StringConverter;
import main.java.util.DateUtil;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditPersonController extends ContentController implements Initializable {
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField phoneField;
    @FXML
    private JFXTextField addressField;
    @FXML
    private JFXDatePicker birthdayDatePicker;

    @Override
    public void setCurrentPerson(Person currentPerson) {
        super.setCurrentPerson(currentPerson);
        setUpTextField();
    }

    private void setUpTextField() {
        Person person = getCurrentPerson();
        if (person == null) {
            return;
        }
        nameField.setText(person.getName());
        phoneField.setText(person.getPhone());
        addressField.setText(person.getAddress());
        birthdayDatePicker.setValue(person.getBirthday());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        birthdayDatePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return DateUtil.format(date);
            }

            @Override
            public LocalDate fromString(String string) {
                return DateUtil.parse(string);
            }
        });
        nameField.textProperty().addListener((o, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                getCurrentPerson().setName(newValue);
            }
        });
        phoneField.textProperty().addListener((o, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                getCurrentPerson().setPhone(newValue);
            }
        });
        addressField.textProperty().addListener((o, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                getCurrentPerson().setAddress(newValue);
            }
        });
        birthdayDatePicker.setOnAction(e -> {
            getCurrentPerson().setBirthday(birthdayDatePicker.getValue());
        });
    }

}
