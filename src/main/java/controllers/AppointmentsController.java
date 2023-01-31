package controllers;

import helper.AlertEvents;
import helper.ScreenEvents;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Appointment;
import santamaria.pa_c195.Main;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the class that acts as the Appointments Controller.
 * This is the class that controls the Appointments View and lets the user add, delete, and modify appointments.
 */
public class AppointmentsController implements Initializable {
//    Creates resource bundle and references the one in the Main class
    static ResourceBundle resourceBundle = Main.myBundle;

//    This observable list will be display on the table view
    ObservableList<models.Appointment> Appointments = FXCollections.observableArrayList();

//    Create table view object that will display all the appointments
    public TableView<Appointment> AppointmentTable;

//    Create table column objects that will be part of the table view
    public TableColumn<Appointment, Integer> Appointment_ID;
    public TableColumn<Appointment, String> Title;
    public TableColumn<Appointment, String> Description;
    public TableColumn<Appointment, String> Location;
    public TableColumn<Appointment, Integer> Contact;
    public TableColumn<Appointment, String> Type;
    public TableColumn<Appointment, Calendar> Start;
    public TableColumn<Appointment, Calendar> End;
    public TableColumn<Appointment, Integer> Customer_ID;
    public TableColumn<Appointment, Integer> User_ID;

//    Create button objects referencing the ones on the scene
    public Button addButton;
    public Button modifyButton;
    public Button deleteButton;
    public Button mainMenuButton;

//    Create radio button objects referencing the ones on the scene
    public RadioButton thisWeekRadioButton;
    public RadioButton thisMonthRadioButton;
    public RadioButton allRadioButton;
    /**
     * This class method checks for appointment conflicts.
     * Returns a boolean indicating whether conflicts were found or not.
     * @param customerID the customer that needs to be checked
     * @param appointmentID the ID of the appointment
     * @param appointmentStart the start time of the appointment
     * @param appointmentEnd the end time of the appointment
     * @return true if appointment conflicts are found, false if no conflicts are found
     */
    public static boolean checkForConflicts(int customerID, int appointmentID, LocalDateTime appointmentStart, LocalDateTime appointmentEnd) throws Exception {
//        Grab all appointments from the database
        ObservableList<Appointment> appointmentsList = DbAcessObjects.AppointmentTable.getAllAppointments();
//        Initialize appointment start and end variables
        LocalDateTime appointmenttStartDateTime;
        LocalDateTime appointmentEndDateTime;

//        Loop through list of appointments
        for (Appointment appointment_ : appointmentsList) {
//            Get start date from object and assign it to the variable
            appointmenttStartDateTime = appointment_.getStart();
//            Get end date from object and assign it to the variable
            appointmentEndDateTime = appointment_.getEnd();
//            Check if the customer IDs do not match
            if (customerID != appointment_.getCustomer_ID()) {
//                Skip appointment object if they do not match
                continue;
            }
//            Check if the appointment IDs match
            if (appointmentID == appointment_.getAppointment_ID()) {
//                Skip appointment if they match
                continue;
            }
//            Check if there are appointments that start or end at the same time as existing customer appointments
            else if (appointmenttStartDateTime.isEqual(appointmentStart) || appointmenttStartDateTime.isEqual(appointmentEnd) || appointmentEndDateTime.isEqual(appointmentStart) || appointmentEndDateTime.isEqual(appointmentEnd)){
//                Display alert to inform user that there are conflicts
                AlertEvents.displayAnAlert("Appointment Conflicts");
                return true;
            }
//            Check if there are appointments that start during existing customer appointments
            else if (appointmentStart.isAfter(appointmenttStartDateTime) && (appointmentStart.isBefore(appointmentEndDateTime))){
//                Display alert to inform user that there are conflicts
                AlertEvents.displayAnAlert("Appointment Conflicts");
                return true;
            }
//            Check if there are appointments that end during existing customer appointments
            else if (appointmentEnd.isAfter(appointmenttStartDateTime) && appointmentEnd.isBefore(appointmentEndDateTime)){
//                Display alert to inform user that there are conflicts
                AlertEvents.displayAnAlert("Appointment Conflicts");
                return true;
            }
//            Need another one that checks if there is an existing appointment within the start and end times
        }
        return false;
    }

    /**
     * This class method displays the scene that allows the user to add an appointment.
     * It loads the screen that asks the user to input all the information necessary for a new appointment record.
     * @param event the event necessary to run this: when the user clicks the Add button on the scene.
     */
    @FXML void onActionAddAppointment(ActionEvent event) throws IOException {
        ScreenEvents.loadAddAppointmentScreen(event);
    }

    /**
     * This class method allows the user to delete an appointment.
     * It displays an alert asking the user to verify the deletion if they selected an appointment;
     * otherwise, it displays and alert notifying the user that no appointments are selected.
     * @param event the even necessary to run this: when the user clicks the Delete button on the scene.
     */
    @FXML void onActionDeleteAppointment(ActionEvent event) throws Exception {
//        Check if there are appointments selected
        if (AppointmentTable.getSelectionModel().getSelectedItem() == null) {
//            Display alert notifying user that no appointments are selected
            AlertEvents.displayAnAlert("No Appointment Selected");
        }
        else {
//            Display dialog window asking user if they want to proceed
            boolean result = AlertEvents.deleteAppointmentConfirmationWindow();
            if (result) {
//                Delete selected appointment from database
                DbAcessObjects.AppointmentTable.deleteAppointmentByID(AppointmentTable.getSelectionModel().getSelectedItem().getAppointment_ID());
//                Display message confirming appointment deletion
                AlertEvents.displayAnAlert("Appointment Deleted");
//                Update the table view
                Appointments = DbAcessObjects.AppointmentTable.getAllAppointments();
                AppointmentTable.setItems(Appointments);
                AppointmentTable.refresh();
            }
        }
    }

