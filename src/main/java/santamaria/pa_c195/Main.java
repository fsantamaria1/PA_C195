package santamaria.pa_c195;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This Application can be used to schedule appointments, create new client, modify current clients, delete clients, etc.
 * It grabs data from the database, lets users login to check appointment information,
 * and allows users to add, modify, and delete clients.
 */
public class Main extends Application {
////    For testing only. This makes the app language French instead of English
//    static Locale locale_ = Locale.FRANCE;
//    public static ResourceBundle myBundle = ResourceBundle.getBundle("lang", locale_);

    /**
     * The bundle used for all the labels and text boxes.
     * It grabs the text for labels and text boxes depending on the locale of the user.
     */
    public static ResourceBundle myBundle = ResourceBundle.getBundle("lang");
    /**
     * The start class initializes the stage, grabs the resource (view), and loads it.
     * It grabs the resource based on the given name, and adds the title, size, etc.
     * @param stage the current stage where the scene will be shown
     * @throws IOException the exception that will be shown if the scene is not found or contains errors.
     */
    @Override
    public void start(Stage stage) throws IOException {
//        Get view resource
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginScreen.fxml"));
//        Create new scene
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
//        Set the title
        stage.setTitle(myBundle.getString("LoginScreen"));
//        Set scene on stage
        stage.setScene(scene);
//        Center the stage on the screen
        stage.centerOnScreen();
//        Show the stage
        stage.show();
    }


    /**
     * The main method of the Main Class.
     * It opens the database connection, launches the app, and closes the database connection when the app is closed.
     * @param args the arguments required by the method.
     */
    public static void main(String[] args) {
//        Open DB Connection
        JDBC.openConnection();
//        Launches the app
        launch();
//        Close DB Connection
        JDBC.closeConnection();
    }
}