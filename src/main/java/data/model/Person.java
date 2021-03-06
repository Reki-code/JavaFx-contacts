package main.java.data.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import main.java.util.ImageAdapter;
import main.java.util.LocalDateAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;

public class Person {
    private static final LocalDate DEFAULT_DATE = LocalDate.ofYearDay(1880, 1);
    private static final Image DEFAULT_AVATAR = new Image(new inner().getAvatar_file());
    private final ObjectProperty<Image> avatar;
    private final StringProperty name;
    private final StringProperty phone;
    private final StringProperty address;
    private final ObjectProperty<LocalDate> birthday;

    public Person() {
        avatar = new SimpleObjectProperty<>(DEFAULT_AVATAR);
        name = new SimpleStringProperty();
        phone = new SimpleStringProperty();
        address = new SimpleStringProperty();
        birthday = new SimpleObjectProperty<>(DEFAULT_DATE);
    }

    public static Image getDefaultAvatar() {
        return DEFAULT_AVATAR;
    }

    @XmlJavaTypeAdapter(type = Image.class, value = ImageAdapter.class)
    public Image getAvatar() {
        return avatar.get();
    }

    public void setAvatar(Image avatar) {
        this.avatar.set(avatar);
    }

    public ObjectProperty<Image> avatarProperty() {
        return avatar;
    }

    static class inner {
        private FileInputStream defaultAvatarFile;

        inner() {
            try {
                defaultAvatarFile = new FileInputStream("src/main/resources/images/default_avatar.png");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public FileInputStream getAvatar_file() {
            return defaultAvatarFile;
        }
    }

    public static LocalDate getDefaultDate() {
        return DEFAULT_DATE;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    @XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateAdapter.class)
    public LocalDate getBirthday() {
        return birthday.get();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
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
