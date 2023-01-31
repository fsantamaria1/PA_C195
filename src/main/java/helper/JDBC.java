package helper;


import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class is used to open and close the database connection.
 * It has two methods: openConnection and closeConnection. They open and close the database connection respectively.
 */
public abstract class JDBC {
//    Set necessary strings for the database connection
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
//    Set the local timezone
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    //    Initialize the driver interface variable
    private static final String driver = "com.mysql.cj.jdbc.Driver";
//    Initialize the username variable
    private static final String userName = "sqlUser";
//    Initialize the password variable
    private static String password = "Passw0rd!";
//    Initialize the connection interface
    public static Connection connection;

    /**
     * This class method opens the database connection.
     * It opens the database connection when all the settings and credentials are correct.
     */
    public static void openConnection()
    {
        try {
//            Locate the driver
            Class.forName(driver);
//            Generate a connection
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
        }
//        Catch any exceptions
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * This class method closes the database connection.
     * It closes the database connection when the program calls the method.
     */
    public static void closeConnection() {
        try {
            connection.close();
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
