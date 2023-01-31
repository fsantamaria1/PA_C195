package DbAcessObjects;

import helper.JDBC;
import models.Appointment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.appointmentType;

/**
 * This class creates the Appointment database methods.
 */
public class AppointmentTable {
    /**
     * This class method creates a list of appointments containing with the given user ID.
     * @param userID the userID that the appointments contain
     * @return a list of appointments
     */
    public static ObservableList<Appointment> getAppointmentByUserID(int userID) throws SQLException, Exception{
//        Create list
        ObservableList<Appointment> userIDAppointments=FXCollections.observableArrayList();
//        Create query string
        String sqlStatement="select * FROM Appointments WHERE User_ID  = '" + userID + "'";
//        Make the query
        Query.makeQuery(sqlStatement);
//        Make appointment object
        Appointment appointmentResult;
//        Get result from query
        ResultSet result=Query.getResult();
//        Loop through results
        while(result.next()){
//            Add data to variables
            int appointmentId = result.getInt("Appointment_ID");
            String Title = result.getString("Title");
            String Description = result.getString("Description");
            String Location = result.getString("Location");
            String Type = result.getString("Type");
            Timestamp Start = result.getTimestamp("Start");
            LocalDateTime startCalendar = Start.toLocalDateTime();
            Timestamp End = result.getTimestamp("End");
            LocalDateTime endCalendar = End.toLocalDateTime();
            Timestamp createDate = result.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar = createDate.toLocalDateTime();
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar = lastUpdate.toLocalDateTime();
            String lastUpdatedBy = result.getString("Last_Updated_By");
            int customerId = result.getInt("Customer_ID");
            int userId = result.getInt("User_ID");
            int contactId = result.getInt("Contact_ID");

//            Create new appointment object with the data above
            appointmentResult= new Appointment(appointmentId, Title, Description, Location, Type, startCalendar, endCalendar, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdatedBy, customerId, userId, contactId);
//            Add appointment to list
            userIDAppointments.add(appointmentResult);
        }
        return userIDAppointments;
    }

    /**
     * This class method creates a list of all the appointments in the table.
     * @return a list of all the appointments.
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException, Exception{
//        Initialize  list
        ObservableList<Appointment> allAppointments=FXCollections.observableArrayList();
//        Create select query string
        String sqlStatement="select * from Appointments";
//        Make the query
        Query.makeQuery(sqlStatement);
//        Execute the query and get results
        ResultSet result=Query.getResult();
//        Loop through the results
        while(result.next()){
//            Create variables and add values from query
            int appointmentId = result.getInt("Appointment_ID");
            String Title = result.getString("Title");
            String Description = result.getString("Description");
            String Location = result.getString("Location");
            String Type = result.getString("Type");
            Timestamp Start = result.getTimestamp("Start");
            LocalDateTime StartCalendar = Start.toLocalDateTime();
            Timestamp End = result.getTimestamp("End");
            LocalDateTime EndCalendar = End.toLocalDateTime();
            Timestamp createDate = result.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar = createDate.toLocalDateTime();
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar = lastUpdate.toLocalDateTime();
            String lastUpdatedBy = result.getString("Last_Updated_By");
            int customerId = result.getInt("Customer_ID");
            int userId = result.getInt("User_ID");
            int contactId = result.getInt("Contact_ID");
//            Create appointment object
            Appointment appointmentResult= new Appointment(appointmentId, Title, Description, Location, Type, StartCalendar, EndCalendar, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdatedBy, customerId, userId, contactId);
//            Add appointment to the observable list
            allAppointments.add(appointmentResult);
        }
//        Return observable list
        return allAppointments;
    }

    /**
     * This class method adds an appointment record to the database.
     * @param appointmentTitle the title of the appointment
     * @param appointmentDescription the description of the appointment
     * @param appointmentLocation the location of the appointment
     * @param appointmentType the type of the appointment
     * @param appointmentStart the start of the appointment
     * @param appointmentEnd the end of the appointment
     * @param createDate the creation date of the appointment
     * @param createdBy the user creating the appointment
     * @param lastUpdate the last updated time of the appointment
     * @param lastUpdateBy the last user to update the appointment
     * @param customerID the customer ID of the appointment
     * @param userID the user ID of the appointment
     * @param contactID the contact ID of the appointment
     */
    public static void addAppointmentToDb(String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy, int customerID, int userID, int contactID) {
        try{
//            Create insert query string
            String insertQueryString = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//            Prepare the statement
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(insertQueryString);
//          Set the strings int the prepared statement
            preparedStatement.setString(1, appointmentTitle);
            preparedStatement.setString(2, appointmentDescription);
            preparedStatement.setString(3, appointmentLocation);
            preparedStatement.setString(4, appointmentType);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(appointmentStart));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(appointmentEnd));
            preparedStatement. setTimestamp(7, createDate);
            preparedStatement.setString(8, createdBy);
            preparedStatement.setTimestamp(9, lastUpdate);
            preparedStatement.setString(10, lastUpdateBy);
            preparedStatement.setInt(11, customerID);
            preparedStatement.setInt(12, userID);
            preparedStatement.setInt(13, contactID);
//            Execute the statement
            preparedStatement.execute();
        }
