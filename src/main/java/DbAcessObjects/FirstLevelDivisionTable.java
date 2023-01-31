package DbAcessObjects;


import models.FirstLevelDivision;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates the FirstLevelDivision database methods.
 */
public class FirstLevelDivisionTable {
    /**
     * This class method returns a division object for the given division name.
     * @param FirstLevelDivision the FirstLevelDivision
     * @return the division object with all the data
     */
    public static FirstLevelDivision getDivision(FirstLevelDivision FirstLevelDivision) throws SQLException, Exception {
//        Generate query string
        String sqlStatement = "select * FROM first_level_divisions WHERE Division  = '" + FirstLevelDivision + "'";
//        Make the query
        Query.makeQuery(sqlStatement);
//        Create new model variable
        FirstLevelDivision divisionResult;
//        Execute the query and get results
        ResultSet result = Query.getResult();
//        Loop through the results
        while (result.next()) {
//            Create variables and add values from query
            int divisionId = result.getInt("Division_ID");
            String Division = result.getString("Division");
            Timestamp createDate = result.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar = createDate.toLocalDateTime();
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar = lastUpdate.toLocalDateTime();
            String lastUpdatedBy = result.getString("Last_Updated_By");
            int countryId = result.getInt("COUNTRY_ID");
//            Create new type object
            divisionResult = new FirstLevelDivision(divisionId, Division, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdatedBy, countryId);
//            Return list
            return divisionResult;
        }
        return null;
    }

    /**
     * This class method generates a list of division for the given country ID
     * @param country_id the country ID
     * @return the list of divisions
     */
    public static ObservableList<FirstLevelDivision> getDivisionsForCountry(int country_id) throws SQLException {
//        create list
        ObservableList<FirstLevelDivision> div = FXCollections.observableArrayList();
//        Create query string
        String sqlStatement = "select * from first_level_divisions where COUNTRY_ID = " + country_id;
//        Make the query
        Query.makeQuery(sqlStatement);
//        Execute and get query results
        ResultSet result = Query.getResult();
//        Loop through results
        while (result.next()) {
//            Add values to variables
            int divisionId = result.getInt("Division_ID");
            String Division = result.getString("Division");
            Timestamp createDate = result.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar = createDate.toLocalDateTime();
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar = lastUpdate.toLocalDateTime();
            String lastUpdatedBy = result.getString("Last_Updated_By");
            int countryId = result.getInt("COUNTRY_ID");
//            Create new division object with the values
            FirstLevelDivision divisionResult = new FirstLevelDivision(divisionId, Division, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdatedBy, countryId);
//            Add it to the list
            div.add(divisionResult);
        }
        return div;
    }

    /**
     * This class method is used to retrieve a division record from the database given the division ID.
     * @param division_id the division ID
     * @return the division data
     */
    public static FirstLevelDivision getDivFromDivID(int division_id) throws SQLException {
//        Generate query string
        String sqlStatement = "select * from first_level_divisions where DIVISION_ID = " + division_id;
//        Make the query
        Query.makeQuery(sqlStatement);
//        Create new division model variable
        FirstLevelDivision divisionData;
//        Execute the query and get results
        ResultSet result = Query.getResult();
//        Loop through the results
        while (result.next()) {
//            Create variables and add values from query
            int divisionId = result.getInt("Division_ID");
            String Division = result.getString("Division");
            Timestamp createDate = result.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar = createDate.toLocalDateTime();
            String createdBy = result.getString("Created_By");
            Timestamp lastUpdate = result.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar = lastUpdate.toLocalDateTime();
            String lastUpdatedBy = result.getString("Last_Updated_By");
            int countryId = result.getInt("COUNTRY_ID");
//            Create new object
            divisionData = new FirstLevelDivision(divisionId, Division, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdatedBy, countryId);
//            return the object
            return divisionData;
        }
        return null;
    }
}