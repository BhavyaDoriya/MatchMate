package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;
public class DatabaseConnector {
    static Dotenv dotenv=Dotenv.load();
    private static final String URL =dotenv.get("DB_URL");
    private static final String USERNAME =dotenv.get("DB_USERNAME");
    private static final String PASSWORD =dotenv.get("DB_PASSWORD");
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        return connection;
    }
}