//        Catch any exceptions
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    /**
     * This class method modified an appointment record in the database.
     * @param appointmentID the ID of the appointment
     * @param appointmentTitle the title of the appointment
     * @param appointmentDescription the description of the appointment
     * @param appointmentLocation the location of the appointment
     * @param appointmentType the type of the appointment
     * @param appointmentStart the start time of the appointment
     * @param appointmentEnd the end time of the appointment
     * @param lastUpdate the last updated time of the appointment
     * @param lastUpdateBy the last user to update the appointment
     * @param customerID the customer ID of the appointment
     * @param userID the user ID of the appointment
     * @param contactID the contact ID of the appointment
     */
    public static void modifyAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, Timestamp lastUpdate, String lastUpdateBy, int customerID, int userID, int contactID) {
        try{
//            Create update query string
            String updateQueryString = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
//            Create prepared statement
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(updateQueryString);
//            Set values in prepared statement
            preparedStatement.setString(1, appointmentTitle);
            preparedStatement.setString(2, appointmentDescription);
            preparedStatement.setString(3, appointmentLocation);
            preparedStatement. setString(4, appointmentType);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(appointmentStart));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(appointmentEnd));
            preparedStatement.setTimestamp(7, lastUpdate);
            preparedStatement.setString(8, lastUpdateBy);
            preparedStatement.setInt(9, customerID);
            preparedStatement.setInt(10, userID);
            preparedStatement.setInt(11, contactID);
            preparedStatement.setInt(12, appointmentID);
//            Execute the statement
            preparedStatement.execute();
        }
