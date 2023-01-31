package DbAcessObjects;


import models.Country;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates the Country database methods.
 */
public class CountryTable {
    /**
     * This class method generates a list of all the countries
     * @return a list of all the countries
     */
    public static ObservableList<Country> getAllCountries() throws SQLException, Exception{
//        create list
        ObservableList<Country> allCountries=FXCollections.observableArrayList();
//        Create query string
        String sqlStatement="select * from countries";
//        Make the query and get the result
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
//        Loop through results
        while(result.next()){
//            Add values to variables
            int Country_ID=result.getInt("Country_ID");
            String Country=result.getString("Country");
            Timestamp Create_Date=result.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar=Create_Date.toLocalDateTime();
            String Created_By=result.getString("Created_By");
            Timestamp Last_Update=result.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar=Last_Update.toLocalDateTime();
            String Last_Updated_By=result.getString("Last_Updated_By");
//            Create new country object with the values
            Country countryResult= new Country(Country_ID, Country, createDateCalendar, Created_By, lastUpdateCalendar, Last_Updated_By);
//            Add country to the list
            allCountries.add(countryResult);
        }
        return allCountries;
    }

    /**
     * This class method returns a country's information given the country ID
     * @param CountryID the country ID
     * @return the country's information
     */
    public static Country getCountryByCountryID(int CountryID) throws SQLException, Exception{
//        Make query string
        String sqlStatement="select * FROM countries WHERE Country_ID  = '" + CountryID + "'";
//        Make query
        Query.makeQuery(sqlStatement);
//        Create new country object
        Country countryResult;
//        Get results
        ResultSet result=Query.getResult();
//        Loop through results
        while(result.next()){
//            Add values to variables
            int Country_ID=result.getInt("Country_ID");
            String Country=result.getString("Country");
            Timestamp Create_Date=result.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar=Create_Date.toLocalDateTime();
            String Created_By=result.getString("Created_By");
            Timestamp Last_Update=result.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar=Last_Update.toLocalDateTime();
            String Last_Updated_By=result.getString("Last_Updated_By");
//            Create new country object
            countryResult= new Country(Country_ID, Country, createDateCalendar, Created_By, lastUpdateCalendar, Last_Updated_By);
//            return object
            return countryResult;
        }
        return null;
    }
}