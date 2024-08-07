package corp.database;

import corp.prefs.Prefs;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private static final Database INSTANCE = new Database();
    private static Connection connection;

    private Database() {
        try {
            Prefs prefs = new Prefs();
            String dbUrl = prefs.getString(Prefs.DB_JDBC_CONNECTION_URL);
            String dbUser = prefs.getString(Prefs.DB_JDBC_CONNECTION_USER);
            String dbPass = prefs.getString(Prefs.DB_JDBC_CONNECTION_PASSWORD);

            new DatabaseInitService().initDb(dbUrl, dbUser, dbPass);
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

}