//        Catch any exceptions
        catch(SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * This class method is used to delete an appointment record from the database given the appointment ID.
     * @param appointmentID the ID of the appointment
     */
    public static void deleteAppointmentByID(int appointmentID) {
        try {
//            Generate query string
            String deleteQueryString = "DELETE FROM appointments WHERE Appointment_ID = ?";
//            Prepare the statement
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(deleteQueryString);
//            Set variables
            preparedStatement.setInt(1, appointmentID);
//            Execute the statement
            preparedStatement.execute();
        }
//        Check if there are any errors
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * This class method is used to delete an appointment record from the database given the appointment ID and customer ID.
     * @param customerID the ID of the customer being deleted
     * @param appointmentID the ID of the appointment to delete
     */
    public static void deleteAppointmentByCustomerID(int customerID, int appointmentID) {
        try {
//            Create delete query statement
            String deleteQueryString = "DELETE FROM appointments WHERE Customer_ID = ? AND Appointment_ID = ?";
//            Create prepared statement
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(deleteQueryString);
//            Set values in prepared statement
            preparedStatement.setInt(1, customerID);
            preparedStatement.setInt(2, appointmentID);
//            Execute
            preparedStatement.execute();
        }
//        Catch any exceptions
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * This class method generates a list of types of appointments
     * @return a list of appointment types
     */
    public static ObservableList<appointmentType> typeOfAppointment() throws SQLException {
//        Create list
        ObservableList<appointmentType> appointmentTypesList = FXCollections.observableArrayList();
//        Create select query statement
        String sqlStatement = "select distinct Type from appointments";
//        Make the query
        Query.makeQuery(sqlStatement);
//        Get the result
        ResultSet result=Query.getResult();
//        Loop through appointments
        while(result.next()){
            String Type=result.getString("Type");
//            Create new type object
            appointmentType appointmentTypeResult= new appointmentType(Type);
            appointmentTypesList.add(appointmentTypeResult);
        }
//        Return list
        return appointmentTypesList;
    }

    /**
     * This class method generates the amount of appointments with the given type in a specific month
     * @param selectedType the type of appointment
     * @param month the month
     * @return the number of appointments with both the selected type and the selected month
     */
    public static int countMonthType(appointmentType selectedType, String month) throws SQLException {
//        Make select query
        String sqlStatement = "SELECT COUNT(*) AS monthType FROM appointments WHERE Type  = '" + selectedType + "' AND MONTHNAME(Start) = '" + month + "'";
//        Make query
        Query.makeQuery(sqlStatement);
        int countMonthTypeResult = 0;
//        Get results
        ResultSet result = Query.getResult();
//        Loop through results
        while(result.next()) {
            countMonthTypeResult = result.getInt("monthType");

            return countMonthTypeResult;
        }
        return countMonthTypeResult;
    }

    /**
     * The Lambda filters appointments based on the given Contact ID.
     * @param selectedContactID the contact ID.
     * @return a list of appointments with the given contact ID.
     */
    public static ObservableList<Appointment> getAppointmentsByContactID(int selectedContactID) throws SQLException, Exception{
//        Create lists
        ObservableList<Appointment> allAppointments = getAllAppointments();
//        Filter appointments
        ObservableList<Appointment> contactList = allAppointments.filtered(appmnt -> {
            if (appmnt.getContact_ID() == selectedContactID){
                return true;
            }
            return false;
        });
//        Returns the list
        return contactList;
    }

    /**
     * This class method generate a list of appointments for the current month
     * @return a list of all the appointments for the current month
     */
    public static ObservableList<Appointment> getCurrentMonthAppointments() throws SQLException, Exception{
//        Initialize observable list
        ObservableList<Appointment> currentMonthAppointments=FXCollections.observableArrayList();
//        Create select query statement
        String sqlStatement="select * from Appointments where MONTH(Start) = MONTH(CURDATE())";
//        Make query
        Query.makeQuery(sqlStatement);
//        Execute query and return results
        ResultSet result=Query.getResult();
//        Loop through results
        while(result.next()){
//            Add values to variables to create an Appointment object
            int appointmentId = result.getInt("Appointment_ID");
            String Title = result.getString("Title");
            String Description = result.getString("Description");
            String Location = result.getString("Location");
            String Type = result.getString("Type");
            Timestamp Start = result.getTimestamp("Start");
            LocalDateTime startCalendar = Start.toLocalDateTime();
            Timestamp End = result.getTimestamp("End");
            LocalDateTime endCalendar = End.toLocalDateTime();
            Timestamp createDate = result.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar = createDate.toLocalDateTime();
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar = lastUpdate.toLocalDateTime();
            String lastUpdatedBy=result.getString("Last_Updated_By");
            int customerId = result.getInt("Customer_ID");
            int userId = result.getInt("User_ID");
            int contactId = result.getInt("Contact_ID");
//            Create Appointment object
            Appointment appointmentResult = new Appointment(appointmentId, Title, Description, Location, Type, startCalendar, endCalendar, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdatedBy, customerId, userId, contactId);
//            Add object to the list
            currentMonthAppointments.add(appointmentResult);
        }
//        Return list
        return currentMonthAppointments;
    }
//
    /**
     * This class method generate a list of appointments for the current week
     * @return a list of all the appointments for the current week
     */
    public static ObservableList<Appointment> getCurrentWeekAppointments() throws SQLException, Exception{
//        Initialize observable list of appointments
        ObservableList<Appointment> currentWeekAppointments=FXCollections.observableArrayList();
//        Create select query string
        String sqlStatement="select * from Appointments where WEEK(Start) = WEEK(CURDATE())";
//        Make the query
        Query.makeQuery(sqlStatement);
//        Execute and get query results
        ResultSet result=Query.getResult();
//        Loop through results
        while(result.next()){
//          Add values to variables
            int appointmentId = result.getInt("Appointment_ID");
            String Title = result.getString("Title");
            String Description = result.getString("Description");
            String Location = result.getString("Location");
            String Type = result.getString("Type");
            Timestamp Start = result.getTimestamp("Start");
            LocalDateTime startCalendar = Start.toLocalDateTime();
            Timestamp End = result.getTimestamp("End");
            LocalDateTime endCalendar = End.toLocalDateTime();
            Timestamp createDate = result.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar = createDate.toLocalDateTime();
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar = lastUpdate.toLocalDateTime();
            String lastUpdatedBy = result.getString("Last_Updated_By");
            int customerId = result.getInt("Customer_ID");
            int userId = result.getInt("User_ID");
            int contactId = result.getInt("Contact_ID");
//            Create new appointment object with the values
            Appointment appointmentResult= new Appointment(appointmentId, Title, Description, Location, Type, startCalendar, endCalendar, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdatedBy, customerId, userId, contactId);
//            Add it to the list
            currentWeekAppointments.add(appointmentResult);
        }
        return currentWeekAppointments;
    }

    /**
     * This class method counts the number of appointments with the given customer ID.
     * @param customerID the customer ID
     * @return the number of appointments containing the given Customer ID
     */
    public static int countCustomerAppointments(int customerID) throws SQLException {
//        Create select query string
        String sqlStatement = "SELECT COUNT(*) AS custAppt FROM appointments WHERE Customer_ID  = '" + customerID + "'";
//        Make the query
        Query.makeQuery(sqlStatement);
//        Initialize count
        int countCustApptResult = 0;
//        Get result
        ResultSet result = Query.getResult();
//        Loop through results
        while(result.next()) {
            countCustApptResult = result.getInt("custAppt");

            return countCustApptResult;
        }
        return countCustApptResult;
    }
}