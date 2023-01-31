package controllers;

import DbAcessObjects.AppointmentTable;
import DbAcessObjects.ContactTable;
import DbAcessObjects.CustomerTable;
import DbAcessObjects.UserTable;
import helper.AlertEvents;
import helper.ScreenEvents;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Contact;
import models.Customer;
import models.User;
import santamaria.pa_c195.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

/**
 * This is the class that acts as the Add Appointments Controller.
 * This is the class that controls the Add Appointments View and lets the user create new appointments.
 */
public class AppointmentsAddController implements Initializable {
//    Creates resource bundle and references the one in the Main class
    static ResourceBundle resourceBundle = Main.myBundle;

//    Initialize combo boxes
    public ComboBox<User> userComboBox;
    public ComboBox<Customer> customerComboBox;
    public ComboBox<Contact> contactComboBox;
    public ComboBox<LocalTime> startTimeComboBox;
    public ComboBox<LocalTime> endTimeComboBox;

//    Initialize labels
    public Label appointmentIDLabel;
    public Label titleLabel;
    public Label descriptionLabel;
    public Label locationLabel;
    public Label contactLabel;
    public Label typeLabel;
    public Label startDateLabel;
    public Label startTimeLabel;
    public Label endTimeLabel;
    public Label customerIDLabel;
    public Label userIDLabel;

//    Initialize Buttons
    public Button saveButton;
    public Button cancelButton;

//    Initialize text fields
    public TextField appointmentLocationTextField;
    public TextField appointmentTitleTextField;
    public TextField appointmentDescriptionTextField;
    public TextField appointmentTypeTextField;

//    Initialize date picker
    public DatePicker appointmentDatePicker;

    /**
     * This class method loads the Main Appointments Screen.
     * It loads the Main Appointments Screen when the user clicks the cancel button.
     * @param event The event required to run this: when the user clicks the cancel button.
     */
    @FXML void onActionCancel(ActionEvent event) throws IOException {
        ScreenEvents.loadAppointmentsScreen(event);
    }

    /**
     * This class method checks if there are any empty fields in the scene.
     * It checks if there are any empty text fields, combo boxes without a value selected, or date pickers without a value selected.
     * @return True if empty field found, False if no empty field found
     */
    public boolean checkForEmptyFields() {
        boolean emptyFieldFound = false;
//        Check if the title text field is empty
        if (appointmentTitleTextField.getText().isEmpty()) {
//            Display alert asking user to enter a title
            AlertEvents.displayAnAlert("No Title");
            return true;
        }
//        Check if the description is empty
        if (appointmentDescriptionTextField.getText().isEmpty()) {
//            Display alert asking user to enter a description
            AlertEvents.displayAnAlert("No Description");
            return true;
        }
//        Check if the location is empty
        if (appointmentLocationTextField.getText().isEmpty()) {
//            Display alert asking user to enter a location
            AlertEvents.displayAnAlert("No Location");
            return true;
        }
//        Check if the user selected a contact
        if (contactComboBox.getValue() == null) {
//            Display an alert asking the user to select a contact
            AlertEvents.displayAnAlert("No Contact");
            return true;
        }
//        Check if the type is empty
        if (appointmentTypeTextField.getText().isEmpty()) {
//            Display alert asking user to enter a type
            AlertEvents.displayAnAlert("No Type");
            return true;
        }
//        Check if a date was selected
        if (appointmentDatePicker.getValue() == null) {
//            Display alert if no date is selected
            AlertEvents.displayAnAlert("No Date");
            return true;
        }
//        Check if the user selected a start time
        if (startTimeComboBox.getSelectionModel().getSelectedItem() == null) {
//                    Display alert asking user to select a start time
            AlertEvents.displayAnAlert("No Start Time");
            return true;
        }
//                Check if the user selected an end time
        if (endTimeComboBox.getSelectionModel().getSelectedItem() == null) {
//                    Display alert asking user to select an end time
            AlertEvents.displayAnAlert("No End Time");
            return true;
        }
//                Check if the user selected a customer
        if (customerComboBox.getSelectionModel().getSelectedItem() == null) {
//                    Display alert asking user to select a customer
            AlertEvents.displayAnAlert("No Customer");
            return true;
        }
//                Check if the user selected a user
        if (userComboBox.getSelectionModel().getSelectedItem() == null) {
//                    Display alert asking user to select a user
            AlertEvents.displayAnAlert("No User");
            return true;
        }
        return emptyFieldFound;
    }

