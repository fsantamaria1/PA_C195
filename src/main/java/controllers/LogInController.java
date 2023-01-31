package controllers;

import DbAcessObjects.AppointmentTable;
import DbAcessObjects.UserTable;
import Interfaces.GeneralInterface;
import helper.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Appointment;
import models.User;
import santamaria.pa_c195.Main;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import java.util.TimeZone;

import java.io.*;
import java.net.URL;

/**
 * This is the class that acts as the Login Screen Controller.
 * This is the class that lets the customer enter their credentials to log in to the application.
 */
public class LogInController implements Initializable {
//    Creates resource bundle and references the one in the Main class
    static ResourceBundle resourceBundle = Main.myBundle;

//    Initializes the time zone variables
    TimeZone timeZone = TimeZone.getDefault();
    String timeZoneID = timeZone.getID();

//    Initialize Text Fields
    public TextField usernameTextField;
    public PasswordField passwordTextField;

//    Initialize labels
    public Label userNameLabel;
    public Label passwordLabel;
    public Label locationText;
    public Label locationLabel;

//    Initialize Buttons
    public Button logInButton;
    public Button exitButton;

//    Initializes the active user variable that all the classes will refer to when they need the active user's username.
    public static String activeUser;

    /**
     * This class method closes the program.
     * It closes the program when the user clicks the Main Menu button on the scene.
     * @param event the even necessary to run this: when the user clicks the Exit button on the scene.
     */
    @FXML void onActionExit(ActionEvent event) {
//        Display alert asking user if they want to close the program and grab response
        boolean result = AlertEvents.exit();
//        Runs if user clicks OK
        if (result) {
//            Closes the db connection
            JDBC.closeConnection();
//            Closes the program
            System.exit(0);
        }
    }

    /**
     * This lambda function indicates that the username was not provided and adds a timestamp.
     */
    public GeneralInterface messageNoUsernameProvided = timeStamp -> {
        return "No username provided at " + timeStamp;
    };

