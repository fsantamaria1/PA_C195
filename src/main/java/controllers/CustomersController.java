package controllers;


import DbAcessObjects.AppointmentTable;
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
import models.Customer;
import santamaria.pa_c195.Main;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the class that acts as the Customers Controller.
 * This is the class that controls the Customers View and lets the user add, delete, and modify customers.
 */
public class CustomersController implements Initializable {
//    Creates resource bundle and references the one in the Main class
    static ResourceBundle resourceBundle = Main.myBundle;

//    This observable list will be display on the table view
    ObservableList<models.Customer> CustomerList = FXCollections.observableArrayList();

//  Create table view object that will display all the customers
    public TableView<Customer> CustomerTable;
//    Create table column objects that will be part of the table view
    public TableColumn<Customer, Integer> Customer_ID;
    public TableColumn<Customer, String> Customer_Name;
    public TableColumn<Customer, String> Address;
    public TableColumn<Customer, String> Postal_Code;
    public TableColumn<Customer, String> Phone_Number;
    public TableColumn<Customer, Calendar> Create_Date;
    public TableColumn<Customer, String> Create_By;
    public TableColumn<Customer, Calendar> Last_Update;
    public TableColumn<Customer, String> Last_Updated_By;
    public TableColumn<Customer, Integer> Division_ID;

//    Create button objects referencing the ones on the scene
    public Button addButton;
    public Button modifyButton;
    public Button deleteButton;
    public Button mainMenuButton;

    /**
     * This class method displays the scene that allows the user to add a customer.
     * It loads the screen that asks the user to input all the information necessary for a new customer record.
     * @param event the event necessary to run this: when the user clicks the Add button on the scene.
     */
    @FXML void onActionAddCustomer(ActionEvent event) throws IOException {
        ScreenEvents.loadAddCustomersScreen(event);
    }

    /**
     * This class method allows the user to delete a customer.
     * It displays an alert asking the user to verify the deletion if they selected a customer;
     * otherwise, it displays and alert notifying the user that no appointments are selected.
     * @param event the even necessary to run this: when the user clicks the Delete button on the scene.
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws Exception {
//        Check if a customer has not been selected
        if (CustomerTable.getSelectionModel().getSelectedItem() == null) {
//            Display alert to inform the user that they did not select a customer
            AlertEvents.displayAnAlert("No Customer");
        }
        else {
//            Display dialog window asking user if they want to proceed and delete the customer
//            and their associated appointments
            boolean result = AlertEvents.deleteCustomerConfirmationWindow();
            if (result) {
//                Get all the appointments from the database
                ObservableList<Appointment> appointmentsList = AppointmentTable.getAllAppointments();
//                Loop through all the appointments
                for (Appointment appointment_ : appointmentsList) {
//                    Check if the appointment was scheduled for this customer
                    if (appointment_.getCustomer_ID() == CustomerTable.getSelectionModel().getSelectedItem().getCustomerId()) {
//                        Delete appointment
                            AppointmentTable.deleteAppointmentByCustomerID(CustomerTable.getSelectionModel().getSelectedItem().getCustomerId(), appointment_.getAppointment_ID());
                    }
                }
//                Check if there are any associated appointments left for this customer
                if (AppointmentTable.countCustomerAppointments(CustomerTable.getSelectionModel().getSelectedItem().getCustomerId()) > 0){
//                    Display alert asking user to delete all the associated appointments
                    AlertEvents.displayAnAlert("Delete Associated Appointments");
                }
                else {
//                    Delete the customer
                    DbAcessObjects.CustomerTable.deleteCustomer(CustomerTable.getSelectionModel().getSelectedItem().getCustomerId());

//                    Display alert notifying user that the customer has been deleted
                    AlertEvents.displayAnAlert("Customer Deleted");

//                    Update the table view
                    CustomerList = DbAcessObjects.CustomerTable.getAllCustomers();
                    CustomerTable.setItems(CustomerList);
                    CustomerTable.refresh();
                }
            }
        }
    }

    /**
     * This class method loads Main Menu screen.
     * It loads the Main Menu screen when the user clicks the Main Menu button on the scene.
     * @param event the even necessary to run this: when the user clicks the Main Menu button on the scene.
     */
    @FXML void onActionMainMenu(ActionEvent event) throws IOException {
        ScreenEvents.loadMainMenu(event);
    }

    /**
     * This class method loads the Modify Customer screen.
     * It loads the Modify Customer Screen when the user clicks the Modify button on the scene.
     * @param event the even necessary to run this: when the user clicks the Modify button on the scene.
     */
    @FXML void onActionModifyCustomer(ActionEvent event) throws IOException {
//        Check if a customer has been selected on the table
        if (CustomerTable.getSelectionModel().getSelectedItem() == null) {
//            Display alert to inform the user that they did not select a customer
            AlertEvents.displayAnAlert("No Customer");
        }
        else {
//            Send selected customer to the Modify Customer Screen
            CustomersModifyController.receiveSelectedCustomer(CustomerTable.getSelectionModel().getSelectedItem());

            ScreenEvents.loadModifyCustomersScreen(event);
        }
    }

    /**
     * This class method initializes the Customers screen.
     * It sets all the labels, buttons, and headers to either English or French
     * and populates the table view.
     * @param url The url for the resources.
     * @param resourceBundle The resource bundle that contains all the views and properties.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Set buttons and labels to English or French
        addButton.setText(CustomersController.resourceBundle.getString("AddButton"));
        modifyButton.setText(CustomersController.resourceBundle.getString("ModifyButton"));
        deleteButton.setText(CustomersController.resourceBundle.getString("DeleteButton"));
        mainMenuButton.setText(CustomersController.resourceBundle.getString("MainMenuButton"));
//        Set headers to English or French
        Customer_ID.setText(CustomersController.resourceBundle.getString("CustomerIDLabel"));
        Customer_Name.setText(CustomersController.resourceBundle.getString("CustomerNameLabel"));
        Address.setText(CustomersController.resourceBundle.getString("AddressLabel"));
        Postal_Code.setText(CustomersController.resourceBundle.getString("PostalCodeLabel"));
        Phone_Number.setText(CustomersController.resourceBundle.getString("PhoneNumberLabel"));
        Create_Date.setText(CustomersController.resourceBundle.getString("CreatedDateLabel"));
        Create_By.setText(CustomersController.resourceBundle.getString("CreatedByLabel"));
        Last_Update.setText(CustomersController.resourceBundle.getString("LastUpdateLabel"));
        Last_Updated_By.setText(CustomersController.resourceBundle.getString("LastUpdatedByLabel"));
        Division_ID.setText(CustomersController.resourceBundle.getString("DivisionIDLabel"));
//        Set cell values
        Customer_ID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        Customer_Name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        Address.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        Postal_Code.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        Phone_Number.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        Create_Date.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        Create_By.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        Last_Update.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        Last_Updated_By.setCellValueFactory(new PropertyValueFactory<>("lastUpdateBy"));
        Division_ID.setCellValueFactory(new PropertyValueFactory<>("Division_ID"));

//        Try to add all the customers to the customer list
        try {
            CustomerList.addAll(DbAcessObjects.CustomerTable.getAllCustomers());
        }
//        Catch any errors and log them
        catch (Exception ex) {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        Set the list as the items the tableview will display
        CustomerTable.setItems(CustomerList);
    }
}