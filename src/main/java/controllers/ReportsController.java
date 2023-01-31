package controllers;

import DbAcessObjects.ContactTable;
import DbAcessObjects.CountryTable;
import DbAcessObjects.CustomerTable;
import helper.ScreenEvents;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.*;
import santamaria.pa_c195.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the class that acts as the Reports Controller.
 * This is the class that controls the Reports View and lets the user create new reports.
 */
public class ReportsController implements Initializable {
    //    Creates resource bundle and references the one in the Main class
    static ResourceBundle resourceBundle = Main.myBundle;
    public Label monthLabel;
    public Label typeLabel;
    public Label totalLabel1;
    public Label contactLabel;
    public Label countryLabel;
    public Label totalLabel2;
    public Button mainMenuButton;
    public Label customersByCountryLabel;
    public Label typeAndMonthLabel;
    //    Create observable lists that will be used to hold important data
    ObservableList<models.Appointment> Appointment = FXCollections.observableArrayList();

//    List of months for the reports
    ObservableList<String> Months = FXCollections.observableList(Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));


//    Initialize combo boxes
    public ComboBox<String> monthsComboBox;
    public ComboBox<appointmentType> typeComboBox;
    public ComboBox<Contact> contactComboBox;
    public ComboBox<Country> countryComboBox;

//    Initialize labels
    public Label customersTotalLabel;
    public Label appointmentsTotalLabel;

//    Create table view object that will display all the reports
    public TableView<Appointment> appointmentTableView;

//    Create table column objects that will be part of the table view
    public TableColumn<Appointment, Integer> appointmentId;
    public TableColumn<Appointment, String> appmntTitle;
    public TableColumn<Appointment, String> appmntDesc;
    public TableColumn<Appointment, String> appmntLocation;
    public TableColumn<Appointment, Integer> appmntContact;
    public TableColumn<Appointment, String> appmntType;
    public TableColumn<Appointment, Calendar> appmntStartDateTime;
    public TableColumn<Appointment, Calendar> appmntEndDateTime;
    public TableColumn<Appointment, Integer> customerId;
    public TableColumn<Appointment, Integer> userId;

    /**
     * This class method populates the total customers label.
     * @param event the event necessary to run this: when the user selects a country.
     */
    @FXML void onActionCountryCombo(ActionEvent event) throws SQLException {
        Country selectedCountry = countryComboBox.getValue();
        int selectedCountryID = selectedCountry.getCountry_ID();
        customersTotalLabel.setText(String.valueOf(CustomerTable.countCustomers(selectedCountryID)));
    }

    /**
     * This class method loads the Main Screen.
     * @param event the event necessary to run this: when the user clicks the Main Menu button on the scene.
     */
    @FXML void onActionMainMenu(ActionEvent event) throws IOException {
        ScreenEvents.loadMainMenu(event);
    }

    /**
     * This class method populates the total appointments label
     * @param event the event necessary to run this: when the user selects a type.
     */
    @FXML void onActionTypeCombo(ActionEvent event) throws SQLException {
        appointmentType selectedType = typeComboBox.getValue();
        String selectedMonth = monthsComboBox.getValue();
        appointmentsTotalLabel.setText(String.valueOf(DbAcessObjects.AppointmentTable.countMonthType(selectedType, selectedMonth)));
    }

    /**
     * This class method populates the total appointments label
     * @param event the event necessary to run this: when the user selects a month.
     */
    @FXML void onActonMonthCombo(ActionEvent event) throws SQLException {
        appointmentType selectedType = typeComboBox.getValue();
        String selectedMonth = monthsComboBox.getValue();
        appointmentsTotalLabel.setText(String.valueOf(DbAcessObjects.AppointmentTable.countMonthType(selectedType, selectedMonth)));
    }

    /**
     * This class method populates the table with a specific customer appointments.
     * @param event the event necessary to run this: when the user selects a contact
     */
    @FXML void onActionContactCombo(ActionEvent event) {
        Appointment.clear();
        Contact selectedContact = contactComboBox.getValue();
        int selectedContactID = selectedContact.getContact_ID();

        try {
                Appointment.addAll(DbAcessObjects.AppointmentTable.getAppointmentsByContactID(selectedContactID));
        } catch (Exception ex) {
            Logger.getLogger(AppointmentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This class method initializes the Reports screen.
     * It sets all the labels, buttons, and headers to either English or French
     * and populates the table view.
     * @param url The url for the resources.
     * @param resourceBundle The resource bundle that contains all the views and properties.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Set labels and buttons to English or French
        monthLabel.setText(ReportsController.resourceBundle.getString("MonthLabel"));
        typeLabel.setText(ReportsController.resourceBundle.getString("TypeLabel"));
        totalLabel1.setText(ReportsController.resourceBundle.getString("TotalLabel"));
        contactLabel.setText(ReportsController.resourceBundle.getString("ContactLabel"));
        countryLabel.setText(ReportsController.resourceBundle.getString("CountryLabel"));
        totalLabel2.setText(ReportsController.resourceBundle.getString("TotalLabel"));
        mainMenuButton.setText(ReportsController.resourceBundle.getString("MainMenuButton"));
        customersByCountryLabel.setText(ReportsController.resourceBundle.getString("CustomersByCountryLabel"));
        typeAndMonthLabel.setText(ReportsController.resourceBundle.getString("TypeAndMonthLabel"));
//        Set headers to English or French
        appointmentId.setText(ReportsController.resourceBundle.getString("AppointmentIDLabel"));
        appmntTitle.setText(ReportsController.resourceBundle.getString("TitleLabel"));
        appmntDesc.setText(ReportsController.resourceBundle.getString("DescriptionLabel"));
        appmntLocation.setText(ReportsController.resourceBundle.getString("LocationLabel"));
        appmntContact.setText(ReportsController.resourceBundle.getString("ContactLabel"));
        appmntType.setText(ReportsController.resourceBundle.getString("TypeLabel"));
        appmntStartDateTime.setText(ReportsController.resourceBundle.getString("StartTimeLabel"));
        appmntEndDateTime.setText(ReportsController.resourceBundle.getString("EndTimeLabel"));
        customerId.setText(ReportsController.resourceBundle.getString("CustomerIDLabel"));
        userId.setText(ReportsController.resourceBundle.getString("UserIDLabel"));
//        Try to populate the combo boxes
        try {
            contactComboBox.setItems(ContactTable.getAllContacts());
            countryComboBox.setItems(CountryTable.getAllCountries());
            typeComboBox.setItems(DbAcessObjects.AppointmentTable.typeOfAppointment());
            monthsComboBox.setItems(Months);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Set cell values
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        appmntTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        appmntDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
        appmntLocation.setCellValueFactory(new PropertyValueFactory<>("Location"));
        appmntContact.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        appmntType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        appmntStartDateTime.setCellValueFactory(new PropertyValueFactory<>("Start"));
        appmntEndDateTime.setCellValueFactory(new PropertyValueFactory<>("End"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        userId.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
        appointmentTableView.setItems(Appointment);
    }
}