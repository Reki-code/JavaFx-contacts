package main.java.util;

import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import main.java.data.model.Person;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PersonListEntry extends HBox {
    private Person person;

    public PersonListEntry(Person person) {
        this.person = person;
        ImageView avatar = new ImageView(person.getAvatar());
        Label name = new Label();
        Label phone = new Label();
        name.textProperty().bind(person.nameProperty());
        phone.textProperty().bind(person.phoneProperty());
        VBox info = new VBox(name, phone);
        info.setPadding(new Insets(0, 0, 0, 10));
        getChildren().addAll(avatar, info);
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
