package controllers;

import DbAcessObjects.CountryTable;
import DbAcessObjects.CustomerTable;
import DbAcessObjects.FirstLevelDivisionTable;
import helper.AlertEvents;
import helper.ScreenEvents;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Country;
import models.Customer;
import models.FirstLevelDivision;
import santamaria.pa_c195.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * This is the class that acts as the Modify Customers Controller.
 * This is the class that controls the Modify Customers View and lets the user modify customer records.
 */
public class CustomersModifyController implements Initializable {
//    Creates resource bundle and references the one in the Main class
    static ResourceBundle resourceBundle = Main.myBundle;

//    Initialize combo boxes
    public ComboBox<Country> countryComboBox;
    public ComboBox<FirstLevelDivision> divisionComboBox;

//    Initialize labels
    public Label customerIDLabel;
    public Label customerNameLabel;
    public Label phoneNumberLabel;
    public Label addressLabel;
    public Label postalCodeLabel;
    public Label divisionLabel;
    public Label countryLabel;
    public Label customertoModifyIDLabel;

//    Initialize Buttons
    public Button saveButton;
    public Button cancelButton;

//    Initialize text fields
    public TextField customerNameTextField;
    public TextField customerPhoneTextField;
    public TextField customerAddressTextField;
    public TextField customerPostalCodeTextField;

//    Initialize variables that will hold crucial data
    private static Customer selectedCustomer;
    private static int selectedCountryID;
    private static Country selectedCountry;

    /**
     * This class method loads the Main Appointments Screen.
     * It loads the Main Appointments Screen when the user clicks the cancel button.
     * @param event The event required to run this: when the user clicks the cancel button.
     */
    @FXML void onActionCancel(ActionEvent event) throws IOException {
        ScreenEvents.loadCustomersScreen(event);
    }
    /**
     * This class method checks if there are any empty fields in the scene.
     * It checks if there are any empty text fields, combo boxes without a value selected, or date pickers without a value selected.
     * @return True if empty field found, False if no empty field found
     */
    public boolean checkForEmptyFields() {
        boolean emptyFieldFound = false;
//        Check if the customer name is empty
        if (customerNameTextField.getText().isEmpty()) {
//            Display alert asking user to enter a name for the customer
            AlertEvents.displayAnAlert("No Customer Name");
            return true;
        }
//        Check if the customer phone number is empty
        if (customerPhoneTextField.getText().isEmpty()) {
//            Display alert asking user to enter a phone number for the customer
            AlertEvents.displayAnAlert("No Phone Number");
            return true;
        }
//        Check if the customer address is empty
        if (customerAddressTextField.getText().isEmpty()) {
//            Display alert asking user to enter an address for the customer
            AlertEvents.displayAnAlert("No Address");
            return true;
        }
//        Check if the customer postal code is empty
        if (customerPostalCodeTextField.getText().isEmpty()) {
//            Display alert asking user to enter a postal code for the customer
            AlertEvents.displayAnAlert("No Postal Code");
            return true;
        }
//        Check if the user selected a country
        if (countryComboBox.getSelectionModel().getSelectedItem() == null) {
//                    Display alert asking user to select an end time
            AlertEvents.displayAnAlert("No Country");
            return true;
        }
//        Check if the user selected a division
        if (divisionComboBox.getSelectionModel().getSelectedItem() == null) {
//                    Display alert asking user to select an end time
            AlertEvents.displayAnAlert("No Division");
            return true;
        }
        return emptyFieldFound;
    }

