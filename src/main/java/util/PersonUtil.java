package main.java.util;

import data.model.Person;
import data.model.PersonListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.Main;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.prefs.Preferences;

public class PersonUtil {
    public static final String PREFS_KEY = "path";
    public static final String DEFAULT_PATH = "/home/togashi/contacts.xml";

    public static ObservableList<Person> loadPersonData() throws JAXBException {
        File path = getPersonFilePath();
        if (path == null) {
            path = new File(DEFAULT_PATH);
        }
        return loadPersonDataFrom(path);
    }

    public static ObservableList<Person> loadPersonDataFrom(File file) throws JAXBException {
        ObservableList<Person> personData = FXCollections.observableArrayList();
        JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        // load data
        PersonListWrapper wrapper = (PersonListWrapper) unmarshaller.unmarshal(file);

        personData.addAll(wrapper.getPersonList());

        setPersonFilePath(file);
        return personData;
    }

    public static void savePersonDataTo(File file, ObservableList<Person> personData) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        PersonListWrapper wrapper = new PersonListWrapper();
        wrapper.setPersonList(personData);

        marshaller.marshal(wrapper, file);

        setPersonFilePath(file);
    }

    private static File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String path = prefs.get(PREFS_KEY, null);
        if (path != null) {
            return new File(path);
        } else {
            return null;
        }
    }

    private static void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (file != null) {
            prefs.put(PREFS_KEY, file.getPath());
        } else {
            prefs.remove(PREFS_KEY);
        }
    }

    public static void savePersonDataTo(ObservableList<Person> personData) throws JAXBException {
        savePersonDataTo(getPersonFilePath(), personData);
    }
}
