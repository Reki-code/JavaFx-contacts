package main.java.util;

import main.java.data.model.Person;
import main.java.data.PersonListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.Main;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class PersonUtil {
    public static final String PREFS_KEY = "path";
    public static final String DEFAULT_PATH = "src/contacts.xml";

    public static ObservableList<Person> loadPersonData() throws JAXBException {
        return loadPersonDataFrom(new File(DEFAULT_PATH));
    }

    public static ObservableList<Person> loadPersonDataFrom(File file) throws JAXBException {
        ObservableList<Person> personData = FXCollections.observableArrayList();
        JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        // load main.java.data
        PersonListWrapper wrapper = (PersonListWrapper) unmarshaller.unmarshal(file);

        List<Person> personList = wrapper.getPersonList();
        personData.addAll(personList != null ? personList : new ArrayList<>());

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