    /**
     * This is the class method that saves a new appointment.
     * It grabs all the information from the form and creates a new record in the database.
     * @param event The event required to run this: when the user clicks the save button.
     */
    @FXML void onActionSave(ActionEvent event) throws IOException {
        try{
//            Check if there are any empty fields
            boolean emptyFieldFound = checkForEmptyFields();
            if (!emptyFieldFound) {
//                Grab data from fields
                String appointmentTitle = appointmentTitleTextField.getText();
                String appointmentDescription = appointmentDescriptionTextField.getText();
                String appointmentLocation = appointmentLocationTextField.getText();
                String appointmentType = appointmentTypeTextField.getText();
//                Generate a datetime value for the start and end dates/times
                LocalDateTime appointmentStartTime = LocalDateTime.of(appointmentDatePicker.getValue(), startTimeComboBox.getSelectionModel().getSelectedItem());
                LocalDateTime appointmentEndTime = LocalDateTime.of(appointmentDatePicker.getValue(), endTimeComboBox.getSelectionModel().getSelectedItem());
//                Make the created date equals to now
                Timestamp createdDate = Timestamp.valueOf(LocalDateTime.now());
//                Make the created by variable equals to the current logged-in user
                String createdBy = LogInController.activeUser;
//                Make the lastUpdate variable equal to now
                Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
//                Make the last updated by variable equals to the current logged-in user
                String lastUpdateBy = LogInController.activeUser;
//                Get IDs from combo box values
                int customerID = customerComboBox.getValue().getCustomerId();
                int userID = userComboBox.getValue().getUserId();
                int contactID = contactComboBox.getValue().getContact_ID();
//                Grab the original start time
                ZonedDateTime originalStartTime = appointmentStartTime.atZone(ZoneId.systemDefault());
//                Convert to EST
                ZonedDateTime startTimeEST = originalStartTime.withZoneSameInstant(ZoneId.of("America/New_York"));
//                Convert to local date time
                LocalDateTime localStartDateTime = startTimeEST.toLocalDateTime();
//                Set first operational hour to 8 AM
                LocalDateTime firstHour = LocalDateTime.of(appointmentDatePicker.getValue(), LocalTime.of(8, 0));

//                Grab the original end time
                ZonedDateTime originalEndTime = appointmentEndTime.atZone(ZoneId.systemDefault());
//                Convert to EST
                ZonedDateTime endTimeEST = originalEndTime.withZoneSameInstant(ZoneId.of("America/New_York"));
//                Convert to local date time
                LocalDateTime localEndDateTime = endTimeEST.toLocalDateTime();
//                Set the last operational hour to 8 PM
                LocalDateTime lastHour = LocalDateTime.of(appointmentDatePicker.getValue(), LocalTime.of(22, 0));

//                Check if the start time is after the end time
                if (localStartDateTime.isAfter(localEndDateTime)) {
//                    Display alert asking user to check the time selections
                    AlertEvents.displayAnAlert("Check Time");
                }
//                Check if the end time is before the start time
                else if (localEndDateTime.isBefore(localStartDateTime)) {
//                    Display alert asking user to check the time selections
                    AlertEvents.displayAnAlert("Check Time");
                }
//                Check if both times are the same
                else if (localStartDateTime.isEqual(localEndDateTime)) {
//                    Display alert asking user to check the time selections
                    AlertEvents.displayAnAlert("Check Time");
                }
//                Check if the start time is too early
                else if (localStartDateTime.isBefore(firstHour)) {
//                    Display alert letting user know that the end time is too early
                    AlertEvents.displayAnAlert("Too Early");
                    return;
                }
//                Check if the end time is too late
                else if (localEndDateTime.isAfter(lastHour)) {
//                    Display alert letting user know that the end time is too late
                    AlertEvents.displayAnAlert("Too Late");
                    return;
                }
//                Check if there are conflicts
                else if (AppointmentsController.checkForConflicts(customerID, 0, appointmentStartTime, appointmentEndTime)) {
                    return;
                }
//                Add appointment to database if there are no conflicts
                else {
//                    Add appointment to the database table
                    AppointmentTable.addAppointmentToDb(appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, appointmentStartTime, appointmentEndTime, createdDate, createdBy, lastUpdate, lastUpdateBy, customerID, userID, contactID);
//                    Go back to the Main Appointments Screen
                    ScreenEvents.loadAppointmentsScreen(event);
                }
            }
        }
//        Catch any exceptions
        catch (Exception e){
            e.printStackTrace();
            return;
        }
    }

