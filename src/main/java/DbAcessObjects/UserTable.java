package DbAcessObjects;
import helper.JDBC;
import models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates the User database methods.
 */
public class UserTable {
    /**
     * This class method is used to verify that a user record exists with the given username and password.
     * @param User_Name the username that needs to be validated
     * @param Password the password that needs to be validated
     * @return true if user is found matching username and password, false if no user is found
     */
    public static boolean validateLogIn(String User_Name, String Password) throws SQLException {
//        Prepare sql statements to select all users from the users table where the username and password matches
        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement("select * FROM Users WHERE User_Name = ? AND Password = ?")) {
//            Set prepare statement index and username string
            preparedStatement.setString(1, User_Name);
//            Set prepare statement index and password string
            preparedStatement.setString(2, Password);
//            Execute query
            ResultSet resultSet = preparedStatement.executeQuery();
//              return true if a record with a matching username and password exists
            if (resultSet.next()) {
                return true;
            }
        }
//        Check if there are exceptions
        catch (SQLException e) {
            e.printStackTrace();
        }
//        Return false if no user, no password, and no exception is found
        return false;
    }

    /**
     * This method is used to verify whether a specific username exists in the database table.
     * It executes a select query to select all users with a matching username
     * @param User_Name the username that needs to be validated
     * @return true if user is found, false if user is not found
     */
    public static boolean validateUserName(String User_Name) throws SQLException {
//        Prepare sql statements to select all users from the users table where the username matches
        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement("SELECT * FROM users WHERE User_Name = ?")) {
//            Set prepare statement index and username string
            preparedStatement.setString(1, User_Name);
//            Execute query
            ResultSet resultSet = preparedStatement.executeQuery();
//            return true if user exists
            if (resultSet.next()) {
                return true;
            }
//            Print exception
        }
//        Check if there are exceptions
        catch (SQLException e) {
            e.printStackTrace();
        }
//        Return false if no user and no exception is found
        return false;
    }

    /**
     * This method is used to verify whether a specific password exists in the database table.
     * It executes a select query to select all passwords that match
     * @param Password the password that needs to be validated
     * @return true if password is found, false if password is not found
     */
    public static boolean validatePassword(String Password) throws SQLException {
//        Prepare sql statements to select all passwords from the users table where the password matches
        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement("SELECT * FROM users WHERE Password = ?")) {
//            Set prepare statement index and password string
            preparedStatement.setString(1, Password);
//            Execute query
            ResultSet resultSet = preparedStatement.executeQuery();
//            return true if password exists
            if (resultSet.next()) {
                return true;
            }
        }
//        Check if there are exceptions
        catch (SQLException e) {
            e.printStackTrace();
        }
//        Return false if no password and no exception is found
        return false;
    }

    /**
     * This method is used to retrieve user's information from the database.
     * It creates a query string and executes it using the Query class to verify whether the username exists.
     * @param user_name the username used to create the User object
     * @return the User object if it exists; otherwise, it returns a null value
     */
    public static User getUser(String user_name) throws SQLException, Exception{
//        Create sql statement string
        String sqlStatement="select * FROM users WHERE User_Name  = '" + user_name+ "'";
//        Make a query statement
        Query.makeQuery(sqlStatement);
//        Create user object
        User userResult;
//        Get result
        ResultSet result=Query.getResult();
//        Loop through results
        while(result.next()){
//            Add data to variables
            int userid=result.getInt("User_ID");
            String userName_=result.getString("User_Name");
            String password=result.getString("Password");
            Timestamp createDate=result.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar=createDate.toLocalDateTime();
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate=result.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar=lastUpdate.toLocalDateTime();
            String lastUpdatedBy=result.getString("Last_Updated_By");

//            Create User object using info from query
            userResult= new User(userid, userName_, password,  createDateCalendar, createdBy, lastUpdateCalendar, lastUpdatedBy);
//            Return user object
            return userResult;
        }
        return null;
    }

    /**
     * This class method is used to get all the users in the table.
     * @return a list of users
     */
    public static ObservableList<User> getAllUsers() throws SQLException, Exception{
//        Create list
        ObservableList<User> allUsers=FXCollections.observableArrayList();
//        Create query string
        String sqlStatement="select * from Users";
//        Make the query
        Query.makeQuery(sqlStatement);
//        Get result from query
        ResultSet result=Query.getResult();
//        Loop through results
        while(result.next()){
//            Add data to variables
            int userid=result.getInt("User_ID");
            String userName_=result.getString("User_Name");
            String password=result.getString("Password");
            Timestamp createDate=result.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar=createDate.toLocalDateTime();
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate=result.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar=lastUpdate.toLocalDateTime();
            String lastUpdatedBy=result.getString("Last_Updated_By");
//            Create new object with the data above
            User userResult= new User(userid, userName_, password, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdatedBy);
//            Add it to the list
            allUsers.add(userResult);
        }
        return allUsers;
    }

    /**
     * This class method returns a user's information for the given the user ID.
     * @param userID the user ID
     * @return the user's information
     */
    public static User getUserByUserID(int userID) throws SQLException {
//        Make query string
        String sqlStatement = "select * from users where User_ID = " + userID;
//        Make query
        Query.makeQuery(sqlStatement);
//        Create new object
        User userData;
//        Get results
        ResultSet result = Query.getResult();
//        Loop through results
        while (result.next()) {
//            Add values to variables
            int userId = result.getInt("User_ID");
            String userName_ = result.getString("User_Name");
            String Password = result.getString("Password");
            Timestamp createDate = result.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar = createDate.toLocalDateTime();
            String Created_By = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar=lastUpdate.toLocalDateTime();
            String lastUpdatedBy = result.getString("Last_Updated_By");
//            Create new object
            userData = new User(userId, userName_, Password, createDateCalendar, Created_By, lastUpdateCalendar, lastUpdatedBy);
//            return object
            return userData;
        }
        return null;
    }

    /**
     * This class method returns a user's information for the given the username.
     * @param userName the username
     * @return the user's information
     */
    public static int getUserIDFromUserName(String userName) throws SQLException {
//        Initialize user Id variable
        int userID = 0;
//        Create select query string
        String sqlStatement = "select User_ID from users where User_Name = '" + userName+ "'";
//        Make a query statement
        Query.makeQuery(sqlStatement);
//        Get result
        ResultSet result = Query.getResult();
//        Loop through results and return the user ID
        while (result.next()) {
//            Add value to variable
            userID = result.getInt("User_ID");

            int userIdFromUsername = userID;
//            Return the user ID
            return userIdFromUsername;
        }
//        Return the user ID
        return userID;
    }
}