    /**
     * This class method grabs the username and password and validates them in the database.
     * It grabs the username and password from the respective fields and makes sure that they match in the database.
     *
     * @param event the even necessary to run this: when the user clicks the Login button on the scene.
     */
    @FXML
    void onActionLogIn(ActionEvent event) throws Exception, SQLException, IOException {
//        Grab username and password from the respective TextBoxes
        String userName_ = usernameTextField.getText();
        String Password = passwordTextField.getText();
//        Get user object
        User userObject = UserTable.getUser(userName_);
//        Create a new name for the activity
        String filename = "login_activity.txt", usernameStr;
//        Create new file object that will contain the user login activity and time stamps
        File file = new File(filename);
//        Create file writer
        FileWriter fileWriter = new FileWriter(file, true);
//        Create print writer
        PrintWriter outputFile = new PrintWriter(fileWriter);
//        Get current time
        LocalDateTime time = LocalDateTime.now();
//        Convert current to local datetime object
        ZonedDateTime localDateTime = time.atZone(ZoneId.systemDefault());
//        Convert the local datetime to UTC
        ZonedDateTime localDateTimeInUTC = localDateTime.withZoneSameInstant(ZoneId.of("Etc/UTC"));
//        Displays message if the username field is empty or blank and logs it
        if (userName_.isEmpty() || userName_.isBlank()) {
//            Adds the no user found message to the activity file
            outputFile.println(messageNoUsernameProvided.getMessage(localDateTimeInUTC.toString()));
//            Closes the file
            outputFile.close();
//            Show alert notifying user that the credentials could not be validated
            AlertEvents.displayAnAlert("Invalid Login");
        }
//        Displays message if the password field is empty or blank
        else if (Password.isEmpty() || Password.isBlank()) {
//             Username string will be added to the output file
            usernameStr = userName_;
//            Adds the no user found message to the activity file
            outputFile.println("User " + usernameStr + " failed login at " + localDateTimeInUTC);
//            Closes the file
            outputFile.close();
//            Display Alert notifying user that the credentials could not be validated
            AlertEvents.displayAnAlert("Invalid Login");
        }
//         Check if the username can be verified
        else if (!UserTable.validateUserName(userName_)) {
//             Username string will be added to the output file
            usernameStr = userName_;
//            Adds the no user found message to the activity file
            outputFile.println("User " + usernameStr + " failed login at " + localDateTimeInUTC);
//            Closes the file
            outputFile.close();
//            Display Alert notifying user that the credentials could not be validated
            AlertEvents.displayAnAlert("Invalid Login");
        }
//         Check if the password can be verified
        else if (!UserTable.validatePassword(Password)) {
//            Username string will be added to the output file
            usernameStr = userName_;
//            Adds the no user found message to the activity file
            outputFile.println("User " + usernameStr + " failed login at " + localDateTimeInUTC);
//            Closes the file
            outputFile.close();
//            Display Alert notifying user that the credentials could not be validated
            AlertEvents.displayAnAlert("Invalid Login");
        }
//        Check if the username AND password can be verified
        else if (UserTable.validateLogIn(userName_, Password)) {
//            Set active user variable to the verified user's username
            activeUser = userObject.getUserName();
//            Get user id form table
            int userID = UserTable.getUserIDFromUserName(activeUser);
//            Initialize LocalTime
            LocalDateTime nowLDT = LocalDateTime.now();
//            Add 15 minutes to local time
            LocalDateTime plus15LDT = nowLDT.plusMinutes(15);
//            Initialize scheduled appointments ObservableList
            ObservableList<Appointment> scheduledAppts = FXCollections.observableArrayList();
            ObservableList<Appointment> appointments = AppointmentTable.getAppointmentByUserID(userID);
//          Loop through all the appointments
            if (appointments != null) {
                for (Appointment appointment : appointments) {
//                    Check if the appointment starts before 15 minutes
                    if ((appointment.getStart().isAfter(nowLDT) || appointment.getStart().isEqual(nowLDT)) && (appointment.getStart().isBefore(plus15LDT) || appointment.getStart().isEqual(plus15LDT))) {
//                        Add appointment to list
                        scheduledAppts.add(appointment);
//                        If the list has more than 1 appointment, then notify the user
                        if (scheduledAppts.size() > 0) {
//                            Display alert notifying user that there is an appointment starting soon
                            AlertEvents.displayAnAlert("Appointment Starts Soon");
                        }
                    }
                }
//                If the list does not have any appointments, then notify the user
                if (scheduledAppts.size() < 1) {
                    AlertEvents.displayAnAlert("No Appointments");
                }
            }
//            Add log to activity file
            usernameStr = userName_;
            outputFile.println("User " + usernameStr + " successfully logged in at " + localDateTimeInUTC);
//            Close the file
            outputFile.close();
//            Load Main Menu Screen
            ScreenEvents.loadMainMenu(event);

        }
//        If the user fails to login, then log that the username failed to do so into the activity file
        else {
            usernameStr = userName_;
            outputFile.println("User " + usernameStr + " failed login at " + localDateTimeInUTC);
//            Close the file
            outputFile.close();
//            Display alert
            AlertEvents.displayAnAlert("Invalid Login");
        }
    }


    /**
     * This class method initializes the Login Screen.
     * It sets all the labels, buttons, and headers to either English or French and also initializes the user's location.
     * and populates the table view.
     * @param url The url for the resources.
     * @param resourceBundle The resource bundle that contains all the views and properties.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Set the location label text
        locationLabel.setText(timeZoneID);
//        Set buttons and labels to English or French
        userNameLabel.setText(LogInController.resourceBundle.getString("Username"));
        passwordLabel.setText(LogInController.resourceBundle.getString("Password"));
        locationText.setText(LogInController.resourceBundle.getString("Location"));
        logInButton.setText(LogInController.resourceBundle.getString("LoginButton"));
        exitButton.setText(LogInController.resourceBundle.getString("ExitButton"));
    }
}