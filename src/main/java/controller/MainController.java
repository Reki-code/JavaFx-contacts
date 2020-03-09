package main.java.controller;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import animatefx.animation.FlipInX;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import main.java.data.model.Person;
import main.java.util.MainWindowMode;
import main.java.util.PersonListEntry;
import main.java.util.PersonUtil;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {
    @FXML
    private JFXButton deleteButton;
    @FXML
    private JFXButton menuButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private Label titleLabel;
    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXListView<PersonListEntry> personListView;
    @FXML
    private SplitPane rootPane;
    @FXML
    private AnchorPane detailPane;
    @FXML
    private JFXButton editButton;

    private MainWindowMode mode;
    private Parent[] content;
    private ContentController[] contentControllers;
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    private JFXPopup popup;

    @FXML
    void close() {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mode = MainWindowMode.NORMAL;
        loadContent();
        loadData();
        setUpListView();
        setUpToolbar();
//        personListView

    }

    private void setUpToolbar() {
        JFXButton saveButton = new JFXButton("保存");
        JFXButton importButton = new JFXButton("导入");
        VBox vBox = new VBox(saveButton, importButton);
        vBox.setPadding(new Insets(10));
        popup = new JFXPopup(vBox);
        saveButton.setOnAction(e -> {
            saveContacts();
        });
        importButton.setOnAction(e -> {
            openNewContacts();
        });
        cancelButton.setVisible(false);
        deleteButton.setVisible(false);
    }

    private void setUpListView() {
        List<PersonListEntry> personListEntryList = personData.stream().map(PersonListEntry::new).collect(Collectors.toList());
        ObservableList<PersonListEntry> personListEntryObservableList = FXCollections.observableArrayList();
        personListEntryObservableList.addAll(personListEntryList);
        FilteredList<PersonListEntry> filteredData = new FilteredList<>(personListEntryObservableList, null);
        searchField.textProperty().addListener((o, oldVal, newVal) -> {
            if (newVal.isEmpty()) {
                filteredData.setPredicate(p -> true);
            } else {
                filteredData.setPredicate(p -> p.getName().contains(newVal)
                        || p.getPhone().contains(newVal));
            }
        });
        personListView.setItems(filteredData);
    }

    private void loadData() {
        try {
            personData = PersonUtil.loadPersonDataFrom(new File("/home/togashi/contacts.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    private void loadContent() {
        content = new Parent[3];
        contentControllers = new ContentController[2];
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/controller/blank.fxml"));
            content[2] = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        detailPane.getChildren().setAll(content[2]);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/fxml/detail.fxml"));
            content[0] = loader.load();
            contentControllers[0] = loader.getController();
            contentControllers[0].setCurrentPerson(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/fxml/editPerson.fxml"));
            content[1] = loader.load();
            contentControllers[1] = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void toggleMode() {
        if (mode.equals(MainWindowMode.NORMAL) && !personListView.getSelectionModel().isEmpty()) {
            editMode();
            contentControllers[1].setCurrentPerson(personListView.getSelectionModel().getSelectedItem().getPerson());
        } else { // Edit Mode
            try {
                PersonUtil.savePersonDataTo(personData);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
            normalMode();
        }
    }

    private void blankMode() {
        mode = MainWindowMode.BLANK;
        changeContent();
        editButton.setVisible(false);
        personListView.setDisable(false);
        searchField.setDisable(false);
        deleteButton.setVisible(false);
    }

    private void editMode() {
        mode = MainWindowMode.EDIT;
        changeContent();
        personListView.setDisable(true);
        searchField.setDisable(true);
        deleteButton.setVisible(true);
        deleteButton.setOnAction(e -> {
            delPerson(personListView.getSelectionModel().getSelectedItem().getPerson());
            toggleMode();
            deleteButton.setVisible(false);
        });
    }

    private void normalMode() {
        mode = MainWindowMode.NORMAL;
        changeContent();
        personListView.setDisable(false);
        searchField.setDisable(false);
        deleteButton.setVisible(false);
    }

    private void changeContent() {
        switch (mode) {
            case BLANK:
                detailPane.getChildren().setAll(content[2]);
                editButton.setVisible(false);
                break;
            case NORMAL:
                detailPane.getChildren().setAll(content[0]);
                if (!personListView.getSelectionModel().isEmpty()) {
                    contentControllers[0].setCurrentPerson(personListView.getSelectionModel().getSelectedItem().getPerson());
                }
                editButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("edit"), false);
                editButton.setVisible(true);
                new FlipInX(editButton).play();
                editButton.setText("修改");
                break;
            case EDIT:
                detailPane.getChildren().setAll(content[1]);
                editButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("edit"), true);
                editButton.setVisible(true);
                new FlipInX(editButton).play();
                editButton.setText("完成");
                break;
        }
    }

    @FXML
    void selectPerson() {
        Person person = personListView.getSelectionModel().getSelectedItem().getPerson();
        if (mode.equals(MainWindowMode.BLANK)) {
            normalMode();
        }
        if (mode.equals(MainWindowMode.NORMAL)) {
            contentControllers[0].setCurrentPerson(person);
        }
        titleLabel.setText(person.getName());
    }

    private void addPerson(Person person) {
        personData.add(person);
        setUpListView();
        System.out.println(personListView.getItems());
    }

    private void delPerson(Person person) {
        personData.remove(person);
        setUpListView();
    }

    @FXML
    void addPerson() {
        editMode();
        Person newPerson = new Person();
        newPerson.setBirthday(LocalDate.now());
        contentControllers[1].setCurrentPerson(newPerson);
        addPerson(newPerson);
        new FadeIn(cancelButton).play();
        cancelButton.setVisible(true);
        cancelButton.setOnAction(event -> {
            delPerson(newPerson);
            new FadeOut(cancelButton).play();
            cancelButton.setVisible(false);
            blankMode();
        });
    }

    @FXML
    void menu() {
        popup.show(menuButton, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
    }

    private void openNewContacts() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML 文件 (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showOpenDialog(rootPane.getScene().getWindow());

        if (file != null) {
            try {
                personData = PersonUtil.loadPersonDataFrom(file);
                setUpListView();
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveContacts() {

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML 文件 (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showSaveDialog(rootPane.getScene().getWindow());

        if (file != null) {
            try {
                PersonUtil.savePersonDataTo(file, personData);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }
}
