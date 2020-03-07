package sample;

import animatefx.animation.FlipInX;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import data.model.Person;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

enum Mode {
    BLANK,
    NORMAL,
    EDIT
}

public class Controller implements Initializable {
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

    private Mode mode;
    Parent[] content;
    DetailController detailController;
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    @FXML
    void close() {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mode = Mode.NORMAL;
        loadContent();
        loadData();
        setUpListView();
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
        personData.add(new Person("wang"));
        personData.add(new Person("zhao"));
        personData.add(new Person("浅草"));
        personData.add(new Person("乱步"));
        Person person = new Person("bir");
        person.setBirthday(LocalDate.MAX);
        personData.add(person);
    }

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    private void loadContent() {
        content = new Parent[3];
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("detail.fxml"));
            content[0] = loader.load();
            detailController = loader.getController();
            detailController.setCurrentPerson(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        detailPane.getChildren().setAll(content[0]);
    }

    @FXML
    void toggleMode() {
        if (mode.equals(Mode.NORMAL)) {
            editMode();
        } else {
            normalMode();
        }
    }

    private void blankMode() {
        mode = Mode.BLANK;
        editButton.setVisible(false);
    }

    private void editMode() {
        mode = Mode.EDIT;
        editButton.setVisible(true);
        new FlipInX(editButton).play();
        editButton.setText("done");
    }

    private void normalMode() {
        mode = Mode.NORMAL;
        editButton.setVisible(true);
        new FlipInX(editButton).play();
        editButton.setText("edit");
    }

    @FXML
    void selectPerson() {
        detailController.setCurrentPerson(personListView.getSelectionModel().getSelectedItem().getPerson());
    }

}
