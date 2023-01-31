package controllers;

import helper.ScreenEvents;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import santamaria.pa_c195.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the MainMenuController class.
 */
public class MainMenuController implements Initializable {
    static ResourceBundle mainResourceBundle = Main.myBundle;
    public Button customersButton;
    public Button appointmentsButton;
    public Button reportsButton;
    public Button logoutButton;

    /**
     * This is the method to load the Appointments screen when the user clicks the Appointments button.
     * @param event the user clicks the Appointments button
     * @throws IOException
     */
    @FXML void onActionAppointments(ActionEvent event) throws IOException {
//        Load Appointments Screen
        ScreenEvents.loadAppointmentsScreen(event);
    }

    /**
     * This is the method to load the Customers screen when the user clicks the Customers button.
     * @param event the user clicks the Customers button
     * @throws IOException
     */
    @FXML void onActionCustomers(ActionEvent event) throws IOException {
        ScreenEvents.loadCustomersScreen(event);
    }

    /**
     * This is the method to load the Reports screen when the user clicks the Reports button.
     * @param event The user clicks the Reports button
     * @throws IOException
     */
    @FXML void onActionReports(ActionEvent event) throws IOException {
        ScreenEvents.loadReportsScreen(event);
    }

    /**
     * This is the method to load the Login Screen when the user clicks the Logout button.
     * @param event The event that happens when the user clicks the logout button
     * @throws IOException
     */
    @FXML void onActionLogOut(ActionEvent event) throws IOException {
        ScreenEvents.loadLoginScreen(event);
    }

    /**
     * This is the method to initialize the MainMenu controller.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customersButton.setText(mainResourceBundle.getString("CustomerButton"));
        appointmentsButton.setText(mainResourceBundle.getString("AppointmentButton"));
        reportsButton.setText(mainResourceBundle.getString("ReportButton"));
        logoutButton.setText(mainResourceBundle.getString("LogoutButton"));
    }
}