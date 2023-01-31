package DbAcessObjects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class creates the Contact database methods.
 */
public class ContactTable {
    /**
     * This class method generates a list of all the contacts
     * @return a list of all the contacts
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException, Exception {
//        create list
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
//        Create query string
        String sqlStatement = "select * from Contacts";
//        Make the query and get the result
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
//        Loop through results
        while (result.next()) {
//            Add values to variables
            int Contact_ID = result.getInt("Contact_ID");
            String Contact_Name = result.getString("Contact_Name");
            String Email = result.getString("Email");
//            Create new contact object with the values
            Contact contactResult = new Contact(Contact_ID, Contact_Name, Email);
//            Add contact to the list
            allContacts.add(contactResult);
        }
        return allContacts;
    }

    /**
     * This class method returns a contact's information given the contact ID
     * @param contactID the contact ID
     * @return the contact's information
     */
    public static Contact getContactByContactID(int contactID) throws SQLException {
//        Make query string
        String sqlStatement = "select * from contacts where Contact_ID = " + contactID;
//        Make query
        Query.makeQuery(sqlStatement);
//        Create new contact object
        Contact contactInfo;
//        Get results
        ResultSet result = Query.getResult();
//        Loop through results
        while (result.next()) {
//            Add values to variables
            int Contact_ID = result.getInt("Contact_ID");
            String Contact_Name = result.getString("Contact_Name");
            String Email = result.getString("Email");
//            Create new contact object
            contactInfo = new Contact(Contact_ID, Contact_Name, Email);
//            return object
            return contactInfo;
        }
        return null;
    }
}