    /**
     * This is the class method that saves a new customer.
     * It grabs all the information from the form and creates a new record in the database.
     * @param event The event required to run this: when the user clicks the save button.
     */
    @FXML void onActionSave(ActionEvent event) throws IOException {
        try {
//            Check if there are any empty fields
            boolean emptyFieldFound = checkForEmptyFields();
            if (!emptyFieldFound) {
//                Get values from fields
                int customerID = Integer.parseInt(customertoModifyIDLabel.getText());
                String customerName = customerNameTextField.getText();
                String customerAddress = customerAddressTextField.getText();
                String postalCode = customerPostalCodeTextField.getText();
                String customerPhone = customerPhoneTextField.getText();
                Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
                String lastUpdateBy = LogInController.activeUser;
                FirstLevelDivision fLDivision = divisionComboBox.getValue();
//                Get division ID
                int Division_ID = fLDivision.getDivision_ID();
//                Modify customer in the table
                CustomerTable.modifyCustomer(customerID, customerName, customerAddress, postalCode, customerPhone, lastUpdate, lastUpdateBy, Division_ID);
//                ScreenEvents.loadCustomersScreen(event);
                ScreenEvents.loadCustomersScreen(event);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * This class method populates the division combo box with only divisions from the selected country.
     * @param event The event required to run this: when the user selects a country.
     */
    @FXML void onActionCountryCombo(ActionEvent event) throws SQLException {
        divisionComboBox.setValue(null);
        Country C = countryComboBox.getValue();
        divisionComboBox.setItems(FirstLevelDivisionTable.getDivisionsForCountry(C.getCountry_ID()));
    }

    /**
     * This class method receives the selected customer object as argument from the main customer screen.
     * It receives a customer object as argument and sets the selectedCustomer class variable.
     * @param customer the selected appointment that needs to be modified
     */
    public static void receiveSelectedCustomer(Customer customer) {
        selectedCustomer = customer;
    }

    /**
     * This class method initializes the Modify Customer screen.
     * Sets all the labels and buttons to English or French and also initializes the combo boxes and populates them.
     * @param url The url for the resources.
     * @param resourceBundle The resource bundle that contains all the views and properties.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Set labels and buttons to English or French
        customerIDLabel.setText(CustomersModifyController.resourceBundle.getString("CustomerIDLabel"));
        customerNameLabel.setText(CustomersModifyController.resourceBundle.getString("CustomerNameLabel"));
        phoneNumberLabel.setText(CustomersModifyController.resourceBundle.getString("PhoneNumberLabel"));
        addressLabel.setText(CustomersModifyController.resourceBundle.getString("AddressLabel"));
        postalCodeLabel.setText(CustomersModifyController.resourceBundle.getString("PostalCodeLabel"));
        divisionLabel.setText(CustomersModifyController.resourceBundle.getString("DivisionLabel"));
        countryLabel.setText(CustomersModifyController.resourceBundle.getString("CountryLabel"));
        saveButton.setText(CustomersModifyController.resourceBundle.getString("SaveButton"));
        cancelButton.setText(CustomersModifyController.resourceBundle.getString("CancelButton"));

//        Populate all the combo boxes with their respective data
        try {
            divisionComboBox.setValue(FirstLevelDivisionTable.getDivFromDivID(selectedCustomer.getDivision_ID()));
            FirstLevelDivision selectedDivision = FirstLevelDivisionTable.getDivision(divisionComboBox.getValue());
            selectedCountryID = selectedDivision.getCOUNTRY_ID();
            selectedCountry = CountryTable.getCountryByCountryID(selectedCountryID);
            countryComboBox.setValue(selectedCountry);
            Country C = countryComboBox.getValue();
            divisionComboBox.setItems(FirstLevelDivisionTable.getDivisionsForCountry(C.getCountry_ID()));
            countryComboBox.setItems(CountryTable.getAllCountries());
        }
//        Catch any exceptions
        catch (Exception e) {
            e.printStackTrace();
        }
//        Populate fields with selected customer info
        customertoModifyIDLabel.setText(Integer.toString(selectedCustomer.getCustomerId()));
        customerNameTextField.setText(selectedCustomer.getCustomerName());
        customerAddressTextField.setText(selectedCustomer.getCustomerAddress());
        customerPhoneTextField.setText(selectedCustomer.getCustomerPhone());
        customerPostalCodeTextField.setText(selectedCustomer.getPostalCode());
    }
}