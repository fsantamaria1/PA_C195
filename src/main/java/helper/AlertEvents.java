package helper;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import santamaria.pa_c195.Main;

import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class used to create alerts.
 * It displays alert to notify the user about important things.
 */
public class AlertEvents {
//    Creates resource bundle and references the one in the Main class
    static ResourceBundle resourceBundle = Main.myBundle;
    /**
     * Displays various alert messages.
     *
     * @param alertType Alert message selector.
     */
    public static void displayAnAlert(String alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertWarning  = new Alert(Alert.AlertType.WARNING);

        switch (alertType) {
            case "Invalid Login" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("InvalidLogin"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "No Appointments" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("NoAppointments"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "No Appointment Selected" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("NoAppointmentSelected"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "Appointment Conflicts" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("AppointmentConflicts"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "Appointment Deleted" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("AppointmentDeleted"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "No Date" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("SelectDateLabel"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "No Title" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("EnterTitleLabel"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "No Description" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("EnterDescriptionLabel"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "No Location" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("EnterLocationLabel"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "No Contact" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("SelectContactLabel"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "No Type" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("EnterTypeLabel"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "No Start Time" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("SelectStartTimeLabel"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "No End Time" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("SelectEndTimeLabel"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "No Customer" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("SelectCustomerLabel"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "No User" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("SelectUserLabel"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "Check Time" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("CheckTimeSelection"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "Too Early" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("TimeTooEarly"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "Too Late" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("TimeTooLate"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "Delete Associated Appointments" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("DeleteAssociatedAppointments"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "Customer Deleted" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("CustomerDeleted"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "Appointment Starts Soon" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("AppointmentStartsSoon"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "No Customer Name" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("EnterCustomerName"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "No Phone Number" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("EnterPhoneNumber"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "No Address" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("EnterAddress"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "No Postal Code" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("EnterPostalCode"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "No Country" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("SelectCountry"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
            case "No Division" -> {
                alertWarning.setTitle(resourceBundle.getString("WarningLabel"));
                alertWarning.setHeaderText(resourceBundle.getString("SelectDivision"));
                alertWarning.setContentText(resourceBundle.getString("OkToContinue"));
                alertWarning.showAndWait();
            }
        }
    }
    /**
     * Shows an Alert asking the users if they want to proceed with the deletion of the appointment
     * @returns Boolean determining whether the user clicked OK
     */
    public static boolean deleteAppointmentConfirmationWindow() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(resourceBundle.getString("WarningLabel"));
        alert.setHeaderText(resourceBundle.getString("DeleteAppointmentQuestion"));
        alert.setContentText(resourceBundle.getString("OkToConfirm"));
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }
    /**
     * Shows an Alert asking the users if they want to proceed with the deletion of the customer
     * @returns Boolean determining whether the user clicked OK
     */
    public static boolean deleteCustomerConfirmationWindow() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(resourceBundle.getString("WarningLabel"));
        alert.setHeaderText(resourceBundle.getString("DeleteCustomerQuestion"));
        alert.setContentText(resourceBundle.getString("OkToConfirm"));
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }
    /**
     * Shows an Alert asking the users if they want to exit
     * @returns Boolean determining whether the user clicked OK
     */
    public static boolean exit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(resourceBundle.getString("WarningLabel"));
        alert.setHeaderText(resourceBundle.getString("ExitQuestion"));
        alert.setContentText(resourceBundle.getString("OkToConfirm"));
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }
}
