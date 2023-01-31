package DbAcessObjects;
import java.sql.ResultSet;
import java.sql.Statement;
import static helper.JDBC.*;

/**
 * This class creates the query database methods.
 */
public class Query {
    /**
     * Variable used to hold the query result.
     * It holds the select query results
     */
    private static ResultSet result;

    /**
     * This method is used to create a query.
     * It creates a query (select, delete, insert, update) and executes it
     * @param sql_statement the String used to make the query
     */
    public static void makeQuery(String sql_statement){
//        Initialize query_string variable
        String query_string;
//        Initialize query statement
        Statement query_statement;
//        Grab class argument and assign in to the query_string variable
        query_string =sql_statement;
//        Try to run the query statement
        try{
//            Create statement
            query_statement =connection.createStatement();
            // check what type of query needs to be executed
//            A select query expects a result
            if(query_string.toLowerCase().startsWith("select"))
                result= query_statement.executeQuery(sql_statement);
//            A result query does not expect a result
            if(query_string.toLowerCase().startsWith("delete")||query_string.toLowerCase().startsWith("insert")||query_string.toLowerCase().startsWith("update"))
                query_statement.executeUpdate(sql_statement);
        }
//        Check if an exception is found
        catch(Exception exception){
            System.out.println("Error: " + exception.getMessage());
        }
    }

    /**
     * This method is used to return the query result.
     * It returns the result of a select query.
     * @return the select query result
     */
    public static ResultSet getResult(){
        return result;
    }
}
