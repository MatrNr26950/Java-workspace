package app.view;

import org.controlsfx.dialog.Dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import app.Main;
import app.model.Person;
import app.util.DateUtil;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;
    Main mainApp;
    
    public PersonOverviewController() {
    }
    
    @FXML
    private void initialize() {
    	firstNameColumn.setCellValueFactory(
            	cellData -> cellData.getValue().firstNameProperty());
    	lastNameColumn.setCellValueFactory(
            	cellData -> cellData.getValue().lastNameProperty());
    	showPersonDetails(null);
    	personTable.getSelectionModel().selectedItemProperty().addListener(
            	(observable, oldValue, newValue) -> showPersonDetails(newValue));
	}

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        personTable.setItems(mainApp.getPersonData());
    }
    
    private void showPersonDetails(Person person) {
    	if (person != null) {
        	firstNameLabel.setText(person.getFirstName());
        	lastNameLabel.setText(person.getLastName());
        	streetLabel.setText(person.getStreet());
        	postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
        	cityLabel.setText(person.getCity());
        	birthdayLabel.setText(DateUtil.format(person.getBirthday()));
    	} else {
	        firstNameLabel.setText("");
    	    lastNameLabel.setText("");
        	streetLabel.setText("");
      		postalCodeLabel.setText("");
        	cityLabel.setText("");
        	birthdayLabel.setText("");
    	}
	}
    
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            Dialogs.create()
                .title("Nichts ausgewaehlt")
                .masthead("Keine Person ausgewaehlt")
                .message("Waehlen Sie eine Person aus der Tabelle.")
                .showWarning();
        }
    }
    
    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }
        } else {
        	Dialogs.create()
        	.title("Nichts ausgewaehlt")
        	.masthead("Keine Person ausgewaehlt")
        	.message("Waehlen Sie eine Person aus der Tabelle.")
        	.showWarning();
        }
    }
}



