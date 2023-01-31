package DbAcessObjects;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class creates the Customer database methods.
 */
public class CustomerTable {
    /**
     * This class method generates a list of all the customers
     * @return a list of all the customers
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException, Exception{
//        create list
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
//        Create query string
        String sqlStatement="select * from Customers";
//        Make the query and get the result
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
//        Loop through results
        while(result.next()){
//            Add values to variables
            int customerId = result.getInt("Customer_ID");
            String customerName_ = result.getString("Customer_Name");
            String address = result.getString("Address");
            String postalCode = result.getString("Postal_Code");
            String phone = result.getString("Phone");
            Timestamp createDate = result.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar = createDate.toLocalDateTime();
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar = lastUpdate.toLocalDateTime();
            String lastUpdatedBy = result.getString("Last_Updated_By");
            int divisionId = result.getInt("Division_ID");
//            Create new customer object with the values
            Customer customerResult = new Customer(customerId, customerName_, address, postalCode, phone, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdatedBy, divisionId);
//            Add customer to the list
            allCustomers.add(customerResult);
        }
        return allCustomers;
    }

    /**
     * This class method adds a customer record to the database.
     * @param customerName the name of the customer
     * @param customerAddress the address of the customer
     * @param postalCode the postal code of the customer
     * @param customerPhone the phone number of the customer
     * @param createDate the creation date of the customer
     * @param createdBy the user creating the customer
     * @param lastUpdate the last updated time of the customer
     * @param lastUpdateBy the last user to update the customer
     * @param Division_ID the division ID of the customer
     */
    public static void addCustomerToDb(String customerName, String customerAddress, String postalCode, String customerPhone, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy, int Division_ID) {
        try {
//            Create insert query string
            String insertQueryString = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
//            Prepare the statement
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(insertQueryString);
//            Set the strings int the prepared statement
            preparedStatement.setString(1, customerName);
            preparedStatement.setString(2, customerAddress);
            preparedStatement.setString(3, postalCode);
            preparedStatement.setString(4, customerPhone);
            preparedStatement.setTimestamp(5, createDate);
            preparedStatement.setString(6, createdBy);
            preparedStatement.setTimestamp(7, lastUpdate);
            preparedStatement.setString(8, lastUpdateBy);
            preparedStatement.setInt(9, Division_ID);
//            Execute the statement
            preparedStatement.execute();
        }
//        Catch any exceptions
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    /**
     *  This class method modified a customer record in the database.
     * @param customerID the ID of the customer
     * @param customerName the name of the customer
     * @param customerAddress the address of the customer
     * @param postalCode the postal code of the customer
     * @param customerPhone the phone number of the customer
     * @param lastUpdate the last updated time of the customer
     * @param lastUpdateBy the last user to update the customer
     * @param Division_ID the division ID of the customer
     */
    public static void modifyCustomer(int customerID, String customerName, String customerAddress, String postalCode, String customerPhone, Timestamp lastUpdate, String lastUpdateBy, int Division_ID) {
        try {
//            Create update query string
            String updateQueryString = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
//            Create prepared statement
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(updateQueryString);
//            Set values in prepared statement
            preparedStatement.setString(1, customerName);
            preparedStatement.setString(2, customerAddress);
            preparedStatement.setString(3, postalCode);
            preparedStatement.setString(4, customerPhone);
            preparedStatement.setTimestamp(5, lastUpdate);
            preparedStatement.setString(6, lastUpdateBy);
            preparedStatement.setInt(7, Division_ID);
            preparedStatement.setInt(8, customerID);
//            Execute the statement
            preparedStatement.execute();
        }
//        Catch any exceptions
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * This class method is used to delete a customer record from the database given the customer ID.
     * @param customerID the ID of the customer
     */
    public static void deleteCustomer(int customerID) {
        try {
//            Generate query string
            String deleteQueryString = "DELETE FROM customers WHERE Customer_ID = ?";
//            Prepare the statement
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(deleteQueryString);
//            Set variables
            preparedStatement.setInt(1, customerID);
//            Execute the statement
            preparedStatement.execute();
        }
//        Check if there are any errors
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * This class method is used to retrieve a customer record from the database given the customer ID.
     * @param customerID the customer ID
     * @return the customer data
     */
    public static Customer getCustomerByCustomerID(int customerID) throws SQLException {
//        Generate query string
        String sqlStatement = "select * from customers where Customer_ID = " + customerID;
//        Make the query
        Query.makeQuery(sqlStatement);
//        Create new customer model variable
        Customer customerData;
//        Execute the query and get results
        ResultSet result = Query.getResult();
//        Loop through the results
        while (result.next()) {
//            Create variables and add values from query
            int customerId = result.getInt("Customer_ID");
            String customerName = result.getString("Customer_Name");
            String Address = result.getString("Address");
            String postalCode = result.getString("Postal_Code");
            String Phone = result.getString("Phone");
            Timestamp createDate=result.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar=createDate.toLocalDateTime();
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate=result.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar=lastUpdate.toLocalDateTime();
            String lastUpdatedBy = result.getString("Last_Updated_By");
            int divisionId = result.getInt("Division_ID");
//            Create new customer object
            customerData = new Customer(customerId, customerName, Address, postalCode, Phone, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdatedBy, divisionId);
//            return the object
            return customerData;
        }
        return null;
    }

    /**
     * This class method counts the number of customers with the given country ID.
     * @param Country_ID the country ID
     * @return the number of customers with the selected Country ID
     */
    public static int countCustomers(int Country_ID) throws SQLException {
//        Create select query string
        String sqlStatement = "SELECT COUNT(*) AS customerCountry FROM customers WHERE Division_ID IN (SELECT Division_ID FROM First_Level_Divisions WHERE Country_ID = " + Country_ID + ")";
//        Make the query
        Query.makeQuery(sqlStatement);
//        Initialize count
        int countCustomersResult = 0;
//        Get result
        ResultSet result = Query.getResult();
//        Loop through results
        while(result.next()) {
            countCustomersResult = result.getInt("customerCountry");

            return countCustomersResult;
        }
        return countCustomersResult;
    }
}
