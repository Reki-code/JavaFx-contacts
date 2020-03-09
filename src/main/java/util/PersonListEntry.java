package main.java.util;

import main.java.data.model.Person;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PersonListEntry extends HBox {
    private Person person;

    public PersonListEntry(Person person) {
        this.person = person;
        Label name = new Label();
        Label phone = new Label();
        name.textProperty().bind(person.nameProperty());
        phone.textProperty().bind(person.phoneProperty());
        getChildren().addAll(name, phone);
    }

    public String getName() {
        return person.getName();
    }

    public String getPhone() {
        return person.getPhone();
    }

    public Person getPerson() {
        return person;
    }
}