    /**
     * This class method loads Main Menu screen.
     * It loads the Main Menu screen when the user clicks the Main Menu button on the scene.
     * @param event the even necessary to run this: when the user clicks the Main Menu button on the scene.
     */
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        ScreenEvents.loadMainMenu(event);
    }

    /**
     * This class method loads the Modify Appointment screen.
     * It loads the Modify Appointment Screen when the user clicks the Modify button on the scene.
     * @param event the even necessary to run this: when the user clicks the Modify button on the scene.
     */
    @FXML void onActionModifyAppointment(ActionEvent event) throws IOException {
//        Display alert if no appointment is selected
        if (AppointmentTable.getSelectionModel().getSelectedItem() == null) {
            AlertEvents.displayAnAlert("No Appointment Selected");
        } else {
//            Opens the View to Modify the Selected Appointment
            AppointmentsModifyController.receiveSelectedAppointment(AppointmentTable.getSelectionModel().getSelectedItem());
            ScreenEvents.loadModifyAppointmentScreen(event);
        }
    }

    /**
     * This class method displays all the appointments on the table view.
     * It loads the all the appointments and populates the table view with them.
     * @param event the event required to run this: when the user clicks the 'All' button on the scene.
     */
    @FXML void onActionAllAppointments(ActionEvent event) throws Exception {
//        Sets the other radio buttons to false
        thisWeekRadioButton.setSelected(false);
        thisMonthRadioButton.setSelected(false);
//        Clears the list
        Appointments.clear();
//        Adds all the appointments to the list
        Appointments.addAll(DbAcessObjects.AppointmentTable.getAllAppointments());
    }

    /**
     * This class method displays all the appointments for this month on the table view.
     * It loads the all the appointments for this month and populates the table view with them.
     * @param event the event required to run this: when the user clicks the 'This Month' button on the scene.
     */
    @FXML void onActionThisMonth(ActionEvent event) throws Exception {
//        Sets the other radio buttons to false
        allRadioButton.setSelected(false);
        thisWeekRadioButton.setSelected(false);
//        Clears the list
        Appointments.clear();
//        Adds all the appointments for this month to the list
        Appointments.addAll(DbAcessObjects.AppointmentTable.getCurrentMonthAppointments());
    }

    /**
     * This class method displays all the appointments for this week on the table view.
     * It loads the all the appointments for this week and populates the table view with them.
     * @param event the event required to run this: when the user clicks the 'This Week' button on the scene.
     */
    @FXML void onActionThisWeek(ActionEvent event) throws Exception {
//        Sets the other radio buttons to false
        allRadioButton.setSelected(false);
        thisMonthRadioButton.setSelected(false);
//        Clears the list
        Appointments.clear();
//        Adds all the appointments for this week to the list
        Appointments.addAll(DbAcessObjects.AppointmentTable.getCurrentWeekAppointments());
    }

    /**
     * This class method initializes the Appointments screen.
     * It sets all the labels, buttons, and headers to either English or French
     * and populates the table view.
     * @param url The url for the resources.
     * @param resourceBundle The resource bundle that contains all the views and properties.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Set buttons and labels to English or French
        thisWeekRadioButton.setText(AppointmentsController.resourceBundle.getString("ThisWeekRadioButton"));
        thisMonthRadioButton.setText(AppointmentsController.resourceBundle.getString("ThisMonthRadioButton"));
        allRadioButton.setText(AppointmentsController.resourceBundle.getString("AllRadioButton"));
        addButton.setText(AppointmentsController.resourceBundle.getString("AddButton"));
        modifyButton.setText(AppointmentsController.resourceBundle.getString("ModifyButton"));
        deleteButton.setText(AppointmentsController.resourceBundle.getString("DeleteButton"));
        mainMenuButton.setText(AppointmentsController.resourceBundle.getString("MainMenuButton"));
//        Set headers to English or French
        Appointment_ID.setText(AppointmentsController.resourceBundle.getString("AppointmentIDLabel"));
        Title.setText(AppointmentsController.resourceBundle.getString("TitleLabel"));
        Description.setText(AppointmentsController.resourceBundle.getString("DescriptionLabel"));
        Location.setText(AppointmentsController.resourceBundle.getString("LocationLabel"));
        Contact.setText(AppointmentsController.resourceBundle.getString("ContactLabel"));
        Type.setText(AppointmentsController.resourceBundle.getString("TypeLabel"));
        Start.setText(AppointmentsController.resourceBundle.getString("StartTimeLabel"));
        End.setText(AppointmentsController.resourceBundle.getString("EndTimeLabel"));
        Customer_ID.setText(AppointmentsController.resourceBundle.getString("CustomerIDLabel"));
        User_ID.setText(AppointmentsController.resourceBundle.getString("UserIDLabel"));
//        Set cell values
        Appointment_ID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Location.setCellValueFactory(new PropertyValueFactory<>("Location"));
        Contact.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        Start.setCellValueFactory(new PropertyValueFactory<>("Start"));
        End.setCellValueFactory(new PropertyValueFactory<>("End"));
        Customer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        User_ID.setCellValueFactory(new PropertyValueFactory<>("User_ID"));

//            Try to add all the appointments to the list
        try {
            Appointments.addAll(DbAcessObjects.AppointmentTable.getAllAppointments());
//      Catch any errors and log them
        } catch (Exception ex) {
//            Create log of exception
            Logger.getLogger(AppointmentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
//      Set the list as the items the tableview will display
        AppointmentTable.setItems(Appointments);
//        The all appointments radio button is selected by default
        allRadioButton.setSelected(true);
    }
}