    /**
     * This class method initializes the Add Appointments screen.
     * Sets all the labels and buttons to English or French and also initializes the combo boxes and populates them.
     * @param url The url for the resources.
     * @param resourceBundle The resource bundle that contains all the views and properties.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Set labels and buttons to English or French
        appointmentIDLabel.setText(AppointmentsAddController.resourceBundle.getString("AppointmentIDLabel"));
        titleLabel.setText(AppointmentsAddController.resourceBundle.getString("TitleLabel"));
        descriptionLabel.setText(AppointmentsAddController.resourceBundle.getString("DescriptionLabel"));
        locationLabel.setText(AppointmentsAddController.resourceBundle.getString("LocationLabel"));
        contactLabel.setText(AppointmentsAddController.resourceBundle.getString("ContactLabel"));
        typeLabel.setText(AppointmentsAddController.resourceBundle.getString("TypeLabel"));
        startDateLabel.setText(AppointmentsAddController.resourceBundle.getString("DateLabel"));
        startTimeLabel.setText(AppointmentsAddController.resourceBundle.getString("StartTimeLabel"));
        endTimeLabel.setText(AppointmentsAddController.resourceBundle.getString("EndTimeLabel"));
        customerIDLabel.setText(AppointmentsAddController.resourceBundle.getString("CustomerNameLabel"));
        userIDLabel.setText(AppointmentsAddController.resourceBundle.getString("Username"));
        saveButton.setText(AppointmentsAddController.resourceBundle.getString("SaveButton"));
        cancelButton.setText(AppointmentsAddController.resourceBundle.getString("CancelButton"));

//      Populate all the combo boxes with their respective data
        try {
            userComboBox.setItems(UserTable.getAllUsers());
            customerComboBox.setItems(CustomerTable.getAllCustomers());
            contactComboBox.setItems(ContactTable.getAllContacts());
        }
//        Catch any exceptions
        catch (Exception e) {
            e.printStackTrace();
        }

//        This time ranges will be used for the combo boxes
        LocalTime firstStartTime = LocalTime.of(5,0);
        LocalTime firstEndTime = LocalTime.of(21,45);
        LocalTime SecondStartTime = LocalTime.of(5,15);
        LocalTime SecondEndTime = LocalTime.of(22,0);

//        Populate start and end time combo boxes
        while(firstStartTime.isBefore(firstEndTime.plusSeconds(1))){
            startTimeComboBox.getItems().add(firstStartTime);
//            Make 15-minute windows
            firstStartTime = firstStartTime.plusMinutes(15);

            while(SecondStartTime.isBefore(SecondEndTime.plusSeconds(1))){
                endTimeComboBox.getItems().add(SecondStartTime);
//                Make 15-minute windows
                SecondStartTime = SecondStartTime.plusMinutes(15);
            }
        }
    }
}