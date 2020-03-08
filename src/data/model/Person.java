package data.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import util.LocalDateAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

public class Person {
    private final StringProperty name;
    private final StringProperty phone;
    private final StringProperty address;
    private final ObjectProperty<LocalDate> birthday;
    private static final LocalDate DEFAULT_DATE = LocalDate.ofYearDay(1880, 1);

    public Person() {
        this(new SimpleStringProperty(), new SimpleStringProperty(), new SimpleStringProperty(), new SimpleObjectProperty<>(DEFAULT_DATE));
    }

    public Person(String name) {
        this(new SimpleStringProperty(name), new SimpleStringProperty(""), new SimpleStringProperty(""), new SimpleObjectProperty<>(LocalDate.ofYearDay(1880, 1)));
    }

    public Person(StringProperty name, StringProperty phone, StringProperty address, ObjectProperty<LocalDate> birthday) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    @XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateAdapter.class)
    public LocalDate getBirthday() {
        return birthday.get();
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public static LocalDate getDefaultDate() {
        return DEFAULT_DATE;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name=" + name +
                ", phone=" + phone +
                ", address=" + address +
                ", birthday=" + birthday +
                '}';
    }
}
