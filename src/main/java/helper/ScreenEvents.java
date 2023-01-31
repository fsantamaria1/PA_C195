package helper;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import santamaria.pa_c195.Main;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Class used to load scenes
 */

public class ScreenEvents {
//    Creates resource bundle and references the one in the Main class
    static ResourceBundle resourceBundle = Main.myBundle;
    /**
     *
     * @param actionEvent The action event that needs to be provided
     * @param viewName The name of the view that will be loaded
     * @param title the title for the window
     * @param v The horizontal measure as Integer (width)
     * @param v1 The vertical measure as Integer (height)
     */
    private static void loadScreen(ActionEvent actionEvent, String viewName, String title, Integer v, Integer v1) throws IOException {
//        Get view resource
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(viewName));
//        Grab the current stage
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
//        Create new scene
        Scene scene = new Scene(fxmlLoader.load(), v, v1);
//        Set the title
        stage.setTitle(title);
//        Set scene on stage
        stage.setScene(scene);
//        Center stage on the screen
        stage.centerOnScreen();
//        Show the stage
        stage.show();
    }

    /**
     * Class will load the MainScreen
     * @param actionEvent The action event that needs to be provided
     */
    public static void backToMainScreen(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 950, 500);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Class will load the Login Screen.
     * It loads the User Login View
     * @param actionEvent The action event that needs to be provided
     */
    public static void loadLoginScreen(ActionEvent actionEvent) throws  IOException {
        loadScreen(actionEvent, "/santamaria/pa_c195/LoginScreen.fxml", resourceBundle.getString("LoginScreen"), 400, 400);
    }
    /**
     * Class will load the Main Menu Screen.
     * It loads the Main Menu View
     * @param actionEvent The action event that needs to be provided
     */
    public static void loadMainMenu(ActionEvent actionEvent) throws IOException {
        loadScreen(actionEvent, "/santamaria/pa_c195/MainMenuScreen.fxml", resourceBundle.getString("MainMenuScreen"), 400, 400);
    }
    /**
     * Class will load the Appointments Screen.
     * It loads the Appointments Screen
     * @param actionEvent The action event that needs to be provided
     */
    public static void loadAppointmentsScreen(ActionEvent actionEvent) throws IOException {
        loadScreen(actionEvent, "/santamaria/pa_c195/AppointmentsScreen.fxml", resourceBundle.getString("AppointmentScreen"), 1200, 500);
    }
    /**
     * Class will load the Add Appointments Screen.
     * It loads the Add Appointments Screen
     * @param actionEvent The action event that needs to be provided
     */
    public static void loadAddAppointmentScreen(ActionEvent actionEvent) throws IOException{
        loadScreen(actionEvent, "/santamaria/pa_c195/AppointmentsAddScreen.fxml", resourceBundle.getString("AddAppointmentScreen"), 400, 500);
    }
    /**
     * Class will load the Modify Appointments Screen.
     * It loads the Modify Appointments Screen
     * @param actionEvent The action event that needs to be provided
     */
    public static void loadModifyAppointmentScreen(ActionEvent actionEvent) throws IOException{
        loadScreen(actionEvent, "/santamaria/pa_c195/AppointmentsModifyScreen.fxml", resourceBundle.getString("ModifyAppointmentScreen"), 400, 500);
    }
    /**
     * Class will load the Reports Screen.
     * It loads the Reports Screen
     * @param actionEvent The action event that needs to be provided
     */
    public static void loadReportsScreen(ActionEvent actionEvent) throws IOException{
        loadScreen(actionEvent, "/santamaria/pa_c195/ReportsScreen.fxml", resourceBundle.getString("ReportScreen"), 1200, 550);
    }
    /**
     * Class will load the Customers Screen.
     * It loads the Customers Screen
     * @param actionEvent The action event that needs to be provided
     */
    public static void loadCustomersScreen(ActionEvent actionEvent) throws IOException{
        loadScreen(actionEvent, "/santamaria/pa_c195/CustomersScreen.fxml", resourceBundle.getString("CustomerScreen"), 1200, 500);
    }
    /**
     * Class will load the Add Customers Screen.
     * It loads the Add Customers Screen
     * @param actionEvent The action event that needs to be provided
     */
    public static void loadAddCustomersScreen(ActionEvent actionEvent) throws IOException{
        loadScreen(actionEvent, "/santamaria/pa_c195/CustomersAddScreen.fxml", resourceBundle.getString("AddCustomerScreen"), 400, 400);
    }
    /**
     * Class will load the Modify Customers Screen.
     * It loads the Modify Customers Screen
     * @param actionEvent The action event that needs to be provided
     */
    public static void loadModifyCustomersScreen(ActionEvent actionEvent) throws IOException{
        loadScreen(actionEvent, "/santamaria/pa_c195/CustomersModifyScreen.fxml", resourceBundle.getString("ModifyCustomerScreen"), 400, 400);
    }

}
