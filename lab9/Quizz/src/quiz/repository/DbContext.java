package quiz.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A singleton class responsible for managing the database connection.
 * It provides a single point of access to obtain database connections.
 */
public class DbContext {
    /**
     * The single instance of the DbContext class.
     */
    private static DbContext instance;

    /**
     * The JDBC URL used to connect to the database.
     */
    private final String url;

    /**
     * The username used to authenticate with the database.
     */
    private final String username;

    /**
     * The password used to authenticate with the database.
     */
    private final String password;

    /**
     * Private constructor to enforce the singleton pattern.
     * Initializes the database connection parameters and loads the JDBC driver.
     *
     * @throws Exception If the JDBC driver class cannot be found.
     */
    private DbContext() throws Exception {
        // Load the MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        url = "jdbc:mysql://localhost:3306/personal_library?useSSL=false&serverTimezone=UTC";
        username = "library_user";
        password = "strong_password";
    }

    /**
     * Returns the single instance of the DbContext class (singleton pattern).
     * Creates the instance if it doesn't already exist, ensuring thread-safe access.
     *
     * @return The singleton instance of DbContext.
     * @throws Exception If an error occurs during the creation of the instance.
     */
    public static synchronized DbContext getInstance() throws Exception {
        if (instance == null) {
            instance = new DbContext();
        }
        return instance;
    }

    /**
     * Establishes and returns a new database connection.
     * It uses the configured URL, username, and password.
     *
     * @return A Connection object representing the database connection.
     * @throws SQLException If a database access error occurs.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}